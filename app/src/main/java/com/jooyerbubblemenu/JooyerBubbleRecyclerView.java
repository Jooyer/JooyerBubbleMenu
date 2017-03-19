package com.jooyerbubblemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Jooyer on 2017/3/19
 */

public class JooyerBubbleRecyclerView extends RecyclerView{

    private JooyerBubbleDrawable mBubbleDrawable;
    private float mArrowWidth;
    private float mArrowHeight;
    private float mRadius;
    private float mArrowOffset;
    private int mBubbleColor;
    private JooyerBubbleDrawable.ArrowDirection mArrowDirection;
    private boolean mArrowCenter;

    public JooyerBubbleRecyclerView(Context context) {
        this(context,null);
    }

    public JooyerBubbleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JooyerBubbleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(R.styleable.JooyerBubbleRecyclerView);
        mArrowWidth = array.getDimension(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_width,
                JooyerBubbleDrawable.Builder.DEFAULT_ARROW_WIDTH);
        mArrowHeight = array.getDimension(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_height,
                JooyerBubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
        mRadius = array.getDimension(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_radius,
                JooyerBubbleDrawable.Builder.DEFAULT_RADIUS);
        int location = array.getInt(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_direction, 0);
        mArrowDirection = JooyerBubbleDrawable.ArrowDirection.mapIntToValue(location);
        mArrowOffset = array.getDimension(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_offset,
                JooyerBubbleDrawable.Builder.DEFAULT_ARROW_OFFSET);
        mBubbleColor = array.getColor(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_color,
                JooyerBubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
        mArrowCenter = array.getBoolean(R.styleable.JooyerBubbleRecyclerView_jooyer_bubble_arrow_center,false);
        array.recycle();
        setPadding();
    }

    private void setPadding() {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        switch (mArrowDirection){
            case LEFT:
                left +=mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case BOTTOM:
                bottom +=mArrowHeight;
                break;
        }
        setPadding(left,top,right,bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w >0 || h > 0)
            setUp(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setUp(getWidth(),getHeight());
    }

    @Override
    public void onDraw(Canvas c) {
        if (null != mBubbleDrawable){
            mBubbleDrawable.draw(c);
        }
        super.onDraw(c);
    }


    private void setUp(int width,int height){
        setUp(0,0,width,height);
    }

    public void setUp(int left,int top,int right,int bottom){
        mBubbleDrawable = new JooyerBubbleDrawable.Builder()
                .rect(new RectF(left,top,right,bottom))
                .arrowWidth(mArrowWidth)
                .arrowHeight(mArrowHeight)
                .arrowOffset(mArrowOffset)
                .arrowCenter(mArrowCenter)
                .arrowDirection(mArrowDirection)
                .radius(mRadius)
                .bubbleColor(mBubbleColor)
                .build();
    }

    public void setArrowOffset(float offset){
        mArrowOffset = offset;
    }


}
