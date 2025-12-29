package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.Position3D;
import be.jolien.advent2025.parsers.ListParser;
import be.jolien.advent2025.parsers.PositionParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionProvider {
    private final PositionParser positionParser;
    private final ListParser listParser;
    private final UrlProvider urlProvider;
    private final InputClient inputClient;


    public PositionProvider(
            PositionParser positionParser,
            ListParser listParser,
            UrlProvider urlProvider,
            InputClient inputClient) {
        this.positionParser = positionParser;
        this.listParser = listParser;
        this.urlProvider = urlProvider;
        this.inputClient = inputClient;
    }

    public List<Position3D> get3DPositions(int day) {
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        var rawList =  listParser.parseToListByEnter(rawText);
        return positionParser.parseListToPostion3DList(rawList);

    }
}
