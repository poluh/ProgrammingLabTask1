package task1;

import task1.auxiliaryClasses.BigNumber;
import task1.auxiliaryClasses.Collection.ArrayBigNumber;
import task1.auxiliaryClasses.Collection.IntegerObject;

public class Main {

    public static void main(String[] args) {


        ArrayBigNumber arrayBigNumber = new ArrayBigNumber();
        arrayBigNumber.add(0);
        arrayBigNumber.add(0);
        arrayBigNumber.add(1);

        int bufStorage = 123;
        IntegerObject integerObject = new IntegerObject();
        integerObject.add(1);

        System.out.println(new BigNumber("9").plus(new BigNumber("9")));
    }
}
