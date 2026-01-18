package online.jeffdev;

import online.jeffdev.command.Command;
import online.jeffdev.command.CommandKey;
import online.jeffdev.command.AddCommand;

import java.util.HashMap;
import java.util.Map;

public class App {

    private static final Map<CommandKey, Command> commands = new HashMap<>();

    static {
        commands.put(CommandKey.ADD, new AddCommand());
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            String action = args[0];
            String info = args[1];

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
