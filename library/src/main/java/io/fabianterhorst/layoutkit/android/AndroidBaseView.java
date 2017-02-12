package io.fabianterhorst.layoutkit.android;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Rect;

/**
 * Created by fabianterhorst on 10.02.17.
 */

public class AndroidBaseView extends BaseView {

    private View view;

    public AndroidBaseView(View view) {
        this(view, null);
    }

    public AndroidBaseView(View view, Rect frame) {
        super(frame);
        this.view = view;
        if (view != null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));//Todo: test
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (view != null) {
            startDraw(canvas);
            view.draw(canvas);
            stopDraw(canvas);
        }
    }

    @Override
    protected void onMeasure(float width, float height) {
        if (view != null)
            view.layout(0, 0, (int) width, (int) height);
    }

    public View getView() {
        return view;
    }
}
