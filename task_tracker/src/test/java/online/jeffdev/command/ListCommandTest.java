package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.JsonFilePersistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ListCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private ListCommand listCommand;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        // Inject dedicated persistence for testing
        JsonFilePersistence persistence = new JsonFilePersistence();
        listCommand = new ListCommand(persistence);

        // Clean up any existing tasks.json before tests
        try {
            Files.deleteIfExists(Paths.get("tasks.json"));
        } catch (Exception e) {
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        try {
            Files.deleteIfExists(Paths.get("tasks.json"));
        } catch (Exception e) {
        }
    }

    private void addTestTask(String description, Status status) {
        JsonFilePersistence persistence = new JsonFilePersistence();
        Task task = new Task(description);
        task.setStatus(status);
        persistence.addNewTask(task);
    }

    @Test
    void testListAllTasks() {
        addTestTask("Task 1", Status.TODO);
        addTestTask("Task 2", Status.DONE);

        listCommand.execute(null);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Task 1"));
        assertTrue(output.contains("Task 2"));
    }

    @Test
    void testListDoneTasks() {
        addTestTask("Task 1", Status.TODO);
        addTestTask("Task 2", Status.DONE);

        listCommand.execute("done");

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Task 2"));
        assertFalse(output.contains("Task 1"));
    }

    @Test
    void testEmptyList() {
        listCommand.execute(null);
        assertTrue(outputStreamCaptor.toString().trim().contains("No tasks found."));
    }

    @Test
    void testInvalidFilter() {
        listCommand.execute("invalid");
        assertTrue(outputStreamCaptor.toString().contains("Invalid status filter"));
    }
}
