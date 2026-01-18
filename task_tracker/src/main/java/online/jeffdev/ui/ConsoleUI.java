package online.jeffdev.ui;

import online.jeffdev.model.Task;
import online.jeffdev.util.TaskFormatter;
import java.util.List;

public class ConsoleUI implements UserInterface {
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayError(String message) {
        System.err.println(message);
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        TaskFormatter.printTable(tasks);
    }
}
