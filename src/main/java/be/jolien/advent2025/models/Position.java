package be.jolien.advent2025.models;

import java.util.Objects;

class Position {
    private final int col;
    private final int row;
    private String value;
    Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public void setValue(String value){
        this.value = value;
    }

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return col == position.col && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}
