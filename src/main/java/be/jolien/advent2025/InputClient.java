package be.jolien.advent2025;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
class InputClient {

    private final RestClient restClient;

    InputClient(){
        this.restClient = RestClient.create();
    }

    String fetchRawText(String url){
        return restClient.get()
                .uri(url)
                .header("Cookie", "session=53616c7465645f5fd77485d447ace8293b32ed6fe934b10a8692f0d72902f1dd418903555dc582dd2ce1f0f6e11ef9841f237327d1923546c32829e276a4e36c")
                .retrieve()
                .body(String.class);
    }
}
