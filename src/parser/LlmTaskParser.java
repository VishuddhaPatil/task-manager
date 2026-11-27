
package parser;

import model.Priority;

import java.time.LocalDate;

public class LlmTaskParser implements TaskParser {

    private final LlmClient llmClient;

    public LlmTaskParser(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public ParsedTask parse(String text, String today) {

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Task description cannot be empty"
            );
        }

        LocalDate.parse(today);

        String prompt =
                "You are a task management assistant.\n" +
                        "Today's date is " + today + ".\n" +
                        "Convert the user's task description into structured data.\n" +
                        "Resolve relative dates using today's date.\n" +
                        "Return ONLY valid JSON. Do not use markdown.\n" +
                        "Use exactly these fields:\n" +
                        "{\n" +
                        "  \"title\": \"task title\",\n" +
                        "  \"description\": \"task description\",\n" +
                        "  \"dueDate\": \"YYYY-MM-DD or null\",\n" +
                        "  \"recurrence\": \"DAILY, WEEKLY, MONTHLY or null\"\n" +
                        "}\n" +
                        "If no due date or recurrence exists, use null.\n" +
                        "User's task: " + text;

        // Send prompt to OpenAI
        String response = llmClient.sendPrompt(prompt);

        // Extract generated JSON text
        String taskJson =
                SimpleJson.extractOutputText(response);

        // Read individual fields
        String title =
                SimpleJson.getString(taskJson, "title");

        String description =
                SimpleJson.getString(taskJson, "description");

        String dueDateValue =
                SimpleJson.getString(taskJson, "dueDate");

        String recurrence =
                SimpleJson.getString(taskJson, "recurrence");

        // Validate required fields
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "AI returned an invalid task title"
            );
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "AI returned an invalid task description"
            );
        }

        // Parse optional due date
        LocalDate dueDate = null;

        if (dueDateValue != null) {
            dueDate = LocalDate.parse(dueDateValue);
        }

        // Return structured task
        return new ParsedTask(
                title,
                description,
                dueDate,
                recurrence,
                false,
                Priority.MEDIUM
        );
    }
}
