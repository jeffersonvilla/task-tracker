package online.jeffdev.command;

import online.jeffdev.model.Status;
import online.jeffdev.model.Task;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkDoneCommandTest {

    private Persistence persistence;
    private UserInterface ui;
    private MarkDoneCommand command;

    @BeforeEach
    void setUp() {
        persistence = mock(Persistence.class);
        ui = mock(UserInterface.class);
        command = new MarkDoneCommand(persistence, ui);
    }

    @Test
    void testExecute_ValidId() {
        Task task = new Task("Test Task");
        task.setId(1);
        when(persistence.getTaskById(1)).thenReturn(task);

        command.execute(new String[] { "1" });

        verify(persistence).updateTask(argThat(t -> t.getId() == 1 && t.getStatus() == Status.DONE));
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
