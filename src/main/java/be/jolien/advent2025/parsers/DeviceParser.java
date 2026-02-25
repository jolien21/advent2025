package be.jolien.advent2025.parsers;

import be.jolien.advent2025.models.Device;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceParser {

    public Map<String, Device> parseDevices(List<String> inputLines) {
        Map<String, Device> deviceMap = new HashMap<>();

        for (String line : inputLines) {
            String[] parts = line.split(":");
            String sourceName = parts[0].trim();

            Device sourceDevice = deviceMap.computeIfAbsent(sourceName, Device::new);

            if (parts.length > 1) {
                String[] destinations = parts[1].trim().split("\\s+");

                for (String destName : destinations) {
                    Device destDevice = deviceMap.computeIfAbsent(destName, Device::new);

                    sourceDevice.addDevice(destDevice);
                }
            }
        }
        return deviceMap;
    }
}
