package rectangles;

import java.util.List;

/**
 * Test harness for the rectangle exercise (Intersection, Containment, Adjacency).
 *
 * Run: javac rectangles/*.java  &&  java rectangles.RectangleTest
 */
public final class RectangleTest {

    private static final Rectangle BASE = new Rectangle(0, 0, 10, 10);

    public static void main(String[] args) {
        testIntersectionOverlapping();
        testIntersectionCornerTouch();
        testIntersectionOneInsideOther();
        testIntersectionDisjoint();
        testContainmentWhollyInside();
        testContainmentEdgesTouchingBoundary();
        testContainmentNotContainedReverse();
        testContainmentDisjoint();
        testContainmentOverlappingNotContained();
        testAdjacencyProperRight();
        testAdjacencySubLineRight();
        testAdjacencyPartialRight();
        testAdjacencyNoneGap();
        testAdjacencyNoneOverlapping();
        testAdjacencyProperTop();
        testAdjacencySubLineTop();
        testAdjacencyPartialTop();
        testAdjacencyProperLeft();
        testAdjacencyProperBottom();
        testAnalyzerRelationshipCount();
        testAnalyzerContainmentDetected();
        testAnalyzerProperAdjacencyDetected();
        testAnalyzerOneContainmentPair();
        testAnalyzerOneProperAdjacencyPair();
        System.out.println("All tests passed.");
    }

    // --- Helpers ---
    private static void assertIntersectionHasPoints(Rectangle a, Rectangle b, String msg, double... xy) {
        List<Point> points = RectangleRelations.intersectionPoints(a, b);
        for (int i = 0; i < xy.length; i += 2) {
            assertTrue(hasPoint(points, xy[i], xy[i + 1]), msg);
        }
    }

    private static void assertIntersectionEmpty(Rectangle a, Rectangle b, String msg) {
        assertTrue(RectangleRelations.intersectionPoints(a, b).isEmpty(), msg);
    }

    private static void assertContains(boolean expected, Rectangle outer, Rectangle inner, String msg) {
        assertTrue(RectangleRelations.contains(outer, inner) == expected, msg);
    }

    private static void assertAdjacency(Rectangle a, Rectangle b, RectangleRelations.AdjacencyType expected, String msg) {
        assertEquals(expected, RectangleRelations.adjacency(a, b), msg);
    }

    private static int countContainmentPairs(List<RectangleAnalyzer.Relationship> rels) {
        int n = 0;
        for (RectangleAnalyzer.Relationship r : rels) {
            if (r.isFirstContainsSecond() || r.isSecondContainsFirst()) n++;
        }
        return n;
    }

    private static int countProperAdjacencyPairs(List<RectangleAnalyzer.Relationship> rels) {
        int n = 0;
        for (RectangleAnalyzer.Relationship r : rels) {
            if (r.getAdjacencyType() == RectangleRelations.AdjacencyType.PROPER) n++;
        }
        return n;
    }

    // --- Intersection ---
    private static void testIntersectionOverlapping() {
        assertIntersectionHasPoints(new Rectangle(0, 0, 10, 10), new Rectangle(5, -5, 15, 5),
            "Overlapping: points (5,0) and (10,5)", 5, 0, 10, 5);
        System.out.println("  Intersection (overlapping): passed");
    }

    private static void testIntersectionCornerTouch() {
        assertIntersectionHasPoints(new Rectangle(0, 0, 10, 10), new Rectangle(10, 0, 20, 10),
            "Corner touch: (10,0)", 10, 0);
        System.out.println("  Intersection (corner touch): passed");
    }

    private static void testIntersectionOneInsideOther() {
        assertIntersectionEmpty(new Rectangle(0, 0, 10, 10), new Rectangle(2, 2, 8, 8),
            "One inside other: no border intersection");
        System.out.println("  Intersection (one inside other): passed");
    }

    private static void testIntersectionDisjoint() {
        assertIntersectionEmpty(new Rectangle(0, 0, 10, 10), new Rectangle(20, 20, 30, 30),
            "Disjoint: no intersection points");
        System.out.println("  Intersection (disjoint): passed");
    }

    // --- Containment ---
    private static void testContainmentWhollyInside() {
        assertContains(true, BASE, new Rectangle(2, 2, 8, 8), "Inner wholly contained in outer");
        System.out.println("  Containment (wholly inside): passed");
    }

    private static void testContainmentEdgesTouchingBoundary() {
        assertContains(true, BASE, new Rectangle(0, 0, 5, 5), "Contained with shared edges");
        System.out.println("  Containment (edges touching boundary): passed");
    }

    private static void testContainmentNotContainedReverse() {
        assertContains(false, new Rectangle(2, 2, 8, 8), BASE, "Outer not contained in inner");
        System.out.println("  Containment (not contained, reverse): passed");
    }

    private static void testContainmentDisjoint() {
        assertContains(false, new Rectangle(2, 2, 8, 8), new Rectangle(-1, -1, 1, 1), "Disjoint not contained");
        System.out.println("  Containment (disjoint): passed");
    }

