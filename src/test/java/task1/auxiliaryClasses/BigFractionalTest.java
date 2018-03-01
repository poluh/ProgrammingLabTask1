package task1.auxiliaryClasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigFractionalTest {

    @org.junit.jupiter.api.Test
    void plus() {
        assertEquals(new BigFractional("246.246"),
                new BigFractional("123.123").plus(new BigFractional("123.123")));

        assertEquals(new BigFractional("14884.55301"),
                new BigFractional("12.93").plus(new BigFractional("14871.62301")));

        assertEquals(new BigFractional("-292012.535111"),
                new BigFractional("-349321.123").plus(new BigFractional("57309.658111")));

        assertEquals(new BigFractional("-224084.404807"),
                new BigFractional("-50179.404404").plus(new BigFractional("-173905.000403")));

        assertEquals(new BigFractional("72.000000922"),
                new BigFractional("0.000000001").plus(new BigFractional("72.000000921")));
        assertEquals(new BigFractional("8800559014.328771"),
                new BigFractional("8800559014.188301").plus(new BigFractional("0.14047")));

    }

    @Test
    void minus() {
        assertEquals(new BigFractional("133.132999"),
                new BigFractional("256.256").minus(new BigFractional("123.123001")));
        assertEquals(new BigFractional("-198.198"),
                new BigFractional("123.123").minus(new BigFractional("321.321")));
        assertEquals(new BigFractional("-4665.661"),
                new BigFractional("-123.321").minus(new BigFractional("4542.340")));
        assertEquals(new BigFractional("1.0"),
                new BigFractional("-123.123").minus(new BigFractional("-124.123")));
    }

    @Test
    void times() {
        assertEquals(new BigFractional("15252.09"),
                new BigFractional("123.9").times(new BigFractional("123.1")));
        assertEquals(new BigFractional("-321.23"),
                new BigFractional("321.23").times(new BigFractional("-1.0")));
        assertEquals(new BigFractional("9208.051"),
                new BigFractional("-213.1").times(new BigFractional("-43.21")));
    }

    @Test
    void round() {
        assertEquals(new BigFractional("123.120"), new BigFractional("123.123").round(2));
        assertEquals(new BigFractional("123.130"), new BigFractional("123.126").round(2));
    }
}