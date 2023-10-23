import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileReader {

    ArrayList<String> readFileContents(String fileName) {
        String path = "./resources/" + fileName;
        try {
            return new ArrayList<>(Files.readAllLines(Path.of(path)));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл отсутствует в нужной директории.");
            return new ArrayList<>();
        }
    }

    // создать метод, который выводит в консоль содержание *.csv-файла
    public void printFileData(String fileName) {
        String path = "./resources/" + fileName;
         System.out.println("В файле отчета " + fileName + " содержатся следующие данные:");
         ArrayList<String> content = readFileContents(fileName);
         System.out.println(content);
    }
}
