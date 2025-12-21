package be.jolien.advent2025.services;

import be.jolien.advent2025.IdChecker;
import be.jolien.advent2025.InputParser;
import be.jolien.advent2025.models.Dial;
import be.jolien.advent2025.models.Grid;
import be.jolien.advent2025.models.PowerBank;
import be.jolien.advent2025.providers.DataProvider;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {
    private final InputParser inputParser;
    private final DataProvider dataProvider;
    private final IdChecker idChecker;
    private final RangeService rangeService;

    SolutionService(DataProvider dataProvider,
                    InputParser inputParser,
                    IdChecker idChecker,
                    RangeService rangeService) {
        this.dataProvider = dataProvider;
        this.inputParser = inputParser;
        this.idChecker = idChecker;
        this.rangeService = rangeService;
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
        var grid = new Grid(dataProvider.getGrid(4));
        var rowCount = grid.getRowCount();
        var colCount = grid.getColCount();
        var character = '@';
        var limit = 4;

        var solution = 0;

        for(var row = 0; row < rowCount; row++){
            for(var col = 0; col < colCount; col++){
                if(grid.isCountOfNeighboursOfRowAndColCharacterLessThanLimit(row, col, character, limit)){
                    solution++;
                }
            }
        }

        return solution;
    }

    public long getSolutionDayFourPartTwo(){
        var grid = new Grid(dataProvider.getGrid(4));
        var rowCount = grid.getRowCount();
        var colCount = grid.getColCount();
        var character = '@';
        var limit = 4;

        var solution = 0;
        var removedCharacter = true;

        while(removedCharacter) {
            removedCharacter = false;
            for (var row = 0; row < rowCount; row++) {
                for (var col = 0; col < colCount; col++) {
                    if (grid.canRemoveCharacter(row, col, character, limit)) {
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

        var ids = inputParser.parseToListByEnter(splittedData.get(1))
                .stream()
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        var stringRanges = inputParser.parseToListByEnter(splittedData.get(0));
        var ranges = inputParser.parseListToRange(stringRanges);

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
        var stringRanges = inputParser.parseToListByEnter(splittedData.getFirst());
        var ranges = inputParser.parseListToRange(stringRanges);
        var mergedRanges = rangeService.mergeRanges(ranges);

        long solution = 0;

        for (var range: mergedRanges){
            solution += range.countNumbersInRange();
        }
        return solution;
    }
}
