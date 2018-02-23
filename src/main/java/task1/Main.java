package task1;

import task1.auxiliaryClasses.BigNumber;
import task1.auxiliaryClasses.Collection.ArrayBigNumber;
import task1.auxiliaryClasses.Collection.IntegerObject;

public class Main {

    public static void main(String[] args) {

        ArrayBigNumber arrayBigNumber = new ArrayBigNumber();

        for (int i = 0; i < 20; i++) {
            arrayBigNumber.add(i < 10 ? i : i % 10);
        }
        System.out.println(arrayBigNumber);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 21; i++) {
            stringBuilder.append(arrayBigNumber.get(i));
        }
        System.out.println(stringBuilder);
    }
}
