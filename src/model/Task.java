
package model;

import java.time.LocalDate;

public class Task {

    // 1. Private final fields
    private final int id;
    private final String title;
    private final String description;
    private final boolean completed;
    private final LocalDate dueDate;
    private final Priority priority;
    private final Integer parentId;
    private final String recurrence;

    // 2. Private constructor
    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.completed = builder.completed;
        this.dueDate = builder.dueDate;
        this.priority = builder.priority;
        this.parentId = builder.parentId;
        this.recurrence = builder.recurrence;
    }

    // 3. Getters (no setters)
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getRecurrence() {
        return recurrence;
    }

    // 4. Builder entry point
    public static Builder builder() {
        return new Builder();
    }

    // 5. Builder class
    public static class Builder {

        private int id;
        private String title;
        private String description;
        private boolean completed;
        private boolean completedProvided;

        private LocalDate dueDate;
        private Priority priority;
        private Integer parentId;
        private String recurrence;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            this.completedProvided = true;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder parentId(Integer parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder recurrence(String recurrence) {
            this.recurrence = recurrence;
            return this;
        }

        // 6. Build the immutable Task
        public Task build() {

            if (title == null || description == null) {
                throw new IllegalStateException(
                        "Title and description are required"
                );
            }

            if (!completedProvided) {
                throw new IllegalStateException(
                        "Completed status is required"
                );
            }

            return new Task(this);
        }
    }

    // 7. Display task information
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", parentId=" + parentId +
                ", recurrence='" + recurrence + '\'' +
                '}';
    }
}
