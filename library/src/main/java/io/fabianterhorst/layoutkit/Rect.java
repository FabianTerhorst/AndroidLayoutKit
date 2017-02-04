package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

//Todo: size or width and heigth?
public class Rect {

    private float width;

    private float height;

    private Point origin;

    private Size size;

    public Rect(Point origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

    public Rect(float x, float y, float width, float height) {
        this.origin = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
}
