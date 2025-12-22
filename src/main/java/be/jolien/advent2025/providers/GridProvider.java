package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.GenericGrid;
import be.jolien.advent2025.models.NumberGrid;
import be.jolien.advent2025.parsers.GridParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GridProvider {
    private final GridParser gridParser;
    private final UrlProvider urlProvider;
    private final InputClient inputClient;


    public GridProvider(GridParser gridParser, UrlProvider urlProvider, InputClient inputClient) {
        this.gridParser = gridParser;
        this.urlProvider = urlProvider;
        this.inputClient = inputClient;
    }

    public NumberGrid getNumberGrid(int day) {
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        return gridParser.parseToNumberGrid(rawText);
    }

    public GenericGrid<Character> getCharacterGrid(int day) {
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        return gridParser.parseToCharacterGrid(rawText);
    }

    public GenericGrid<String> getStringGrid(int day) {
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        List<String> lines = Arrays.asList(rawText.split("\\R"));
        return gridParser.parseListToGridWithSpaces(lines);
    }
}
