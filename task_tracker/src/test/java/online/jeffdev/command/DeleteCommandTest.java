package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteCommandTest {

    private Persistence persistence;
    private DeleteCommand command;

    @BeforeEach
    void setUp() {
        persistence = mock(Persistence.class);
        command = new DeleteCommand(persistence);
    }

    @Test
    void testExecute_ValidDelete() {
        Task task = new Task("Task to delete");
        task.setId(1);
        when(persistence.getTaskById(1)).thenReturn(task);

        command.execute(new String[] { "1" });

        verify(persistence).deleteTask(1);
    }

    @Test
    void testExecute_InvalidIdFormat() {
        command.execute(new String[] { "abc" });
        verify(persistence, never()).deleteTask(anyInt());
    }

    @Test
    void testExecute_TaskNotFound() {
        when(persistence.getTaskById(1)).thenReturn(null);

        command.execute(new String[] { "1" });

        verify(persistence, never()).deleteTask(anyInt());
    }

    @Test
    void testExecute_NoArgs() {
        command.execute(new String[0]);
        verify(persistence, never()).deleteTask(anyInt());
    }

    @Test
    void testExecute_NullArgs() {
        command.execute(null);
        verify(persistence, never()).deleteTask(anyInt());
    }
}
