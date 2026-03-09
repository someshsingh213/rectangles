package rectangles;

/**
 * Rectangle entity: axis-aligned, defined by left, right, bottom, top.
 * Construct from any two opposite corners (x1,y1), (x2,y2); coordinates are normalized.
 */
public final class Rectangle {
    private final double left;
    private final double right;
    private final double bottom;
    private final double top;

    /**
     * Creates a rectangle from any two opposite corners.
     * The constructor normalizes coordinates so that left < right and bottom < top.
     */
    public Rectangle(double x1, double y1, double x2, double y2) {
        this.left = Math.min(x1, x2);
        this.right = Math.max(x1, x2);
        this.bottom = Math.min(y1, y2);
        this.top = Math.max(y1, y2);

        // Simple check for non-zero width/height in this exercise.
        // If this was production I would have checked for floating point errors
        // by using a small tolerance instead of exact equality. 
        if (left == right || bottom == top) {
            throw new IllegalArgumentException("Rectangle must have positive area (non-zero width and height).");
        }
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getBottom() {
        return bottom;
    }

    public double getTop() {
        return top;
    }

    public double getWidth() {
        return right - left;
    }

    public double getHeight() {
        return top - bottom;
    }
}

