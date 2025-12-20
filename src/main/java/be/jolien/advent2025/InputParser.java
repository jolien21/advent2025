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
}
