package io.fabianterhorst.layoutkit.layouts;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.android.AndroidBaseView;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public abstract class BaseLayout extends Layout {

    public interface LayoutConfig {
        void onConfigure(Object view);
    }

    private BaseView baseView;

    private Alignment alignment;

    private Flexibility flexibility;

    private LayoutConfig config;

    public BaseLayout(BaseView baseView, Alignment alignment, Flexibility flexibility, LayoutConfig config) {
        this.baseView = baseView;
        this.alignment = alignment;
        this.flexibility = flexibility;
        this.config = config;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Flexibility getFlexibility() {
        return flexibility;
    }

    public boolean needsView() {
        return baseView != null;
    }

    //Todo: makeView() should not be a setter it should create the layout object
    //Todo: maybe measure (layout) the view in makeView
    public BaseView makeView() {
        return baseView;
    }

    @Override
    public void configure(BaseView baseTypeView) {
        if (config != null && baseView != null) {
            if (baseView instanceof AndroidBaseView) {
                config.onConfigure(((AndroidBaseView) baseView).getView() != null
                        ? ((AndroidBaseView) baseView).getView()
                        : baseView);
            } else {
                config.onConfigure(baseView);
            }
        }
    }
}
