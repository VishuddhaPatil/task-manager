
package repository;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Integer, Task> tasks = new HashMap<>();

    private static final InMemoryTaskRepository INSTANCE =
            new InMemoryTaskRepository();

    private InMemoryTaskRepository() {
    }

    public static InMemoryTaskRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Task save(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public boolean delete(int id) {
        return tasks.remove(id) != null;
    }
}
