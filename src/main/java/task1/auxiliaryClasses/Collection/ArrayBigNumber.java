package task1.auxiliaryClasses.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    private List<IntegerObject> storage = new ArrayList<>();

    private void create(String addedStr) {
        char[] charArray = addedStr.toCharArray();
        for (char symbol : charArray) {
            this.add(symbol);
        }
    }

    public ArrayBigNumber() {
    }

    public ArrayBigNumber(String addedStr) {
        create(addedStr);
    }

    public ArrayBigNumber(int addedInt) {
        create(String.valueOf(addedInt));
    }

    ArrayBigNumber(List<IntegerObject> objectList) {
    }

    @Override
    public Integer size() {
        return this.storage.stream().mapToInt(IntegerObject::size).sum();
    }

    private Integer columnObjects() {
        return this.storage.size();
    }

    @Override
    public Integer get(Integer index) {
        int answer = -1;
        int addressObject = index / 9;
        if (!this.storage.isEmpty() && addressObject < this.storage.size()) {
            IntegerObject object = this.storage.get(addressObject);
            answer = object.get(index - 9 * addressObject);
        }
        return answer;
    }

    @Override
    public void set(Integer index, Integer object) {
        Integer addressObject = index / 9;
        this.storage.get(addressObject).set(index - addressObject * 9, object);
    }

    @Override
    public void remove(Integer index) {
    }

    public void add(char symbol) {
        this.add(Integer.valueOf(String.valueOf(symbol)));
    }

    @Override
    public void add(Integer added) {

        if (!this.storage.isEmpty()) {
            IntegerObject object = this.storage.get(this.storage.size() - 1);
            if (!object.isCompleted()) {
                object.add(added);
            } else {
                IntegerObject newObject = new IntegerObject();
                newObject.add(added);
                this.storage.add(newObject);
            }
        } else {
            this.storage.add(new IntegerObject(added));
        }
    }

    @Override
    public void asCollection(Integer... values) {
        for (Integer integer : values) {
            this.add(integer);
        }
    }

    @Override
    public String toString() {
        return this.storage.toString();
        //return this.storage.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        for (int i = 0; i < this.columnObjects(); i++) {
            if (!Objects.equals(this.storage.get(i), this.storage.get(i))) return false;
        }
        return true;
    }
}
