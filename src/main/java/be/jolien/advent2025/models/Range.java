package be.jolien.advent2025.models;

public class Range {
    private final long start;
    private final long end;

    public Range(long start, long end){
        if(start > end){
            throw new IllegalArgumentException("start > end");
        }
        if(start < 0){
            throw new IllegalArgumentException("start < 0");
        }
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean isTargetInRange(long target) {
        return target >= this.start && target <= this.end;
    }

    public long countNumbersInRange(){
        return (this.end - this.start) + 1;
    }
}
