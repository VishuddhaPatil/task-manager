
package parser;

import model.Priority;

import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleBasedTaskParser implements TaskParser {

    @Override
    public ParsedTask parse(String input, String today) {

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Task description cannot be empty"
            );
        }

        LocalDate currentDate = LocalDate.parse(today);

        String text = input.trim();
        String lowerText = text.toLowerCase(Locale.ROOT);

        // Extract priority
        Priority priority = Priority.MEDIUM;

        if (lowerText.contains("high priority")) {
            priority = Priority.HIGH;
        } else if (lowerText.contains("low priority")) {
            priority = Priority.LOW;
        }

        // Extract completion status
        boolean completed =
                lowerText.contains("already completed") ||
                        lowerText.contains("already done");

        // Extract due date
        LocalDate dueDate = null;

        if (lowerText.contains("tomorrow")) {
            dueDate = currentDate.plusDays(1);
        } else {
            Pattern datePattern = Pattern.compile(
                    "\\b\\d{4}-\\d{2}-\\d{2}\\b"
            );

            Matcher matcher = datePattern.matcher(text);

            if (matcher.find()) {
                try {
                    dueDate = LocalDate.parse(matcher.group());
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Invalid date in task description"
                    );
                }
            }
        }

        // Extract recurrence
        String recurrence = null;

        if (lowerText.contains("every month")) {
            recurrence = "MONTHLY";
        } else if (lowerText.contains("every day")) {
            recurrence = "DAILY";
        } else if (lowerText.contains("every week")) {
            recurrence = "WEEKLY";
        }

        return new ParsedTask(
                text,
                text,
                dueDate,
                recurrence,
                completed,
                priority
        );
    }
}
