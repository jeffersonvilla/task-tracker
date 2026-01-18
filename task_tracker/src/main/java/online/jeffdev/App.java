package online.jeffdev;

import online.jeffdev.command.Command;
import online.jeffdev.command.CommandKey;
import online.jeffdev.command.AddCommand;
import online.jeffdev.command.ListCommand;
import online.jeffdev.command.MarkInProgressCommand;
import online.jeffdev.command.MarkDoneCommand;

import online.jeffdev.persistence.JsonFilePersistence;
import online.jeffdev.persistence.Persistence;

import java.util.EnumMap;
import java.util.Map;

public class App {

    private static final Map<CommandKey, Command> commands = new EnumMap<>(CommandKey.class);

    static {
        Persistence persistence = new JsonFilePersistence();
        commands.put(CommandKey.ADD, new AddCommand(persistence));
        commands.put(CommandKey.LIST, new ListCommand(persistence));
        commands.put(CommandKey.MARK_IN_PROGRESS, new MarkInProgressCommand(persistence));
        commands.put(CommandKey.MARK_DONE, new MarkDoneCommand(persistence));
    }

    public static void main(String[] args) {
        if (args.length >= 1) {
            String action = args[0];
            String info = args.length > 1 ? args[1] : null;

            CommandKey key = CommandKey.fromKey(action);
            Command command = commands.get(key);
            if (command != null) {
                command.execute(info);
            } else {
                System.out.println("Wrong command");
            }
        }

    }

}
