package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.parsers.ListParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataProvider {
        private final InputClient inputClient;
        private final ListParser listParser;
        private final UrlProvider urlProvider;

        public DataProvider(InputClient inputClient, ListParser listParser, UrlProvider urlProvider) {
            this.inputClient = inputClient;
            this.listParser = listParser;
            this.urlProvider = urlProvider;
        }

        public List<String> getLines(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return listParser.parseToListByEnter(rawText);
        }

        public List<String> getCommaSeparated(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return listParser.parseToListByComma(rawText);
        }

        public List<String> getBlocks(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return listParser.parseToListByDoubleEnter(rawText);
        }
}
