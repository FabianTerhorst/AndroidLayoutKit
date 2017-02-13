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
import io.fabianterhorst.layoutkit.layouts.SizeLayout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;
import io.fabianterhorst.layoutkit.utils.InsetLayoutBuilder;
import io.fabianterhorst.layoutkit.utils.SizeLayoutBuilder;
import io.fabianterhorst.layoutkit.utils.StackLayoutBuilder;

/**
 * Created by fabianterhorst on 03.02.17.
 */
//Todo: docu that unsetted width or height is fill
public class CustomView extends View {

    private InsetLayout insetLayout = new InsetLayoutBuilder()
            .insets(new EdgeInsets(10, 10, 10, 10))
            .subLayout(new SizeLayoutBuilder()
                    .baseView(new AndroidBaseView(new TextView(getContext())))
                    .size(100f, 100f)
                    .layoutConfig(new BaseLayout.LayoutConfig() {
                        @Override
                        public void onConfigure(Object view) {
                            if (view instanceof TextView) {
                                TextView textView = (TextView) view;
                                textView.setText("bla");
                                textView.setBackgroundColor(Color.GRAY);
                            }
                        }
                    }).build()).build();

    private SizeLayout sizeLayoutCustom = new SizeLayoutBuilder()
            .baseView(new CustomBaseView())
            .size(100f, 100f)
            .layoutConfig(new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof CustomBaseView) {
                        CustomBaseView customBaseView = (CustomBaseView) view;
                        customBaseView.setColor(Color.LTGRAY);
                    }
                }
            }).build();

    private SizeLayout sizeLayout2 = new SizeLayoutBuilder()
            .baseView(new AndroidBaseView(new TextView(getContext())))
            .size(100f, 100f)
            .layoutConfig(new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla2");
                        textView.setBackgroundColor(Color.BLUE);
                    }
                }
            }).build();

    private SizeLayout sizeLayout3 = new SizeLayoutBuilder()
            .baseView(new AndroidBaseView(new TextView(getContext())))
            .size(100f, 100f)
            .layoutConfig(new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(Object view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla3");
                        textView.setBackgroundColor(Color.DKGRAY);
                    }
                }
            }).build();

    public static float convertSpToPixel(float sp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    private TextPaint paint = new TextPaint() {{
        setColor(Color.CYAN);
        setTextSize(50/*convertSpToPixel(10, getContext())*/);//Todo: google
    }};

    private SizeLayout sizeLayout4 = /*new LabelLayout(getContext(), "bla4", paint, 1, null, null, null);*/new SizeLayout(
            new AndroidBaseView(new TextView(getContext())),
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
            });

    private StackLayout stackLayout2 = new StackLayoutBuilder()
            .axis(Axis.VERTICAL)
            .spacing(0)
            .subLayouts(Arrays.asList(sizeLayout3, sizeLayout4))
            .build();

    private StackLayout stackLayout = new StackLayoutBuilder()
            .axis(Axis.HORIZONTAL)
            .spacing(0)
            .subLayouts(Arrays.asList(insetLayout, sizeLayoutCustom, sizeLayout2, stackLayout2))
            .build();

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
