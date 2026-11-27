
package service;

import api.TaskRequest;
import exception.ValidationException;
import model.Priority;
import model.Task;
import repository.TaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private final TaskRepository taskRepository;
    private int nextId = 1;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a task from a raw request
    public Task createTask(TaskRequest request) {

        if (request == null) {
            throw new ValidationException("Task request cannot be null");
        }

        // Validate title
        if (request.getTitle() == null ||
                request.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title cannot be empty");
        }

        // Validate description
        if (request.getDescription() == null ||
                request.getDescription().trim().isEmpty()) {
            throw new ValidationException("Description cannot be empty");
        }

        // Validate completed
        String completedValue = request.getCompleted();

        if (completedValue == null ||
                !(completedValue.equals("true") ||
                        completedValue.equals("false"))) {
            throw new ValidationException(
                    "Completed must be true or false"
            );
        }

        boolean completed = Boolean.parseBoolean(completedValue);

        // Validate date
        LocalDate dueDate = null;

        if (request.getDueDate() != null &&
                !request.getDueDate().trim().isEmpty()) {

            try {
                dueDate = LocalDate.parse(request.getDueDate());
            } catch (DateTimeParseException e) {
                throw new ValidationException(
                        "Due date must be a valid date in YYYY-MM-DD format"
                );
            }
        }

        // Validate priority
        Priority priority = null;

        if (request.getPriority() != null &&
                !request.getPriority().trim().isEmpty()) {

            try {
                priority = Priority.valueOf(
                        request.getPriority().trim().toUpperCase()
                );
            } catch (IllegalArgumentException e) {
                throw new ValidationException(
                        "Priority must be LOW, MEDIUM, or HIGH"
                );
            }
        }

        // Build immutable Task
        Task task = Task.builder()
                .id(nextId++)
                .title(request.getTitle().trim())
                .description(request.getDescription().trim())
                .completed(completed)
                .dueDate(dueDate)
                .priority(priority)
                .build();

        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public boolean deleteTask(int id) {
        return taskRepository.delete(id);
    }
}