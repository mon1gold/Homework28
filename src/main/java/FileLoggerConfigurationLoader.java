import java.io.*;

public class FileLoggerConfigurationLoader {
    public FileLoggerConfiguration load(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String filePath = bufferedReader.readLine().substring(5);
            String level = bufferedReader.readLine().substring(6);
            int maxSize = Integer.parseInt(bufferedReader.readLine().substring(9));
            String fileFormat = bufferedReader.readLine().substring(7);
            return new FileLoggerConfiguration(new File(filePath), LoggingLevel.valueOf(level.toUpperCase()), maxSize, fileFormat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(FileLoggerConfiguration fileLoggerConfiguration, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write("FILE:" + fileLoggerConfiguration.getFile().getPath()
                    + "\nLEVEL:" + fileLoggerConfiguration.getLoggingLevel().toString()
                    + "\nMAX-SIZE:" + fileLoggerConfiguration.getMaxFileSize()
                    + "\nFORMAT:" + fileLoggerConfiguration.getFormat());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
