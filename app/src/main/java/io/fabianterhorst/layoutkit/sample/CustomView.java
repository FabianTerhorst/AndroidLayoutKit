package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.EdgeInsets;
import io.fabianterhorst.layoutkit.layouts.BaseLayout;
import io.fabianterhorst.layoutkit.layouts.InsetLayout;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private InsetLayout sizeLayout = new InsetLayout(
            new EdgeInsets(10, 10, 10, 10), null,
            new SizeLayout(
                    new BaseView(new TextView(getContext())),
                    100f, 100f, 100f, 100f,
                    null, null, null,
                    new BaseLayout.LayoutConfig() {
                        @Override
                        public void onConfigure(View view) {
                            if (view instanceof TextView) {
                                TextView textView = (TextView) view;
                                textView.setText("bla");
                                textView.setBackgroundColor(Color.GRAY);
                            }
                        }
                    }), null);

    private SizeLayout sizeLayout2 = new SizeLayout(
            new BaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(View view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla2");
                        textView.setBackgroundColor(Color.BLUE);
                    }
                }
            });

    private SizeLayout sizeLayout3 = new SizeLayout(
            new BaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(View view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla3");
                        textView.setBackgroundColor(Color.DKGRAY);
                    }
                }
            });

    private SizeLayout sizeLayout4 = new SizeLayout(
            new BaseView(new TextView(getContext())),
            100f, 100f, 100f, 100f,
            null, null, null,
            new BaseLayout.LayoutConfig() {
                @Override
                public void onConfigure(View view) {
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText("bla4");
                        textView.setBackgroundColor(Color.CYAN);
                    }
                }
            });

    private StackLayout stackLayout2 = new StackLayout(
            Axis.VERTICAL, 0,
            null, null, null,
            Arrays.asList(sizeLayout3, sizeLayout4),
            null);

    private StackLayout stackLayout = new StackLayout(
            Axis.HORIZONTAL, 0,
            null, null, null,
            Arrays.asList(sizeLayout, sizeLayout2, stackLayout2),
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
