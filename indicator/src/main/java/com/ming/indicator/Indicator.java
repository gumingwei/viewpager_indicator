package com.ming.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mingwei on 2015/12/2.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Indicator extends LinearLayout implements OnClickListener, OnPageChangeListener {
    /**
     * 绘制时用的画笔
     */
    private Paint mPaint;
    /**
     * 默认颜色
     */
    private int mCursorColor;
    /**
     * 滚动的指示器的矩形范围
     */
    private Rect mRect = new Rect();
    /**
     * 滚动游标的绘制范围
     */
    private int mL, mR, mT, mB;
    /**
     * 最多可见的游标数
     */
    private int mVisiableTabCount = 4;
    /**
     * 游标高度
     */
    private int mCursorHeight = 6;
    /**
     * tab的宽度
     */
    private int mTabWidth;
    /**
     * 选中和非选中状态的标签文字颜色
     */
    private int mTabColorNormal;
    private int mTabColorLight;
    /**
     * 水平滚动的距离
     */
    private float mTranslationX;
    /**
     * 需要监听ViewPager动作来跟新游标
     */
    private ViewPager mViewPager;

    public Indicator(Context context) {
        this(context, null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(mCursorColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        mTabColorNormal = array.getColor(R.styleable.Indicator_tab_color_normal, Color.BLACK);
        mTabColorLight = array.getColor(R.styleable.Indicator_tab_color_light, Color.WHITE);
        mCursorColor = array.getColor(R.styleable.Indicator_cursor_color, Color.BLACK);
        mCursorHeight = array.getDimensionPixelSize(R.styleable.Indicator_cursor_height, mCursorHeight);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int count = getChildCount();
        if (count == 0) {
            return;
        }
        mTabWidth = getWidth() / mVisiableTabCount;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = 0;
            params.width = mTabWidth;
            params.height = getHeight() - mCursorHeight;
            view.setLayoutParams(params);
        }
        mL = 0;
        mT = getHeight() - mCursorHeight;
        mR = mTabWidth;
        mB = getHeight();
        mRect = new Rect(mL, mT, mR, mB);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, 0);
        canvas.drawRect(mRect, mPaint);
        canvas.restore();

    }

    private void scroll(int position, float offset) {
        mTranslationX = getWidth() / mVisiableTabCount * (position + offset);
        /**
         * 当tab数大于可见数目的时候，整个容器滚动
         */
        if (getChildCount() > mVisiableTabCount && offset > 0 && (position >= mVisiableTabCount - 2)) {
            if (mVisiableTabCount != 1) {
                if (position != getChildCount() - 2) {
                    scrollTo((position - (mVisiableTabCount - 2)) * mTabWidth + (int) (offset * mTabWidth), 0);
                }
            } else {
                scrollTo(position * mTabWidth + (int) (offset * mTabWidth), 0);
            }
        }
        invalidate();

    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
    }

    public void setTabs(String[] tabs) {
        removeAllViews();
        for (String t : tabs) {
            createChild(t);
        }
        TextView view = (TextView) getChildAt(0);
        view.setTextColor(mTabColorLight);
    }

    public void setTabs(List<String> tabs) {
        removeAllViews();
        for (String t : tabs) {
            createChild(t);
        }
        TextView view = (TextView) getChildAt(0);
        view.setTextColor(mTabColorLight);
    }

    private void setTabLight(int arg0) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) getChildAt(i);
            if (i == arg0) {
                view.setTextColor(mTabColorLight);
            } else {
                view.setTextColor(mTabColorNormal);
            }
        }
    }

    /**
     * 创建子View
     *
     * @param text
     */
    private void createChild(String text) {
        TextView view = new TextView(getContext());
        view.setText(text);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(mTabColorNormal);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        view.setOnClickListener(this);
        view.setTag(getChildCount());
        addView(view);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        scroll(arg0, arg1);
    }

    @Override
    public void onPageSelected(int arg0) {
        setTabLight(arg0);
    }

    @Override
    public void onClick(View arg0) {
        int i = (Integer) arg0.getTag();
        mViewPager.setCurrentItem(i);
    }

}