    private static void testContainmentOverlappingNotContained() {
        Rectangle overlapping = new Rectangle(5, 5, 15, 15);
        assertContains(false, BASE, overlapping, "Overlapping but not contained");
        assertContains(false, overlapping, BASE, "Overlapping but not contained (reverse)");
        System.out.println("  Containment (overlapping but not contained): passed");
    }

    // --- Adjacency ---
    private static void testAdjacencyProperRight() {
        assertAdjacency(BASE, new Rectangle(10, 0, 20, 10), RectangleRelations.AdjacencyType.PROPER, "Proper (right)");
        System.out.println("  Adjacency (proper, right): passed");
    }

    private static void testAdjacencySubLineRight() {
        assertAdjacency(BASE, new Rectangle(10, 2, 20, 8), RectangleRelations.AdjacencyType.SUB_LINE, "Sub-line (right)");
        System.out.println("  Adjacency (sub-line, right): passed");
    }

    private static void testAdjacencyPartialRight() {
        assertAdjacency(BASE, new Rectangle(10, -2, 20, 5), RectangleRelations.AdjacencyType.PARTIAL, "Partial (right)");
        System.out.println("  Adjacency (partial, right): passed");
    }

    private static void testAdjacencyNoneGap() {
        assertAdjacency(BASE, new Rectangle(11, 0, 21, 10), RectangleRelations.AdjacencyType.NONE, "No adjacency (gap)");
        System.out.println("  Adjacency (none, gap): passed");
    }

    private static void testAdjacencyNoneOverlapping() {
        assertAdjacency(BASE, new Rectangle(5, 0, 15, 10), RectangleRelations.AdjacencyType.NONE, "Overlapping: not adjacent");
        System.out.println("  Adjacency (none, overlapping): passed");
    }

    private static void testAdjacencyProperTop() {
        assertAdjacency(BASE, new Rectangle(0, 10, 10, 20), RectangleRelations.AdjacencyType.PROPER, "Proper (top)");
        System.out.println("  Adjacency (proper, top): passed");
    }

    private static void testAdjacencySubLineTop() {
        assertAdjacency(BASE, new Rectangle(2, 10, 8, 20), RectangleRelations.AdjacencyType.SUB_LINE, "Sub-line (top)");
        System.out.println("  Adjacency (sub-line, top): passed");
    }

    private static void testAdjacencyPartialTop() {
        assertAdjacency(BASE, new Rectangle(-2, 10, 5, 20), RectangleRelations.AdjacencyType.PARTIAL, "Partial (top)");
        System.out.println("  Adjacency (partial, top): passed");
    }

    private static void testAdjacencyProperLeft() {
        assertAdjacency(BASE, new Rectangle(-10, 0, 0, 10), RectangleRelations.AdjacencyType.PROPER, "Proper (left)");
        System.out.println("  Adjacency (proper, left): passed");
    }

    private static void testAdjacencyProperBottom() {
        assertAdjacency(BASE, new Rectangle(0, -10, 10, 0), RectangleRelations.AdjacencyType.PROPER, "Proper (bottom)");
        System.out.println("  Adjacency (proper, bottom): passed");
    }

    // --- Analyzer ---
    private static List<RectangleAnalyzer.Relationship> analyzerThreeRects() {
        return RectangleAnalyzer.analyzeAll(java.util.Arrays.asList(
            new Rectangle(0, 0, 10, 10), new Rectangle(2, 2, 8, 8), new Rectangle(10, 0, 20, 10)));
    }

    private static void testAnalyzerRelationshipCount() {
        assertEquals(3, analyzerThreeRects().size(), "Expected three pairwise relationships");
        System.out.println("  Analyzer (relationship count for 3 rectangles): passed");
    }

    private static void testAnalyzerContainmentDetected() {
        assertTrue(countContainmentPairs(analyzerThreeRects()) >= 1, "Analyzer should detect at least one containment");
        System.out.println("  Analyzer (containment detected): passed");
    }

    private static void testAnalyzerProperAdjacencyDetected() {
        assertTrue(countProperAdjacencyPairs(analyzerThreeRects()) >= 1, "Analyzer should detect at least one proper adjacency");
        System.out.println("  Analyzer (proper adjacency detected): passed");
    }

    private static void testAnalyzerOneContainmentPair() {
        assertEquals(1, countContainmentPairs(analyzerThreeRects()), "Exactly one containment pair (base contains inner)");
        System.out.println("  Analyzer (one containment pair): passed");
    }

    private static void testAnalyzerOneProperAdjacencyPair() {
        assertEquals(1, countProperAdjacencyPairs(analyzerThreeRects()), "Exactly one proper adjacency pair (base-right)");
        System.out.println("  Analyzer (one proper adjacency pair): passed");
    }

    private static boolean hasPoint(List<Point> points, double x, double y) {
        for (Point p : points) {
            if (Double.compare(p.getX(), x) == 0 && Double.compare(p.getY(), y) == 0) {
                return true;
            }
        }
        return false;
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("Assertion failed: " + message +
                " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}

