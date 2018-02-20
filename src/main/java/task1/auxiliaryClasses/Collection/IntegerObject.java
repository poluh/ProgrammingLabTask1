package task1.auxiliaryClasses.Collection;

public class IntegerObject implements CollectionBigNumber<Integer> {

    private int storage = -2;
    private int size = -1;

    public IntegerObject() {
    }

    @Override
    public void add(int added) {
        if (added < 0) throw new NumberFormatException("Only positive numbers are allowed");

        if (storage == -2) {
            this.storage = added == 0 ? -1 : added;
            this.size = 1;
        } else if (this.size < 10) {
            this.storage = this.storage * 10 + (this.storage > 0 ? added : -added);
            this.size++;
        }
    }

    @Override
    public int get(Integer index) {
        index++;
        if (index < 0 || index > 10 || index > this.size - 1) {
            throw new IllegalArgumentException("Invalid index");
        }
        int storage = this.storage;
        int size = this.size;
        while (size != index) {
            storage /= 10;
            size--;
        }
        int answer = storage % 10;
        answer = answer == -1 ? 0 : answer;
        return storage < 0 ? answer * -1 : answer;
    }

    @Override
    public void remove(Integer index) {

    }

    @Override
    public int size() {
        return size;
    }

    public int getStorage() {
        return storage;
    }

    public boolean isCompleted() {
        return this.size > 8;
    }

    @Override
    public String toString() {
        int thisStorage = this.getStorage();
        return thisStorage > 0 ? String.valueOf(thisStorage) : ("0".concat(String.valueOf(thisStorage).substring(2)));
    }

}
