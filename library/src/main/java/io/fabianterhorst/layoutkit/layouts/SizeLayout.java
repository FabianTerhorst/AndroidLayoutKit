package io.fabianterhorst.layoutkit.layouts;

import java.util.ArrayList;
import java.util.List;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.LayoutArrangement;
import io.fabianterhorst.layoutkit.LayoutMeasurement;
import io.fabianterhorst.layoutkit.Point;
import io.fabianterhorst.layoutkit.Rect;
import io.fabianterhorst.layoutkit.Size;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class SizeLayout extends BaseLayout implements Layout {

    private static float floatTolerance = 0.0001f;

    private Float minWidth;
    private Float maxWidth;
    private Float minHeight;
    private Float maxHeight;
    private Layout subLayout;

    public SizeLayout(BaseView baseView, Float minWidth, Float maxWidth, Float minHeight,
                      Float maxHeight, Alignment alignment, Flexibility flexibility,
                      Layout subLayout, LayoutConfig config) {
        super(baseView,
                alignment == null
                        ? SizeLayout.defaultAlignment(maxWidth, maxHeight)
                        : alignment,
                flexibility == null
                        ? SizeLayout.defaultFlexibility(minWidth, maxWidth, minHeight, maxHeight)
                        : flexibility,
                config);
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.subLayout = subLayout;
    }

    private static Alignment defaultAlignment(Float maxWidth, Float maxHeight) {
        return new Alignment(maxHeight == null ? Alignment.Vertical.FILL : Alignment.Vertical.CENTER,
                maxWidth == null ? Alignment.Horizontal.FILL : Alignment.Horizontal.CENTER);
    }

    private static Flexibility defaultFlexibility(Float minWidth, Float maxWidth,
                                                  Float minHeight, Float maxHeight) {
        Integer horizontal = dimensionFlex(minWidth, maxWidth);
        Integer vertical = dimensionFlex(minHeight, maxHeight);
        return new Flexibility(horizontal, vertical);
    }

    private static Integer dimensionFlex(Float min, Float max) {
        return equals(min, max) ? Flexibility.inflexibleFlex : Flexibility.defaultFlex;
    }

    private static boolean equals(Float left, Float right) {
        return !(left == null || right == null) && Math.abs(left - right) < floatTolerance;
    }

    @Override
    public LayoutMeasurement measurement(Size maxSize) {
        // Take the smaller of our configured max size and the given max size for measurement.
        Size availableSize = maxSize.decreasedToSize(new Size(maxWidth == null ? Float.MAX_VALUE : maxWidth, maxHeight == null ? Float.MAX_VALUE : maxHeight));

        // Measure the sublayout if it exists.
        LayoutMeasurement sublayoutMeasurement;
        Size subLayoutSize;
        if (subLayout != null) {
            sublayoutMeasurement = subLayout.measurement(availableSize);
            subLayoutSize = sublayoutMeasurement.getSize();
        } else {
            subLayoutSize = new Size(0, 0);
            sublayoutMeasurement = null;
        }

        // Make sure that our size is in the desired range.
        Size size = subLayoutSize.increasedToSize(new Size(minWidth == null ? 0 : minWidth, minHeight == null ? 0 : minHeight)).decreasedToSize(availableSize);

        List<LayoutMeasurement> subLayouts = new ArrayList<>();
        if (sublayoutMeasurement != null) {
            subLayouts.add(sublayoutMeasurement);
        }
        return new LayoutMeasurement(this, size, maxSize, subLayouts);
    }

    public LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement) {
        Rect frame = getAlignment().position(measurement.getSize(), rect);
        Rect sublayoutRect = new Rect(0, 0, frame.getWidth(), frame.getHeight());
        List<LayoutArrangement> subLayouts = new ArrayList<>();
        for (LayoutMeasurement layoutMeasurement : measurement.getSubLayouts()) {
            subLayouts.add(layoutMeasurement.arrangement(sublayoutRect));
        }
        return new LayoutArrangement(this, frame, subLayouts);
    }

    //Todo: implement
    @Override
    public void configure(BaseView baseTypeView) {

    }

    public final LayoutArrangement arrangement(Point origin, Float width, Float height) {
        if (origin == null) {
            origin = new Point(0, 0);
        }
        Size maxSize = new Size(width == null ? Float.MAX_VALUE : width, height == null ? Float.MAX_VALUE : height);
        LayoutMeasurement measurement = measurement(maxSize);
        Rect rect = new Rect(origin, measurement.getSize());
        if (width != null) {
            rect.setSizeWidth(width);
        }
        if (height != null) {
            rect.setSizeHeight(height);
        }
        return arrangement(rect, measurement);
    }
}
