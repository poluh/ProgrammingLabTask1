package main.java.task6.auxiliaryClasses;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
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
