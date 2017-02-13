package io.fabianterhorst.layoutkit.utils;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.layouts.BaseLayout;
import io.fabianterhorst.layoutkit.layouts.SizeLayout;

/**
 * Created by fabianterhorst on 12.02.17.
 */

public class SizeLayoutBuilder {

    private BaseView baseView;

    private Float minWidth, maxWidth, minHeight, maxHeight;

    private Alignment alignment;

    private Flexibility flexibility;

    private Layout subLayout;

    private BaseLayout.LayoutConfig layoutConfig;

    public SizeLayoutBuilder() {
    }

    public SizeLayoutBuilder baseView(BaseView baseView) {
        this.baseView = baseView;
        return this;
    }

    public SizeLayoutBuilder width(Float width) {
        this.minWidth = width;
        this.maxWidth = width;
        return this;
    }

    public SizeLayoutBuilder height(Float height) {
        this.minHeight = height;
        this.maxHeight = height;
        return this;
    }

    public SizeLayoutBuilder size(Float width, Float height) {
        width(width);
        height(height);
        return this;
    }

    public SizeLayoutBuilder minWidth(Float minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public SizeLayoutBuilder maxWidth(Float maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public SizeLayoutBuilder minHeight(Float minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public SizeLayoutBuilder maxHeight(Float maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public SizeLayoutBuilder alignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public SizeLayoutBuilder flexibility(Flexibility flexibility) {
        this.flexibility = flexibility;
        return this;
    }

    public SizeLayoutBuilder subLayout(Layout subLayout) {
        this.subLayout = subLayout;
        return this;
    }

    public SizeLayoutBuilder layoutConfig(BaseLayout.LayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
        return this;
    }

    public SizeLayout build() {
        return new SizeLayout(baseView, minWidth, maxWidth, minHeight, maxHeight,
                alignment, flexibility, subLayout, layoutConfig);
    }
}
