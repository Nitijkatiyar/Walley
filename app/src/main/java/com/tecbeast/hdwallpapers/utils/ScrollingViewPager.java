package com.tecbeast.hdwallpapers.utils;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class ScrollingViewPager extends ViewPager {
    private boolean mOverrideMultiTouch = true;


    public ScrollingViewPager(Context context) {
        super(context);
    }

    public ScrollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOverrideMultiTouch(boolean overrideMultiTouch) {
        mOverrideMultiTouch = overrideMultiTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOverrideMultiTouch) {
            final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
            if (action == MotionEvent.ACTION_DOWN && MotionEventCompat.getPointerCount(ev) > 1) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageView) {
            //
            // canScrollHorizontally is not supported for Api < 14. To get around this issue,
            // ViewPager is extended and canScrollHorizontallyFroyo, a wrapper around
            // canScrollHorizontally supporting Api >= 8, is called.
            //
            return ((ImageView) v).canScrollHorizontally(-dx);

        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

    public interface CanScrollCompat {
        public boolean canScrollHorizontally(int direction);

        public boolean canScrollVertically(int direction);
    }
}