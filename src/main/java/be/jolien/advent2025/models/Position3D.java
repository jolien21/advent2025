package be.jolien.advent2025.models;

public class Position3D extends Position {
    private int z;

    public Position3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }
    public int getZ() {
        return z;
    }

    public long getStraigthLineDistance(Position3D other) {
        long dx = (long) other.getX() - this.getX();
        long dy = (long) other.getY() - this.getY();
        long dz = (long) other.getZ() - this.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
