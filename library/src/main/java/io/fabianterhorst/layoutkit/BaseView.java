package io.fabianterhorst.layoutkit;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class BaseView {

    private View view;

    private Rect frame;

    private List<BaseView> subViews;

    public BaseView(Rect rect) {
        this(null, rect);
    }

    public BaseView(View view) {
        this(view, null);
    }

    public BaseView(View view, Rect frame) {
        this.view = view;
        if (view != null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));//Todo: test
        }
        setFrame(frame);
    }

    public void draw(Canvas canvas) {
        if (view != null) {
            startDraw(canvas);
            view.draw(canvas);
            endDraw(canvas);
        } else {
            onDraw(canvas);
        }
        if (subViews == null) return;
        for (BaseView view : subViews) {
            view.draw(canvas);
        }
    }

    void startDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(frame.getOrigin().getX(), frame.getOrigin().getY());
    }

    void endDraw(Canvas canvas) {
        canvas.restore();
    }

    private void onDraw(Canvas canvas) {

    }

    public void measure(float width, float height) {
        if (view != null) {
            view.layout(0, 0, (int) width, (int) height);
        } else {
            onMeasure(width, height);
        }
    }

    private void onMeasure(float width, float height) {

    }

    public void setFrame(Rect frame) {
        this.frame = frame;
        if (frame != null) {
            measure(frame.getWidth(), frame.getHeight());
        }
    }

    public Rect getFrame() {
        return frame;
    }

    public View getView() {
        return view;
    }

    public void addSubView(BaseView view) {
        if (subViews == null)
            subViews = new ArrayList<>();
        subViews.add(view);
    }
}
