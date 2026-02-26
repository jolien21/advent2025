package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.Region;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegionParser {
    public List<Region> parseRegions(List<String> lines) {
        List<Region> regions = new ArrayList<>();
        for (String line : lines) {
            if (!line.contains("x")) continue;

            // "12x5: 1 0 1 0 3 2" -> split op ":"
            String[] parts = line.split(":");
            String[] dims = parts[0].trim().split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            String[] counts = parts[1].trim().split("\\s+");
            Map<Integer, Integer> presentCounts = new HashMap<>();
            for (int i = 0; i < counts.length; i++) {
                int quantity = Integer.parseInt(counts[i]);
                if (quantity > 0) {
                    presentCounts.put(i, quantity);
                }
            }
            regions.add(new Region(width, height, presentCounts));
        }
        return regions;
    }
}
