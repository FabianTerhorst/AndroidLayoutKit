package io.fabianterhorst.layoutkit.utils;

import java.util.List;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.layouts.StackLayout;

/**
 * Created by fabianterhorst on 12.02.17.
 */

public class StackLayoutBuilder extends ChildLayoutBuilder<StackLayoutBuilder> {

    private Axis axis;

    private float spacing;

    private StackLayout.StackLayoutDistribution distribution;

    private List<? extends Layout> subLayouts;

    public StackLayoutBuilder() {
    }

    public StackLayoutBuilder axis(Axis axis) {
        this.axis = axis;
        return this;
    }

    public StackLayoutBuilder spacing(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public StackLayoutBuilder distribution(StackLayout.StackLayoutDistribution distribution) {
        this.distribution = distribution;
        return this;
    }

    public StackLayoutBuilder subLayouts(List<? extends Layout> subLayouts) {
        this.subLayouts = subLayouts;
        return this;
    }

    public StackLayout build() {
        return new StackLayout(axis, spacing, distribution, alignment, flexibility, subLayouts, layoutConfig);
    }
}
