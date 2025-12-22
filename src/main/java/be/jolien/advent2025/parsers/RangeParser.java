package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.Range;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RangeParser {

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
