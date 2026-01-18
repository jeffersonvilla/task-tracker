package online.jeffdev;

import online.jeffdev.command.*;
import online.jeffdev.persistence.JsonFilePersistence;
import online.jeffdev.persistence.Persistence;
import online.jeffdev.ui.ConsoleUI;
import online.jeffdev.ui.UserInterface;

import java.util.EnumMap;
import java.util.Map;

public class App {

    private final Map<CommandKey, Command> commands = new EnumMap<>(CommandKey.class);
    private final UserInterface ui;

    public App(Persistence persistence, UserInterface ui) {
        this.ui = ui;
        commands.put(CommandKey.ADD, new AddCommand(persistence, ui));
        commands.put(CommandKey.LIST, new ListCommand(persistence, ui));
        commands.put(CommandKey.MARK_IN_PROGRESS, new MarkInProgressCommand(persistence, ui));
        commands.put(CommandKey.MARK_DONE, new MarkDoneCommand(persistence, ui));
        commands.put(CommandKey.UPDATE, new UpdateCommand(persistence, ui));
        commands.put(CommandKey.DELETE, new DeleteCommand(persistence, ui));
    }

    public void run(String[] args) {
        if (args.length >= 1) {
            String action = args[0];
            CommandKey key = CommandKey.fromKey(action);
            Command command = commands.get(key);

            if (command != null) {
                String[] commandArgs = new String[0];
                if (args.length > 1) {
                    commandArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, commandArgs, 0, args.length - 1);
                }
                command.execute(commandArgs);
            } else {
                ui.displayError("Error: Unknown command '" + action + "'.");
            }
        } else {
            ui.displayMessage("Usage: task-tracker <command> [args]");
        }
    }

    public static void main(String[] args) {
        Persistence persistence = new JsonFilePersistence();
        UserInterface ui = new ConsoleUI();
        App app = new App(persistence, ui);
        app.run(args);
    }
}
