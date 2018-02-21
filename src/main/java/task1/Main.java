package task1;

import task1.auxiliaryClasses.BigFractional;
import task1.auxiliaryClasses.BigNumber;
import task1.auxiliaryClasses.Collection.ArrayBigNumber;
import task1.auxiliaryClasses.Collection.CollectionBigNumber;
import task1.auxiliaryClasses.Collection.IntegerObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {


        ArrayBigNumber arrayBigNumber = new ArrayBigNumber();
        for (int i = 0; i < 3; ++i) {
            arrayBigNumber.add(i);
        }
        arrayBigNumber.add(arrayBigNumber.get(2));
    }
}
