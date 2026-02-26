package be.jolien.advent2025.models;

import java.util.*;

public record PresentShape(int id, List<boolean[][]> variants) {

    public static PresentShape createWithVariants(int id, boolean[][] baseShape) {
        return new PresentShape(id, generateAllVariants(baseShape));
    }

    private static List<boolean[][]> generateAllVariants(boolean[][] original) {
        Set<String> uniqueShapes = new HashSet<>();
        List<boolean[][]> variants = new ArrayList<>();
        boolean[][] current = original;

        for (int flip = 0; flip < 2; flip++) {
            for (int rot = 0; rot < 4; rot++) {
                String signature = Arrays.deepToString(current);
                if (uniqueShapes.add(signature)) {
                    variants.add(current);
                }
                current = rotate90(current);
            }
            current = flipHorizontal(original);
        }
        return variants;
    }

    private static boolean[][] rotate90(boolean[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        boolean[][] rotated = new boolean[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                rotated[j][r - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }

    private static boolean[][] flipHorizontal(boolean[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        boolean[][] flipped = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                flipped[i][c - 1 - j] = matrix[i][j];
            }
        }
        return flipped;
    }
}
