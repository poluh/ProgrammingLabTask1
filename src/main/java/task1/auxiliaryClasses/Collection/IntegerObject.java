package task1.auxiliaryClasses.Collection;

public class IntegerObject {
    private int storage = -2;
    private int size = -1;

    public IntegerObject() {}

    public void add(int added) {
        if (added < 0) throw new NumberFormatException("Only positive numbers are allowed");

        if (storage == -2) {
            storage = added == 0 ? -1 : added;
            size = 1;
        } else if (size != 9){
            storage = storage * 10 + (storage > 0 ? added : -added);
            size++;
        } else {
            throw new NumberFormatException("The storage is full");
        }
    }

    public int get(int index) {
        index++;
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

    public int getSize() {
        return size;
    }

    public int getStorage() {
        return storage;
    }

    @Override
    public String toString() {
        return String.valueOf(getStorage());
    }
}
