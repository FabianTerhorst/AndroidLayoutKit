package io.fabianterhorst.layoutkit.utils;

import io.fabianterhorst.layoutkit.EdgeInsets;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.layouts.InsetLayout;

/**
 * Created by fabianterhorst on 12.02.17.
 */

public class InsetLayoutBuilder extends LayoutBuilder<InsetLayoutBuilder> {

    private EdgeInsets insets;

    private Layout subLayout;

    public InsetLayoutBuilder() {
    }

    public InsetLayoutBuilder insets(EdgeInsets insets) {
        this.insets = insets;
        return this;
    }

    public InsetLayoutBuilder subLayout(Layout subLayout) {
        this.subLayout = subLayout;
        return this;
    }

    public InsetLayout build() {
        return new InsetLayout(insets, alignment, subLayout, layoutConfig);
    }
}
