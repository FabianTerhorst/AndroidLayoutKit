package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class Size {

    private float width;

    private float height;

    public Size(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public Size decreasedByInsets(EdgeInsets insets) {
        return new Size(width - insets.getLeft() - insets.getRight(), height - insets.getTop() - insets.getBottom());
    }

    public Size increasedByInsets(EdgeInsets insets) {
        return new Size(width + insets.getLeft() + insets.getRight(), height + insets.getTop() + insets.getBottom());
    }

    public Size decreasedToSize(Size maxSize) {
        float width = Math.min(this.width, maxSize.width);
        float height = Math.min(this.height, maxSize.height);
        return new Size(width, height);
    }

    public Size increasedToSize(Size minSize) {
        float width = Math.max(this.width, minSize.width);
        float height = Math.max(this.height, minSize.height);
        return new Size(width, height);
    }
}
