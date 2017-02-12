package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.EdgeInsets;
import io.fabianterhorst.layoutkit.android.AndroidBaseView;
import io.fabianterhorst.layoutkit.layouts.BaseLayout;
import io.fabianterhorst.layoutkit.layouts.InsetLayout;
import io.fabianterhorst.layoutkit.layouts.LabelLayout;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private InsetLayout insetLayout = new InsetLayout(
            new EdgeInsets(10, 10, 10, 10), null,
            new SizeLayout(
                    new AndroidBaseView(new TextView(getContext())),
                    100f, 100f, 100f, 100f,
                    null, null, null,
                    new BaseLayout.LayoutConfig() {
                        @Override
                        public void onConfigure(Object view) {
                            if (view instanceof TextView) {
                                TextView textView = (TextView) view;
                                textView.setText("bla");
                                textView.setBackgroundColor(Color.GRAY);
                            }
                        }
                    }), null);

    private SizeLayout sizeLayoutCustom = new SizeLayout(
            new CustomBaseView(),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof CustomBaseView) {
                        CustomBaseView customBaseView = (CustomBaseView) view;
                        customBaseView.setColor(Color.LTGRAY);
                    }
                }
            });

    private SizeLayout sizeLayout2 = new SizeLayout(
            new AndroidBaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla2");
                        textView.setBackgroundColor(Color.BLUE);
                    }
                }
            });

    private SizeLayout sizeLayout3 = new SizeLayout(
            new AndroidBaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla3");
                        textView.setBackgroundColor(Color.DKGRAY);
                    }
                }
            });

    public static float convertSpToPixel(float sp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    private TextPaint paint = new TextPaint() {{
        setColor(Color.CYAN);
        setTextSize(50/*convertSpToPixel(10, getContext())*/);//Todo: google
    }};

    private LabelLayout sizeLayout4 = new LabelLayout(getContext(), "bla4", paint, 1, null, null, null);/*new SizeLayout(
            new BaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla4");
                        textView.setBackgroundColor(Color.CYAN);
                    }
                }
            });*/

    private StackLayout stackLayout2 = new StackLayout(
            Axis.VERTICAL, 0,
            null, null, null,
            Arrays.asList(sizeLayout3, sizeLayout4),
            null);

    private StackLayout stackLayout = new StackLayout(
            Axis.HORIZONTAL, 0,
            null, null, null,
            Arrays.asList(insetLayout, sizeLayoutCustom, sizeLayout2, stackLayout2),
            null);

    public CustomView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long time = System.nanoTime();
        stackLayout.arrangement(null, (float) getWidth(), null).makeViews().draw(canvas);
        Log.d("time", String.valueOf(System.nanoTime() - time));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
    }
}
