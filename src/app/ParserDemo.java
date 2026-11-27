package app;

import parser.ParsedTask;
import parser.RuleBasedTaskParser;
import parser.TaskParser;
import java.time.LocalDate;

public class ParserDemo {
public static void main(String[] args) {
    TaskParser parser = new RuleBasedTaskParser();

    String input = "Completed Java assignment. LOW priority Already completed.Due 2026-11-27.";

    ParsedTask task = parser.parse(
            "\"Pay rent every month. High priority.\"",
            LocalDate.now().toString()
    );

    System.out.println("Parsed Task Details:");
    System.out.println("--------------------");
    System.out.println("Title:" + task.getTitle());
    System.out.println("Description:" + task.getDescription());
    System.out.println("Completed:" + task.isCompleted());
    System.out.println("Due Date:" + task.getDueDate());
    System.out.println("Priority:" + task.getPriority());
}
}
