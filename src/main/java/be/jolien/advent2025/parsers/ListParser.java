package be.jolien.advent2025.parsers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListParser {

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



    public List<String> parseToListByDoubleEnter(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return List.of();
        }

        return List.of(rawInput.split("\\R{2,}"));
    }


}
