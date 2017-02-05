package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class Rect {

    private Point origin;

    private Size size;

    public Rect(Point origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

    public Rect(float x, float y, float width, float height) {
        this.origin = new Point(x, y);
        this.size = new Size(width, height);
    }

    public float getWidth() {
        return size.getWidth();
    }

    public float getHeight() {
        return size.getHeight();
    }

    public Point getOrigin() {
        return origin;
    }

    public void offsetBy(float x, float y) {
        origin.setX(origin.getX() + x);
        origin.setY(origin.getY() + y);
    }

    public Size getSize() {
        return size;
    }

    public void setSizeWidth(float width) {
        size.setWidth(width);
    }

    public void setSizeHeight(float height) {
        size.setHeight(height);
    }
}
