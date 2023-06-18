import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class FileLoggerTest {
    private static FileLogger fileLogger;
    private static File file;

    @BeforeClass
    public static void beforeClass() {
        fileLogger = new FileLogger("src/main/resources/TestConfiguration");
        file = new File("TestLog.txt");
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void afterClass() {
        fileLogger = null;
        file = null;
    }

    @Test
    public void log() {
        String message = "Hello";
        fileLogger.log(message);
        List<String> allLines;
        try {
            allLines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertFalse(allLines.isEmpty());
        assertTrue(allLines.get(0).endsWith(message));
    }
}