package online.jeffdev.command;

import online.jeffdev.model.Task;

import online.jeffdev.persistence.Persistence;

public class AddCommand implements Command {
    private final Persistence persistence;

    public AddCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Error: Description is required.");
            return;
        }
        String description = args[0];
        Task taskCreated = persistence.addNewTask(new Task(description));
        System.out.println("Task added succesfully (ID: " + taskCreated.getId() + ")");
    }
}
