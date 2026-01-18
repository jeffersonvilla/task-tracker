package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;

import java.time.Instant;

public class UpdateCommand implements Command {
    private final Persistence persistence;

    public UpdateCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Error: Task ID and new description are required.");
            System.out.println("Usage: update <id> <description>");
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            String newDescription = args[1];

            Task task = persistence.getTaskById(id);
            if (task == null) {
                System.out.println("Error: Task with ID " + id + " not found.");
                return;
            }

            task.setDescription(newDescription);
            task.setUpdatedAt(Instant.now());
            persistence.updateTask(task);

            System.out.println("Task updated successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Task ID format.");
        }
    }
}
