package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkDoneCommandTest {

    private Persistence persistence;
    private MarkDoneCommand command;

    @BeforeEach
    void setUp() {
        persistence = mock(Persistence.class);
        command = new MarkDoneCommand(persistence);
    }

    @Test
    void testExecute_ValidId() {
        Task task = new Task("Test Task");
        task.setId(1);
        when(persistence.getTaskById(1)).thenReturn(task);

        command.execute("1");

        verify(persistence).updateTask(argThat(t -> t.getId() == 1 && t.getStatus() == Status.DONE));
    }

    @Test
    void testExecute_InvalidIdFormat() {
        command.execute("abc");
        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_TaskNotFound() {
        when(persistence.getTaskById(1)).thenReturn(null);

        command.execute("1");

        verify(persistence, never()).updateTask(any());
    }

    @Test
    void testExecute_NullId() {
        command.execute(null);
        verify(persistence, never()).updateTask(any());
    }
}
