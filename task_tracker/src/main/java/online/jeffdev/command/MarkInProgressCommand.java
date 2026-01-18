package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;

import java.time.Instant;

public class MarkInProgressCommand implements Command {
    private final Persistence persistence;
    private final UserInterface ui;

    public MarkInProgressCommand(Persistence persistence, UserInterface ui) {
        this.persistence = persistence;
        this.ui = ui;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            ui.displayError("Error: Task ID is required.");
            return;
        }
        String idStr = args[0];

        try {
            int id = Integer.parseInt(idStr);
            Task task = persistence.getTaskById(id);

            if (task == null) {
                ui.displayError("Error: Task with ID " + id + " not found.");
                return;
            }

            task.setStatus(Status.IN_PROGRESS);
            task.setUpdatedAt(Instant.now());
            persistence.updateTask(task);

            ui.displayMessage("Task marked as in progress successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            ui.displayError("Error: Invalid Task ID format.");
        }
    }
}
