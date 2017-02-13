package io.fabianterhorst.layoutkit.utils;

import io.fabianterhorst.layoutkit.Flexibility;

/**
 * Created by fabianterhorst on 12.02.17.
 */

public class ChildLayoutBuilder<T extends ChildLayoutBuilder> extends LayoutBuilder<T> {

    protected Flexibility flexibility;

    public ChildLayoutBuilder() {
    }

    public T flexibility(Flexibility flexibility) {
        this.flexibility = flexibility;
        return (T) this;
    }
}
