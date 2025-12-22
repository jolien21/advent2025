package be.jolien.advent2025;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class InputClient {

    private final RestClient restClient;

    InputClient(){
        this.restClient = RestClient.create();
    }

    public String fetchRawText(String url){
        return restClient.get()
                .uri(url)
                .header("Cookie", "session=53616c7465645f5fd77485d447ace8293b32ed6fe934b10a8692f0d72902f1dd418903555dc582dd2ce1f0f6e11ef9841f237327d1923546c32829e276a4e36c")
                .retrieve()
                .body(String.class);
    }

    public String readExampleFile(String fileName) {
        try {
            Path path = Paths.get("src/main/resources/" + fileName);
            return Files.readString(path);
        } catch (IOException e) {
            System.err.println("Kon bestand niet vinden: " + e.getMessage());
            return "";
        }
    }
}
