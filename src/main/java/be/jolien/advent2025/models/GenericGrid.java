package be.jolien.advent2025.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GenericGrid<T> {
    private final T[][] grid;

    public GenericGrid(T[][] grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        if (grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be empty");
        }
        this.grid = grid;
    }

    public int getRowCount() {
        return grid.length;
    }

    public int getColCount() {
        return grid[0].length;
    }

    public T getValue(int row, int col) {
        return grid[row][col];
    }

    public List<T> getRow(int rowNumber) {
        List<T> row = new ArrayList<>();
        for (int col = 0; col < getColCount(); col++) {
            row.add(getValue(rowNumber, col));
        }
        return row;
    }

    public GenericGrid<T> removeRow(int rowNumber) {
        if (rowNumber < 0 || rowNumber >= getRowCount()) {
            throw new IndexOutOfBoundsException("Rij index bestaat niet");
        }

        int newRowCount = getRowCount() - 1;
        @SuppressWarnings("unchecked")
        T[][] newGridData = (T[][]) java.lang.reflect.Array.newInstance(
                grid.getClass().getComponentType(),
                newRowCount
        );

        int destRow = 0;
        for (int srcRow = 0; srcRow < getRowCount(); srcRow++) {
            if (srcRow == rowNumber) continue;

            newGridData[destRow] = grid[srcRow];
            destRow++;
        }

        return new GenericGrid<>(newGridData);
    }

    public List<T> getCol(int colNumber) {
        List<T> col = new ArrayList<>();
        for (int row = 0; row < getRowCount(); row++) {
            col.add(getValue(row, colNumber));
        }
        return col;
    }

    /**
     * Telt hoeveel buren voldoen aan de opgegeven conditie.
     */
    public boolean isCountOfNeighboursLessThanLimit(int row, int col, Predicate<T> condition, int limit) {
        int counter = 0;
        int rowCount = getRowCount();
        int colCount = getColCount();

        for (int rOffset = -1; rOffset <= 1; rOffset++) {
            for (int cOffset = -1; cOffset <= 1; cOffset++) {
                if (rOffset == 0 && cOffset == 0) continue;

                int neighborRow = row + rOffset;
                int neighborCol = col + cOffset;

                if (neighborRow >= 0 && neighborRow < rowCount &&
                        neighborCol >= 0 && neighborCol < colCount) {

                    if (condition.test(grid[neighborRow][neighborCol])) {
                        counter++;
                    }
                }
            }
        }

        return counter < limit;
    }

    /**
     * Controleert of een waarde aangepast mag worden op basis van de buren.
     */
    public boolean canUpdateValue(int row, int col, Predicate<T> condition, int limit, T newValue) {
        if (!condition.test(grid[row][col])) {
            return false;
        }

        if (this.isCountOfNeighboursLessThanLimit(row, col, condition, limit)) {
            this.updateValueTo(row, col, newValue);
            return true;
        }
        return false;
    }

    public void updateValueTo(int row, int col, T newValue) {
        grid[row][col] = newValue;
    }

    public List<String> concatenateColumns() {
        List<String> combinedColumns = new ArrayList<>();

        for (int col = getColCount() - 1; col >= 0; col--) {
            StringBuilder sb = new StringBuilder();

            for (int row = 0; row < getRowCount(); row++) {
                T cellValue = getValue(row, col);
                if (cellValue != null) {
                    String charStr = String.valueOf(cellValue).trim();
                    if (!charStr.isEmpty()) {
                        sb.append(charStr);
                    }
                }
            }

            String result = sb.toString();
            if (!result.isEmpty()) {
                combinedColumns.add(result);
            }
        }
        return combinedColumns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GenericGrid[").append(getRowCount()).append("x").append(getColCount()).append("]:\n");
        for (int row = 0; row < getRowCount(); row++) {
            sb.append("[");
            for (int col = 0; col < getColCount(); col++) {
                T value = getValue(row, col);
                sb.append(value == null ? "null" : "'" + value + "'");
                if (col < getColCount() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}