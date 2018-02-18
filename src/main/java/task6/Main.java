package main.java.task6;

import main.java.task6.auxiliaryClasses.Point;
import main.java.task6.auxiliaryClasses.Schedule;
import task2.auxiliaryClasses.Point;
import task2.auxiliaryClasses.Schedule;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> pointList = Arrays.asList(new Point(1, 2),
                new Point(3, 1), new Point(19, 2), new Point(43, 4));
        Schedule schedule = new Schedule(pointList);
        System.out.println(schedule.neighbor(new Point(19, 1)));

    }
}
