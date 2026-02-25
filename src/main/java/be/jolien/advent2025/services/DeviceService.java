package be.jolien.advent2025.services;

import be.jolien.advent2025.models.Device;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class DeviceService {
    public long calculatePathsFromYouToOut(Map<String, Device> deviceMap) {
        Device startNode = deviceMap.get("you");
        if (startNode == null) return 0;

        // Map <DeviceName, amountOfPathsFromHere>
        Map<String, Long> memo = new HashMap<>();

        return countPaths(startNode, memo);
    }

    private long countPaths(Device current, Map<String, Long> memo) {
        // Base case
        if (current.getName().equals("out")) {
            return 1;
        }

        if (memo.containsKey(current.getName())) {
            return memo.get(current.getName());
        }

        long totalPathsFromHere = 0;

        // count paths
        for (Device next : current.getAtachedDevices()) {
            totalPathsFromHere += countPaths(next, memo);
        }

        // Save result
        memo.put(current.getName(), totalPathsFromHere);

        return totalPathsFromHere;
    }

    public long findAmountPathsVisitedDacAndFft(Map<String, Device> deviceMap) {
        Device startNode = deviceMap.get("svr");
        if (startNode == null) return 0;

        Map<String, Long> memo = new HashMap<>();

        return countPathsWithVisitedDacFft(startNode, false, false, memo);
    }

    private long countPathsWithVisitedDacFft(Device current, boolean visitedDac, boolean visitedFft, Map<String, Long> memo) {
        // Update de status voor de huidige node
        boolean nowDac = visitedDac || current.getName().equals("dac");
        boolean nowFft = visitedFft || current.getName().equals("fft");

        // Base case: we zijn bij de uitgang
        if (current.getName().equals("out")) {
            // Alleen tellen als beide verplichte nodes zijn bezocht!
            return (nowDac && nowFft) ? 1 : 0;
        }

        // Check memo met de uitgebreide state
        String stateKey = current.getName() + "|" + nowDac + "|" + nowFft;
        if (memo.containsKey(stateKey)) {
            return memo.get(stateKey);
        }

        long totalPaths = 0;
        for (Device next : current.getAtachedDevices()) {
            totalPaths += countPathsWithVisitedDacFft(next, nowDac, nowFft, memo);
        }

        memo.put(stateKey, totalPaths);
        return totalPaths;
    }
}
