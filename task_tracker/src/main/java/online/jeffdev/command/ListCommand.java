package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.CliLogger;
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
                CliLogger.info("Invalid status filter: " + filter);
                return;
            }
        }

        if (tasks.isEmpty()) {
            CliLogger.info("No tasks found.");
        } else {
            tasks.forEach(task -> CliLogger.info(task.toString()));
        }
    }
}
