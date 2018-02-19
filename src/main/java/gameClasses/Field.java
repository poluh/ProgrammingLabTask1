package gameClasses;

public class Field {
    int[][] cells;

    Field(int side) {
        this.cells = new int[side][side];
        for (int column = 0; column < side; column++) {
            for (int row = 0; row < side; row++) {
                this.cells[column][row] = 0;
            }
        }
    }

    public int getCell(int column, int row) {
        return this.cells[column][row];
    }

    public void setCell(int column, int row, int value) {
        if (value > 0 && value < 3)
        this.cells[column][row] = value;
    }
}
