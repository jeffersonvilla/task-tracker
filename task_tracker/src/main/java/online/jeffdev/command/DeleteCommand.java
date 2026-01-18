package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;

public class DeleteCommand implements Command {
    private final Persistence persistence;

    public DeleteCommand(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Error: Task ID is required.");
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            Task task = persistence.getTaskById(id);

            if (task == null) {
                System.out.println("Error: Task with ID " + id + " not found.");
                return;
            }

            persistence.deleteTask(id);
            System.out.println("Task deleted successfully (ID: " + id + ")");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Task ID format.");
        }
    }
}
