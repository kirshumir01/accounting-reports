import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public String readFileContentsOrNull(String fileName) {
        String path = "./resources/" + fileName;
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return null;
        }
    }

    public void printFileData(int inputMenuItem, int firstMonthNumber, int lastMonthNumber, int year) {
        if (inputMenuItem == 1) {
            for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
                String monthlyReportFileName = "m.20210" + i + ".csv";
                if (readFileContentsOrNull(monthlyReportFileName) != null) {
                    System.out.println("В файле месячного отчета " + monthlyReportFileName + " содержатся следующие данные:");
                    String content = readFileContentsOrNull(monthlyReportFileName);
                    System.out.println(content);
                } else {
                    break;
                }
            }
        } else if (inputMenuItem == 2) {
            String yearlyReportFileName = "y." + year + ".csv";
            if (readFileContentsOrNull(yearlyReportFileName) != null) {
                System.out.println("В файле годового отчета " + yearlyReportFileName + " содержатся следующие данные:");
                String content = readFileContentsOrNull(yearlyReportFileName);
                System.out.println(content);
            }
        }
    }
}
