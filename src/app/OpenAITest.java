package app;

import parser.LlmClient;

public class OpenAITest {

    public static void main(String[] args) {

        System.out.println("Connecting to OpenAI...");
        String key = System.getenv("LLM_API_KEY");
        System.out.println("API key available: " + (key != null && !key.isBlank()));
        try {
            LlmClient client = new LlmClient();

            String response = client.sendPrompt(
                    "Reply with exactly this text: OpenAI connection successful"
            );

            System.out.println("OpenAI responded!");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("OpenAI connection failed!");
            System.out.println(e.getMessage());
        }
    }
}
