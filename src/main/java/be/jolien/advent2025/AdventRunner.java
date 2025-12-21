package be.jolien.advent2025;

import be.jolien.advent2025.services.SolutionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class AdventRunner implements CommandLineRunner {

    private final SolutionService solutionService;

    AdventRunner(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("day 1.1: " + solutionService.getSolutionDayOnePartOne());

        System.out.println("day 1.2: " + solutionService.getSolutionDayOnePartTwo());

        System.out.println("day 2.1: " + solutionService.getSolutionDayTwoPartOne());

        System.out.println("day 2.2: " + solutionService.getSolutionDayTwoPartTwo());

        System.out.println("day 3.1: " + solutionService.getSolutionDayThree(2));

        System.out.println("day 3.2: " + solutionService.getSolutionDayThree(12));

        System.out.println("day 4.1: " + solutionService.getSolutionDayFourPart1());

        System.out.println("day 4.2: " + solutionService.getSolutionDayFourPartTwo());

        System.out.println("day 5.1: " + solutionService.getSolutionDayFivePartOne());

        System.out.println("day 5.2: " + solutionService.getSolutionDayFivePartTwo());
    }
}