package rectangles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algorithms operating on rectangles:
 * - intersection: compute intersection points between rectangle borders
 * - containment: check whether one rectangle is wholly contained in another
 * - adjacency: detect and classify side adjacency (proper, sub-line, partial)
 */
public final class RectangleRelations {

    private RectangleRelations() {
    }

    public enum AdjacencyType {
        NONE,
        PROPER,
        SUB_LINE,
        PARTIAL
    }

    /**
     * Returns true if inner rectangle is wholly contained within outer rectangle.
     * Edges lying exactly on the border of outer rectangle are considered contained.
     */
    public static boolean contains(Rectangle outer, Rectangle inner) {
        return inner.getLeft() >= outer.getLeft()
            && inner.getRight() <= outer.getRight()
            && inner.getBottom() >= outer.getBottom()
            && inner.getTop() <= outer.getTop();
    }

    /**
     * Computes all distinct intersection points between the borders of two rectangles.
     * Every such point is where one rect's vertical edge meets the other's horizontal edge.
     */
    public static List<Point> intersectionPoints(Rectangle a, Rectangle b) {
        List<Point> points = new ArrayList<>();
        addEdgeIntersections(points, a, b);
        addEdgeIntersections(points, b, a);
        return Collections.unmodifiableList(points);
    }

    /** Points where vertical edges of vert meet horizontal edges of horiz. */
    private static void addEdgeIntersections(List<Point> points, Rectangle vert, Rectangle horiz) {
        for (double x : new double[]{vert.getLeft(), vert.getRight()}) {
            for (double y : new double[]{horiz.getBottom(), horiz.getTop()}) {
                if (between(x, horiz.getLeft(), horiz.getRight()) && between(y, vert.getBottom(), vert.getTop())) {
                    points.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Detects adjacency: sharing a side without overlapping area.
     * Check vertical (left/right) and horizontal (bottom/top); return the stronger type.
     */
    public static AdjacencyType adjacency(Rectangle a, Rectangle b) {
        if (hasAreaOverlap(a, b)) {
            return AdjacencyType.NONE;
        }
        AdjacencyType vertical = verticalAdjacency(a, b);
        AdjacencyType horizontal = horizontalAdjacency(a, b);
        return strongerOf(vertical, horizontal);
    }

    /** Adjacency on left/right: touch when a.right==b.left or a.left==b.right; then compare overlap on y. */
    private static AdjacencyType verticalAdjacency(Rectangle a, Rectangle b) {
        boolean touch = (a.getRight() == b.getLeft()) || (a.getLeft() == b.getRight());
        if (!touch) return AdjacencyType.NONE;
        double overlapY = overlapLength(a.getBottom(), a.getTop(), b.getBottom(), b.getTop());
        return classifyOverlap(overlapY, a.getHeight(), b.getHeight());
    }

    /** Adjacency on bottom/top: touch when a.top==b.bottom or a.bottom==b.top; then compare overlap on x. */
    private static AdjacencyType horizontalAdjacency(Rectangle a, Rectangle b) {
        boolean touch = (a.getTop() == b.getBottom()) || (a.getBottom() == b.getTop());
        if (!touch) return AdjacencyType.NONE;
        double overlapX = overlapLength(a.getLeft(), a.getRight(), b.getLeft(), b.getRight());
        return classifyOverlap(overlapX, a.getWidth(), b.getWidth());
    }

    /** PROPER = full side shared; SUB_LINE = one side contained in other; PARTIAL = segment only. */
    private static AdjacencyType classifyOverlap(double overlap, double len1, double len2) {
        if (overlap <= 0.0) {
            return AdjacencyType.NONE;
        }
        double minLen = Math.min(len1, len2);
        if (overlap == len1 && overlap == len2) {
            return AdjacencyType.PROPER;
        }
        if (overlap == minLen) {
            return AdjacencyType.SUB_LINE;
        }
        return AdjacencyType.PARTIAL;
    }

    private static boolean hasAreaOverlap(Rectangle a, Rectangle b) {
        double w = overlapLength(a.getLeft(), a.getRight(), b.getLeft(), b.getRight());
        double h = overlapLength(a.getBottom(), a.getTop(), b.getBottom(), b.getTop());
        return w > 0.0 && h > 0.0;
    }

    /** Length of overlap of two intervals [start1,end1] and [start2,end2]. */
    private static double overlapLength(double start1, double end1, double start2, double end2) {
        double start = Math.max(start1, start2);
        double end = Math.min(end1, end2);
        return end - start;
    }

    private static boolean between(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /** Stronger adjacency wins: PROPER > SUB_LINE > PARTIAL. */
    private static AdjacencyType strongerOf(AdjacencyType a, AdjacencyType b) {
        if (a == AdjacencyType.PROPER || b == AdjacencyType.PROPER) return AdjacencyType.PROPER;
        if (a == AdjacencyType.SUB_LINE || b == AdjacencyType.SUB_LINE) return AdjacencyType.SUB_LINE;
        if (a == AdjacencyType.PARTIAL || b == AdjacencyType.PARTIAL) return AdjacencyType.PARTIAL;
        return AdjacencyType.NONE;
    }
}

