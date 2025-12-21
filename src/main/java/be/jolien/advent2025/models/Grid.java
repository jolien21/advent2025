package be.jolien.advent2025.models;

public class Grid {
    private final char[][] grid;

    public Grid(char[][] grid) {
        this.grid = grid;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getRowCount() {
        return grid.length;
    }
    public int getColCount() {
        return grid[0].length;
    }

    public boolean isCountOfNeighboursOfRowAndColCharacterLessThanLimit(int row, int col, char character, int limit) {
        if (grid[row][col] != character) {
            return false;
        }

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

                    if (grid[neighborRow][neighborCol] == character) {
                        counter++;
                    }
                }
            }
        }

        return counter < limit;
    }
}
