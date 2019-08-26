package com.snowman.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import java.lang.IllegalArgumentException
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

    var mUpdateInterval = 1000L
        set(value) {
            field = if (value < 16) {
                throw IllegalArgumentException("非法的时间间隔，不能小于Android手机刷新屏幕间隔")
            } else value
        }

    private var pointerList = ArrayList<ClockPointer>(3)

    private var mNowX = 0f
    private var mNowY = 0f
    private val mDecimalFormat = DecimalFormat("#.00")

    init {
        mPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        object : Thread() {
            override fun run() {
                while (true) {
                    sleep(mUpdateInterval)
                    for (pointer in pointerList) {
                        pointer.angle = pointer.changeAngle(pointer.angle)
                        pointer.angle %= 360
                    }
                    postInvalidate()
                }
            }
        }.start()
        addPointer(object : ClockPointer(200) {
            override fun changeAngle(angle: Int) = angle + 20
        })
        addPointer(object : ClockPointer(100) {
            var index = 1
            override fun changeAngle(angle: Int): Int {
                return if (index > 10) {
                    index = 1
                    angle + 10
                } else {
                    index++
                    angle
                }
            }
        })
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
        updateNewCoordinates(pointerList)
        drawClock(canvas)
    }

    fun addPointer(pointer: ClockPointer) {
        pointerList.add(pointer)
    }

    private fun drawClock(canvas: Canvas?) {
        //绘制圆心和外面的圆
        canvas?.drawCircle(mCenterX, mCenterY, 3f, mPaint)
        mPaint.style = Paint.Style.STROKE
        canvas?.drawCircle(mCenterX, mCenterY, mRadius - 5, mPaint)
        //画指针
        for (pointer in pointerList) {
            pointer.drawSelf(canvas, mCenterX, mCenterY, mPaint)
        }
    }

    var i = 1
    private fun updateNewCoordinates(pointerList: ArrayList<ClockPointer>) {
        for (pointer in pointerList) {
            log("length = ${pointer.length}")
            var offsetX =
                pointer.length * mDecimalFormat.format(sin(Math.toRadians(pointer.angle % 180.0))).toFloat()
            var offsetY =
                pointer.length * mDecimalFormat.format(cos(Math.toRadians(pointer.angle % 180.0))).toFloat()
            log("offsetX = $offsetX , offsetY = $offsetY")
            i = if (pointer.angle / 180 == 1) -1 else 1
            pointer.endX = mCenterX + offsetX * i
            pointer.endY = mCenterY + (offsetY * -1) * i
            log("pointer.endX = ${pointer.endX} , pointer.endY = ${pointer.endY}")
        }
    }

    private fun getCenterCoordinates() {
        mCenterX = (right - left) / 2f
        mCenterY = (bottom - top) / 2f
        mNowX = mCenterX
        mNowY = 0f
        log("mCenterX = $mCenterX , mCenterY = $mCenterY")
    }
}
