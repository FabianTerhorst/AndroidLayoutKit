package io.fabianterhorst.layoutkit;

/**
 * Created by fabianterhorst on 03.02.17.
 */

/**
 * Specifies how a layout positions itself inside of the rect that it is given to it by its parent during arrangement.
 */
public class Alignment {

    public static final Alignment center = new Alignment(Vertical.CENTER, Horizontal.CENTER);
    public static final Alignment centerLeading = new Alignment(Vertical.CENTER, Horizontal.LEADING);
    public static final Alignment centerTrailing = new Alignment(Vertical.CENTER, Horizontal.TRAILING);

    public static final Alignment fill = new Alignment(Vertical.FILL, Horizontal.FILL);
    public static final Alignment fillLeading = new Alignment(Vertical.FILL, Horizontal.LEADING);
    public static final Alignment fillTrailing = new Alignment(Vertical.FILL, Horizontal.TRAILING);

    public static final Alignment topLeading = new Alignment(Vertical.TOP, Horizontal.LEADING);
    public static final Alignment topTrailing = new Alignment(Vertical.TOP, Horizontal.TRAILING);
    public static final Alignment topCenter = new Alignment(Vertical.TOP, Horizontal.CENTER);
    public static final Alignment topFill = new Alignment(Vertical.TOP, Horizontal.FILL);

    public static final Alignment bottomLeading = new Alignment(Vertical.BOTTOM, Horizontal.LEADING);
    public static final Alignment bottomTrailing = new Alignment(Vertical.BOTTOM, Horizontal.TRAILING);
    public static final Alignment bottomCenter = new Alignment(Vertical.BOTTOM, Horizontal.CENTER);
    public static final Alignment bottomFill = new Alignment(Vertical.BOTTOM, Horizontal.FILL);

    public enum Vertical {
        TOP, BOTTOM, CENTER, FILL;

        public float[] align(float length, float availableLength, float offset) {
            Horizontal horizontal;
            switch (this) {
                case TOP:
                    horizontal = Horizontal.LEADING;
                    break;
                case BOTTOM:
                    horizontal = Horizontal.TRAILING;
                    break;
                case CENTER:
                    horizontal = Horizontal.CENTER;
                    break;
                case FILL:
                    horizontal = Horizontal.FILL;
                    break;
                default:
                    horizontal = null;
            }
            return horizontal.align(length, availableLength, offset);
        }
    }

    public enum Horizontal {
        LEADING, TRAILING, CENTER, FILL;

        public float[] align(float length, float availableLength, float offset) {
            float excessLength = availableLength - length;
            float clampedLength = Math.min(availableLength, length);
            float alignedLength;
            float alignedOffset;
            switch (this) {
                case LEADING:
                    alignedOffset = 0;
                    alignedLength = clampedLength;
                    break;
                case TRAILING:
                    alignedOffset = excessLength;
                    alignedLength = clampedLength;
                    break;
                case CENTER:
                    alignedOffset = excessLength / 2.0f;
                    alignedLength = clampedLength;
                    break;
                case FILL:
                    alignedOffset = 0;
                    alignedLength = availableLength;
                    break;
                default:
                    alignedLength = 0;
                    alignedOffset = 0;
                    break;
            }
            return new float[]{offset + alignedOffset, alignedLength};

        }
    }

    private Aligner aligner;

    public Alignment(final Vertical vertical, final Horizontal horizontal) {
        aligner = new Aligner() {
            @Override
            public Rect align(Size size, Rect rect) {
                float[] horizontalAlignment = horizontal.align(size.getWidth(), rect.getWidth(), rect.getOrigin().getX());
                float[] verticalAlignment = vertical.align(size.getHeight(), rect.getHeight(), rect.getOrigin().getY());
                return new Rect(horizontalAlignment[0], verticalAlignment[0], horizontalAlignment[1], verticalAlignment[1]);
            }
        };
    }

    public Rect position(Size size, Rect rect) {
        return aligner.align(size, rect);
    }
}
