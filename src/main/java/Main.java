
public class Main {
    public static void main(String[] args) {

        FileLogger fileLogger = new FileLogger("src/main/resources/superFile");
        fileLogger.log("Hello Danya");
    }
}