package client.utils;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Logger {
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("logger");

    public static void logger(String filename, String message) {
        FileHandler handler = null;
        try {
            handler = new FileHandler("logs//" + filename + ".log", true);
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return new Date(record.getMillis()) + "  ---   " + record.getMessage() + "\n";
                }
            });
            logger.addHandler(handler);
            logger.info(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.close();
    }
}
