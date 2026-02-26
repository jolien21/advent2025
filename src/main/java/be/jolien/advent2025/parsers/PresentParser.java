package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.PresentShape;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PresentParser {
    private final GridParser gridParser;

    public PresentParser(GridParser gridParser) {
        this.gridParser = gridParser;
    }

    public List<PresentShape> parseShapes(List<String> lines) {
        List<PresentShape> shapes = new ArrayList<>();
        int i = 0;
        while (i < lines.size() && !lines.get(i).contains("x")) {
            if (lines.get(i).contains(":")) {
                int id = Integer.parseInt(lines.get(i).replace(":", "").trim());
                List<String> shapeLines = new ArrayList<>();
                i++;
                // Lees de #/. regels tot de volgende lege regel of ID
                while (i < lines.size() && (lines.get(i).startsWith("#") || lines.get(i).startsWith("."))) {
                    shapeLines.add(lines.get(i).trim());
                    i++;
                }

                boolean[][] baseGrid = gridParser.convertToBooleanGrid(shapeLines);
                shapes.add(PresentShape.createWithVariants(id, baseGrid));
            } else {
                i++;
            }
        }
        return shapes;
    }
}
