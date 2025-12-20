package be.jolien.advent2025;

class Dial {
    private final int range;
    private int currentPosition;

    Dial(int range) {
        if(range < 0) {
            throw new IllegalArgumentException("Range must be positive");
        }
        this.range = range;
        this.currentPosition = 50;
    }

    int getCurrentPosition() {
        return  currentPosition;
    }

    void move(char direction, int steps){
        switch(direction) {
            case 'R':
                currentPosition = (currentPosition + steps) % range;
                break;
            case 'L':
                currentPosition = (currentPosition - steps) % range;
                break;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    int moveAndCountAllZeros(char direction, int steps) {
        int zeroCount = 0;

        for (int i = 0; i < steps; i++) {
            if (direction == 'R') {
                currentPosition++;
                if (currentPosition == range) {
                    currentPosition = 0;
                }
            } else if (direction == 'L') {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = range - 1;
                }
            }

            if (currentPosition == 0) {
                zeroCount++;
            }
        }
        return zeroCount;
    }
}
