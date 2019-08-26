package com.snowman.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import kotlin.math.cos
import kotlin.math.sin

class ClockView(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = -1) :
    View(context, attr, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attr: AttributeSet) : this(context, attr, -1)

    private val mPaint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 5f
    }
    private var mRadius = -1f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mAngle = 300

    private var mNowX = 0f
    private var mNowY = 0f
    private val mDecimalFormat = DecimalFormat("#.00")

    init {
        mPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        object :Thread(){
            override fun run() {
                while (true){
                    sleep(50)
                    mAngle += 10
                    mAngle %= 360
                    postInvalidate()
                }
            }
        }.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (parent as ViewGroup).measuredWidth
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (parent as ViewGroup).measuredWidth
        }
        mRadius = if (widthSize > heightSize) heightSize / 2f else widthSize / 2f
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        getCenterCoordinates()
        updateNewCoordinates()
        drawClock(canvas)
    }

    private fun drawClock(canvas: Canvas?) {
        //绘制圆心
        canvas?.drawCircle(mCenterX, mCenterY, 3f, mPaint)
        mPaint.style = Paint.Style.STROKE
        canvas?.drawCircle(mCenterX, mCenterY, mRadius - 5, mPaint)

//        //画线
        canvas?.drawLine(mCenterX, mCenterY, mNowX, mNowY, mPaint)
    }

    var i = 1
    private fun updateNewCoordinates() {
        var offsetX = mRadius * mDecimalFormat.format(sin(Math.toRadians(mAngle % 180.0))).toFloat()
        var offsetY = mRadius * mDecimalFormat.format(cos(Math.toRadians(mAngle % 180.0))).toFloat()

        log("offsetX = $offsetX , offsetY = $offsetY")

        i = if (mAngle / 180 == 1) -1 else 1

        mNowX = mCenterX + offsetX * i
        mNowY = mCenterY + (offsetY * -1) * i

        log("mNowX = $mNowX , mNowY = $mNowY")
    }

    private fun getCenterCoordinates() {
        mCenterX = (right - left) / 2f
        mCenterY = (bottom - top) / 2f

        mNowX = mCenterX
        mNowY = 0f

        log("mCenterX = $mCenterX , mCenterY = $mCenterY")
    }
}