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

        char[] problem = "1".toCharArray();
        ArrayBigNumber arrayBigNumber = new ArrayBigNumber();
        for (char ch : problem) arrayBigNumber.add(ch);
        System.out.println(arrayBigNumber);
        System.out.println("112312321323");


    }
}
