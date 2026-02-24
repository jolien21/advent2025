package be.jolien.advent2025.models;

public class Rectangle {
    private final Position corner1;
    private final Position corner2;

    public Rectangle(Position corner1, Position corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public Position getCorner1() {
        return corner1;
    }
    public Position getCorner2() {
        return corner2;
    }

    public long getOppervlakte() {
        long breedte = Math.abs((long) corner2.getX() - corner1.getX()) + 1;
        long hoogte = Math.abs((long) corner2.getY() - corner1.getY()) + 1;
        return breedte * hoogte;
    }
}
