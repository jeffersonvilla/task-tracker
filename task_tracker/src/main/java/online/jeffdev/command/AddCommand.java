package online.jeffdev.command;

import online.jeffdev.model.Task;

import online.jeffdev.persistence.Persistence;

public class AddCommand implements Command {
    private final Persistence persistence;

    public AddCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String description) {
        Task taskCreated = persistence.addNewTask(new Task(description));
        System.out.println("Task added succesfully (ID: " + taskCreated.getId() + ")");
    }
}
