package io.fabianterhorst.layoutkit.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class CustomView extends View {

    private TextView textView = new TextView(getContext());

    private BaseView baseView = new BaseView(textView);

    private SizeLayout sizeLayout = new SizeLayout(baseView, 100f, null, 100f, null, null, null, null, null);

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BaseView view = sizeLayout.arrangement(null, 200f, 200f).makeViews(null);//.draw(canvas);
        view.setView(textView);
        Log.d("size", String.valueOf(view.getFrame().getHeight()));
        Log.d("size", String.valueOf(view.getFrame().getWidth()));
        Log.d("size", String.valueOf(view.getFrame().getOrigin().getX()));
        Log.d("size", String.valueOf(view.getFrame().getOrigin().getY()));
        view.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY));
    }
}
