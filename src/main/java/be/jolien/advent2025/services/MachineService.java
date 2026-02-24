package be.jolien.advent2025.services;

import be.jolien.advent2025.models.Machine;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.*;

import java.util.*;

@Service
class MachineService {

    // --- DIAGRAM LOGIC ---

    public long calculateMinimumPushesForDiagram(List<Machine> machines) {
        return machines.stream()
                .mapToLong(this::calculateFewestPressesByDiagram)
                .sum();
    }

    private long calculateFewestPressesByDiagram(Machine machine) {
        String target = machine.getLightDiagram();
        String start = ".".repeat(target.length());
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> visited = new HashMap<>();

        queue.add(start);
        visited.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int distance = visited.get(current);

            if (current.equals(target)) return distance;

            for (List<Integer> button : machine.getButtons()) {
                String nextState = toggle(current, button);
                if (!visited.containsKey(nextState)) {
                    visited.put(nextState, distance + 1);
                    queue.add(nextState);
                }
            }
        }
        return 0;
    }

    private String toggle(String state, List<Integer> buttonIndices) {
        char[] chars = state.toCharArray();
        for (int index : buttonIndices) {
            chars[index] = (chars[index] == '.') ? '#' : '.';
        }
        return new String(chars);
    }

    // --- JOLTAGE LOGIC ---

    public long calculateMinumumPushesForJoltage(List<Machine> machines) {
        return machines.stream()
                .mapToLong(this::calculateFewestPressesByJoltage)
                .sum();
    }

    private long calculateFewestPressesByJoltage(Machine machine) {
        List<Integer> target = machine.getJoltageRequirements();
        List<List<Integer>> buttons = machine.getButtons();
        int numCounters = target.size();
        int numButtons = buttons.size();

        double[] objCoeffs = new double[numButtons];
        Arrays.fill(objCoeffs, 1.0);
        LinearObjectiveFunction f = new LinearObjectiveFunction(objCoeffs, 0);

        List<LinearConstraint> baseConstraints = new ArrayList<>();
        for (int i = 0; i < numCounters; i++) {
            double[] coeffs = new double[numButtons];
            for (int j = 0; j < numButtons; j++) {
                if (buttons.get(j).contains(i)) coeffs[j] = 1.0;
            }
            baseConstraints.add(new LinearConstraint(coeffs, Relationship.EQ, target.get(i)));
        }

        return branchAndBound(f, baseConstraints, numButtons, Long.MAX_VALUE);
    }

    private long branchAndBound(LinearObjectiveFunction f, List<LinearConstraint> constraints,
                                int numButtons, long bestSoFar) {
        try {
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(
                    f,
                    new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true)
            );

            if (solution == null) return bestSoFar;

            double[] point = solution.getPoint();
            double lpSum = Arrays.stream(point).sum();

            // Prune: if LP relaxation is already worse than best known, stop
            if (Math.ceil(lpSum - 1e-9) >= bestSoFar) return bestSoFar;

            // Find first fractional variable
            int fracIndex = -1;
            for (int i = 0; i < numButtons; i++) {
                if (Math.abs(point[i] - Math.round(point[i])) > 1e-6) {
                    fracIndex = i;
                    break;
                }
            }

            // All integer — valid solution found
            if (fracIndex == -1) {
                long total = Math.round(Arrays.stream(point).sum());
                return Math.min(total, bestSoFar);
            }

            // Branch on fracIndex
            double fracValue = point[fracIndex];
            int lo = (int) Math.floor(fracValue);
            int hi = lo + 1;

            // Branch 1: x[fracIndex] <= lo
            double[] coeffsLo = new double[numButtons];
            coeffsLo[fracIndex] = 1.0;
            List<LinearConstraint> constraintsLo = new ArrayList<>(constraints);
            constraintsLo.add(new LinearConstraint(coeffsLo, Relationship.LEQ, lo));
            bestSoFar = branchAndBound(f, constraintsLo, numButtons, bestSoFar);

            // Branch 2: x[fracIndex] >= hi
            double[] coeffsHi = new double[numButtons];
            coeffsHi[fracIndex] = 1.0;
            List<LinearConstraint> constraintsHi = new ArrayList<>(constraints);
            constraintsHi.add(new LinearConstraint(coeffsHi, Relationship.GEQ, hi));
            bestSoFar = branchAndBound(f, constraintsHi, numButtons, bestSoFar);

            return bestSoFar;

        } catch (NoFeasibleSolutionException | UnboundedSolutionException e) {
            return bestSoFar;
        }
    }
}