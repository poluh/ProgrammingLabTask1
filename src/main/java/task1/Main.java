package task1;

import task1.auxiliaryClasses.BigFractional;
import task1.auxiliaryClasses.BigNumber;
import task1.auxiliaryClasses.Collection.IntegerObject;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        IntegerObject integerObject = new IntegerObject();
        for (int i = 0; i < 9; i++) {
            integerObject.add(0);
        }
        System.out.println(integerObject);
        System.out.println(integerObject.get(0));
    }
}
