package be.jolien.advent2025.providers;

import be.jolien.advent2025.InputClient;
import be.jolien.advent2025.models.Device;
import be.jolien.advent2025.parsers.DeviceParser;
import be.jolien.advent2025.parsers.ListParser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeviceProvider {
    private final InputClient inputClient;
    private final ListParser listParser;
    private final DeviceParser deviceParser;
    private final UrlProvider urlProvider;

    public DeviceProvider(InputClient inputClient, ListParser listParser, DeviceParser deviceParser, UrlProvider urlProvider) {
        this.inputClient = inputClient;
        this.listParser = listParser;
        this.deviceParser = deviceParser;
        this.urlProvider = urlProvider;
    }

    public Map<String, Device> getDevices(int day) {
        var rawText = inputClient.fetchRawText(urlProvider.getUrlForDay(day));
        var rawLines = listParser.parseToListByEnter(rawText);

        return deviceParser.parseDevices(rawLines);
    }
}
