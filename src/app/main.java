package app;

import model.Task;
import model.Priority;

import java.time.LocalDate;
import java.util.Arrays;

public class main {

    public static void main(String[] args){

        System.out.println("Task manager started");
     Task task = Task.builder()
             .id(1)
             .title("Pay Rent")
             .description("Pay monthly apartment rent")
             .completed(false)
             .dueDate(LocalDate.of(2026,12,1))
             .priority(Priority.HIGH)
             .build();
        System.out.println("\nCreated Task:");
        System.out.println(task);
}
}