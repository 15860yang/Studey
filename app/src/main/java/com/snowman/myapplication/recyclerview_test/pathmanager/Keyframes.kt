package com.snowman.myapplication.recyclerview_test.pathmanager

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.annotation.FloatRange
import com.snowman.myapplication.recyclerview_test.log
import kotlin.math.atan2

class Keyframes(mPath: Path) {

    private var mX: Array<Float>
    private var mY: Array<Float>
    private var mAngle: Array<Float>
    private var mNumPoints: Int
    private var mPosTans: Array<PosTan?>
    private var mPathLength: Int

    private var mMaximumMultiple: Float = 2f
    private var mMinimumMultiple: Float = 0.2f

    init {
        val pathMeasure = PathMeasure(mPath, false)
        mPathLength = pathMeasure.length.toInt()

        log("length = $mPathLength")

        mNumPoints = mPathLength / 2
        log("mNumPoints = $mNumPoints, pathlength = $mPathLength")
        mX = Array(mNumPoints) { 0f }
        mY = Array(mNumPoints) { 0f }
        mAngle = Array(mNumPoints) { 0f }
        mPosTans = Array(mNumPoints) { null }

        var position = FloatArray(2)
        var tangent = FloatArray(2)
        var distance: Float

        /**
         * 这里是把这条线分成 线长度/2  个点  然后计算每个点的xy值和角度，之后的布局我们就用这个角度去旋转图形
         */
        for (i in 0 until mNumPoints) {
            distance = i * 2.0F
            pathMeasure.getPosTan(distance, position, tangent)
            mX[i] = position[0]
            mY[i] = position[1]

            /**
             * 这里是根据两个正切值算与X正半轴的角度
             * Math.atan2(tangent[1].toDouble(), tangent[0].toDouble()) * 180 / Math.PI
             */
            mAngle[i] = fixAngle((atan2(tangent[1].toDouble(), tangent[0].toDouble()) * 180 / Math.PI).toFloat())

            mPosTans[i] = PosTan(mX[i], mY[i], mAngle[i])
        }
    }

    private fun fixAngle(rotation: Float): Float {
        var angle = 90F
        var res = rotation

        if (rotation < 0) res = angle + rotation
        if (rotation > angle) res = rotation % angle

        return res
    }

    fun getValue(@FloatRange(from = 0.0, to = 1.0) fraction: Float): PosTan? {
        return if (fraction >= 1f || fraction < 0) {
            null
        } else {
            val index = (mNumPoints * fraction).toInt()
            mPosTans[index]
        }
    }

    fun setZoom(maximumMultiple: Float, minimumMultiple: Float) {
        mMaximumMultiple = maximumMultiple
        mMinimumMultiple = minimumMultiple
    }

    fun getGain(faction: Float): Float {
        var fac = faction
        if (fac > 0.5) fac = 1 - fac
        log("fac = $fac")
        return (((fac * 2) * (mMaximumMultiple - mMinimumMultiple) + mMinimumMultiple).toFloat())
    }


    /**
     * 路径长度直接返回pathlength即可
     */
    fun getPathLength() = mPathLength

    override fun toString(): String {
        for (i in 0 until mNumPoints) {
            val s = "point { mx = ${mX[i]} ,mY = ${mY[i]},mAngle = ${mAngle[i]} }"
            log(s)
        }
        return ""
    }


}