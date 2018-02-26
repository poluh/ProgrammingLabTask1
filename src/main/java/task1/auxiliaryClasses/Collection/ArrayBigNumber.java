package task1.auxiliaryClasses.Collection;

import task1.auxiliaryClasses.BigFractional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ArrayBigNumber implements CollectionBigNumber<Integer> {

    int size = 0;
    int notation;
    byte[] storage;

    @Override
    public void add(Integer added) {

    }

    @Override
    public Integer get(Integer index) {
        return null;
    }

    @Override
    public void set(Integer index, Integer object) {

    }

    @Override
    public void remove(Integer index) {

    }

    @Override
    public Integer size() {
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
        return this.storage.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.storage.equals(((ArrayBigNumber) obj).storage));
    }
}
