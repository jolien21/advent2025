package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.GenericGrid;
import be.jolien.advent2025.models.NumberGrid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GridParser {

    /**
     * Transformeert een String grid naar een NumberGrid (Long),
     * negeert de laatste rij en filtert spaties eruit per rij.
     */
    public NumberGrid parseStringToNumberGridAndRemoveLastRow(GenericGrid<String> stringGrid) {
        int rowCount = stringGrid.getRowCount();
        if (rowCount <= 1) return null;

        Long[][] data = new Long[rowCount - 1][];

        for (int i = 0; i < rowCount - 1; i++) {
            List<Long> rowValues = new ArrayList<>();
            for (int j = 0; j < stringGrid.getColCount(); j++) {
                String cell = stringGrid.getValue(i, j);
                if (cell != null && !cell.trim().isEmpty()) {
                    rowValues.add(Long.valueOf(cell.trim()));
                }
            }
            data[i] = rowValues.toArray(new Long[0]);
        }

        return new NumberGrid(data);
    }

    /**
     * Zet een lijst met strings om naar een GenericGrid<String> met behoud van spaties.
     */
    public GenericGrid<String> parseListToGridWithSpaces(List<String> lines) {
        if (lines == null || lines.isEmpty()) return null;

        int rowCount = lines.size();
        int colCount = lines.stream().mapToInt(String::length).max().orElse(0);

        String[][] grid = new String[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            String line = lines.get(i);
            for (int j = 0; j < colCount; j++) {
                grid[i][j] = (j < line.length()) ? String.valueOf(line.charAt(j)) : " ";
            }
        }
        return new GenericGrid<>(grid);
    }

    /**
     * Voor Part 1: een grid van Longs gebaseerd op witruimte-scheiding.
     */
    public NumberGrid parseToNumberGrid(String rawInput) {
        Long[][] data = Arrays.stream(rawInput.trim().split("\\R"))
                .map(line -> Arrays.stream(line.trim().split("\\s+"))
                        .map(Long::valueOf)
                        .toArray(Long[]::new))
                .toArray(Long[][]::new);
        return new NumberGrid(data);
    }

    /**
     * Voor puzzels met karakters (zoals woordzoekers).
     */
    public GenericGrid<Character> parseToCharacterGrid(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) return null;

        String[] rows = rawInput.trim().split("\\R");
        int rowCount = rows.length;
        int colCount = rows[0].length();

        Character[][] grid = new Character[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                grid[i][j] = rows[i].charAt(j);
            }
        }
        return new GenericGrid<>(grid);
    }
}