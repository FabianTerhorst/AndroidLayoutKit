package io.fabianterhorst.layoutkit.utils;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.layouts.BaseLayout;

/**
 * Created by fabianterhorst on 12.02.17.
 */

public class LayoutBuilder<T extends LayoutBuilder> {

    protected Alignment alignment;

    protected BaseLayout.LayoutConfig layoutConfig;

    public LayoutBuilder() {
    }

    public T alignment(Alignment alignment) {
        this.alignment = alignment;
        return (T) this;
    }

    public T layoutConfig(BaseLayout.LayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
        return (T) this;
    }
}
