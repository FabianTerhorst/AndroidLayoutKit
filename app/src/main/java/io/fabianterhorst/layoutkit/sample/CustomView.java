package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private TextView textView = new TextView(getContext());

    private TextView textView2 = new TextView(getContext());

    private BaseView baseView = new BaseView(textView);

    private BaseView baseView2 = new BaseView(textView2);

    private SizeLayout sizeLayout = new SizeLayout(baseView, 100f, null, 100f, null, null, null, null, null);

    private SizeLayout sizeLayout2 = new SizeLayout(baseView2, 100f, null, 100f, null, null, null, null, null);

    private StackLayout stackLayout = new StackLayout(Axis.VERTICAL, 0, null, null, null, Arrays.asList(sizeLayout, sizeLayout2), null);

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
        textView.setBackgroundColor(Color.GRAY);
        textView2.setText("bla2");
        textView2.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        stackLayout.arrangement(null, 200f, 200f).makeViews(null).draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY));
    }
}
