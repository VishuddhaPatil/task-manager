
package api;

import exception.ApiException;
import exception.NotFoundException;
import model.Task;
import service.TaskService;
import service.TaskCreationService;

import java.util.List;

public class TaskApi {

    private final TaskService taskService;
    private final TaskCreationService taskCreationService;

    // Existing constructor
    public TaskApi(TaskService taskService) {
        this.taskService = taskService;
        this.taskCreationService = null;
    }

    // New constructor for natural-language creation
    public TaskApi(TaskService taskService,
                   TaskCreationService taskCreationService) {
        this.taskService = taskService;
        this.taskCreationService = taskCreationService;
    }

    // Create task
    public ApiResponse<Task> createTask(TaskRequest request) {
        try {
            Task task = taskService.createTask(request);
            return ApiResponse.success("Task created successfully", task);
        } catch (Exception e) {
            return ErrorResponseFactory.create(e);
        }
    }

    // Create task from natural language
    public ApiResponse<Task> createTaskFromText(
            String text, String idempotencyKey) {
        try {
            if (taskCreationService == null) {
                throw new IllegalStateException(
                        "Natural-language task creation is not configured"
                );
            }

            Task task = taskCreationService.createTaskFromText(
                    text, idempotencyKey
            );

            return ApiResponse.success(
                    "Task created from natural language successfully",
                    task
            );

        } catch (Exception e) {
            return ErrorResponseFactory.create(e);
        }
    }

    // Get task by ID
    public ApiResponse<Task> getTaskById(int id) {
        try {
            Task task = taskService.getTaskById(id)
                    .orElseThrow(() ->
                            new NotFoundException("Task not found: " + id)
                    );

            return ApiResponse.success(
                    "Task retrieved successfully", task
            );
        } catch (Exception e) {
            return ErrorResponseFactory.create(e);
        }
    }

    // Get all tasks
    public ApiResponse<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return ApiResponse.success(
                    "Tasks retrieved successfully", tasks
            );
        } catch (Exception e) {
            return ErrorResponseFactory.create(e);
        }
    }

    // Delete task
    public ApiResponse<Boolean> deleteTask(int id) {
        try {
            boolean deleted = taskService.deleteTask(id);

            if (!deleted) {
                throw new NotFoundException("Task not found: " + id);
            }

            return ApiResponse.success("Task deleted successfully", true);
        } catch (Exception e) {
            return ErrorResponseFactory.create(e);
        }
    }
}
