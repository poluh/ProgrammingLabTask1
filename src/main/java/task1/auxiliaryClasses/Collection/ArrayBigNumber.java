package task1.auxiliaryClasses.Collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    private final int notation = 1000000000;
    private final int lenOneNum =  (int) Math.ceil(Math.log10(notation + 0.5) - 1);
    private List<Integer> storage = new ArrayList<>();
    private int size;
    private int flag;

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
            if (i + lenOneNum >= addedLength) {
                flag = (i + lenOneNum) - addedLength + 1;
            }
            storage.add(add);
        }
        this.size = this.storage.size();
    }

    public void add(int added) {
        this.add(String.valueOf(added));
    }

    @Override
    public Integer get(int index) {
        return this.storage.get(index);
    }

    @Override
    public void set(int index, int object) {
        // TODO
    }

    @Override
    public void remove(int index) {
        // TODO
    }

    @Override
    public int size() {
        return lenOneNum * (size - 1) + (lenOneNum - flag);
    }

    public int columnBlocks() {
        return size;
    }

    public void reverse() {
        List<Integer> buff = new ArrayList<>();
        List<Integer> storage = this.storage;
        for (int i = this.size - 1; i >= 0; i--) {
            buff.add(storage.get(i));
        }
        this.storage = buff;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void asCollection(Integer... values) {
        this.add(Arrays.toString(values));
    }

    public int getNotation() {
        return notation;
    }

    public List<Integer> getStorage() {
        return storage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        List<Integer> storage1 = this.storage;
        for (int i = 0; i < storage1.size(); i++) {
            Integer element = storage1.get(i);
            int lenElement = (int) Math.ceil(Math.log10(element + 0.5) - 1);
            try {
                if (lenElement < lenOneNum - 1) {
                    if (element == 0) lenElement++;
                    String pattern = "%0" + (lenOneNum - lenElement - (i == storage1.size() - 1 ? flag : 1)) + "d";
                    answer.append(pattern.contains("00") ? "" : String.format(pattern, 0));
                }
            } catch (IllegalFormatException ignored) { }
            answer.append(element);
        }
        return answer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.storage.equals(((ArrayBigNumber) obj).storage));
    }

    @Override
    public int hashCode() {
        return this.size * this.lenOneNum * this.storage.hashCode();
    }
}
