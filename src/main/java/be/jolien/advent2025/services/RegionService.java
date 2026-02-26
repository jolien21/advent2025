package be.jolien.advent2025.services;

import be.jolien.advent2025.models.PresentShape;
import be.jolien.advent2025.models.Region;
import be.jolien.advent2025.solver.DlxSolver;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class RegionService {

    private record PlacementKey(int shapeId, int width, int height) {}
    private final Map<PlacementKey, int[][]> placementCache = new ConcurrentHashMap<>();

    public long countFittingRegions(List<Region> regions, List<PresentShape> allShapes) {
        int maxId = allShapes.stream().mapToInt(PresentShape::id).max().orElse(0);
        PresentShape[] shapes = new PresentShape[maxId + 1];
        int[] cellCounts = new int[maxId + 1];
        for (PresentShape s : allShapes) {
            shapes[s.id()] = s;
            cellCounts[s.id()] = countCells(s);
        }

        int cores = Runtime.getRuntime().availableProcessors();

        try (ForkJoinPool customPool = new ForkJoinPool(cores)) {
            return customPool.submit(() ->
                    regions.parallelStream().filter(region ->
                            canFit(region, shapes, cellCounts)
                    ).count()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error while calculating: " + e.getMessage());
            Thread.currentThread().interrupt();
            return 0;
        }
    }

    private boolean canFit(Region region, PresentShape[] shapes, int[] cellCounts) {
        int W = region.width(), H = region.height();
        int totalCells = W * H;
        Map<Integer, Integer> counts = region.presentCounts();

        // Collect shape requests, sorted by cell count descending (largest shapes first)
        List<Integer> reqIds = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : counts.entrySet())
            if (e.getValue() > 0) reqIds.add(e.getKey());
        reqIds.sort((a, b) -> cellCounts[b] - cellCounts[a]);

        // Fast check: do all shapes fit in the region by cell count?
        int totalNeeded = 0;
        for (int id : reqIds) totalNeeded += counts.get(id) * cellCounts[id];
        if (totalNeeded > totalCells) return false;

        // Fast check: does each shape have at least one valid placement?
        for (int id : reqIds)
            if (getOrComputePlacements(id, shapes[id], W, H).length == 0) return false;

        // DLX setup:
        // Columns 1..totalCells       = cell columns (at-most-once, not in header)
        // Columns totalCells+1..numCols = shape columns (mandatory, in header)
        int numCols = totalCells + reqIds.size();
        int[] need = new int[numCols + 1];
        int[] shapeColMap = new int[reqIds.size()];
        for (int i = 0; i < reqIds.size(); i++) {
            shapeColMap[i] = totalCells + i + 1;
            need[shapeColMap[i]] = counts.get(reqIds.get(i));
        }

        DlxSolver solver = new DlxSolver(numCols, need, totalCells);
        for (int i = 0; i < reqIds.size(); i++) {
            int[][] placements = getOrComputePlacements(reqIds.get(i), shapes[reqIds.get(i)], W, H);
            for (int[] p : placements) solver.addRow(shapeColMap[i], p);
        }

        return solver.solve();
    }

    private int[][] getOrComputePlacements(int id, PresentShape shape, int W, int H) {
        return placementCache.computeIfAbsent(new PlacementKey(id, W, H), k -> {
            List<int[]> res = new ArrayList<>();
            Set<String> seen = new HashSet<>();
            for (boolean[][] var : shape.variants()) {
                if (!seen.add(Arrays.deepToString(var))) continue;
                int vH = var.length, vW = var[0].length;
                if (vH > H || vW > W) continue;
                int cc = 0;
                for (boolean[] row : var) for (boolean c : row) if (c) cc++;
                for (int r = 0; r <= H - vH; r++)
                    for (int c = 0; c <= W - vW; c++) {
                        int[] cells = new int[cc];
                        int idx = 0;
                        for (int vr = 0; vr < vH; vr++)
                            for (int vc = 0; vc < vW; vc++)
                                if (var[vr][vc]) cells[idx++] = (r + vr) * W + (c + vc) + 1;
                        res.add(cells);
                    }
            }
            return res.toArray(new int[0][]);
        });
    }

    private int countCells(PresentShape s) {
        int c = 0;
        for (boolean[] row : s.variants().getFirst()) for (boolean v : row) if (v) c++;
        return c;
    }
}