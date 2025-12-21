package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.InputParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataProvider {
        private final InputClient inputClient;
        private final InputParser inputParser;
        private final UrlProvider urlProvider;

        public DataProvider(InputClient inputClient, InputParser inputParser, UrlProvider urlProvider) {
            this.inputClient = inputClient;
            this.inputParser = inputParser;
            this.urlProvider = urlProvider;
        }

        public List<String> getLines(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return inputParser.parseToListByEnter(rawText);
        }

        public List<String> getCommaSeparated(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return inputParser.parseToListByComma(rawText);
        }

        public char[][] getGrid(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return inputParser.parseToGrid(rawText);
        }

        public List<String> getBlocks(int day) {
            String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
            return inputParser.parseToListByDoubleEnter(rawText);
        }
}
