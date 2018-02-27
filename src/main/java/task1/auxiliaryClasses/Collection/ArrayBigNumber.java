package task1.auxiliaryClasses.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    private int size = 0;
    private final int notation = 1000000000;
    private final int lenOneNum =  (int) Math.ceil(Math.log10(notation + 0.5) - 1);
    private final List<Integer> storage = new ArrayList<>();

    public ArrayBigNumber(String string) {
        this.add(string);
    }

    public ArrayBigNumber() {
    }

    @Override
    public void add(String added) {
        int addedLength = added.length();
        for (int i = 0; i < addedLength; i += lenOneNum) {
            Integer add = Integer.valueOf(added.substring(i, (addedLength > i + lenOneNum) ? i + lenOneNum : addedLength));
            storage.add(add);
        }
    }

    public void add(Integer value) {

    }

    @Override
    public Integer get(int index) {
        return 0;
    }

    @Override
    public void set(int index, int object) {

    }

    @Override
    public void remove(int index) {

    }

    @Override
    public int size() {
        return size;
    }

    public int notation() {
        return notation;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void asCollection(Integer... values) {
        for (Integer value : values) {
            this.add(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (Integer element : this.storage) {
            int lenElement = (int) Math.ceil(Math.log10(element + 0.5) - 1);
            if (lenElement < lenOneNum - 1) {
                String pattern = "%0" + (lenOneNum - lenElement - 1) + "d";
                answer.append(String.format(pattern, 0));
            }
            answer.append(element);
        }
        return answer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.storage.equals(((ArrayBigNumber) obj).storage));
    }

    public int getLenOneNum() {
        return lenOneNum;
    }
}
