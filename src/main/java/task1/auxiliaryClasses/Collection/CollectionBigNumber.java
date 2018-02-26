package task1.auxiliaryClasses.Collection;

public interface CollectionBigNumber<E> {

    E size();
    E get(E index);
    boolean isEmpty();
    void set(E index, E object);
    void remove(E index);
    void add(E added);
    void asCollection(E... values);

}
