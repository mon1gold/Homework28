import java.io.File;

public class FileLoggerConfiguration {
    private File file;
    private LoggingLevel loggingLevel;
    private int maxFileSize;
    private String format;

    public FileLoggerConfiguration(File file, LoggingLevel loggingLevel, int maxFileSize, String format) {
        this.file = file;
        this.loggingLevel = loggingLevel;
        this.maxFileSize = maxFileSize;
        this.format = format;
    }

    public File getFile() {
        return file;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public String getFormat() {
        return format;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileLoggerConfiguration{" +
                "file=" + file +
                ", loggingLevel=" + loggingLevel +
                ", maxFileSize=" + maxFileSize +
                ", format='" + format + '\'' +
                '}';
    }
}
