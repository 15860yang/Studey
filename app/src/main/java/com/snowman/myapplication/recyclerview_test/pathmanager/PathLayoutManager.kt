package com.snowman.myapplication.recyclerview_test.pathmanager

import android.graphics.Path
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snowman.myapplication.recyclerview_test.log

class PathLayoutManager(
    path: Path,
    private val mItemOffset: Int = 0,//Item间距
    @RecyclerView.Orientation
    val mOrientation: Int = RecyclerView.VERTICAL
) : RecyclerView.LayoutManager() {

    private lateinit var mKeyframes: Keyframes //关键帧
    private var mItemCountInScreen: Int = 0 //屏幕中最多能同时显示的Item个数
    private var mFirstVisibleItemPos: Int = 0 //第一个可见的Item索引
    private var mOffsetX: Float = 0.toFloat()
    private var mOffsetY: Float = 0.toFloat() //x轴偏移量和y轴偏移量

    init {
        updatePath(path)
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    private fun updatePath(path: Path) {
        mKeyframes = Keyframes(path)
        if (mItemOffset == 0) throw IllegalStateException("itemOffset must be > 0 !!!")
        //计算出这个Path最多能同时出现几个Item
        mItemCountInScreen = mKeyframes.getPathLength() / mItemOffset + 1
        requestLayout()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        /**
         * 在发生位移时，就把所有的先回收再布局
         */
        detachAndScrapAttachedViews(recycler)
        //临时记录上一次的offset
        var lastOffset = mOffsetY
        //更新偏移量
        updateOffsetY(dy)
        //布局item
        relayoutChildren(recycler, state)
        //如果offset没有改变，那么就直接return 0，表示不消费本次滑动
        return if (lastOffset == mOffsetY) 0 else dy
    }

    private fun relayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {

        val needLayoutItems = ArrayList<PosTan>()
        //获取需要布局的items
        initNeedLayoutItems(needLayoutItems, state!!.itemCount)
        //判断一下状态
        if (needLayoutItems.isEmpty() || mKeyframes == null) {
            removeAndRecycleAllViews(recycler)
            return
        }
        //开始布局
        onLayout(recycler, needLayoutItems)
    }

    /**
     * 这个主要是处理滑动事件，当滑动时改变他的偏移量，然后重新布局
     */
    private fun updateOffsetY(offsetY: Int) {
        //更新offset
        mOffsetY += offsetY
        //路径总长度
        var pathLength = mKeyframes.getPathLength()

        log("mOffset = $mOffsetY")
        //item总长度
        var itemLength = getItemLength()
//        log("itemLength = 25250 - 2793")

        //item总长度相对于路径总长度多出来的部分
        val overflowLength = itemLength - pathLength

        if (overflowLength <= 0) {
            mOffsetY -= offsetY
        }

//        /**
//         * 这里是往上滑的限制
//         */
//        if (mOffsetY > itemLength - mItemOffset * 3) {
//            mOffsetY = itemLength.toFloat() - mItemOffset * 3
//        }
//        /**
//         * 这里是往下滑的限制
//         */
//        if (mOffsetY < (pathLength * -1) + mItemOffset * 2) {
//            mOffsetY = ((pathLength * -1) + mItemOffset * 2).toFloat()
//        }

        if (mOffsetY > getItemLength()) {
            mOffsetY = 0F
        }
        if (mOffsetY < -1 * mKeyframes.getPathLength()) {
            mOffsetY = (getItemLength() - mKeyframes.getPathLength()).toFloat()
        }
    }

    private fun getItemLength(): Int {
        return itemCount * mItemOffset + mItemOffset
    }

    override fun canScrollVertically() = mOrientation == RecyclerView.VERTICAL

    override fun canScrollHorizontally() = mOrientation == RecyclerView.HORIZONTAL

    /**
     * 这个方法主要是初始化布局内的item
     */
    private fun initNeedLayoutItems(result: ArrayList<PosTan>, itemCount: Int) {
        var currentDistance: Float
        //必须从第一个item开始，因为要拿到最小的，也就是最先的

        val offset: Int = getScrollOffset().toInt()
        mFirstVisibleItemPos = if (offset > mItemOffset) {
            offset / mItemOffset
        } else {
            0
        }

        log("mFirstVisibleItemPos + mItemCountInScreen  = ${mFirstVisibleItemPos + mItemCountInScreen}")
        log("itemCount = $itemCount")
        //结束的position
        var endIndex = if (mFirstVisibleItemPos + mItemCountInScreen > itemCount) {
            itemCount
        } else {
            mFirstVisibleItemPos + mItemCountInScreen
        }

        var fraction: Float
        var posTan: PosTan?
        for (i in mFirstVisibleItemPos until endIndex) {

            log("i = $i")
            //得到当前距离
            currentDistance = i * mItemOffset - getScrollOffset()
            //得到百分比
            fraction = currentDistance / mKeyframes.getPathLength()
            //根据百分比从Keyframes中取出对应的坐标和角度
            posTan = mKeyframes.getValue(fraction)
            if (posTan == null) continue
            posTan.index = i
            posTan.fraction = fraction
            result.add(posTan)
        }

        if (getItemLength() < mKeyframes.getPathLength()) {
            return
        }

        /**
         * 循环滚动代码
         */
        //向上
        if (offset > getItemLength() - mKeyframes.getPathLength() - mItemOffset) {
            val bottomLengthFilling = mKeyframes.getPathLength() - (getItemLength() - offset)
            val size = (bottomLengthFilling) / mItemOffset

            for (i in 0 until size + 2) {
                val fraction =
                    ((getItemLength() - offset) + (i - 1) * mItemOffset) / mKeyframes.getPathLength().toFloat()
                posTan = mKeyframes.getValue(fraction)
                if (posTan == null) continue
                posTan.index = i
                posTan.fraction = fraction
                result.add(posTan)
            }
        }
        //向下
        if (offset < 0) {
            var topLengthFilling = (offset * -1)

            for (i in getItemCount() - 1 downTo 0) {
                topLengthFilling -= mItemOffset
                if (topLengthFilling < 0) return
                val fraction = topLengthFilling / mKeyframes.getPathLength().toFloat()
                posTan = mKeyframes.getValue(fraction)
                if (posTan == null) continue
                posTan.index = i
                posTan.fraction = fraction
                result.add(posTan)
            }
        }

    }

    /**
     * 当requestLayout的时候调用，布局的总方法，所有逻辑都在这里体现
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }
        //暂时分离和回收全部有效的Item
        detachAndScrapAttachedViews(recycler)

        /**
         * 初始化需要显示的item
         */
        val needLayoutItems = ArrayList<PosTan>()
        initNeedLayoutItems(needLayoutItems, state.itemCount)
        //检查一下
        if (needLayoutItems.isEmpty() || mKeyframes == null) {
            removeAndRecycleAllViews(recycler)
            return
        }
        //开始布局
        onLayout(recycler, needLayoutItems)
    }

    /**
     * 真正的布局方法，负责把每个item放到指定位置上去
     */
    private fun onLayout(recycler: RecyclerView.Recycler, needLayoutItems: List<PosTan>) {
        var x: Int
        var y: Int
        var item: View
        for (tmp in needLayoutItems) {
            //根据position获取View
            log("item.index = ${tmp.index}")
            item = recycler.getViewForPosition(tmp.index)
            //添加进去，当然里面不一定每次都是调用RecyclerView的addView方法的，
            //如果是从缓存区里面找到的，只需调用attachView方法把它重新连接上就行了。
            addView(item)
            //测量item，当然，也不是每次都会调用measure方法进行测量的，
            //它里面会判断，如果已经测量过，而且当前尺寸又没有收到更新的通知，就不会重新测量。
            measureChild(item, 0, 0)

            //Path线条在View的中间
            x = (tmp.x - getDecoratedMeasuredWidth(item) / 2).toInt()
            y = (tmp.y - getDecoratedMeasuredHeight(item) / 2).toInt()

            //旋转item
            item.rotation = tmp.getChildAngle()

            item.scaleX = mKeyframes.getGain(tmp.fraction)
            item.scaleY = mKeyframes.getGain(tmp.fraction)

            //进行布局
            layoutDecorated(item, x, y, x + getDecoratedMeasuredWidth(item), y + getDecoratedMeasuredHeight(item))
        }
    }

    private fun getScrollOffset() = if (mOrientation == RecyclerView.VERTICAL) mOffsetY else mOffsetX

}