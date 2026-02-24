package be.jolien.advent2025.services;

import be.jolien.advent2025.models.Position;
import be.jolien.advent2025.models.Rectangle;
import java.util.*;

public class RectangleService {

    public long getLargestOppervlakte(Set<Position> positions) {
        var posList = new ArrayList<>(positions);
        int size = posList.size();

        return java.util.stream.IntStream.range(0, size)
                .boxed()
                .flatMapToLong(i -> java.util.stream.IntStream.range(i + 1, size)
                        .mapToLong(j -> new Rectangle(posList.get(i), posList.get(j)).getOppervlakte()))
                .max()
                .orElse(0L);
    }

    public long getLargestOppervlaktePartTwo(List<Position> redTiles) {
        if (redTiles == null || redTiles.isEmpty()) return 0;

        Map<Integer, TreeSet<Integer>> borderByRow = new HashMap<>();
        for (int i = 0; i < redTiles.size(); i++) {
            addPathToMap(borderByRow, redTiles.get(i), redTiles.get((i + 1) % redTiles.size()));
        }

        long maxArea = 0;
        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                Position p1 = redTiles.get(i);
                Position p2 = redTiles.get(j);

                Rectangle rect = new Rectangle(p1, p2);
                long area = rect.getOppervlakte();

                if (area > maxArea) {
                    if (isRectangleValid(p1, p2, borderByRow)) {
                        maxArea = area;
                    }
                }
            }
        }
        return maxArea;
    }

    private void addPathToMap(Map<Integer, TreeSet<Integer>> borderByRow, Position p1, Position p2) {
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());

        for (int y = minY; y <= maxY; y++) {
            borderByRow.computeIfAbsent(y, k -> new TreeSet<>());
            for (int x = minX; x <= maxX; x++) {
                borderByRow.get(y).add(x);
            }
        }
    }

    private boolean isRectangleValid(Position p1, Position p2, Map<Integer, TreeSet<Integer>> borderByRow) {
        int x1 = Math.min(p1.getX(), p2.getX());
        int x2 = Math.max(p1.getX(), p2.getX());
        int y1 = Math.min(p1.getY(), p2.getY());
        int y2 = Math.max(p1.getY(), p2.getY());

        for (int y = y1; y <= y2; y++) {
            TreeSet<Integer> rowBorder = borderByRow.get(y);
            if (rowBorder == null) return false;

            if (x1 < rowBorder.first() || x2 > rowBorder.last()) {
                return false;
            }
        }
        return true;
    }
}