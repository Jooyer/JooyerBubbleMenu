package com.jooyerbubblemenu;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jooyerbubblemenu.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一个PopupWindow 实现类似QQ,支付宝等右上角弹框效果
 * Created by Jooyer on 2017/2/10
 */
public class TopRightMenu {
    private static final String TAG = "TopRightMenu";
    private static final int DEFAULT_AMEND = 200;
    private Context mContext;

    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;
    private View mParent;

    private TopRightMenuAdapter mTopRightMenuAdapter;
    private List<MenuItem> mItemList;

    /**
     * 弹窗默认高度
     */
    private static final int DEFAULT_HEIGHT = 480;
    /**
     * 弹窗默认高度
     */
    private static final int DEFAULT_WIDTH = 320;
    private int mPopupHeight = DEFAULT_HEIGHT;
    private int mPopupWidth = DEFAULT_WIDTH;

    /**
     * 默认显示图标
     */
    private boolean isShowIcon = true;

    /**
     * 默认显示背景 --> 背景变暗
     */
    private boolean isShowBackground = true;

    /**
     * 默认显示动画
     */
    private boolean isShowAnimationStyle = true;

    /**
     * 默认弹出或者关闭动画
     */
    private static final int DEFAULT_ANIM_STYLE = R.style.TopRightMenu_Anim;
    private int mAnimationStyle;

    /**
     * 默认的透明度值
     */
    private float mAlpha = 0.7f;


    public TopRightMenu(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mParent = LayoutInflater.from(mContext).inflate(R.layout.top_right_menu, null);
        mRecyclerView = (RecyclerView) mParent.findViewById(R.id.rv_top_right_menu);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mItemList = new ArrayList<>();
        mTopRightMenuAdapter = new TopRightMenuAdapter(mContext, mItemList, isShowIcon, this);
    }

    /**
     * 设置高度
     */
    public TopRightMenu setHeight(int height) {
        if (height > 0) {
            this.mPopupHeight = height;
        }
        return this;
    }

    /**
     * 设置宽度
     */
    public TopRightMenu setWidth(int width) {
        if (width > 0) {
            this.mPopupWidth = width;
        } else {
            throw new IllegalArgumentException("宽度不能为空,且必须大于0!");
        }
        return this;
    }

    /**
     * 设置是否显示图标
     */
    public TopRightMenu setShowIcon(boolean isShowIcon) {
        this.isShowIcon = isShowIcon;
        return this;
    }

    /**
     * 设置背景是否变暗
     */
    public TopRightMenu setShowBackground(boolean isShowBackground) {
        this.isShowBackground = isShowBackground;
        return this;
    }

    /**
     * 设置背景颜色变化动画
     *
     * @param from     --> 开始值
     * @param to       --> 结束值
     * @param duration --> 持续时间
     */
    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                ((Activity) mContext).getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }


    /**
     * 设置是否显示动画
     */
    public TopRightMenu setShowAnimationStyle(boolean isShowAnimationStyle) {
        this.isShowAnimationStyle = isShowAnimationStyle;
        return this;
    }

    /**
     * 设置动画
     */
    public TopRightMenu setAnimationStyle(int animationStyle) {
        this.mAnimationStyle = animationStyle;
        return this;
    }


    /**
     * 添加单个菜单
     */
    public TopRightMenu addMenuItem(MenuItem menuItem) {
        mItemList.add(menuItem);
        return this;
    }

    /**
     * 添加多个菜单
     */
    public TopRightMenu addMenuItems(List<MenuItem> list) {
        mItemList.addAll(list);
        return this;
    }

    public TopRightMenu setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mTopRightMenuAdapter.setOnTopRightMenuItemClickListener(listener);
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
        return this;
    }
    public TopRightMenu setArrowPosition(float value) {
        if (mRecyclerView != null && mRecyclerView instanceof JooyerBubbleRecyclerView) {
            ((JooyerBubbleRecyclerView) mRecyclerView).setArrowOffset(value);
        }
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor, int offsetX, int offsetY) {
        if (null == mPopupWindow) {
            mPopupWindow = getPopupWindow();
        }

        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, offsetX, offsetY);
            if (isShowBackground)
                setBackgroundAlpha(1f, mAlpha, 300);
        }

        return this;
    }


    public TopRightMenu show(View anchor, Rect frame, Point origin) {

        if (null == mPopupWindow) {
            mPopupWindow = getPopupWindow();
        }

        if (null == frame) frame = new Rect();
        if (null == origin) origin = new Point(-1, -1);

        int[] location = reviseFrameAndOrigin(anchor, frame, origin);
        int x = location[0];
        int y = location[1];

        LogUtils.i(TAG, "==location====X : " + x + "======Y : " + y);

        int width = anchor.getWidth();
        int height = anchor.getHeight();

        int contentHeight = mPopupWindow.getContentView().getMeasuredHeight();
        if (!mPopupWindow.isShowing()) {

            if (y + height + contentHeight < frame.bottom) {
                mPopupWindow.showAsDropDown(anchor, (int) (-DEFAULT_AMEND - (mPopupWidth - DEFAULT_WIDTH)), 0);
                LogUtils.i(TAG, "=====showAsDropDown=====" + (int) (-DEFAULT_AMEND - (mPopupWidth - DEFAULT_WIDTH)));
            }
            if (isShowBackground)
                setBackgroundAlpha(1f, mAlpha, 300);
        }
        return this;
    }


    /**
     * 确定 弹框的位置
     */
    private int[] reviseFrameAndOrigin(View anchor, Rect frame, Point origin) {
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        if (origin.x < 0 || origin.y < 0) {
            origin.set(anchor.getWidth() >> 1, anchor.getHeight() >> 1);
            LogUtils.i(TAG, "====1====" + (anchor.getWidth() >> 1) + "=====" + (anchor.getHeight() >> 1)
                    + "=======getWidth : " + anchor.getWidth() + "===== : " + anchor.getHeight());
        }
        if (frame.isEmpty() || !frame.contains(origin.x + location[0], origin.y + location[1])) {
            anchor.getWindowVisibleDisplayFrame(frame);
        }
        LogUtils.i(TAG, "====2====" + (origin.x + location[0]) + "=====" + (origin.y + location[1]));
        return location;
    }


    private PopupWindow getPopupWindow() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(mParent);
        mPopupWindow.setWidth(mPopupWidth);
        mPopupWindow.setHeight(mPopupHeight);
        if (isShowAnimationStyle)
            mPopupWindow.setAnimationStyle(mAnimationStyle <= 0 ? DEFAULT_ANIM_STYLE : mAnimationStyle);

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isShowBackground)
                    setBackgroundAlpha(mAlpha, 1f, 300);
            }
        });

        mTopRightMenuAdapter.setItemData(mItemList);
        mTopRightMenuAdapter.setShowIcon(isShowIcon);
        mRecyclerView.setAdapter(mTopRightMenuAdapter);
        return mPopupWindow;
    }


    public void dismiss() {
        if (null != mPopupWindow && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }


}
