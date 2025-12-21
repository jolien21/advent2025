package be.jolien.advent2025;

import be.jolien.advent2025.models.Range;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdChecker {

    public long sumOfDoubleNumbersInHalfFrom(String startId, String endId) {
        var startNumber = Long.parseLong(startId);
        var endNumber = Long.parseLong(endId);
        long sum = 0;

        for (long i = startNumber; i <= endNumber; i++){
            if(isInvalidByHalf(i)) sum += i;
        }
        return sum;
    }

    boolean isInvalidByHalf(long id){
        var stringId = String.valueOf(id);

        if(stringId.length() % 2 != 0) return false;

        int middle = stringId.length() / 2;
        var part1 = stringId.substring(0, middle);
        var part2 = stringId.substring(middle);

        return part1.equals(part2);
    }

    public long sumOfRepetition(String startId, String endId) {
        var startNumber = Long.parseLong(startId);
        var endNumber = Long.parseLong(endId);
        long sum = 0;

        for (long i = startNumber; i <= endNumber; i++){
            if(isInvalidByRepetition(i)) sum += i;
        }
        return sum;
    }

    boolean isInvalidByRepetition(long id) {
        var idString = String.valueOf(id);
        var length = idString.length();
        for (int i = 1; i <= length / 2; i++) {
            if (length % i == 0) {
                String block = idString.substring(0, i);
                StringBuilder stringBuilder = new StringBuilder();

                int repetitions = length / i;
                for (int j = 0; j < repetitions; j++) {
                    stringBuilder.append(block);
                }

                if (stringBuilder.toString().equals(idString)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isIdInRangesOf(List<Range> ranges, long targetId){
        for(var range : ranges){
            if(range.isTargetInRange(targetId)) return true;
        }
        return false;
    }
}
