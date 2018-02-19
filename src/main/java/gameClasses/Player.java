package gameClasses;

public class Player {

    private boolean nowPlay = false;
    private String name;
    private int value;

    public Player(String name, int value) {
        if (value == 0 ^ value == 1) {
            this.name = name;
            this.value = value;
        } else throw new IllegalArgumentException("Invalid value.");
    }

    public boolean isNowPlay() {
        return nowPlay;
    }

    public void setNowPlay(boolean nowPlay) {
        this.nowPlay = nowPlay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
