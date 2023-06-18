import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FileLoggerConfigurationLoaderTest {
    private static final String FILE_DATA_PATTERN = "FILE:%s\nLEVEL:%s\nMAX-SIZE:%d\nFORMAT:%s";
    private static FileLoggerConfigurationLoader fileLoggerConfigurationLoader;

    @BeforeClass
    public static void beforeClass() {
        fileLoggerConfigurationLoader = new FileLoggerConfigurationLoader();
    }

    @AfterClass
    public static void afterClass() {
        fileLoggerConfigurationLoader = null;
    }

    @Test
    public void loadTest() {
        File file = new File("FileTest.txt");
        String fileName = "test.txt";
        String level = "INFO";
        int maxSize = 200;
        String format = "txt";
        String data = String.format(FILE_DATA_PATTERN, fileName, level, maxSize, format);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileLoggerConfiguration fileLoggerConfiguration = fileLoggerConfigurationLoader.load(file.getPath());
        assertEquals(fileName, fileLoggerConfiguration.getFile().getName());
        assertEquals(level, fileLoggerConfiguration.getLoggingLevel().toString());
        assertEquals(maxSize, fileLoggerConfiguration.getMaxFileSize());
        assertEquals(format, fileLoggerConfiguration.getFormat());
    }

    @Test
    public void writeTest() {
        File file = new File("FileTest.txt");
        String fileName = "test.txt";
        String level = "INFO";
        int maxSize = 200;
        String format = "txt";
        fileLoggerConfigurationLoader.write(new FileLoggerConfiguration(new File(fileName), LoggingLevel.valueOf(level), maxSize, format), file.getName());
        String data;
        try {
            data = Files.readAllLines(file.toPath()).stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String originalData = String.format(FILE_DATA_PATTERN, fileName, level, maxSize, format);
        assertEquals(originalData, data);
    }
}