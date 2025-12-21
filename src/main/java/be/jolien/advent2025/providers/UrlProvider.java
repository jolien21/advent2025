package be.jolien.advent2025.providers;

import org.springframework.stereotype.Component;

@Component
public class UrlProvider {
    private static final String BASE_URL = "https://adventofcode.com/2025/day/%d/input";

    public String getUrlForDay(int day) {
        return String.format(BASE_URL, day);
    }
}
