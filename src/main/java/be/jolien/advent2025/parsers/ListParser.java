package be.jolien.advent2025.parsers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListParser {

    //Strings
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

    //Characters
    public List<Character> parseToCharacterList(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return List.of();
        }
        return rawInput.chars()
                .mapToObj(c -> (char) c)
                .toList();
    }

}
