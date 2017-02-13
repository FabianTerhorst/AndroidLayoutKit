package io.fabianterhorst.layoutkit.android;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Layout;

/**
 * Created by fabianterhorst on 13.02.17.
 */

public class LayoutView extends View {

    private BaseView baseView;

    private Layout layout;

    public LayoutView(Context context, BaseView baseView) {
        super(context);
        this.baseView = baseView;
    }

    public LayoutView(Context context, Layout layout) {
        super(context);
        this.layout = layout;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (baseView != null) {
            baseView.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layout != null) {
            baseView = layout.arrangement(null, (float) MeasureSpec.getSize(widthMeasureSpec), (float) MeasureSpec.getSize(heightMeasureSpec)).makeViews();
        }
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(Math.round(baseView.getFrame().getWidth()), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(Math.round(baseView.getFrame().getHeight()), MeasureSpec.EXACTLY)
        );
    }
}
