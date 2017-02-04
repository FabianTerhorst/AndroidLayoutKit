package io.fabianterhorst.layoutkit;

import java.util.List;

/**
 * Created by fabianterhorst on 03.02.17.
 */

/**
 * The size of a layout and the sizes of its sublayouts.
 */
public class LayoutMeasurement {

    private Layout layout;

    private Size size;

    private Size maxSize;

    private List<LayoutMeasurement> subLayouts;

    public LayoutMeasurement(Layout layout, Size size, Size maxSize, List<LayoutMeasurement> subLayouts) {
        this.layout = layout;
        this.size = size;
        this.maxSize = maxSize;
        this.subLayouts = subLayouts;
    }

    public LayoutArrangement arrangement(Rect rect) {
        return layout.arrangement(rect, this);
    }

    public Size getSize() {
        return size;
    }

    public List<LayoutMeasurement> getSubLayouts() {
        return subLayouts;
    }
}
