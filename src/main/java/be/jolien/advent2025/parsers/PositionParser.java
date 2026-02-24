package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.Position3D;
import be.jolien.advent2025.models.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PositionParser {

    public List<Position3D> parseListToPostion3DList(List<String> rawPositions) {
        List<Position3D> positions = new ArrayList<>();
        rawPositions.forEach(rawPosition -> {
            var splittedPositions = rawPosition.split(",");
            var position = new Position3D(
                    Integer.parseInt(splittedPositions[0]),
                    Integer.parseInt(splittedPositions[1]),
                    Integer.parseInt(splittedPositions[2]));
            positions.add(position);
        });
        return positions;
    }

    public Set<Position> parseListToPositionSet(List<String> rawPositions) {
        Set<Position> positions = new HashSet<>();
        rawPositions.forEach(rawPosition -> {
            var splittedPositions = rawPosition.split(",");
            var position = new Position(
                    Integer.parseInt(splittedPositions[0]),
                    Integer.parseInt(splittedPositions[1]));
            positions.add(position);
        });
        return positions;
    }

    public List<Position> parseListToPositionList(List<String> rawPositions) {
        List<Position> positions = new ArrayList<>();
        rawPositions.forEach(rawPosition -> {
            var splittedPositions = rawPosition.split(",");
            var position = new Position(
                    Integer.parseInt(splittedPositions[0].trim()),
                    Integer.parseInt(splittedPositions[1].trim()));
            positions.add(position);
        });
        return positions;
    }
}
