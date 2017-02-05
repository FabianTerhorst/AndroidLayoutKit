package io.fabianterhorst.layoutkit.layouts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.fabianterhorst.layoutkit.Alignment;
import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.BaseView;
import io.fabianterhorst.layoutkit.Flexibility;
import io.fabianterhorst.layoutkit.Layout;
import io.fabianterhorst.layoutkit.LayoutArrangement;
import io.fabianterhorst.layoutkit.LayoutMeasurement;
import io.fabianterhorst.layoutkit.Rect;
import io.fabianterhorst.layoutkit.Size;
import io.fabianterhorst.layoutkit.math.AxisFlexibility;
import io.fabianterhorst.layoutkit.math.AxisPoint;
import io.fabianterhorst.layoutkit.math.AxisSize;

/**
 * Created by fabianterhorst on 05.02.17.
 */

public class StackLayout extends BaseLayout {

    public enum StackLayoutDistribution {
        LEADING, TRAILING, CENTER, FILL_EQUAL_SPACING, FILL_EQUAL_SIZE, FILL_FLEXING
    }

    private static class DistributionConfig {
        float initialAxisOffset;
        float axisSpacing;
        Integer stretchIndex;

        private DistributionConfig(float initialAxisOffset, float axisSpacing, Integer stretchIndex) {
            this.initialAxisOffset = initialAxisOffset;
            this.axisSpacing = axisSpacing;
            this.stretchIndex = stretchIndex;
        }
    }

    private Axis axis;

    private float spacing;

    private StackLayoutDistribution distribution;

    private List<? extends Layout> subLayouts;

    public StackLayout(Axis axis,
                       float spacing,
                       StackLayoutDistribution distribution,
                       Alignment alignment,
                       Flexibility flexibility,
                       List<? extends Layout> subLayouts,
                       LayoutConfig config) {
        super(null,
                alignment == null
                        ? Alignment.fill
                        : alignment,
                flexibility == null
                        ? StackLayout.defaultFlexibility(axis, subLayouts)
                        : flexibility, config);
        if (distribution == null) {
            distribution = StackLayoutDistribution.FILL_FLEXING;
        }
        this.axis = axis;
        this.spacing = spacing;
        this.distribution = distribution;
        this.subLayouts = subLayouts;
    }

    public LayoutMeasurement measurement(Size maxSize) {
        AxisSize availableSize = new AxisSize(axis, maxSize);
        List<LayoutMeasurement> sublayoutMeasurements = new ArrayList<>(subLayouts.size());
        AxisSize usedSize = new AxisSize(axis, new Size(0, 0));

        Float subLayoutLengthForEqualSizeDistribution;
        if (distribution == StackLayoutDistribution.FILL_EQUAL_SIZE) {
            subLayoutLengthForEqualSizeDistribution = sublayoutSpaceForEqualSizeDistribution(
                    availableSize.getAxisLength(), subLayouts.size());
        } else {
            subLayoutLengthForEqualSizeDistribution = null;
        }

        List<Layout> sublayoutsByAxisFlexibilityAscending = new ArrayList<>();
        sublayoutsByAxisFlexibilityAscending.addAll(subLayouts);
        Collections.sort(sublayoutsByAxisFlexibilityAscending, new Comparator<Layout>() {
            @Override
            public int compare(Layout left, Layout right) {
                Integer leftFlex = left.getFlexibility().flex(axis);
                Integer rightFlex = right.getFlexibility().flex(axis);
                if (leftFlex == null && rightFlex == null) return 0;
                if (leftFlex == null) return -1;
                if (rightFlex == null) return 1;
                return leftFlex.compareTo(rightFlex);
            }
        });

        Layout subLayout;
        for (int i = 0, length = sublayoutsByAxisFlexibilityAscending.size();i < length;i++) {
            if (availableSize.getAxisLength() <= 0 || availableSize.getCrossLength() <= 0) {
                // There is no more room in the stack so don't bother measuring the rest of the sublayouts.
                break;
            }
            subLayout = sublayoutsByAxisFlexibilityAscending.get(i);

            Size sublayoutMasurementAvailableSize;
            if  (subLayoutLengthForEqualSizeDistribution != null) {
                sublayoutMasurementAvailableSize = new AxisSize(axis,
                        subLayoutLengthForEqualSizeDistribution,
                        availableSize.getCrossLength()).getSize();
            } else {
                sublayoutMasurementAvailableSize = availableSize.getSize();
            }

            LayoutMeasurement sublayoutMeasurement = subLayout.measurement(sublayoutMasurementAvailableSize);
            sublayoutMeasurements.add(i, sublayoutMeasurement);
            AxisSize sublayoutAxisSize = new AxisSize(axis, sublayoutMeasurement.getSize());

            if (sublayoutAxisSize.getAxisLength() > 0) {
                // If we are the first sublayout in the stack, then no leading spacing is required.
                // Otherwise account for the spacing.
                float leadingSpacing = (usedSize.getAxisLength() > 0) ? spacing : 0;
                usedSize.setAxisLength(usedSize.getCrossLength() + leadingSpacing + sublayoutAxisSize.getAxisLength());
                usedSize.setCrossLength(Math.max(usedSize.getCrossLength(), sublayoutAxisSize.getCrossLength()));

                // Reserve spacing for the next sublayout.
                availableSize.setAxisLength(availableSize.getAxisLength() - sublayoutAxisSize.getAxisLength() + spacing);
            }
        }

        List<LayoutMeasurement> nonNilMeasuredSublayouts = new ArrayList<>();
        for (LayoutMeasurement measurement : sublayoutMeasurements) {
            if (measurement != null) {
                nonNilMeasuredSublayouts.add(measurement);
            }
        }

        if (distribution == StackLayoutDistribution.FILL_EQUAL_SIZE && !nonNilMeasuredSublayouts.isEmpty()) {
            float maxAxisLength = 0;
            for (LayoutMeasurement measurement : nonNilMeasuredSublayouts) {
                float currentAxisLength = new AxisSize(axis, measurement.getSize()).getAxisLength();
                if (maxAxisLength < currentAxisLength) {
                    maxAxisLength = currentAxisLength;
                }
            }
            //let maxAxisLength = nonNilMeasuredSublayouts.map({ new AxisSize(axis, size: $0.size).axisLength }).max() ?? 0
            usedSize.setAxisLength((maxAxisLength + spacing) * nonNilMeasuredSublayouts.size() - spacing);
        }

        return new LayoutMeasurement(this, usedSize.getSize(), maxSize, nonNilMeasuredSublayouts);
    }

