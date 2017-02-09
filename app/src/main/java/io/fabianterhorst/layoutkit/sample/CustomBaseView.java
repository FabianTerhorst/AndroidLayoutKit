package io.fabianterhorst.layoutkit.sample;

import android.graphics.Canvas;
import android.graphics.Paint;

import io.fabianterhorst.layoutkit.BaseView;

/**
 * Created by fabianterhorst on 09.02.17.
 */

public class CustomBaseView extends BaseView {

    private final Paint paint = new Paint();

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        startDraw(canvas);
        canvas.drawCircle(30, 30, 30, paint);
        stopDraw(canvas);
    }

    @Override
    protected void onMeasure(float width, float height) {
    }
}
