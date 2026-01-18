package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;

import java.time.Instant;

public class MarkDoneCommand implements Command {
    private final Persistence persistence;

    public MarkDoneCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Error: Task ID is required.");
            return;
        }
        String idStr = args[0];
        if (idStr == null) {
            System.out.println("Error: Task ID is required.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Task task = persistence.getTaskById(id);

            if (task == null) {
                System.out.println("Error: Task with ID " + id + " not found.");
                return;
            }

            task.setStatus(Status.DONE);
            task.setUpdatedAt(Instant.now());
            persistence.updateTask(task);

            System.out.println("Task marked as done successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Task ID format.");
        }
    }
}
