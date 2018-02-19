package task1;

import task1.auxiliaryClasses.BigFractional;
import task1.auxiliaryClasses.BigNumber;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        BigNumber a = new BigNumber("10");
        BigNumber b = new BigNumber("-5");
        BigNumber c = a.plus(b);
        System.out.println(b);
    }
}
