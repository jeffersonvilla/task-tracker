package online.jeffdev.ui;

import online.jeffdev.model.Task;
import java.util.List;

public interface UserInterface {
    void displayMessage(String message);

    void displayError(String message);

    void displayTasks(List<Task> tasks);
}
