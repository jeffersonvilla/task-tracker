package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;

import java.util.List;

public class ListCommand implements Command {
    private final Persistence persistence;
    private final UserInterface ui;

    public ListCommand(Persistence persistence, UserInterface ui) {
        this.persistence = persistence;
        this.ui = ui;
    }

    @Override
    public void execute(String[] args) {
        String filter = (args != null && args.length > 0) ? args[0] : null;
        List<Task> tasks = persistence.getAllTasks();

        if (filter != null) {
            try {
                Status statusFilter = Status.valueOf(filter.toUpperCase().replace("-", "_"));
                tasks = tasks.stream()
                        .filter(task -> task.getStatus() == statusFilter)
                        .toList();
            } catch (IllegalArgumentException e) {
                ui.displayError("Invalid status filter: " + filter);
                return;
            }
        }

        if (tasks.isEmpty()) {
            ui.displayMessage("No tasks found.");
        } else {
            ui.displayTasks(tasks);
        }
    }
}
