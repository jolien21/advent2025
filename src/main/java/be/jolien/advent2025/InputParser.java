package be.jolien.advent2025;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class InputParser {

    List<String> parseToListByEnter(String rawInput) {
        if(rawInput == null || rawInput.isBlank()){
            return List.of();
        }
        return List.of(rawInput.split("\\R"));
    }

    List<String> parseToListByComma(String rawInput){
        if(rawInput == null || rawInput.isBlank()){
            return List.of();
        }
        return List.of(rawInput.split(","));
    }

    char[][] parseToGrid(String rawInput){
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
}
