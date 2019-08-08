package com.snowman.myapplication.recyclerview_test.pathmanager

import android.graphics.PointF

class PosTan(
    x:Float,
    y:Float,
    var angle:Float = 0f
) : PointF(x,y){

    var fraction:Float = 0f
    var index:Int = -1

    fun getChildAngle() = angle - 90f
}
