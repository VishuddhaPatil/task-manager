
package service;

import model.Priority;
import model.Task;
import parser.ParsedTask;
import parser.TaskParser;
import repository.TaskRepository;

import java.time.LocalDate;

public class TaskCreationService {

    private final TaskParser taskParser;
    private final TaskRepository repository;
    private int nextId = 1000;

    public TaskCreationService(TaskParser taskParser,
                               TaskRepository repository) {
        this.taskParser = taskParser;
        this.repository = repository;
    }

    public Task createTaskFromText(String text, String idempotencyKey) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("Idempotency key is required");
        }

        ParsedTask parsed = taskParser.parse(
                text,
                LocalDate.now().toString()
        );

        if (parsed.getTitle() == null || parsed.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title is invalid");
        }

        if (parsed.getDescription() == null ||
                parsed.getDescription().isBlank()) {
            throw new IllegalArgumentException("Task description is invalid");
        }


        Task task = Task.builder()
                .id(nextId++)
                .title(parsed.getTitle())
                .description(parsed.getDescription())
                .completed(parsed.isCompleted())
                .dueDate(parsed.getDueDate())
                .priority(parsed.getPriority() == null
                        ? Priority.MEDIUM : parsed.getPriority())
                .recurrence(parsed.getRecurrence())
                .build();

        return repository.save(task);
    }
}
