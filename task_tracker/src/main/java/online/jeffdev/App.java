package online.jeffdev;

public class App {

    private static final java.util.Map<String, online.jeffdev.command.Command> commands = new java.util.HashMap<>();

    static {
        commands.put("add", new online.jeffdev.command.AddCommand());
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            String action = args[0];
            String info = args[1];

            online.jeffdev.command.Command command = commands.get(action);
            if (command != null) {
                command.execute(info);
            } else {
                System.out.println("Wrong command");
            }
        }

    }

}
