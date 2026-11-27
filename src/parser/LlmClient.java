
package parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LlmClient {

    private final HttpClient httpClient;

    public LlmClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    public String sendPrompt(String prompt) {

        String apiKey = System.getenv("LLM_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "OpenAI API key is missing"
            );
        }

        String requestBody =
                "{"
                        + "\"model\":\"gpt-4o-mini\","
                        + "\"input\":\"" + escapeJson(prompt) + "\""
                        + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.openai.com/v1/responses"
                ))
                .timeout(Duration.ofSeconds(60))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() < 200 ||
                    response.statusCode() >= 300) {throw new RuntimeException(
                    "OpenAI API request failed: "
                            + response.statusCode()
                            + " - "
                            + response.body()

                );
            }

            return response.body();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "OpenAI request was interrupted", e
            );

        } catch (java.io.IOException e) {
            throw new RuntimeException(
                    "Could not connect to OpenAI", e
            );
        }
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
