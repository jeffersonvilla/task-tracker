package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;

public class AddCommand implements Command {
    private final Persistence persistence;
    private final UserInterface ui;

    public AddCommand(Persistence persistence, UserInterface ui) {
        this.persistence = persistence;
        this.ui = ui;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            ui.displayError("Error: Description is required.");
            return;
        }
        String description = args[0];
        Task taskCreated = persistence.addNewTask(new Task(description));
        ui.displayMessage("Task added succesfully (ID: " + taskCreated.getId() + ")");
    }
}
