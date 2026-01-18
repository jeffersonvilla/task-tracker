package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.CliLogger;
import online.jeffdev.persistence.Persistence;

public class AddCommand implements Command {
    private final Persistence persistence;

    public AddCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String description) {
        Task taskCreated = persistence.addNewTask(new Task(description));
        CliLogger.info("Task added succesfully (ID: " + taskCreated.getId() + ")");
    }
}
