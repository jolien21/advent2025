package be.jolien.advent2025.models;

public class Grid {
    private char[][] grid;

    public Grid(char[][] grid) {
        if(grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        if(grid.length != grid[0].length){
            throw new IllegalArgumentException("Grid must have same length");
        }
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

    public boolean canRemoveCharacter(int row, int col, char character, int limit) {
        if (grid[row][col] != character ) {
            return false;
        }

        if(this.isCountOfNeighboursOfRowAndColCharacterLessThanLimit(row, col, character, limit)) {
            this.removeCharacterTo(row, col, '.');
            return true;
        }
        return false;
    }

    void removeCharacterTo(int row, int col, char newCharacter){
        grid[row][col] = newCharacter;
    }
}
