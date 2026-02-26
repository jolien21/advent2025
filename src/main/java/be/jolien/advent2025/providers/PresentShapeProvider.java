package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.PresentShape;
import be.jolien.advent2025.parsers.ListParser;
import be.jolien.advent2025.parsers.PresentParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PresentShapeProvider {
    private final PresentParser presentParser;
    private final UrlProvider urlProvider;
    private final InputClient inputClient;
    private final ListParser listParser;

    public PresentShapeProvider(
            PresentParser presentParser,
            UrlProvider urlProvider,
            InputClient inputClient, ListParser listParser) {
        this.presentParser = presentParser;
        this.urlProvider = urlProvider;
        this.inputClient = inputClient;
        this.listParser = listParser;
    }

    public List<PresentShape> providePresentShapes(int day){
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        List<String> lines = listParser.parseToListByEnter(rawText);

        return presentParser.parseShapes(lines);
    }
}
