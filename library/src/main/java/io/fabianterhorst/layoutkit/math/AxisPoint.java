package io.fabianterhorst.layoutkit.math;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.Point;

/**
 * Created by fabianterhorst on 05.02.17.
 */
public class AxisPoint {

    private Axis axis;
    private Point point;

    public AxisPoint(Axis axis, Point point) {
        this.axis = axis;
        this.point = point;
    }

    public AxisPoint(Axis axis, float axisOffset, float crossOffset) {
        this.axis = axis;
        switch (axis) {
            case HORIZONTAL:
                this.point = new Point(axisOffset, crossOffset);
                break;
            case VERTICAL:
                this.point = new Point(crossOffset, axisOffset);
                break;
        }
    }

    public void setAxisOffset(float length) {
        switch (axis) {
            case HORIZONTAL:
                point.setX(length);
                break;
            case VERTICAL:
                point.setY(length);
                break;
        }
    }

    public float getAxisOffset() {
        switch (axis) {
            case HORIZONTAL:
                return point.getX();
            case VERTICAL:
                return point.getY();
            default:
                return 0;
        }
    }

    //Todo: is needed?
    public void setCrossOffset(float length) {
        switch (axis) {
            case HORIZONTAL:
                point.setY(length);
                break;
            case VERTICAL:
                point.setX(length);
                break;
        }
    }

    public float getCrossOffset() {
        switch (axis) {
            case HORIZONTAL:
                return point.getY();
            case VERTICAL:
                return point.getX();
            default:
                return 0;
        }
    }

    public Point getPoint() {
        return point;
    }
}
