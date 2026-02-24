package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.Machine;
import be.jolien.advent2025.parsers.ListParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MachineProvider {
    private final InputClient inputClient;
    private final ListParser listParser;
    private final UrlProvider urlProvider;

    public MachineProvider(InputClient inputClient, ListParser listParser, UrlProvider urlProvider) {
        this.inputClient = inputClient;
        this.listParser = listParser;
        this.urlProvider = urlProvider;
    }

    public List<Machine> getMachines(int day) {
        String rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        var rawMachines = listParser.parseToListByEnter(rawText);
        var machines = new ArrayList<Machine>();

        for (var rawMachine : rawMachines) {
            //schema
            var schema = rawMachine.substring(rawMachine.indexOf("[") + 1, rawMachine.indexOf("]"));

            //buttons
            var buttons = new ArrayList<List<Integer>>();
            Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(rawMachine);
            while (m.find()) {
                var content = m.group(1);
                var numbers = new ArrayList<Integer>();
                for (String number : content.split(","))
                    numbers.add(Integer.parseInt(number.trim()));
                buttons.add(numbers);
            }

            //joltage
            var rawJoltage = rawMachine.substring(rawMachine.indexOf("{") + 1, rawMachine.indexOf("}"));
            var joltageStrings = listParser.parseToListByComma(rawJoltage);

            var joltages = new ArrayList<Integer>();
            for (String number : joltageStrings){
                joltages.add(Integer.parseInt(number.trim()));
            }

            machines.add(new Machine(
                    schema,
                    buttons,
                    joltages
            ));
        }
        return machines;
    }
}