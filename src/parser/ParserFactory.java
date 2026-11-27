
package parser;

public class ParserFactory {

    public static TaskParser createParser() {
        String apiKey = System.getenv("LLM_API_KEY");

        if (apiKey != null && !apiKey.isBlank()) {
            return new LlmTaskParser(new LlmClient());
        }

        return new RuleBasedTaskParser();
    }
}
