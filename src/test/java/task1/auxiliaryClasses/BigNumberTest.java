package task1.auxiliaryClasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    @Test
    void isGreater() {
        assertTrue(new BigNumber("112312321323").isGreater(new BigNumber("112312321322")));
        assertTrue(new BigNumber("13213123123123").isGreater(new BigNumber("-13243243242343243423434")));
        assertTrue(new BigNumber("-54545624532").isGreater(new BigNumber("3234872847438")));
        assertTrue(new BigNumber("-4325244261346").isGreater(new BigNumber("-9852982598423938")));
    }

    @Test
    void isNegative() {
        assertTrue(new BigNumber("-1").isNegative());
        assertFalse(new BigNumber("1").isNegative());
    }

    @Test
    void maxOf() {
        assertEquals(new BigNumber("112312321323"),
                BigNumber.maxOf(new BigNumber("112312321323"), new BigNumber("112312321322")));
        assertEquals(new BigNumber("13213123123123"),
                BigNumber.maxOf(new BigNumber("13213123123123"), new BigNumber("-13243243242343243423434")));
        assertEquals(new BigNumber("-54545624532"),
                BigNumber.maxOf(new BigNumber("-54545624532"), new BigNumber("3234872847438")));
        assertEquals(new BigNumber("-4325244261346"),
                BigNumber.maxOf(new BigNumber("-4325244261346"), new BigNumber("-9852982598423938")));
        assertEquals(new BigNumber("321"), BigNumber.maxOf(new BigNumber("123"), new BigNumber("321")));
    }

    @Test
    void minOf() {
        assertEquals(new BigNumber("112312321322"),
                BigNumber.minOf(new BigNumber("112312321323"), new BigNumber("112312321322")));
        assertEquals(new BigNumber("-13243243242343243423434"),
                BigNumber.minOf(new BigNumber("-54545624532"), new BigNumber("-13243243242343243423434")));
        assertEquals(new BigNumber("-3234872847438"),
                BigNumber.minOf(new BigNumber("-54545624532"), new BigNumber("-3234872847438")));
        assertEquals(new BigNumber("-9852982598423938"),
                BigNumber.minOf(new BigNumber("4325244261346"), new BigNumber("-9852982598423938")));
    }

    @Test
    void plus() {
        assertEquals(new BigNumber("1876748"), new BigNumber("938374").plus(new BigNumber("938374")));
        assertEquals(new BigNumber("93682766"), new BigNumber("93248423").plus(new BigNumber("434343")));
        assertEquals(new BigNumber("88854654"), new BigNumber("-34234").plus(new BigNumber("88888888")));
        assertEquals(new BigNumber("-3482"), new BigNumber("-3494").plus(new BigNumber("12")));
        assertEquals(new BigNumber("-123"), new BigNumber("-23").plus(new BigNumber("-100")));

        BigNumber a = new BigNumber("10");
        BigNumber b = new BigNumber("-5");
        BigNumber c = a.plus(b);
        assertEquals(new BigNumber("0"), b.plus(c));
    }

    @Test
    void minus() {
        assertEquals(new BigNumber("0"), new BigNumber("938374").minus(new BigNumber("938374")));
        assertEquals(new BigNumber("92814080"), new BigNumber("93248423").minus(new BigNumber("434343")));
        assertEquals(new BigNumber("-88923122"), new BigNumber("-34234").minus(new BigNumber("88888888")));
        assertEquals(new BigNumber("-3506"), new BigNumber("-3494").minus(new BigNumber("12")));
        assertEquals(new BigNumber("77"), new BigNumber("-23").minus(new BigNumber("-100")));
        assertEquals(new BigNumber("-77"), new BigNumber("-100").minus(new BigNumber("-23")));
        assertEquals(new BigNumber("-198"), new BigNumber("123").minus(new BigNumber("321")));
    }

    @Test
    void times() {
        assertEquals(new BigNumber("1013449554"), new BigNumber("1233").times(new BigNumber("821938")));
        assertEquals(new BigNumber("1013449554"), new BigNumber("821938").times(new BigNumber("1233")));
        assertEquals(new BigNumber("-5748348"), new BigNumber("5748348").times(new BigNumber("-1")));
        assertEquals(new BigNumber("15129"), new BigNumber("-123").times(new BigNumber("-123")));
        assertEquals(new BigNumber("1519325480"), new BigNumber("1231220").times(new BigNumber("1234")));
    }

    @Test
    void delNegative() {
        BigNumber bigNumber = new BigNumber("-123123");
        bigNumber.delNegative();
        assertEquals(new BigNumber("123123"), bigNumber);
    }

}