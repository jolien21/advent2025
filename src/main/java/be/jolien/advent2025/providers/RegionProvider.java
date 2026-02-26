package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.Region;
import be.jolien.advent2025.parsers.ListParser;
import be.jolien.advent2025.parsers.PositionParser;
import be.jolien.advent2025.parsers.RegionParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegionProvider {
    private final RegionParser regionParser;
    private final UrlProvider urlProvider;
    private final InputClient inputClient;
    private final ListParser listParser;

    public RegionProvider(
            RegionParser regionParser,
            UrlProvider urlProvider,
            InputClient inputClient, ListParser listParser) {
        this.regionParser = regionParser;
        this.urlProvider = urlProvider;
        this.inputClient = inputClient;
        this.listParser = listParser;
    }

    public List<Region> provideRegions(int day){
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        List<String> lines = listParser.parseToListByEnter(rawText);

        return regionParser.parseRegions(lines);
    }
}
