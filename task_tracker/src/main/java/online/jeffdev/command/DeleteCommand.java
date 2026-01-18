package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;

public class DeleteCommand implements Command {
    private final Persistence persistence;
    private final UserInterface ui;

    public DeleteCommand(Persistence persistence, UserInterface ui) {
        this.persistence = persistence;
        this.ui = ui;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            ui.displayError("Error: Task ID is required.");
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            Task task = persistence.getTaskById(id);

            if (task == null) {
                ui.displayError("Error: Task with ID " + id + " not found.");
                return;
            }

            persistence.deleteTask(id);
            ui.displayMessage("Task deleted successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            ui.displayError("Error: Invalid Task ID format.");
        }
    }
}
