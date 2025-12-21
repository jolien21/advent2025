package be.jolien.advent2025;

import be.jolien.advent2025.models.Range;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InputParser {

    public List<String> parseToListByEnter(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return List.of();
        }
        return List.of(rawInput.split("\\R"));
    }

    public List<String> parseToListByComma(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return List.of();
        }
        return List.of(rawInput.split(","));
    }

    public char[][] parseToGrid(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return new char[0][0];
        }

        String[] rows = rawInput.trim().split("\n");
        int rowCount = rows.length;
        int colCount = rows[0].length();

        char[][] grid = new char[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            grid[i] = rows[i].toCharArray();
        }
        return grid;
    }

    public List<String> parseToListByDoubleEnter(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return List.of();
        }

        return List.of(rawInput.split("\\R{2,}"));
    }

    public List<Range> parseListToRange(List<String> inputList){
        return inputList.stream()
                .filter(s -> s.contains("-"))
                .map(line -> {
                    var parts = line.split("-");
                    return new Range(Long.parseLong(parts[0].trim()), Long.parseLong(parts[1].trim()));
                })
                .toList();
    }


}
