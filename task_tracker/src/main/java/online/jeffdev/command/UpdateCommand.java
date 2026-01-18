package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;

import java.time.Instant;

public class UpdateCommand implements Command {
    private final Persistence persistence;
    private final UserInterface ui;

    public UpdateCommand(Persistence persistence, UserInterface ui) {
        this.persistence = persistence;
        this.ui = ui;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length < 2) {
            ui.displayError("Error: Task ID and new description are required.");
            ui.displayMessage("Usage: update <id> <description>");
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            String newDescription = args[1];

            Task task = persistence.getTaskById(id);
            if (task == null) {
                ui.displayError("Error: Task with ID " + id + " not found.");
                return;
            }

            task.setDescription(newDescription);
            task.setUpdatedAt(Instant.now());
            persistence.updateTask(task);

            ui.displayMessage("Task updated successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            ui.displayError("Error: Invalid Task ID format.");
        }
    }
}
