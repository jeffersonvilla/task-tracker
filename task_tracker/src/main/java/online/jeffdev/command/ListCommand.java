package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;

import online.jeffdev.persistence.Persistence;

import java.util.List;

public class ListCommand implements Command {
    private final Persistence persistence;

    public ListCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String filter) {
        List<Task> tasks = persistence.getAllTasks();

        if (filter != null) {
            try {
                Status statusFilter = Status.valueOf(filter.toUpperCase().replace("-", "_"));
                tasks = tasks.stream()
                        .filter(task -> task.getStatus() == statusFilter)
                        .toList();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status filter: " + filter);
                return;
            }
        }

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            tasks.forEach(task -> System.out.println(task.toString()));
        }
    }
}
