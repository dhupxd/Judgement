package com.example.pxd.judgement.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by pxd on 2016/10/25.
 */
public class NoScrollHorizontalScrollView extends HorizontalScrollView {


    public NoScrollHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollHorizontalScrollView(Context context) {
        super(context);
    }

    public NoScrollHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event){
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        return super.dispatchTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
}
