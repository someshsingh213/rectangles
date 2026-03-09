# Rectangles Exercise

Java implementation for the **Rectangles** exercise: algorithms that analyze rectangles and the relationships between them.

## Problem Description (from exercise)

You are required to implement:

1. **Intersection**: Determine whether two rectangles have one or more intersecting lines and produce the **points of intersection** (where their borders meet).
2. **Containment**: Determine whether a rectangle is **wholly contained** within another rectangle.
3. **Adjacency**: Detect whether two rectangles are **adjacent** (sharing at least one side). Side sharing is classified as:
   - **Proper**: one full side of rectangle A coincides with a full side of rectangle B.
   - **Sub-line**: one side of A is a line wholly contained on some side of B.
   - **Partial**: a line segment on a side of A lies on some line segment of B.

## What This Submission Includes

Per the exercise requirements:

1. **Implementation**: Rectangle entity (`Rectangle`, `Point`) and algorithms for intersection, containment, and adjacency (`RectangleRelations`).
2. **Documentation**: Javadoc on public types and methods; this README for build, run, and structure.
3. **Test cases**: `RectangleTest` covers intersection (overlapping, corner touch, one-inside-other, disjoint), containment (all variants), and adjacency (proper, sub-line, partial on all four sides).
4. **Expansion**: `RectangleAnalyzer` for pairwise analysis and optional CLI (see below).

## Project Structure

- **`Rectangle.java`**: Axis-aligned rectangle (left, right, bottom, top). Construct from any two opposite corners.
- **`Point.java`**: Immutable 2D point.
- **`RectangleRelations.java`**: Core algorithms: `intersectionPoints`, `contains`, `adjacency` (with `AdjacencyType`: NONE, PROPER, SUB_LINE, PARTIAL).
- **`RectangleAnalyzer.java`**: **Expansion**: pairwise analysis of a list of rectangles; optional CLI to run on a file.
- **`RectangleTest.java`**: Test harness: runs all tests and prints one line per passing scenario.
- **`rects.txt`**: Sample rectangle data (one rectangle per line: `x1 y1 x2 y2`).

## Build and Run

The exercise requires that the code **runs on Linux**. The instructions below work on Linux (and other Unix-like systems with bash) and on Windows.

**Requirements:** A Java compiler and runtime (no external libraries). Source is in package `rectangles`. Run from the **parent** of the `rectangles` folder (the project root).

**Bash / Linux (and macOS):**

```bash
# Compile
javac rectangles/*.java

# Run all tests
java rectangles.RectangleTest

# Run the Analyzer on a file (optional expansion)
java rectangles.RectangleAnalyzer rectangles/rects.txt
```

**Windows (PowerShell):** Use backslashes for paths: `javac rectangles\*.java`, then `java rectangles.RectangleTest` and `java rectangles.RectangleAnalyzer rectangles\rects.txt`.

## Dependencies

None. Plain Java; no frameworks or extra JARs. Runs on Linux, macOS, and Windows with a standard JDK.

## Expansion

- **RectangleAnalyzer**: Given a collection of rectangles, computes all pairwise relationships (containment, adjacency type, intersection points). Can be run from the command line with a text file where each non-empty, non-comment line has four numbers `x1 y1 x2 y2` for two opposite corners of a rectangle. See `rects.txt` for format.
