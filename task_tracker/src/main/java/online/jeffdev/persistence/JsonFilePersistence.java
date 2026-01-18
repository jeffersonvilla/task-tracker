package online.jeffdev.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;

public class JsonFilePersistence implements Persistence {

    private final String fileName;

    public JsonFilePersistence() {
        this.fileName = "tasks.json";
    }

    public JsonFilePersistence(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Task addNewTask(Task task) {
        List<Task> tasks = loadTasks();

        int nextId = 1;
        if (!tasks.isEmpty()) {
            nextId = tasks.stream().mapToInt(Task::getId).max().getAsInt() + 1;
        }

        task.setId(nextId);
        tasks.add(task);

        saveTasks(tasks);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return loadTasks();
    }

    @Override
    public Task getTaskById(int id) {
        return loadTasks().stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateTask(Task task) {
        List<Task> tasks = loadTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                saveTasks(tasks);
                return;
            }
        }
    }

    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            return tasks;
        }

        try {
            String content = Files.readString(path).trim();
            if (content.isEmpty() || content.equals("[]")) {
                return tasks;
            }

            // Simple regex parser for JSON objects in an array
            Pattern pattern = Pattern.compile(
                    "\\{\"id\":(\\d+),\"description\":\"(.*?)\",\"status\":\"(.*?)\",\"createdAt\":\"(.*?)\",\"updatedAt\":\"(.*?)\"\\}");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                int id = Integer.parseInt(matcher.group(1));
                String description = matcher.group(2).replace("\\\"", "\"");
                Status status = Status.valueOf(matcher.group(3));
                Instant createdAt = Instant.parse(matcher.group(4));
                Instant updatedAt = Instant.parse(matcher.group(5));

                Task task = new Task(description);
                task.setId(id);
                task.setStatus(status);
                task.setCreatedAt(createdAt);
                task.setUpdatedAt(updatedAt);
                tasks.add(task);
            }
        } catch (IOException e) {
            SupportLogger.logError("Error reading tasks file", e);
        }

        return tasks;
    }

    private void saveTasks(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append("  {");
            sb.append("\"id\":").append(task.getId()).append(",");
            sb.append("\"description\":\"").append(task.getDescription().replace("\"", "\\\"")).append("\",");
            sb.append("\"status\":\"").append(task.getStatus().name()).append("\",");
            sb.append("\"createdAt\":\"").append(task.getCreatedAt().toString()).append("\",");
            sb.append("\"updatedAt\":\"").append(task.getUpdatedAt().toString()).append("\"");
            sb.append("}");
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        try {
            Files.writeString(Paths.get(fileName), sb.toString());
        } catch (IOException e) {
            SupportLogger.logError("Error saving tasks file", e);
        }
    }
}
