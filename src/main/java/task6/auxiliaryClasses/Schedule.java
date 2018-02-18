package task6.auxiliaryClasses;


import java.util.ArrayList;
import java.util.List;

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

    public Point searchValue(int argument) {
        int answer = 0;

        for (int i = 0; i < collectionPoint.size(); i++) {
            double bufAnswer = 1;
            Point pointI = collectionPoint.get(i);
            for (int j = 0; j < collectionPoint.size(); j++) {
                if (i != j) {
                    Point pointJ = collectionPoint.get(j);
                    bufAnswer *= (argument - pointJ.getX()) / (pointI.getX() - pointJ.getX());
                }
            }
            answer += bufAnswer * pointI.getY();
        }
        return new Point(argument, answer);
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
