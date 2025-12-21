package be.jolien.advent2025.services;

import be.jolien.advent2025.models.Range;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
class RangeService {
    public List<Range> mergeRanges(List<Range> ranges) {
        if (ranges.isEmpty()) return List.of();

        List<Range> sortedRanges = ranges.stream()
                .sorted(Comparator.comparingLong(Range::getStart))
                .toList();

        List<Range> merged = new ArrayList<>();

        Range current = sortedRanges.getFirst();

        for (int i = 1; i < sortedRanges.size(); i++) {
            Range next = sortedRanges.get(i);
            if (next.getStart() <= current.getEnd() + 1) {
                long newEnd = Math.max(current.getEnd(), next.getEnd());
                current = new Range(current.getStart(), newEnd);
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);
        return merged;
    }
}
