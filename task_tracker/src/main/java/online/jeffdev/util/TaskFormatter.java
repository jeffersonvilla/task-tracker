package online.jeffdev.util;

import online.jeffdev.model.Task;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskFormatter {

    private TaskFormatter() {
        // Utility class
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static void printTable(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }

        String separator = "+----+--------------------------------+-------------+---------------------+---------------------+";
        String header = "| ID | Description                    | Status      | Created At          | Updated At          |";

        System.out.println(separator);
        System.out.println(header);
        System.out.println(separator);

        for (Task task : tasks) {
            System.out.printf("| %-2d | %-30s | %-11s | %-19s | %-19s |%n",
                    task.getId(),
                    truncate(task.getDescription(), 30),
                    task.getStatus(),
                    formatter.format(task.getCreatedAt()),
                    formatter.format(task.getUpdatedAt()));
        }

        System.out.println(separator);
    }

    private static String truncate(String text, int maxLength) {
        if (text == null)
            return "";
        if (text.length() <= maxLength)
            return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