    public LayoutArrangement arrangement(Rect rect, LayoutMeasurement measurement) {
        Rect frame = getAlignment().position(measurement.getSize(), rect);
        AxisSize availableSize = new AxisSize(axis, frame.getSize());
        float excessAxisLength = availableSize.getAxisLength() - new AxisSize(axis, measurement.getSize()).getAxisLength();
        DistributionConfig config = distributionConfig(excessAxisLength);

        AxisPoint nextOrigin = new AxisPoint(axis, config.initialAxisOffset, 0);
        List<LayoutArrangement> sublayoutArrangements = new ArrayList<>();
        List<LayoutMeasurement> sublayoutMeasurements = measurement.getSubLayouts();
        LayoutMeasurement sublayout;
        for (int i = 0, length = sublayoutMeasurements.size(); i < length; i++) {
            sublayout = sublayoutMeasurements.get(i);
            AxisSize sublayoutAvailableSize = new AxisSize(axis, sublayout.getSize());
            sublayoutAvailableSize.setCrossLength(availableSize.getCrossLength());
            if (distribution == StackLayoutDistribution.FILL_EQUAL_SIZE) {
                sublayoutAvailableSize.setAxisLength(sublayoutSpaceForEqualSizeDistribution(
                        new AxisSize(axis, frame.getSize()).getAxisLength(),
                        measurement.getSubLayouts().size()));
            } else if (config.stretchIndex != null && config.stretchIndex == i) {
                sublayoutAvailableSize.setAxisLength(sublayoutAvailableSize.getAxisLength() + excessAxisLength);
            }
            LayoutArrangement sublayoutArrangement = sublayout.arrangement(new Rect(nextOrigin.getPoint(), sublayoutAvailableSize.getSize()));
            sublayoutArrangements.add(sublayoutArrangement);
            nextOrigin.setAxisOffset(nextOrigin.getAxisOffset() + sublayoutAvailableSize.getAxisLength());
            if (sublayoutAvailableSize.getAxisLength() > 0) {
                // Only add spacing below a view if it was allocated non-zero height.
                nextOrigin.setAxisOffset(nextOrigin.getAxisOffset() + config.axisSpacing);
            }
        }
        return new LayoutArrangement(this, frame, sublayoutArrangements);
    }

