package task1.auxiliaryClasses.Collection;

import java.util.ArrayList;
import java.util.List;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    private int size;
    private List<IntegerObject> storage = new ArrayList<>();

    public ArrayBigNumber() {
    }

    public ArrayBigNumber(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int get(Integer index) {
        int answer = -1;
        int addressObject = index / 9;
        if (!storage.isEmpty() && addressObject < storage.size()) {
            CollectionBigNumber<Integer> object = storage.get(index);
            answer = object.get(index - 9 * addressObject);
        }
        return answer;
    }

    @Override
    public void remove(Integer index) {
    }

    @Override
    public void add(int added) {
        if (!storage.isEmpty()) {
            IntegerObject object = storage.get(storage.size() - 1);
            if (!object.isCompleted()) {
                object.add(added);
            } else {
                IntegerObject newObject = new IntegerObject();
                newObject.add(added);
                storage.add(newObject);
            }
        } else {
            IntegerObject newObject = new IntegerObject();
            newObject.add(added);
            System.out.println(newObject);
            storage.add(newObject);
        }
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (IntegerObject object : this.storage) {
            answer.append(object);
        }
        return answer.toString();
    }
}
