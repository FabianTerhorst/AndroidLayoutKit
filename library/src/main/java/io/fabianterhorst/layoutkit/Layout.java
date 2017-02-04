package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public interface Layout {

    LayoutMeasurement measurement(Size maxSize);

    LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement);

    boolean needsView();

    BaseView makeView();

    void configure(BaseView baseTypeView);

    Flexibility getFlexibility();

    //String viewReuseId();
}
