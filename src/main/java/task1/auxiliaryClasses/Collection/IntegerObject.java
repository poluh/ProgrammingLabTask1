package task1.auxiliaryClasses.Collection;

public class IntegerObject implements CollectionBigNumber<Integer> {

    private int storage = -2;
    private int size = -1;

    public IntegerObject() {
    }

    IntegerObject(Integer added) {
        this.add(added);
    }


    @Override
    public void add(Integer added) {
        if (added < 0 || added > 9)
            throw new NumberFormatException("Only positive/single-character numbers are allowed");
        if (this.size > 8) throw new IllegalArgumentException("Container overflow");

        if (storage == -2) {
            this.storage = (added == 0) ? -1 : added;
            this.size = 1;
        } else {
            this.storage = this.storage * 10 + (this.storage > 0 ? added : -added);
            this.size++;
        }
    }

    @Override
    public void asCollection(Integer... values) {
        for (Integer integer : values) {
            this.add(integer);
        }
    }

    @Override
    public Integer get(Integer index) {
        index = index == 0 ? 1 : index;
        if (index < 1 || index > this.size) {
            throw new IllegalArgumentException("Invalid index " + index + " real size " + this.size);
        }
        if (index == 1 && this.storage < 0) return 0;
        int answer = this.size != 1 ? ((this.storage / (int) Math.pow(10, this.size - index)) % 10) : this.storage;
        return answer < 0 ? answer * -1 : answer;
    }

    @Override
    public void set(Integer index, Integer object) {
        if (index < 0 || index > 8 || index > this.size) {
            throw new IllegalArgumentException("Invalid index");
        }
        double bufStorage = this.storage;
        this.storage = -2;
        for (int i = 0; i < this.size; i++) {
            if (i == index) {
                this.add(object);
                i++;
            }
            if (i < this.size) {
                bufStorage %= Math.pow(10, Math.log10(bufStorage));
                this.add((int) (bufStorage / Math.pow(10, Math.log10(bufStorage))));
            }
        }
    }

    @Override
    public void remove(Integer index) {

    }

    @Override
    public Integer size() {
        return size;
    }

    public Integer getStorage() {
        return storage;
    }

    public boolean isCompleted() {
        return this.size > 8;
    }

    @Override
    public String toString() {
        Integer thisStorage = this.getStorage();
        String thisStorageStr = thisStorage.toString();
        if (thisStorage < 0) {
            return (thisStorageStr.length() == 2) ? "0" : "0" + thisStorageStr.substring(2, thisStorageStr.length());
        }
        return thisStorageStr;
    }

    @Override
    public boolean equals(Object obj) {
        IntegerObject object = (IntegerObject) obj;
        return obj.getClass() == this.getClass() && object.storage == this.storage;
    }
}
