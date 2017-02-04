package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.Rect;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private TextView textView = new TextView(getContext());

    private BaseView baseView = new BaseView(textView, new Rect(500, 500, 20, 50));

    private SizeLayout sizeLayout = new SizeLayout(baseView, 200f, 400f, 200f, 400f, Alignment.center, Flexibility.flexible, null, null);

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textView.setText("bla");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        sizeLayout.arrangement(null, null, null).makeViews(null).draw(canvas);
        //baseView.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY));
    }
}
