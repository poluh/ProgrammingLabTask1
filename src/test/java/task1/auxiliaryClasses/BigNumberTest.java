package task1.auxiliaryClasses;

import org.junit.jupiter.api.Test;
import task1.auxiliaryClasses.Collection.ArrayBigNumber;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    private BigInteger generate() {
        Random rnd = new Random();
        return new BigInteger(30, 100, rnd);
    }


    @Test
    void compareTo() {
        assertEquals(1, new BigNumber("3225454354353").compareTo(new BigNumber("3225454354352")));
        assertEquals(1, new BigNumber("13213123123123").compareTo(new BigNumber("-13243243242343243423434")));
        assertEquals(-1, new BigNumber("-54545624532").compareTo(new BigNumber("3234872847438")));
        assertEquals(1, new BigNumber("-4325244261346").compareTo(new BigNumber("-9852982598423938")));
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
        assertEquals(new BigNumber("3234872847438"),
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
        BigInteger first = generate();
        BigInteger second = generate();
        BigNumber firstBig = new BigNumber(first.toString());
        BigNumber secondBig = new BigNumber(second.toString());

        assertEquals(new BigNumber(first.add(second).toString()), firstBig.plus(secondBig));
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
        BigInteger first = generate();
        BigInteger second = generate();
        BigNumber firstBig = new BigNumber(first.toString());
        BigNumber secondBig = new BigNumber(second.toString());
        System.out.println(first);
        System.out.println(second);

        assertEquals(new BigNumber(first.remainder(second).toString()), firstBig.minus(secondBig));
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
        BigInteger first = generate();
        BigInteger second = generate();
        BigNumber firstBig = new BigNumber(first.toString());
        BigNumber secondBig = new BigNumber(second.toString());

        System.out.println(first.multiply(second));
        assertEquals(new BigNumber(first.multiply(second).toString()), firstBig.times(secondBig));


        assertEquals(new BigNumber("3531308949366665767031418539758154"),
                new BigNumber("1234354334300012").times(new BigNumber("8212323242234938")));

        assertEquals(new BigNumber("652481086699806766868154"),
                new BigNumber("821938").times(new BigNumber("793832487933")));

        assertEquals(new BigNumber("-10835247823489623489603245"),
                new BigNumber("10835247823489623489603245").times(new BigNumber("-1")));

        assertEquals(new BigNumber("15129"), new BigNumber("-123").times(new BigNumber("-123")));

        assertEquals(new BigNumber("-1525209"), new BigNumber("-1231").times(new BigNumber("1239")));
    }

    @Test
    void delNegative() {
        BigNumber bigNumber = new BigNumber("-123123");
        bigNumber.delNegative();
        assertEquals(new BigNumber("123123"), bigNumber);
    }

    @Test
    void equals() {
        BigNumber bigNumber = new BigNumber("123123123");
        BigNumber otherBigNumber = new BigNumber("123123123");

        assertEquals(bigNumber, otherBigNumber);
    }

    @Test
    void asCollection() {
        ArrayBigNumber arrayBigNumber = new ArrayBigNumber();
        arrayBigNumber.asCollection(123456789, 123);
        assertTrue(new BigNumber("123456789123").equals(new BigNumber(arrayBigNumber, false)));
    }
}