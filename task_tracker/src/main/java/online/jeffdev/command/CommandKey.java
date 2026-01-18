package online.jeffdev.command;

public enum CommandKey {
    ADD("add"),
    LIST("list"),
    MARK_IN_PROGRESS("mark-in-progress"),
    MARK_DONE("mark-done");

    private final String key;

    CommandKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static CommandKey fromKey(String key) {
        for (CommandKey commandKey : CommandKey.values()) {
            if (commandKey.key.equals(key)) {
                return commandKey;
            }
        }
        return null;
    }
}
