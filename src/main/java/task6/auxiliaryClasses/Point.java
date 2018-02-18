package task6.auxiliaryClasses;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        return (this.x == ((Point) object).x) && (this.y == ((Point) object).y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
