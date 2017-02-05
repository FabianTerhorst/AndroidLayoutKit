package io.fabianterhorst.layoutkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianterhorst on 03.02.17.
 */

/**
 * The frame of a layout and the frames of its subLayouts.
 */
public class LayoutArrangement {

    private Layout layout;

    private Rect frame;

    private List<LayoutArrangement> subLayouts;

    public LayoutArrangement(Layout layout, Rect frame, List<LayoutArrangement> subLayouts) {
        this.layout = layout;
        this.frame = frame;
        this.subLayouts = subLayouts;
    }

    public BaseView makeViews(BaseView view) {
        List<BaseView> views = makeSubViews();
        BaseView rootView;
        if (view != null) {
            for (BaseView subView : views) {
                subView.addSubView(subView);
            }
            rootView = view;
        } else if (views.size() == 1) {
            // We have a single view so it is our root view.
            rootView = views.get(0);
        } else {
            // We have multiple views so create a root view.
            rootView = new BaseView(frame);
            for (BaseView subView : views) {
                subView.getFrame().offsetBy(-frame.getOrigin().getX(), -frame.getOrigin().getY());
                rootView.addSubView(subView);
            }
        }
        return rootView;
    }

    private List<BaseView> makeSubViews() {
        List<BaseView> subViews = new ArrayList<>();
        for (LayoutArrangement subLayout : this.subLayouts) {
            subViews.addAll(subLayout.makeSubViews());
        }
        if (layout.needsView()) {
            BaseView view = layout.makeView();
            view.setFrame(frame);
            layout.configure(view);//Todo: maybe view.getView() ?
            for (BaseView subView : subViews) {
                view.addSubView(subView);
            }
            List<BaseView> viewArray = new ArrayList<>();//Todo: reuse top array list
            viewArray.add(view);
            return viewArray;
        } else {
            for (BaseView subView : subViews) {
                subView.getFrame().offsetBy(frame.getOrigin().getX(), frame.getOrigin().getY());
            }
            return subViews;
        }
    }
}
