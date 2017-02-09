package io.fabianterhorst.layoutkit.layouts;

import java.util.ArrayList;
import java.util.Collections;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.EdgeInsets;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.LayoutArrangement;
import io.fabianterhorst.layoutkit.LayoutMeasurement;
import io.fabianterhorst.layoutkit.Point;
import io.fabianterhorst.layoutkit.Rect;
import io.fabianterhorst.layoutkit.Size;

/**
 * Created by fabianterhorst on 09.02.17.
 */

public class InsetLayout extends BaseLayout {

    private EdgeInsets insets;

    private Layout subLayout;

    public InsetLayout(EdgeInsets insets,
                Alignment alignment,
                Layout subLayout,
                LayoutConfig config) {
        super(null, alignment == null ? Alignment.fill : alignment, subLayout.getFlexibility(), config);
        this.insets = insets;
        this.subLayout = subLayout;
    }

    @Override
    public LayoutMeasurement measurement(Size maxSize) {
        Size insetMaxSize = maxSize.decreasedByInsets(insets);
        LayoutMeasurement subLayoutMeasurement = subLayout.measurement(insetMaxSize);
        Size size = subLayoutMeasurement.getSize().increasedByInsets(insets);
        return new LayoutMeasurement(this, size, maxSize, Collections.singletonList(subLayoutMeasurement));
    }

    @Override
    public LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement) {
        Rect frame = getAlignment().position(measurement.getSize(), rect);
        Point insetOrigin = new Point(insets.getLeft(), insets.getTop());
        Size insetSize = frame.getSize().decreasedByInsets(insets);
        Rect sublayoutRect = new Rect(insetOrigin, insetSize);
        ArrayList<LayoutArrangement> subLayouts = new ArrayList<>();
        for (LayoutMeasurement layoutMeasurement : measurement.getSubLayouts()) {
            subLayouts.add(layoutMeasurement.arrangement(sublayoutRect));
        }
        return new LayoutArrangement(this, frame, subLayouts);
    }
}
