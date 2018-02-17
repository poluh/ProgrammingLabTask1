package task1.auxiliaryClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BigFactionalTest {

    @org.junit.jupiter.api.Test
    void plus() {
        assertEquals(new BigFactional("246.246"),
                new BigFactional("123.123").plus(new BigFactional("123.123")));

        assertEquals(new BigFactional("14884.55301"),
                new BigFactional("12.93").plus(new BigFactional("14871.62301")));

        assertEquals(new BigFactional("-292012.781111"),
                new BigFactional("-349321.123").plus(new BigFactional("57309.658111")));

        assertEquals(new BigFactional("-224084.404807"),
                new BigFactional("-50179.404404").plus(new BigFactional("-173905.000403")));

        assertEquals(new BigFactional("72.000000922"),
                new BigFactional("0.000000001").plus(new BigFactional("72.000000921")));

        assertEquals(new BigFactional("8800559014.328771"),
                new BigFactional("8800559014.188301").plus(new BigFactional("0.14047")));
    }

    @Test
    void minus() {
        assertEquals(new BigFactional("133.132999"),
                new BigFactional("256.256").minus(new BigFactional("123.123001")));
        assertEquals(new BigFactional("-198.198"),
                new BigFactional("123.123").minus(new BigFactional("321.321")));
        assertEquals(new BigFactional("-4665.661"),
                new BigFactional("-123.321").minus(new BigFactional("4542.340")));
        assertEquals(new BigFactional("1.0"),
                new BigFactional("-123.123").minus(new BigFactional("-124.123")));
    }

    @Test
    void times() {
        assertEquals(new BigFactional("15156.4413"),
                new BigFactional("123.123").times(new BigFactional("123.1")));
        assertEquals(new BigFactional("-321.230"),
                new BigFactional("321.23").times(new BigFactional("-1.0")));
        assertEquals(new BigFactional("8647.66051"),
                new BigFactional("-213.1").times(new BigFactional("-43.21")));
    }
}