package task1.auxiliaryClasses.Collection;

public interface CollectionBigNumber<E> {

    int size();
    int get(E index);
    void remove(E index);
    void add(int added);

}
