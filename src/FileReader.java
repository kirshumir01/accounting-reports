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

    // создать метод, который выводит в консоль содержание *.csv-файла
    public void printFileData(int inputMenuItem, int firstMonthNumber, int lastMonthNumber, int year) {
        // вывести содержание файлов месячных отчетов
        if (inputMenuItem == 1) {
            for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
                int monthNumber = i;
                String monthlyReportFileName = "m.20210" + monthNumber + ".csv";
                if (readFileContentsOrNull(monthlyReportFileName) != null) {
                    System.out.println("В файле отчета " + monthlyReportFileName + " содержатся следующие данные:");
                    String content = readFileContentsOrNull(monthlyReportFileName);
                    System.out.println(content);
                } else {
                    break;
                }
            }
        // вывести содержание файла годового отчета
        } else if (inputMenuItem == 2) {
            String yearlyReportFileName = "y." + year + ".csv";
            if (readFileContentsOrNull(yearlyReportFileName) != null) {
                System.out.println("В файле отчета " + yearlyReportFileName + " содержатся следующие данные:");
                String content = readFileContentsOrNull(yearlyReportFileName);
                System.out.println(content);
            }
        }
    }
}
