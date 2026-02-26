package be.jolien.advent2025.models;

import java.util.Map;

public record Region(int width, int height, Map<Integer, Integer> presentCounts) {
}
