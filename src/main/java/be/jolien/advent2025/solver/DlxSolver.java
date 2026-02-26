package be.jolien.advent2025.solver;

import java.util.Arrays;

/**
 * DLX solver for set packing:
 * - Shape columns: mandatory (need = quantity), all must be satisfied
 * - Cell columns: "at most once" constraint, cells may remain uncovered
 *
 * Cell columns are NOT in the header (not chosen by MRV).
 * They are only used to detect conflicts (a cell may not be covered twice).
 * Shape columns ARE in the header and are chosen by MRV.
 */
public class DlxSolver {
    private int[] L, R, U, D, C, S, need;
    private final boolean[] isShape;
    private int nodeCount;
    private final int root = 0;

    public DlxSolver(int numCols, int[] need, int cellColCount) {
        int cap = 4_000_000;
        L = new int[cap]; R = new int[cap];
        U = new int[cap]; D = new int[cap]; C = new int[cap];
        S = new int[numCols + 1];
        this.need = Arrays.copyOf(need, numCols + 1);
        isShape = new boolean[numCols + 1];

        for (int i = cellColCount + 1; i <= numCols; i++) isShape[i] = true;

        // Initialize columns but do NOT link cell columns into the header
        for (int i = 0; i <= numCols; i++) {
            U[i] = D[i] = i; C[i] = i;
        }

        // Build header with shape columns only
        L[0] = 0; R[0] = 0;
        for (int i = cellColCount + 1; i <= numCols; i++) {
            L[i] = L[0]; R[i] = 0;
            R[L[0]] = i; L[0] = i;
        }

        nodeCount = numCols + 1;
    }

    public void addRow(int shapeCol, int[] cells) {
        if (nodeCount + cells.length + 2 >= L.length) grow(cells.length + 2);
        int f = makeNode(shapeCol, -1);
        for (int c : cells) makeNode(c, f);
    }

    private int makeNode(int c, int f) {
        int n = nodeCount++;
        C[n] = c; S[c]++;
        U[n] = U[c]; D[n] = c; D[U[c]] = n; U[c] = n;
        if (f == -1) { L[n] = R[n] = n; return n; }
        L[n] = L[f]; R[n] = f; R[L[f]] = n; L[f] = n;
        return f;
    }

    public boolean solve() {
        int[] frameCol = new int[4096];
        int[] frameRow = new int[4096];
        int depth = 0;

        while (true) {
            // All shape columns satisfied — solution found
            if (R[root] == root) {
                for (int d = depth - 1; d >= 0; d--) {
                    uncoverRow(frameRow[d]);
                    uncoverCol(frameCol[d]);
                }
                return true;
            }

            // Choose shape column with fewest options (MRV)
            int c = chooseCol();
            if (S[c] == 0) {
                depth = backtrack(frameCol, frameRow, depth);
                if (depth < 0) return false;
                continue;
            }

            coverCol(c);
            int r = D[c];
            if (r == c) {
                // No rows available for this column
                uncoverCol(c);
                depth = backtrack(frameCol, frameRow, depth);
                if (depth < 0) return false;
                continue;
            }

            coverRow(r);
            if (depth >= frameCol.length) {
                frameCol = Arrays.copyOf(frameCol, frameCol.length * 2);
                frameRow = Arrays.copyOf(frameRow, frameRow.length * 2);
            }
            frameCol[depth] = c;
            frameRow[depth] = r;
            depth++;
        }
    }

    private int backtrack(int[] frameCol, int[] frameRow, int depth) {
        while (depth > 0) {
            depth--;
            int prevCol = frameCol[depth];
            int prevRow = frameRow[depth];

            uncoverRow(prevRow);

            // Try the next row in the same column
            int next = D[prevRow];
            if (next != prevCol) {
                coverRow(next);
                frameRow[depth] = next;
                return depth + 1;
            }

            // No more rows — uncover column and go up one level
            uncoverCol(prevCol);
        }
        return -1;
    }

    private int chooseCol() {
        int c = R[root];
        for (int j = R[c]; j != root; j = R[j])
            if (S[j] < S[c]) c = j;
        return c;
    }

    /**
     * Cover all columns in row r.
     * - Shape columns: multiplicity logic
     * - Cell columns: remove conflicting rows (cell may only be covered once)
     */
    private void coverRow(int r) {
        for (int j = R[r]; j != r; j = R[j]) coverCol(C[j]);
    }

    private void uncoverRow(int r) {
        for (int j = L[r]; j != r; j = L[j]) uncoverCol(C[j]);
    }

    private void coverCol(int c) {
        if (isShape[c]) {
            need[c]--;
            if (need[c] == 0) hideCol(c);
        } else {
            hideRows(c);
        }
    }

    private void uncoverCol(int c) {
        if (isShape[c]) {
            if (need[c] == 0) showCol(c);
            need[c]++;
        } else {
            showRows(c);
        }
    }

    private void hideCol(int c) {
        L[R[c]] = L[c]; R[L[c]] = R[c];
        hideRows(c);
    }

    private void showCol(int c) {
        showRows(c);
        L[R[c]] = c; R[L[c]] = c;
    }

    private void hideRows(int c) {
        for (int i = D[c]; i != c; i = D[i])
            for (int j = R[i]; j != i; j = R[j]) {
                U[D[j]] = U[j]; D[U[j]] = D[j]; S[C[j]]--;
            }
    }

    private void showRows(int c) {
        for (int i = U[c]; i != c; i = U[i])
            for (int j = L[i]; j != i; j = L[j]) {
                S[C[j]]++; U[D[j]] = j; D[U[j]] = j;
            }
    }

    private void grow(int extra) {
        int nc = Math.max(L.length * 2, L.length + extra);
        L = Arrays.copyOf(L, nc); R = Arrays.copyOf(R, nc);
        U = Arrays.copyOf(U, nc); D = Arrays.copyOf(D, nc); C = Arrays.copyOf(C, nc);
    }
}