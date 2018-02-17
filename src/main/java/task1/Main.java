package task1;

import task1.auxiliaryClasses.BigFactional;
import task1.auxiliaryClasses.BigNumber;

import java.util.logging.Logger;

public class Main {


    static class HelloWorld {
        private static int column = 0;
        HelloWorld() {
            column++;
        }
    }

    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            HelloWorld helloWorld = new HelloWorld();
        }
        System.out.println(HelloWorld.column);
    }

}
