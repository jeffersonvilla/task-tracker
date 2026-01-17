package online.jeffdev.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SupportLogger {
    private static final Logger logger = Logger.getLogger("SupportLogger");
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE = LOG_DIR + "/support.log";

    private SupportLogger() {
        throw new UnsupportedOperationException("Utility class");
    }

    static {
        try {
            if (!Files.exists(Paths.get(LOG_DIR))) {
                Files.createDirectories(Paths.get(LOG_DIR));
            }
            FileHandler fh = new FileHandler(LOG_FILE, true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize SupportLogger", e);
        }
    }

    public static void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void logInfo(String message) {
        logger.info(message);
    }
}
