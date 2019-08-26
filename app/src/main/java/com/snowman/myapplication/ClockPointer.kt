package com.snowman.myapplication

import android.graphics.Canvas
import android.graphics.Paint

open class ClockPointer(var length: Int, var angle: Int = 0) {
    var endX: Float = 0f
    var endY: Float = 0f

    open fun drawSelf(canvas: Canvas?, startX: Float, startY: Float, mPaint: Paint) {
        canvas?.drawLine(startX, startY, endX, endY, mPaint)
    }

    open fun changeAngle(angle: Int) = angle + 10

}

