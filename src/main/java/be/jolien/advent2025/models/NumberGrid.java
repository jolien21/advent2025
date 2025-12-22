package be.jolien.advent2025.models;


import java.util.List;

public class NumberGrid extends GenericGrid<Long>{

    public NumberGrid(Long[][] grid) {
        super(grid);
    }

    public long calculateColumnByOperator(int colNumber, String operator) {
        long count = this.getValue(0, colNumber);

        for (int row = 1; row < getRowCount(); row++) {
            long nextValue = this.getValue(row, colNumber);

            switch (operator) {
                case "+" -> count += nextValue;
                case "-" -> count -= nextValue;
                case "*" -> count *= nextValue;
                default -> throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }
        return count;
    }

    public static long calculateProblemRightToLeft(List<Long> numbers, String operator) {
        if (numbers.isEmpty()) return 0;

        long result = numbers.getLast();
        for (int i = numbers.size() - 2; i >= 0; i--) {
            long next = numbers.get(i);
            result = switch (operator) {
                case "+" -> result + next;
                case "*" -> result * next;
                case "-" -> result - next;
                default -> result;
            };
        }
        return result;
    }
}
