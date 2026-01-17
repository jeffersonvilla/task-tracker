package online.jeffdev.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreation() {
        String description = "Test Task";
        Task task = new Task(description);

        assertEquals(description, task.getDescription());
        assertEquals(Status.TODO, task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void testTaskSetters() {
        Task task = new Task("Initial");
        task.setDescription("Updated");
        task.setStatus(Status.DONE);
        task.setId(10);

        assertEquals("Updated", task.getDescription());
        assertEquals(Status.DONE, task.getStatus());
        assertEquals(10, task.getId());
    }
}
