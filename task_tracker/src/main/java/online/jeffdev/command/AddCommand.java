package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.JsonFilePersistence;
import online.jeffdev.persistence.Persistence;

public class AddCommand implements Command {
    @Override
    public void execute(String description) {
        Persistence jsonFile = new JsonFilePersistence();
        Task taskCreated = jsonFile.addNewTask(new Task(description));
        System.out.println("Task added succesfully (ID: " + taskCreated.getId() + ")");
    }
}
