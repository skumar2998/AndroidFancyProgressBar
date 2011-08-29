package com.troubadorian.mobile.android.androidfancyprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class ProgressBarOutline extends View{

    private Paint paint;

    public ProgressBarOutline(Context context) {
        super(context);
        paint = new Paint();
    }


    @Override
    public void onDraw(Canvas canvas) {
//        paint.setColor(Color.GRAY);
        paint.setColor(Color.argb(255, 75, 75, 75));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setARGB(255, 230, 230, 230);
        paint.setARGB(255, 75, 75, 75); //gray
//        RectF r = new RectF(1,1,getWidth()-2,getHeight()-2);
        RectF r = new RectF(1,1,getWidth(),getHeight());
        canvas.drawRoundRect(r,getHeight()/2,getHeight()/2, paint);
    }

}
