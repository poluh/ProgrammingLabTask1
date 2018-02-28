package task1.auxiliaryClasses.Collection;

public interface CollectionBigNumber<E> {

    E get(int index);
    int length();
    boolean isEmpty();
    void set(int index, int object);
    void remove(int index);
    void add(String added);
    void asCollection(E... values);

}
