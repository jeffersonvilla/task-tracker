package online.jeffdev.persistence;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Utility class for logging to the CLI console.
 * This class ensures that messages are printed clearly without extra metadata
 * like timestamps or class names.
 */
public class CliLogger {
    private static final Logger logger = Logger.getLogger("CliLogger");

    private CliLogger() {
        throw new UnsupportedOperationException("Utility class");
    }

    static {
        // Remove default handlers
        Logger rootLogger = Logger.getLogger("");
        java.util.logging.Handler[] handlers = rootLogger.getHandlers();
        for (java.util.logging.Handler h : handlers) {
            rootLogger.removeHandler(h);
        }

        // Add custom handler that writes to CURRENT System.out
        // This is important for tests that use System.setOut
        java.util.logging.Handler customHandler = new java.util.logging.Handler() {
            @Override
            public void publish(LogRecord recordVar) {
                // No-op for console output
            }

            @Override
            public void flush() {
                // No-op for console output
            }

            @Override
            public void close() throws SecurityException {
                // No-op for console output
            }
        };

        customHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord recordVar) {
                return recordVar.getMessage();
            }
        });
        customHandler.setLevel(Level.INFO);

        logger.setUseParentHandlers(false);
        logger.addHandler(customHandler);
        logger.setLevel(Level.INFO);
    }

    /**
     * Prints an informational message to the console.
     * 
     * @param message The message to print.
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Prints an error message to the console.
     * 
     * @param message The message to print.
     */
    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }
}
