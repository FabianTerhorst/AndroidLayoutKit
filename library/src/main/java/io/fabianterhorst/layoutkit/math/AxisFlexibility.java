package io.fabianterhorst.layoutkit.math;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.Flexibility;

/**
 * Created by fabianterhorst on 05.02.17.
 */
public class AxisFlexibility {

    private Axis axis;
    private Flexibility flexibility;

    public AxisFlexibility(Axis axis, Flexibility flexibility) {
        this.axis = axis;
        this.flexibility = flexibility;
    }

    public AxisFlexibility(Axis axis, Integer axisFlex, Integer crossFlex) {
        this.axis = axis;
        switch (axis) {
            case HORIZONTAL:
                this.flexibility = new Flexibility(axisFlex, crossFlex);
                break;
            case VERTICAL:
                this.flexibility = new Flexibility(crossFlex, axisFlex);
                break;
        }
    }

    public Integer getAxisFlex() {
        switch (axis) {
            case HORIZONTAL:
                return flexibility.getHorizontal();
            case VERTICAL:
                return flexibility.getVertical();
            default:
                return 0;
        }
    }

    public Integer getCrossFlex() {
        switch (axis) {
            case HORIZONTAL:
                return flexibility.getVertical();
            case VERTICAL:
                return flexibility.getHorizontal();
            default:
                return 0;
        }
    }

    public Flexibility getFlexibility() {
        return flexibility;
    }
}
