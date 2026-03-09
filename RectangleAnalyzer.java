package rectangles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Higher-level analysis utilities operating on sets of rectangles.
 *
 * This is an "expansion" of the original exercise:
 * given an arbitrary collection of rectangles, it computes all pairwise
 * relationships (intersection points, containment, adjacency classification)
 * and can be invoked from the command line for quick exploration.
 */
public final class RectangleAnalyzer {

    private RectangleAnalyzer() {
    }

    /**
     * Immutable object describing the relationship between two rectangles.
     */
    public static final class Relationship {
        private final Rectangle first;
        private final Rectangle second;
        private final boolean firstContainsSecond;
        private final boolean secondContainsFirst;
        private final RectangleRelations.AdjacencyType adjacencyType;
        private final List<Point> intersectionPoints;

        public Relationship(
            Rectangle first,
            Rectangle second,
            boolean firstContainsSecond,
            boolean secondContainsFirst,
            RectangleRelations.AdjacencyType adjacencyType,
            List<Point> intersectionPoints
        ) {
            this.first = Objects.requireNonNull(first, "first");
            this.second = Objects.requireNonNull(second, "second");
            this.firstContainsSecond = firstContainsSecond;
            this.secondContainsFirst = secondContainsFirst;
            this.adjacencyType = Objects.requireNonNull(adjacencyType, "adjacencyType");
            this.intersectionPoints = Collections.unmodifiableList(new ArrayList<>(intersectionPoints));
        }

        public Rectangle getFirst() {
            return first;
        }

        public Rectangle getSecond() {
            return second;
        }

        public boolean isFirstContainsSecond() {
            return firstContainsSecond;
        }

        public boolean isSecondContainsFirst() {
            return secondContainsFirst;
        }

        public RectangleRelations.AdjacencyType getAdjacencyType() {
            return adjacencyType;
        }

        public List<Point> getIntersectionPoints() {
            return intersectionPoints;
        }
    }

    /**
     * Computes relationships for all unordered pairs of rectangles.
     */
    public static List<Relationship> analyzeAll(List<Rectangle> rectangles) {
        List<Relationship> results = new ArrayList<>();

        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = i + 1; j < rectangles.size(); j++) {
                Rectangle a = rectangles.get(i);
                Rectangle b = rectangles.get(j);

                boolean aContainsB = RectangleRelations.contains(a, b);
                boolean bContainsA = RectangleRelations.contains(b, a);
                RectangleRelations.AdjacencyType adjacency = RectangleRelations.adjacency(a, b);
                List<Point> intersections = RectangleRelations.intersectionPoints(a, b);

                results.add(new Relationship(a, b, aContainsB, bContainsA, adjacency, intersections));
            }
        }

        return Collections.unmodifiableList(results);
    }

    /**
     * Command-line entry point for exploring relationships between rectangles.
     *
     * Usage:
     *   java rectangles.RectangleAnalyzer rectangles.txt
     *
     * Each non-empty, non-comment line in the input file must contain four
     * whitespace-separated numbers:
     *   x1 y1 x2 y2
     *
     * representing two opposite corners of a rectangle.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java rectangles.RectangleAnalyzer <rectangles-file>");
            System.err.println("Each non-empty line: x1 y1 x2 y2  (whitespace-separated)");
            System.exit(1);
        }

        Path path = Path.of(args[0]);
        List<Rectangle> rectangles = readRectangles(path);
        if (rectangles.size() < 2) {
            System.out.println("Need at least two rectangles to analyze relationships.");
            return;
        }

        System.out.println("Analyzer: pairwise rectangle relationships");
        List<Relationship> relationships = analyzeAll(rectangles);
        for (Relationship rel : relationships) {
            System.out.println(formatSummary(rel));
        }
    }

    private static List<Rectangle> readRectangles(Path path) throws IOException {
        List<Rectangle> result = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            String[] parts = trimmed.split("\\s+");
            if (parts.length != 4) {
                throw new IllegalArgumentException(
                    "Invalid rectangle line (expected 4 numbers): " + line);
            }
            double x1 = Double.parseDouble(parts[0]);
            double y1 = Double.parseDouble(parts[1]);
            double x2 = Double.parseDouble(parts[2]);
            double y2 = Double.parseDouble(parts[3]);
            result.add(new Rectangle(x1, y1, x2, y2));
        }
        return result;
    }

    private static String formatSummary(Relationship rel) {
        String containment;
        if (rel.isFirstContainsSecond()) {
            containment = "first_contains_second";
        } else if (rel.isSecondContainsFirst()) {
            containment = "second_contains_first";
        } else {
            containment = "none";
        }

        int intersections = rel.getIntersectionPoints().size();

        return "  relation: containment=" + containment
            + ", adjacency=" + rel.getAdjacencyType()
            + ", intersectionPoints=" + intersections;
    }
}

