package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkInProgressCommandTest {

    private Persistence persistence;
    private MarkInProgressCommand command;

    @BeforeEach
    void setUp() {
        persistence = mock(Persistence.class);
        command = new MarkInProgressCommand(persistence);
    }

    @Test
    void testExecute_ValidId() {
        Task task = new Task("Test Task");
        task.setId(1);
        when(persistence.getTaskById(1)).thenReturn(task);

        command.execute(new String[] { "1" });

        verify(persistence).updateTask(argThat(t -> t.getId() == 1 && t.getStatus() == Status.IN_PROGRESS));
    }

    @Test
    void testExecute_InvalidIdFormat() {
        command.execute(new String[] { "abc" });
        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_TaskNotFound() {
        when(persistence.getTaskById(1)).thenReturn(null);

        command.execute(new String[] { "1" });

        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_NullId() {
        command.execute(null);
        verify(persistence, never()).updateTask(any());
    }
}
