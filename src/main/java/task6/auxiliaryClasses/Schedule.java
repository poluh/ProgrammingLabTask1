package main.java.task6.auxiliaryClasses;

import task2.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schedule {
    private List<Point> collectionPoint = new ArrayList<>();

    Schedule() {
    }

    public Schedule(List<Point> points) {
        this.collectionPoint = points;
    }

    public void add(Point point) {
        collectionPoint.add(point);
    }

    public void remove(Point point) {
        collectionPoint.remove(point);
    }

    public List<Point> getCollectionPoint() {
        return collectionPoint;
    }

    public Point neighbor(Point sought) {
        double length = sought.distance(collectionPoint.get(0));
        Point answer = collectionPoint.get(0);
        for (Point point : collectionPoint) {
            if (!sought.equals(point)) {
                if (sought.distance(point) < length) {
                    length = sought.distance(point);
                    answer = point;
                }
            }
        }
        return answer;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (Point point : collectionPoint) {
            answer.append(point).append(" ");
        }
        return answer.toString().trim();
    }
}
