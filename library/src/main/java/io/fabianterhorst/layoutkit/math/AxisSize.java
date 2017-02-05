package io.fabianterhorst.layoutkit.math;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.Size;

/**
 * Created by fabianterhorst on 05.02.17.
 */
public class AxisSize {

    private Axis axis;
    private Size size;

    public AxisSize(Axis axis, Size size) {
        this.axis = axis;
        this.size = size;
    }

    public AxisSize(Axis axis, float axisLength, float crossLength) {
        this.axis = axis;
        switch (axis) {
            case HORIZONTAL:
                this.size = new Size(axisLength, crossLength);
                break;
            case VERTICAL:
                this.size = new Size(crossLength, axisLength);
                break;
        }
    }

    public void setAxisLength(float length) {
        switch (axis) {
            case HORIZONTAL:
                size.setWidth(length);
                break;
            case VERTICAL:
                size.setHeight(length);
                break;
        }
    }

    public float getAxisLength() {
        switch (axis) {
            case HORIZONTAL:
                return size.getWidth();
            case VERTICAL:
                return size.getHeight();
            default:
                return 0;
        }
    }

    //Todo: is needed?
    public void setCrossLength(float length) {
        switch (axis) {
            case HORIZONTAL:
                size.setHeight(length);
                break;
            case VERTICAL:
                size.setWidth(length);
                break;
        }
    }

    public float getCrossLength() {
        switch (axis) {
            case HORIZONTAL:
                return size.getHeight();
            case VERTICAL:
                return size.getWidth();
            default:
                return 0;
        }
    }

    public Size getSize() {
        return size;
    }
}
