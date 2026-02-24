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
                .header("Cookie", "session=53616c7465645f5f4f1f29ce37f29e7a784d54ebb3e5e2721a09f1b4e60adccf7028d020c5bee892d7480714eb8663916f5789dd5c2e6339445a13889bb0aa59")
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
