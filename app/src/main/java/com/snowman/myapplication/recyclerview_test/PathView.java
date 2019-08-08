package com.snowman.myapplication.recyclerview_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;

public class PathView extends View {

    private Path path;
    private Paint paint;
    private Context mContext;
    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        paint = new Paint();
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(((ViewGroup)getParent()).getMeasuredWidth(),((ViewGroup)getParent()).getMeasuredHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(ContextCompat.getColor(mContext,android.R.color.holo_red_dark));
        canvas.drawPath(path,paint);
    }
}
