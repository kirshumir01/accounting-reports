import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader();
        MonthlyReportManager monthlyReportManager = new MonthlyReportManager();
        YearlyReportManager yearlyReportManager = new YearlyReportManager(monthlyReportManager);
        Verifier verifier = new Verifier(monthlyReportManager, yearlyReportManager);


        while (true) {
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Считать все месячные отчеты");
            System.out.println("2 - Считать годовой отчет");
            System.out.println("3 - Сверить отчеты");
            System.out.println("4 - Вывести информацию о всех месячных отчетах");
            System.out.println("5 - Вывести информацию о годовом отчете");
            System.out.println("0 - Выход");

            int inputMenuItem = scanner.nextInt();

            int year = 2021;
            int firstMonthNumber = 1;
            int lastMonthNumber = 3;

            switch (inputMenuItem) {
                case (1):
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker(firstMonthNumber, lastMonthNumber);

                    // вывести содержание отчета в консоль
                    fileReader.printFileData(inputMenuItem, firstMonthNumber, lastMonthNumber, year);
                    break;
                case (2):
                    // проверить, что отчет считан, иначе - считать из файла
                    yearlyReportManager.loadYearlyReportFileChecker(year);

                    // вывести содержание отчета в консоль
                    fileReader.printFileData(inputMenuItem, firstMonthNumber, lastMonthNumber, year);
                    break;
                case (3):
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker(firstMonthNumber, lastMonthNumber);
                    yearlyReportManager.loadYearlyReportFileChecker(year);

                    // вывести результат в консоль
                    verifier.verifyByExpenses();
                    verifier.verifyByIncomes();
                    break;
                case (4):
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker(firstMonthNumber, lastMonthNumber);

                    // вывести информацию обо всех месячных отчетах:
                    monthlyReportManager.printMonthlyReportInformation(firstMonthNumber, lastMonthNumber);
                    break;
                case (5):
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker(firstMonthNumber, lastMonthNumber);
                    yearlyReportManager.loadYearlyReportFileChecker(year);

                    // вывести номер года, прибыль по каждому месяцу, средние расход и доход за все операции
                    yearlyReportManager.printYearlyReportInformation(year, firstMonthNumber, lastMonthNumber);
                    break;
                case (0):
                    // выход из программы
                    // ввести метод exit(), который остановит работу JVM
                    // код равен 0, программа была завершена намеренно
                    // break в этом случае не нужен
                    System.exit(0);
                default:
                    System.out.println("Такой команды не существует");
                    System.out.println(" ");
                    break;
            }
        }
    }
}
