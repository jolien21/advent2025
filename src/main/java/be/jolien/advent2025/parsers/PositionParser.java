package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.Position3D;
import org.springframework.stereotype.Component;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

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
}
