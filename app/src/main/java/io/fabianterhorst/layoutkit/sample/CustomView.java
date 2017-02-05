package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private TextView textView = new TextView(getContext());

    private TextView textView2 = new TextView(getContext());

    private TextView textView3 = new TextView(getContext());

    private TextView textView4 = new TextView(getContext());

    private BaseView baseView = new BaseView(textView);

    private BaseView baseView2 = new BaseView(textView2);

    private BaseView baseView3 = new BaseView(textView3);

    private BaseView baseView4 = new BaseView(textView4);

    private SizeLayout sizeLayout = new SizeLayout(baseView, 1f, null, 100f, 100f, Alignment.fill, Flexibility.min, null, null);

    private SizeLayout sizeLayout2 = new SizeLayout(baseView2, 1f, null, 100f, 100f, Alignment.fill, Flexibility.min, null, null);

    private SizeLayout sizeLayout3 = new SizeLayout(baseView3, 1f, null, 100f, 100f, null, null, null, null);

    private SizeLayout sizeLayout4 = new SizeLayout(baseView4, 1f, null, 100f, 100f, null, null, null, null);

    private StackLayout stackLayout2 = new StackLayout(Axis.VERTICAL, 0, null, null, null, Arrays.asList(sizeLayout3, sizeLayout4), null);

    private StackLayout stackLayout = new StackLayout(Axis.HORIZONTAL, 0, StackLayout.StackLayoutDistribution.FILL_EQUAL_SIZE, null, null, Arrays.asList(sizeLayout, sizeLayout2, stackLayout2), null);

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
        textView4.setText("bla6");
        textView4.setBackgroundColor(Color.CYAN);
        textView3.setText("bla4");
        textView3.setBackgroundColor(Color.DKGRAY);
        textView.setText("bla");
        textView.setBackgroundColor(Color.GRAY);
        textView2.setText("bla2");
        textView2.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        stackLayout.arrangement(null, (float) getWidth(), (float) getHeight()).makeViews(null).draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
    }
}
