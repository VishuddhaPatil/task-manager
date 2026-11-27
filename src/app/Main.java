
package app;

import api.ApiResponse;
import api.TaskApi;
import api.TaskRequest;
import model.Task;
import repository.InMemoryTaskRepository;
import repository.TaskRepository;
import service.TaskService;
import service.TaskCreationService;
import parser.TaskParser;
import parser.ParserFactory;

public class Main {

    public static void main(String[] args) {

        System.out.println("Task Manager API started!");

        // 1. Initialize repository and services
        TaskRepository repository =
                InMemoryTaskRepository.getInstance();

        TaskService taskService = new TaskService(repository);

        TaskParser taskParser = ParserFactory.createParser();

        TaskCreationService creationService =
                new TaskCreationService(taskParser, repository);

        TaskApi taskApi = new TaskApi(taskService, creationService);

        // 2. Create a normal task
        System.out.println("\n1. CREATE TASK");

        TaskRequest request = new TaskRequest(
                "Learn API Development",
                "Practice API response handling",
                "false",
                "2026-12-05",
                "HIGH"
        );

        ApiResponse<Task> createResponse =
                taskApi.createTask(request);

        System.out.println(createResponse);

        // 3. Get all tasks
        System.out.println("\n2. GET ALL TASKS");
        System.out.println(taskApi.getAllTasks());

        // 4. Get task by ID
        System.out.println("\n3. GET TASK BY ID");
        System.out.println(taskApi.getTaskById(1));

        // 5. Test invalid request
        System.out.println("\n4. TEST INVALID REQUEST");

        TaskRequest invalidRequest = new TaskRequest(
                "",
                "Invalid task",
                "false",
                "2026-12-05",
                "HIGH"
        );

        System.out.println(taskApi.createTask(invalidRequest));

        // 6. Test missing task
        System.out.println("\n5. TEST MISSING TASK");
        System.out.println(taskApi.getTaskById(999));

        // 7. Delete task
        System.out.println("\n6. DELETE TASK");
        System.out.println(taskApi.deleteTask(1));

        // 8. Natural-language task creation
        System.out.println("\n7. NATURAL LANGUAGE TASK CREATION");

        ApiResponse<Task> aiResponse =
                taskApi.createTaskFromText(
                        "Complete Java assignment tomorrow. High priority.",
                        "request-001"
                );

        System.out.println(aiResponse);

        System.out.println("\nAPI testing completed!");
    }
}
