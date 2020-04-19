package pixeleditor.domain;

/**
 * Represents a point on a 2D plane.
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * Creates a new point.
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
    */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
