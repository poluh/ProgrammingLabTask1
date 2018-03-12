package task1;


import task1.auxiliaryClasses.BigNumber;

import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println(new BigNumber("123456790123456790").times(new BigNumber("123456790123456790")));
        System.out.println(new BigInteger("123456790123456790").multiply(new BigInteger("123456790123456790")));
    }
}
