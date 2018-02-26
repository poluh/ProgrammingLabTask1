package task1.auxiliaryClasses.Collection;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    private int size = 0;
    private final int notation = 100;
    private List<Byte> storage;

    public ArrayBigNumber(String string) {
        this.add(string);
    }

    public ArrayBigNumber() {
    }

    @Override
    public void add(String added) {
        for (int i = 0; i < added.length() - 1; i += 2) {
            try {
                System.out.println((added.substring(i, i + 2)));
                storage.add(Byte.valueOf(added.substring(i, i + 2)));
            } catch (NullPointerException ignored) {
            }
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
        return this.storage.toString().replace(",", "");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.storage.equals(((ArrayBigNumber) obj).storage));
    }
}
