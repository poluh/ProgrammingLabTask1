package task6.auxiliaryClasses;

import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

class ScheduleTest {

    private List<Point> pointList = Arrays.asList(new Point(1, 2),
            new Point(2, 4), new Point(3, 6), new Point(4, 8),
            new Point(5, 10), new Point(6, 12), new Point(8, 16));
    private Schedule schedule = new Schedule(pointList);

    @org.junit.jupiter.api.Test
    void neighbor() {
        Assertions.assertEquals(new Point(8, 16), schedule.neighbor(new Point(7, 20)));
    }

    @org.junit.jupiter.api.Test
    void searchValue() {
        Assertions.assertEquals(new Point(7, 14), schedule.searchValue(7));
    }
}