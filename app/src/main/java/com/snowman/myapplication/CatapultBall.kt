package com.snowman.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlin.collections.ArrayList

class CatapultBall(
    context: Context,
    attr: AttributeSet?,
    defStyleAttr: Int
) : View(context, attr, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, -1)

    private var mPaint: Paint = Paint()

    private var balls: ArrayList<Ball> = ArrayList(15)

    companion object {
        private val colors: Array<Int> = Array(10) {
            when (it) {
                0 -> android.R.color.holo_purple
                1 -> android.R.color.holo_green_light
                2 -> R.color.colorAccent
                3 -> android.R.color.holo_blue_dark
                4 -> R.color.aaaa
                5 -> R.color.bbbb
                6 -> R.color.cccc
                7 -> R.color.dddd
                8 -> R.color.eeee
                9 -> R.color.ffff
                else -> android.R.color.holo_orange_dark
            }
        }
    }

    init {
        mPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_dark)
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (parent as ViewGroup).measuredWidth
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (parent as ViewGroup).measuredHeight
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    private fun initBall(ball: Ball) {
        ball.x = (left + 10).toFloat()
        ball.y = (top + 20).toFloat()
        ball.speedX = (5 + Math.random() * 11).toInt()
        ball.speedY = 0
        ball.color = colors[(0 + Math.random() * 10).toInt()]
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        checkStatus()
        doMotion()
        paintBall(canvas)
        invalidate()
    }

    private fun createBall(): Ball {
        return Ball().apply {
            initBall(this)
        }
    }

    private fun paintBall(canvas: Canvas?) {
        for (ball in balls) {
            mPaint.color = ContextCompat.getColor(context, ball.color)
            canvas?.drawCircle(ball.x, ball.y, 20f, mPaint)
        }
    }

    private var addIndex: Int = 0

    private fun checkStatus() {
        if (balls.size < 15) {
            if (addIndex > 5) {
                balls.add(createBall())
                addIndex = 0
            } else {
                addIndex++
            }
        }

        var ball: Ball

        for (i in 0 until balls.size) {
            ball = balls[i]
            if (ball.y > bottom) {
                ball.down = false
                ball.speedY = ball.speedY * 4 / 5
            }
            if (ball.x > right) {
                initBall(ball)
            }
        }
    }

    private fun doMotion() {
        for (ball in balls) {
            if (ball.speedY < 0) {
                ball.down = true
            }

            if (ball.down) {
                ball.speedY += ball.accelerationY
                ball.y += ball.speedY
            } else {
                ball.speedY -= ball.accelerationY
                ball.y -= ball.speedY
            }
            ball.x += ball.speedX
        }
    }

    class Ball(
        var speedX: Int = 11,
        var speedY: Int = 0,
        var accelerationX: Int = 0,
        var accelerationY: Int = 1,
        var down: Boolean = true,
        x: Float = 0f,
        y: Float = 0f,
        var color: Int = R.color.colorAccent
    ) : PointF(x, y)
}
