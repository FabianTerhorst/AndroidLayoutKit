package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

public class Flexibility {

    public static final Integer inflexibleFlex = null;
    public static final Integer defaultFlex = 0;
    public static final Integer maxFlex = Integer.MAX_VALUE;
    public static final Integer minFlex = Integer.MIN_VALUE;
    public static final Integer highFlex = Integer.MAX_VALUE / 2;
    public static final Integer lowFlex = Integer.MIN_VALUE / 2;

    public static final Flexibility inflexible = new Flexibility(inflexibleFlex, inflexibleFlex);
    public static final Flexibility flexible = new Flexibility(defaultFlex, defaultFlex);
    public static final Flexibility high = new Flexibility(highFlex, highFlex);
    public static final Flexibility low = new Flexibility(lowFlex, lowFlex);
    public static final Flexibility min = new Flexibility(minFlex, minFlex);
    public static final Flexibility max = new Flexibility(maxFlex, maxFlex);

    private Integer vertical;

    private Integer horizontal;

    public Flexibility(Integer vertical, Integer horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public Integer flex(Axis axis) {
        switch (axis) {
            case VERTICAL:
                return vertical;
            case HORIZONTAL:
                return horizontal;
            default:
                return null;
        }
    }

    public Integer max(Integer left, Integer right) {
        if (left == null) return right;
        if (right == null) return left;
        return Math.max(left, right);
    }

    public Integer min(Integer left, Integer right) {
        // One of them is inflexible so return null flex (inflexible)
        if (left == null || right == null) return null;
        return Math.min(left, right);
    }
}
