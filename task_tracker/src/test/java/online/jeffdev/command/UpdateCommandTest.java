package online.jeffdev.command;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateCommandTest {

    private Persistence persistence;
    private UpdateCommand command;

    @BeforeEach
    void setUp() {
        persistence = mock(Persistence.class);
        command = new UpdateCommand(persistence);
    }

    @Test
    void testExecute_ValidUpdate() {
        Task task = new Task("Old Description");
        task.setId(1);
        when(persistence.getTaskById(1)).thenReturn(task);

        command.execute(new String[] { "1", "New Description" });

        verify(persistence).updateTask(argThat(t -> t.getId() == 1 && t.getDescription().equals("New Description")));
    }

    @Test
    void testExecute_InvalidIdFormat() {
        command.execute(new String[] { "abc", "New Description" });
        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_TaskNotFound() {
        when(persistence.getTaskById(1)).thenReturn(null);

        command.execute(new String[] { "1", "New Description" });

        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_InsufficientArgs() {
        command.execute(new String[] { "1" });
        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_NullArgs() {
        command.execute(null);
        verify(persistence, never()).updateTask(any());
    }
}
