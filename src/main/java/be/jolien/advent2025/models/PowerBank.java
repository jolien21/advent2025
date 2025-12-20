package be.jolien.advent2025.models;

public class PowerBank {
    private final String batteries;

    public PowerBank(String batteries) {
        this.batteries = batteries;
    }

    private String findBestSequence(int targetLength) {
        int totalDigits = batteries.length();
        int toRemove = totalDigits - targetLength;
        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < totalDigits; i++) {
            int currentDigit = Character.getNumericValue(batteries.charAt(i));

            while (!stack.isEmpty() && toRemove > 0 &&
                    Character.getNumericValue(stack.charAt(stack.length() - 1)) < currentDigit) {
                stack.deleteCharAt(stack.length() - 1);
                toRemove--;
            }
            stack.append(currentDigit);
        }

        return stack.substring(0, targetLength);
    }

    public long findMaximumJoltage(int targetLength) {
        if (this.batteries == null || this.batteries.length() < targetLength) {
            return 0;
        }

        String bestSequence = findBestSequence(targetLength);

        return Long.parseLong(bestSequence);
    }
}