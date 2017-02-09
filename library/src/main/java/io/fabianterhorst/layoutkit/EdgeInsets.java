package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 09.02.17.
 */

public class EdgeInsets {

    private float top;

    private float left;

    private float bottom;

    private float right;

    public EdgeInsets(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public float getTop() {
        return top;
    }

    public float getLeft() {
        return left;
    }

    public float getBottom() {
        return bottom;
    }

    public float getRight() {
        return right;
    }
}
