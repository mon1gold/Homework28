import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private FileLoggerConfiguration fileLoggerConfiguration;
    private String configurationFile;

    public FileLogger(String configurationFileName) {
        this.configurationFile = configurationFileName;
        this.fileLoggerConfiguration = new FileLoggerConfigurationLoader().load(configurationFileName);
    }

    public void log(String message) {
        String text = LocalTime.now() + " " + fileLoggerConfiguration.getLoggingLevel() + " Повідемлення: " + message;
        info(text);
        if (fileLoggerConfiguration.getLoggingLevel().equals(LoggingLevel.DEBUG)) {
            try {
                debug(text);
            } catch (FileMaxSizeReachedException fileMaxSizeReachedException) {
                File file = new File("Log_"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm"))
                        + "."
                        + fileLoggerConfiguration.getFormat());
                try {
                    file.createNewFile();
                    fileLoggerConfiguration.setFile(file);
                    new FileLoggerConfigurationLoader().write(fileLoggerConfiguration, configurationFile);
                    debug(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void debug(String message) {
        if (fileLoggerConfiguration.getFile().length() > fileLoggerConfiguration.getMaxFileSize()) {
            throw new FileMaxSizeReachedException("Current size = " + fileLoggerConfiguration.getFile().length()
                    + " max size = " + fileLoggerConfiguration.getMaxFileSize()
                    + " file path = " + fileLoggerConfiguration.getFile().getPath());
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileLoggerConfiguration.getFile(), true))) {
            bufferedWriter.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void info(String message) {
        System.out.println(message);
    }
}

