package online.jeffdev.persistence;

import online.jeffdev.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonFilePersistenceTest {

    @TempDir
    Path tempDir;

    @Test
    void testAddAndLoadTasks() {
        Path tempFile = tempDir.resolve("tasks-test.json");
        JsonFilePersistence persistence = new JsonFilePersistence(tempFile.toString());

        Task task1 = new Task("Task 1");
        persistence.addNewTask(task1);

        Task task2 = new Task("Task 2");
        persistence.addNewTask(task2);

        // We can't access loadTasks directly as it's private,
        // but we can verify by creating a new persistence instance
        // or by creating a method to list tasks if available.
        // For now, let's verify addNewTask returns the task with correct ID.

        assertEquals(1, task1.getId());
        assertEquals(2, task2.getId());
    }

    @Test
    void testPersistenceIntegrity() {
        Path tempFile = tempDir.resolve("tasks-integrity.json");
        JsonFilePersistence persistence1 = new JsonFilePersistence(tempFile.toString());

        Task task = new Task("Persistent Task");
        persistence1.addNewTask(task);

        // Create a new persistence pointing to the same file
        JsonFilePersistence persistence2 = new JsonFilePersistence(tempFile.toString());
        Task task2 = new Task("Second Task");
        persistence2.addNewTask(task2);

        assertEquals(2, task2.getId(), "Should resume ID from existing file");
    }

    @Test
    void testGetTaskById() {
        Path tempFile = tempDir.resolve("tasks-get.json");
        JsonFilePersistence persistence = new JsonFilePersistence(tempFile.toString());

        Task task = new Task("Test getTaskById");
        persistence.addNewTask(task);

        Task found = persistence.getTaskById(task.getId());
        assertNotNull(found);
        assertEquals(task.getDescription(), found.getDescription());
    }

    @Test
    void testUpdateTask() {
        Path tempFile = tempDir.resolve("tasks-update.json");
        JsonFilePersistence persistence = new JsonFilePersistence(tempFile.toString());

        Task task = new Task("Original Description");
        persistence.addNewTask(task);

        task.setDescription("Updated Description");
        persistence.updateTask(task);

        Task updated = persistence.getTaskById(task.getId());
        assertNotNull(updated);
        assertEquals("Updated Description", updated.getDescription());
    }
}
