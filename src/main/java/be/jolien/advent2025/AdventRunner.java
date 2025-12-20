package be.jolien.advent2025;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class AdventRunner implements CommandLineRunner {

    private final InputService inputService;

    AdventRunner(InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("day 1.1: " + inputService.getSolutionDayOnePartOne());

        System.out.println("day 1.2: " + inputService.getSolutionDayOnePartTwo());

        System.out.println("day 2.1: " + inputService.getSolutionDayTwoPartOne());

        System.out.println("day 2.2: " + inputService.getSolutionDayTwoPartTwo());

        System.out.println("day 3.1: " + inputService.getSolutionDayThree(2));

        System.out.println("day 3.2: " + inputService.getSolutionDayThree(12));
    }
}