package be.jolien.advent2025.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Machine {
    private final String lightDiagram;
    private final List<List<Integer>> buttons;
    private final List<Integer> joltageRequirements;

    public Machine (String lightDiagram, List<List<Integer>> buttons, List<Integer> joltageRequirements){
        this.lightDiagram = lightDiagram;
        this.buttons = buttons;
        this.joltageRequirements = joltageRequirements;
    }

    public String getLightDiagram() {
        return lightDiagram;
    }

    public List<List<Integer>> getButtons() {
        return buttons;
    }

    public List<Integer> getJoltageRequirements() {
        return joltageRequirements;
    }
}
