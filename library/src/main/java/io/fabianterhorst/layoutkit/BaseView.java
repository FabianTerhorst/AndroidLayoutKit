package io.fabianterhorst.layoutkit;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class BaseView {

    private Rect frame;

    private List<BaseView> subViews;

    public BaseView() {
        this(null);
    }

    public BaseView(Rect frame) {
        setFrame(frame);
    }

    public void draw(Canvas canvas) {
        onDraw(canvas);
        if (subViews == null) return;
        for (BaseView view : subViews) {
            view.draw(canvas);
        }
    }

    protected void startDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(frame.getOrigin().getX(), frame.getOrigin().getY());
    }

    protected void stopDraw(Canvas canvas) {
        canvas.restore();
    }

    protected void onDraw(Canvas canvas) {
    }

    protected void onMeasure(float width, float height) {
    }

    public void setFrame(Rect frame) {
        this.frame = frame;
        if (frame != null) {
            onMeasure(frame.getWidth(), frame.getHeight());
        }
    }

    public Rect getFrame() {
        return frame;
    }

    public void addSubView(BaseView view) {
        if (subViews == null)
            subViews = new ArrayList<>();
        subViews.add(view);
    }
}
