package online.jeffdev.persistence;

import java.util.List;

import online.jeffdev.model.Task;

public interface Persistence {

    Task addNewTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task);

    void deleteTask(int id);
}
