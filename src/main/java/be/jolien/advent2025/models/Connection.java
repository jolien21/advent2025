package be.jolien.advent2025.models;

import java.util.ArrayList;
import java.util.List;

public class Connection implements Comparable<Connection> {
    public int indexA;
    public int indexB;
    public long distanceSq;

    public Connection(int indexA, int indexB, long distanceSq) {
        this.indexA = indexA;
        this.indexB = indexB;
        this.distanceSq = distanceSq;
    }

    @Override
    public int compareTo(Connection other) {
        return Long.compare(this.distanceSq, other.distanceSq);
    }
}