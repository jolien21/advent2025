package be.jolien.advent2025.managers;

import java.util.ArrayList;
import java.util.List;

public class CircuitManager {
    private final int[] parent;
    private final int[] size;

    public CircuitManager(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Zoekt de 'leider' van het circuit waar punt i bij hoort.
     * Maakt gebruik van path compression voor snelheid.
     */
    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    /**
     * Verbindt twee punten met elkaar.
     * Geeft true terug als ze uit verschillende circuits kwamen en nu verbonden zijn.
     * Geeft false terug als ze al in hetzelfde circuit zaten.
     */
    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            if (size[rootI] < size[rootJ]) {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            } else {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
            return true;
        }
        return false;
    }

    /**
     * Hulpmethode voor Part 1: geeft een lijst van alle huidige circuit-groottes.
     */
    public List<Integer> getAllCircuitSizes() {
        List<Integer> sizes = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) {
                sizes.add(size[i]);
            }
        }
        return sizes;
    }
}