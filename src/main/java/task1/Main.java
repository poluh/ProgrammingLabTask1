package task1;


import task1.auxiliaryClasses.BigNumber;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        System.out.println(new BigNumber("12399494").times(new BigNumber("12399494")));
        System.out.println(new BigInteger("12399494").multiply(new BigInteger("12399494")));
    }
}
