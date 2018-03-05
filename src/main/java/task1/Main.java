package task1;

import task1.auxiliaryClasses.BigFractional;
import task1.auxiliaryClasses.BigNumber;

public class Main {

    public static void main(String[] args) {
        BigNumber first = new BigNumber("123456789");
        BigNumber second = new BigNumber("2");
        BigNumber result = first.division(second);
        System.out.println(result);
        System.out.println(123456789 >> 1);
    }
}
