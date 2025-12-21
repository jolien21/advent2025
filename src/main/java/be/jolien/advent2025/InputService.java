package be.jolien.advent2025;

import be.jolien.advent2025.models.Dial;
import be.jolien.advent2025.models.Grid;
import be.jolien.advent2025.models.PowerBank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InputService {
    private final InputClient inputClient;
    private final InputParser inputParser;
    private final IdChecker idChecker;

    InputService(InputClient inputClient,
                 InputParser inputParser,
                 IdChecker idChecker) {
        this.inputClient = inputClient;
        this.inputParser = inputParser;
        this.idChecker = idChecker;
    }

    List<String> getLinesByEnterFromUrl(String url){
        String rawText = inputClient.fetchRawText(url);
        return inputParser.parseToListByEnter(rawText);
    }

    List<String> getLinesByCommaFromUrl(String url){
        String rawText = inputClient.fetchRawText(url);
        return inputParser.parseToListByComma(rawText);
    }

    char[][] getGridFromUrl(String url){
        String rawText = inputClient.fetchRawText(url);
        return inputParser.parseToGrid(rawText);
    }

    int getSolutionDayOnePartOne(){
        var commands = this.getLinesByEnterFromUrl("https://adventofcode.com/2025/day/1/input");
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

    int getSolutionDayOnePartTwo(){
        var commands = this.getLinesByEnterFromUrl("https://adventofcode.com/2025/day/1/input");
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

    long getSolutionDayTwoPartOne(){
        var commands = this.getLinesByCommaFromUrl("https://adventofcode.com/2025/day/2/input");
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

    long getSolutionDayTwoPartTwo(){
        var commands = this.getLinesByCommaFromUrl("https://adventofcode.com/2025/day/2/input");
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

    long getSolutionDayThree(int batteriesToSwitchOn){
        var powerbanks = this.getLinesByEnterFromUrl("https://adventofcode.com/2025/day/3/input");
        long solution = 0;
        for(var bank : powerbanks){
            if(bank.isBlank()) continue;

            var powerbank = new PowerBank(bank);
            solution += powerbank.findMaximumJoltage(batteriesToSwitchOn);
        }
        return solution;
    }

    long getSolutionDayFourPart1(){
        var grid = new Grid(this.getGridFromUrl("https://adventofcode.com/2025/day/4/input"));
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

    long getSolutionDayFourPartTwo(){
        var grid = new Grid(this.getGridFromUrl("https://adventofcode.com/2025/day/4/input"));
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
}
