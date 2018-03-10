package task1.auxiliaryClasses.Collection;

public interface CollectionBigNumber<E> {

    /**
     * A method for obtaining a block of numbers.
     *
     * @param index index of block
     * @return int-block
     */
    E get(int index);

    /**
     * Returns the number of digits in the number
     * (not the number of blocks in the repository,
     * for this there is the method columnBlocks())
     *
     * @return length
     */
    int length();

    /**
     * Checks if the vault is empty.
     *
     * @return true/false
     */
    boolean isEmpty();

    /**
     * Replaces one block with another
     *
     * @param index  block to be replaced
     * @param object replacement unit
     */
    void set(int index, int object);

    /**
     * Removes a block by index
     *
     * @param index removable block
     */
    void remove(int index);

    /**
     * Adds a number to the vault, breaking it into blocks.
     *
     * @param added number as a string
     */
    void add(String added);

    /**
     * Converts arguments to ArrayBigNumber,
     * adding each argument to a new block.
     *
     * @param values args
     */
    void asCollection(int... values);

}
