
package parser;

import model.Priority;
import java.time.LocalDate;

public class ParsedTask {

    private final String title;
    private final String description;
    private final LocalDate dueDate;
    private final String recurrence;
    private final boolean completed;
    private final Priority priority;

    public ParsedTask(
            String title,
            String description,
            LocalDate dueDate,
            String recurrence,
            boolean completed,
            Priority priority
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.recurrence = recurrence;
        this.completed = completed;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Priority getPriority() {
        return priority;
    }
}
