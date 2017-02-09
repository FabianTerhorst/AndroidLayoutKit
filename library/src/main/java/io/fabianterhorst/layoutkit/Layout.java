package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public abstract class Layout {

    public abstract LayoutMeasurement measurement(Size maxSize);

    public abstract LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement);

    public abstract boolean needsView();

    public abstract BaseView makeView();

    public abstract void configure(BaseView baseTypeView);

    public abstract Flexibility getFlexibility();//Todo: remove unused

    public LayoutArrangement arrangement() {
        return arrangement(null, null, null);
    }

    public /*Todo: what was the reason for this final*/ LayoutArrangement arrangement(Point origin, Float width, Float height) { //Todo: remove the final
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

    //String viewReuseId();
}
