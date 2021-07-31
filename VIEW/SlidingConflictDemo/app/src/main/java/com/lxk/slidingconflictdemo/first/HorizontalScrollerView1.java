package com.lxk.slidingconflictdemo.first;

import android.view.MotionEvent;
import android.view.ViewGroup;

//HorizontalScrollerView.java
public class HorizontalScrollerView1  extends ViewGroup {
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (x - lastX);
                //跟随手指滑动
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //计算1s内的速度
                velocityTracker.computeCurrentVelocity(1000);
                //获取水平的滑动速度
                float xVelocity = velocityTracker.getXVelocity();

                if (Math.abs(xVelocity) > 50) {
                    childIndex = xVelocity > 0 ? childIndex - 1 : childIndex + 1;
                } else {
                    childIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                childIndex = Math.max(0, Math.min(childIndex, mChildSize - 1));
                //计算还需滑动到整个child的偏移
                int sx = childIndex * mChildWidth - scrollX;
                //通过Scroller来平滑滑动
                smoothScrollBy(sx);
                //清除
                velocityTracker.clear();
                break;
            default:
                break;
        }
        return true;
    }
}
