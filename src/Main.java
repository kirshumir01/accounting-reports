import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader();
        MonthlyReportManager monthlyReportManager = new MonthlyReportManager();
        YearlyReportManager yearlyReportManager = new YearlyReportManager();
        MonthNumberToMonthName monthNumberToMonthName = new MonthNumberToMonthName();
        Verifier verifier = new Verifier(monthlyReportManager, yearlyReportManager);


        while (true) {

            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Считать все месячные отчеты");
            System.out.println("2 - Считать годовой отчет");
            System.out.println("3 - Сверить отчеты");
            System.out.println("4 - Вывести информацию о всех месячных отчетах");
            System.out.println("5 - Вывести информацию о годовом отчете");
            System.out.println("0 - Выход");

            int year = 2021;
            String yearlyReportFileName = "y." + year + ".csv";

            int userInput = scanner.nextInt();

            switch (userInput) {
                case (1):
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker();
                    System.out.println(" ");

                    // вывести содержание отчета в консоль
                    for (int monthNumber = 1; monthNumber < 4; monthNumber++) {
                        String monthlyReportFileName = "m.20210" + monthNumber + ".csv";
                        fileReader.printFileData(monthlyReportFileName);
                    }

                    break;
                case (2):
                    // проверить, что отчет считан, иначе - считать из файла
                    yearlyReportManager.loadYearlyReportFileChecker(yearlyReportFileName);
                    System.out.println(" ");

                    // вывести содержание отчета в консоль
                    fileReader.printFileData(yearlyReportFileName);
                    break;
                case (3):
                    System.out.println("Проверка считывания информации из отчетов.");
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker();

                    // проверить, что отчет считан, иначе - считать из файла
                    yearlyReportManager.loadYearlyReportFileChecker(yearlyReportFileName);
                    System.out.println(" ");

                    // вывести результат в консоль
                    System.out.println("Результат сверки файлов годового и месячного отчетов" +
                            "по тратам: " + verifier.verifyByExpenses());
                    System.out.println("Результат сверки файлов годового и месячного отчетов " +
                            " по доходам: " + verifier.verifyByIncomes());
                    System.out.println(" ");
                    break;
                case (4):
                    System.out.println("Проверка считывания информации из отчетов.");
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker();
                    System.out.println(" ");

                    // вывести информацию обо всех месячных отчетах:
                    for (int monthNumber = 1; monthNumber < 4; monthNumber++) {
                        System.out.println("Информация по отчету за " +
                                monthNumberToMonthName.printMonthName(monthNumber) + ":");
                        monthlyReportManager.getMaxExpensiveItemByMonth(monthNumber);
                        monthlyReportManager.getMaxProfitItemByMonth(monthNumber);
                        System.out.println(" ");
                    }
                    break;
                case (5):
                    System.out.println("Проверка считывания информации из отчетов.");
                    // проверить, что отчеты считаны, иначе - считать из файла
                    monthlyReportManager.loadMonthlyReportFileChecker();
                    System.out.println(" ");

                    yearlyReportManager.loadYearlyReportFileChecker(yearlyReportFileName);
                    System.out.println(" ");

                    // вывести номер года, прибыль по каждому месяцу, средние расход и доход за все операции
                    System.out.println("Информация по отчету за " + year + " год:");

                    for (int monthNumber = 1; monthNumber < 4; monthNumber++) {
                        System.out.println("Прибыль за " + monthNumberToMonthName.printMonthName(monthNumber) +
                                " (разница между доходами и расходами) по версии годового отчета составила " +
                                yearlyReportManager.getEarningsByMonth(monthNumber) + " руб.");
                    }
                    System.out.println(" ");

                    System.out.println("Средний расход за год по " + monthlyReportManager.getExpensesOperationsQuantity() +
                            " операциям составил: " + yearlyReportManager.getExpensesSumByYear() /
                            monthlyReportManager.getExpensesOperationsQuantity() + " руб. за одну операцию.");

                    System.out.println("Средний доход за год по " + monthlyReportManager.getIncomesOperationsQuantity() +
                            " операциям составил: " + yearlyReportManager.getIncomesSumByYear() /
                            monthlyReportManager.getIncomesOperationsQuantity() + " руб. за одну операцию.");
                    System.out.println(" ");

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