    private float sublayoutSpaceForEqualSizeDistribution(float totalAvailableSpace, int sublayoutCount) {
        if (sublayoutCount == 0) {
            return totalAvailableSpace;
        }
        if (spacing == 0) {
            return totalAvailableSpace / sublayoutCount;//Todo: maybe float is needed for division?
        }
        // Note: we don't actually need to check for zero spacing above, because division by zero produces a valid result for floating point values.
        // We check anyway for the sake of clarity.
        float maxSpacings = (float) Math.floor(totalAvailableSpace / spacing);//Todo: check if float is working
        float visibleSublayoutCount = Math.min(sublayoutCount, maxSpacings + 1);
        float spaceAvailableForSublayouts = totalAvailableSpace - (visibleSublayoutCount - 1) * spacing;
        return spaceAvailableForSublayouts / visibleSublayoutCount;
    }

    private DistributionConfig distributionConfig(float excessAxisLength) {
        float initialAxisOffset;
        float axisSpacing;
        Integer stretchIndex = null;
        switch (distribution) {
            case LEADING:
                initialAxisOffset = 0;
                axisSpacing = spacing;
                break;
            case TRAILING:
                initialAxisOffset = excessAxisLength;
                axisSpacing = spacing;
                break;
            case CENTER:
                initialAxisOffset = excessAxisLength / 2.0f;
                axisSpacing = spacing;
                break;
            case FILL_EQUAL_SPACING:
                initialAxisOffset = 0;
                axisSpacing = Math.max(spacing, excessAxisLength / (subLayouts.size() - 1));//Todo: maybe float is needed for division?
                break;
            case FILL_EQUAL_SIZE:
                initialAxisOffset = 0;
                axisSpacing = spacing;
                break;
            case FILL_FLEXING:
                axisSpacing = spacing;
                initialAxisOffset = 0;
                if (excessAxisLength > 0) {
                    stretchIndex = stretchableSublayoutIndex();
                }
                break;
            default:
                initialAxisOffset = 0;
                axisSpacing = 0;
                break;
        }
        return new DistributionConfig(initialAxisOffset, axisSpacing, stretchIndex);
    }

    private Integer stretchableSublayoutIndex() {
        Layout layout;
        Integer flex = null;
        Layout subLayout = null;
        int index = 0;
        //FIXME: use max Returns the maximum element in the sequence, using the given predicate as the comparison between elements.
        for (int i = 0, length = subLayouts.size(); i < length;i++) {
            layout = subLayouts.get(i);
            Integer currentFlex = layout.getFlexibility().flex(axis);
            if (currentFlex == null) {
                currentFlex = Integer.MIN_VALUE;
            }
            if (flex == null || flex < currentFlex) {
                subLayout = layout;
                flex = currentFlex;
                index = i;
            }
        }
        if (subLayout == null) {
            return null;
        }
        /*guard let (index, sublayout) = sublayouts.enumerated().max(by: layoutsFlexibilityAscending) else {
            return nil
        }*/
        if (subLayout.getFlexibility().flex(axis) == null) {
            // The most flexible sublayout is still not flexible, so don't stretch it.
            return null;
        }
        return index;
    }

    /*private boolean compare(int leftIndex, int rightIndex, Layout left, Layout right) {
        Integer leftFlex = left.getFlexibility().flex(axis);
        Integer rightFlex = right.getFlexibility().flex(axis);
        if (leftFlex != null && rightFlex != null) {
            if (leftFlex.equals(rightFlex)) {
                return leftIndex < rightIndex;
            }
        }
        // null is less than all integers
        return (leftFlex == null ? Integer.MIN_VALUE : leftFlex) < (rightFlex == null ? Integer.MIN_VALUE : rightFlex);
    }*/

    private static Flexibility defaultFlexibility(Axis axis, List<? extends Layout> sublayouts) {
        AxisFlexibility initial = new AxisFlexibility(axis, null, Integer.MAX_VALUE);
        for (Layout sublayout : sublayouts) {
            AxisFlexibility subflex = new AxisFlexibility(axis, sublayout.getFlexibility());
            Integer axisFlex = Flexibility.max(initial.getAxisFlex(), subflex.getAxisFlex());
            Integer crossFlex = Flexibility.min(initial.getCrossFlex(), subflex.getCrossFlex());
            initial = new AxisFlexibility(axis, axisFlex, crossFlex);
        }
        /*return sublayouts.reduce(initial) { (flexibility:AxisFlexibility, sublayout: Layout) -> AxisFlexibility in
            AxisFlexibility subflex = new AxisFlexibility(axis, sublayout.flexibility);
            let axisFlex = new Flexibility.max(flexibility.axisFlex, subflex.axisFlex);
            let crossFlex = new Flexibility.min(flexibility.crossFlex, subflex.crossFlex);
            return new AxisFlexibility(axis, axisFlex, crossFlex);
        }.flexibility;*/
        return initial.getFlexibility();
    }

    @Override
    public void configure(BaseView baseTypeView) {

    }
}
