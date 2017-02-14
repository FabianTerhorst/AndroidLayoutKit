package io.fabianterhorst.layoutkit.layouts;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.TextView;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.LayoutArrangement;
import io.fabianterhorst.layoutkit.LayoutMeasurement;
import io.fabianterhorst.layoutkit.Rect;
import io.fabianterhorst.layoutkit.Size;
import io.fabianterhorst.layoutkit.android.AndroidBaseView;

/**
 * Created by fabianterhorst on 06.02.17.
 */

public class LabelLayout extends BaseLayout {

    private String text;
    private TextPaint font;
    private int numberOfLines = 0;

    public LabelLayout(Context context,
                       String text,
                       TextPaint font,
                       int numberOfLines,
                       Alignment alignment,
                       Flexibility flexibility,
                       LayoutConfig config) {
        super(new AndroidBaseView(new TextView(context)),
                alignment == null
                        ? Alignment.topLeading
                        : alignment,
                flexibility == null
                        ? Flexibility.flexible
                        : flexibility
                , config);
        this.text = text;
        this.numberOfLines = numberOfLines;
        this.font = font;
        TextView textView = (TextView) ((AndroidBaseView)makeView()).getView();
        textView.setText(text);
        //textView.setIncludeFontPadding(false);
        /*if (font.getTypeface() != Typeface.DEFAULT) {
            textView.setTypeface(font.getTypeface());
        }*/
        //textView.setTextSize(font.getTextSize());
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, font.getTextSize());
        //textView.setLines(numberOfLines);
    }

    //Todo: use staticlayout here for multiple lines
    @Override
    public LayoutMeasurement measurement(Size maxSize) {
        android.graphics.Rect rect = new android.graphics.Rect();
        font.getTextBounds(text, 0, text.length(), rect);
        StaticLayout staticLayout = new StaticLayout(text, 0, text.length(), font, 0, Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
        return new LayoutMeasurement(this, new Size(font.measureText(text), staticLayout.getHeight()).decreasedToSize(maxSize), maxSize, null);
    }

    @Override
    public LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement) {
        Rect frame = getAlignment().position(measurement.getSize(), rect);
        return new LayoutArrangement(this, frame, null);
    }
}
