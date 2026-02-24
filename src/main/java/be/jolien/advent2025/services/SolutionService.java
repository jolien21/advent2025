package be.jolien.advent2025.services;

import be.jolien.advent2025.IdChecker;
import be.jolien.advent2025.parsers.GridParser;
import be.jolien.advent2025.parsers.ListParser;
import be.jolien.advent2025.models.*;
import be.jolien.advent2025.parsers.RangeParser;
import be.jolien.advent2025.providers.DataProvider;
import be.jolien.advent2025.providers.GridProvider;
import be.jolien.advent2025.providers.MachineProvider;
import be.jolien.advent2025.providers.PositionProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SolutionService {
    private final ListParser listParser;
    private final DataProvider dataProvider;
    private final IdChecker idChecker;
    private final RangeService rangeService;
    private final GridParser gridParser;
    private final GridProvider gridProvider;
    private final RangeParser rangeParser;
    private final PositionProvider positionProvider;
    private final MachineProvider machineProvider;

    SolutionService(DataProvider dataProvider,
                    ListParser listParser,
                    IdChecker idChecker,
                    RangeService rangeService,
                    GridParser gridParser,
                    GridProvider gridProvider,
                    RangeParser rangeParser,
                    PositionProvider positionProvider,
                    MachineProvider machineProvider) {
        this.dataProvider = dataProvider;
        this.listParser = listParser;
        this.idChecker = idChecker;
        this.rangeService = rangeService;
        this.gridParser = gridParser;
        this.gridProvider = gridProvider;
        this.rangeParser = rangeParser;
        this.positionProvider = positionProvider;
        this.machineProvider = machineProvider;
    }

    public int getSolutionDayOnePartOne(){
        var commands = dataProvider.getLines(1);
        var dial = new Dial(100);
        var solution = 0;

        for (String command : commands) {
            if(command.isBlank()) continue;

            var direction = command.charAt(0);
            var value = Integer.parseInt(command.substring(1));

            dial.move(direction, value);
            if(dial.getCurrentPosition() == 0){
                solution++;
            }
        }
        return solution;
    }

    public int getSolutionDayOnePartTwo(){
        var commands = dataProvider.getLines(1);
        var dial = new Dial(100);
        var solution = 0;

        for (String command : commands) {
            if(command.isBlank()) continue;

            var direction = command.charAt(0);
            var value = Integer.parseInt(command.substring(1));

            solution += dial.moveAndCountAllZeros(direction, value);
        }
        return solution;
    }

    public long getSolutionDayTwoPartOne(){
        var commands = dataProvider.getCommaSeparated(2);
        long solution = 0;
        for (String command : commands) {
            if(command.isBlank()) continue;

            var parts = command.split("-");
            var start = parts[0].trim();
            var end = parts[1].trim();

            solution += idChecker.sumOfDoubleNumbersInHalfFrom(start, end);
        }
        return solution;
    }

    public long getSolutionDayTwoPartTwo(){
        var commands = dataProvider.getCommaSeparated(2);
        long solution = 0;
        for (String command : commands) {
            if(command.isBlank()) continue;

            var parts = command.split("-");
            var start = parts[0].trim();
            var end = parts[1].trim();

            solution += idChecker.sumOfRepetition(start, end);
        }
        return solution;
    }

    public long getSolutionDayThree(int batteriesToSwitchOn){
        var powerbanks = dataProvider.getLines(3);
        long solution = 0;
        for(var bank : powerbanks){
            if(bank.isBlank()) continue;

            var powerbank = new PowerBank(bank);
            solution += powerbank.findMaximumJoltage(batteriesToSwitchOn);
        }
        return solution;
    }

    public long getSolutionDayFourPart1(){
        var grid = gridProvider.getCharacterGrid(4);
        var solution = 0;

        for(var row = 0; row < grid.getRowCount(); row++){
            for(var col = 0; col < grid.getColCount(); col++){
                if(grid.isCountOfNeighboursLessThanLimit(row, col, val -> val != null && val == '@' , 4)){
                    solution++;
                }
            }
        }
        return solution;
    }

    public long getSolutionDayFourPartTwo(){
        var grid = gridProvider.getCharacterGrid(4);
        var limit = 4;

        var solution = 0;
        var removedCharacter = true;

        while(removedCharacter) {
            removedCharacter = false;
            for (var row = 0; row < grid.getRowCount(); row++) {
                for (var col = 0; col < grid.getColCount(); col++) {
                    if (grid.canUpdateValue(row, col, val -> val != null && val == '@', limit, '.')) {
                        solution++;
                        removedCharacter = true;
                    }
                }
            }
        }
        return solution;
    }

    public long getSolutionDayFivePartOne() {
        var splittedData = dataProvider.getBlocks(5);

        var ids = listParser.parseToListByEnter(splittedData.get(1))
                .stream()
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        var stringRanges = listParser.parseToListByEnter(splittedData.get(0));
        var ranges = rangeParser.parseListToRange(stringRanges);

        long solution = 0;

        for (var id : ids) {
            if (idChecker.isIdInRangesOf(ranges, id)) {
                solution++;
            }
        }
        return solution;
    }

    public long getSolutionDayFivePartTwo() {
        var splittedData = dataProvider.getBlocks(5);
        var stringRanges = listParser.parseToListByEnter(splittedData.getFirst());
        var ranges = rangeParser.parseListToRange(stringRanges);
        var mergedRanges = rangeService.mergeRanges(ranges);

        long solution = 0;

        for (var range: mergedRanges){
            solution += range.countNumbersInRange();
        }
        return solution;
    }

    public long getSolutionDaySixPartOne(){
        var stringGrid = gridProvider.getStringGrid(6);

        var operatorRow = stringGrid.getRow(stringGrid.getRowCount() -1);

        var numberGrid = gridParser.parseStringToNumberGridAndRemoveLastRow(stringGrid);

        long solution = 0;

        for(var col = 0; col < numberGrid.getColCount(); col++){
            var operator = operatorRow.get(col);
            if (operator.equals("+") || operator.equals("*") || operator.equals("-")) {
                solution += numberGrid.calculateColumnByOperator(col, operator);
            }
        }
        return solution;
    }

    public long getSolutionDaySixPartTwo() {
        var grid = gridProvider.getStringGrid(6);
        int rowCount = grid.getRowCount();
        int colCount = grid.getColCount();

        long grandTotal = 0;
        List<Long> currentProblemNumbers = new ArrayList<>();
        String currentOperator = "";

        for (int col = colCount - 1; col >= 0; col--) {
            StringBuilder verticalNumber = new StringBuilder();
            boolean hasDigitInColumn = false;

            for (int row = 0; row < rowCount - 1; row++) {
                String cell = grid.getValue(row, col);
                if (cell != null && !cell.isBlank() && Character.isDigit(cell.charAt(0))) {
                    verticalNumber.append(cell.trim());
                    hasDigitInColumn = true;
                }
            }

            String opAtBottom = grid.getValue(rowCount - 1, col).trim();
            if (!opAtBottom.isEmpty()) {
                currentOperator = opAtBottom;
            }

            if (hasDigitInColumn) {
                currentProblemNumbers.add(Long.parseLong(verticalNumber.toString()));
            } else {
                if (!currentProblemNumbers.isEmpty()) {
                    grandTotal += calculateResult(currentProblemNumbers, currentOperator);
                    currentProblemNumbers.clear();
                }
            }
        }

        if (!currentProblemNumbers.isEmpty()) {
            grandTotal += calculateResult(currentProblemNumbers, currentOperator);
        }

        return grandTotal;
    }

    private long calculateResult(List<Long> nums, String op) {
        if (nums.isEmpty()) return 0;
        long res = nums.getFirst();
        for (int i = 1; i < nums.size(); i++) {
            switch (op) {
                case "+" -> res += nums.get(i);
                case "*" -> res *= nums.get(i);
                case "-" -> res -= nums.get(i);
            }
        }
        return res;
    }

    public long getSolutionDaySevenPartOne(){
        var manifold = gridProvider.getCharacterGrid(7);
        return manifold.countTimesBeamSplittedWhileGoingDown();
    }

    public long getSolutionDaySevenPartTwo(){
        var manifold = gridProvider.getCharacterGrid(7);
        return manifold.countTimelines();
    }
    public long getSolutionDayEightPartOne() {
        var junctionBoxes = positionProvider.get3DPositions(8);
        var connectionManager = new ConnectionService();
        var connections = connectionManager.getSortedConnections(junctionBoxes);

        CircuitService manager = new CircuitService(junctionBoxes.size());

        int limit = Math.min(1000, connections.size());

        for (int k = 0; k < limit; k++) {
            Connection c = connections.get(k);
            manager.union(c.indexA, c.indexB);
        }

        List<Integer> sizes = manager.getAllCircuitSizes();
        sizes.sort(Collections.reverseOrder());

        return (long) sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    public long getSolutionDayEightPartTwo() {
        var junctionBoxes = positionProvider.get3DPositions(8);
        var connectionManager = new ConnectionService();

        var connections = connectionManager.getSortedConnections(junctionBoxes);

        return connectionManager.findXProductOfFinalCircuitClosingConnection(junctionBoxes, connections);
    }

    public long getSolutionDayNinePartOne(){
        var coordinates = positionProvider.getPositionsAsSet(9);
        var rectangleService = new  RectangleService();

        return rectangleService.getLargestOppervlakte(coordinates);
    }

    public long getSolutionDayNinePartTwo(){
        var coordinates = positionProvider.getPositionsAsList(9);
        var rectangleService = new  RectangleService();

        return rectangleService.getLargestOppervlaktePartTwo(coordinates);
    }

    public long getSolutionDayTenPartOne() {
        var machines = machineProvider.getMachines(10);
        var machineService = new MachineService();

        return machineService.calculateMinimumPushesForDiagram(machines);
    }

    public long getSolutionDayTenPartTwo() {
        var machines = machineProvider.getMachines(10);
        var machineService = new MachineService();

        return machineService.calculateMinumumPushesForJoltage(machines);
    }
}
