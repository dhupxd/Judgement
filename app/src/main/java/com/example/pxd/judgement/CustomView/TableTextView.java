package com.example.pxd.judgement.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pxd on 2016/10/15.
 */
public class TableTextView extends TextView {
    Paint paint=new Paint();
    public TableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int color= Color.parseColor("#80b9f2");
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //»­±ß¿ò
        canvas.drawLine(0,0,this.getWidth()-1,0,paint);
        canvas.drawLine(0,0,0,this.getHeight()-1,paint);
        canvas.drawLine(this.getWidth()-1,0,this.getWidth()-1,this.getHeight()-1,paint);
        canvas.drawLine(0,this.getHeight()-1,this.getWidth()-1,this.getHeight()-1,paint);
    }
}
