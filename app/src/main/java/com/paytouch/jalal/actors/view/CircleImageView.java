package com.paytouch.jalal.actors.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.paytouch.jalal.actors.R;

/**
 * Created by jalalsouky on 06/01/15.
 */
public class CircleImageView extends NetworkImageView {

    public CircleImageView(Context context) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
        if(b == null) {
            return;
        }

        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        drawCircleBitmap(bitmap, canvas);
    }

    private void drawCircleBitmap(Bitmap bitmap, Canvas canvas) {

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final int size = Math.min(width, height);
        final Bitmap circleBitmap = Bitmap.createBitmap(bitmap, width/2 - size/2, height/ 2 - size/2, size, size);

        final Path path = new Path();
        path.addCircle(size / 2, size / 2, size / 2, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(circleBitmap, 0,0, null);

        Paint strokePaint = new Paint();
        strokePaint.setColor(getResources().getColor(R.color.actionbar_color));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.circle_stroke_width));
        strokePaint.setAntiAlias(true);
        canvas.drawCircle(size / 2, size / 2, size / 2, strokePaint);
    }
}