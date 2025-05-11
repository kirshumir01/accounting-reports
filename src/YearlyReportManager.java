import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReportManager {
    FileReader fileReader = new FileReader();
    MonthNumberToMonthName monthNumberToMonthName = new MonthNumberToMonthName();

    MonthlyReportManager monthlyReportManager;
    boolean checkLoadYearlyReportFile = false;

    public ArrayList<YearlyReport> yearlyData = new ArrayList<>();

    public YearlyReportManager(MonthlyReportManager monthlyReportManager) {
        this.monthlyReportManager = monthlyReportManager;
    }

    public void loadYearlyReportFile(String fileName) {
        String content = fileReader.readFileContentsOrNull(fileName);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            int monthNumber = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            YearlyReport yearlyReportString = new YearlyReport(monthNumber, amount, isExpense);
            yearlyData.add(yearlyReportString);
        }
    }

    public void readYearlyReport(int year) {
        yearNumberValidation(year);

        checkLoadYearlyReportFile = true;
        String yearlyReportFileName = "y." + year + ".csv";
        if (fileReader.readFileContentsOrNull(yearlyReportFileName) != null) {
            loadYearlyReportFile(yearlyReportFileName);
            System.out.println("Файл отчета " + yearlyReportFileName + " считан.");
        } else {
            System.out.println("Файл с отчетом " + yearlyReportFileName + " не найден в текущей директории.");
            checkLoadYearlyReportFile = false;
        }
        if (!checkLoadYearlyReportFile) {
            System.out.println(" ");
            System.out.println("ВНИМАНИЕ! Ошибка выполнения программы. Данные не сохранены.");
            System.out.println("Необходимо разместить недостающие файлы в директории './resources/'.");
        }
        System.out.println(" ");
    }

    public void loadYearlyReportFileChecker(int year) {
        yearNumberValidation(year);

        if (!checkLoadYearlyReportFile) {
            readYearlyReport(year);
        } else {
            System.out.println("Файл годового отчета считан ранее. Считанные данные сохранены в память программы.");
            System.out.println(" ");
        }
    }

    public Integer getExpensesSumByYear() {
        HashMap<Integer, Integer> expensesByMonthNumber = new HashMap<>();
        for (YearlyReport data : yearlyData) {
            if (data.isExpense) {
                expensesByMonthNumber.put(data.monthNumber, expensesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            }
        }

        int expensesSum = 0;
        for (int expense : expensesByMonthNumber.values()) {
            expensesSum += expense;
        }
        return expensesSum;
    }

    public Integer getIncomesSumByYear() {
        HashMap<Integer, Integer> incomesByMonthNumber = new HashMap<>();

        for (YearlyReport data : yearlyData) {
            if (!data.isExpense) {
                incomesByMonthNumber.put(data.monthNumber, incomesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            }
        }

        int incomesSum = 0;
        for (int income : incomesByMonthNumber.values()) {
            incomesSum += income;
        }
        return incomesSum;
    }

    // метод, который посчитает прибыль по каждому месяцу
    // "прибыль" = "доходы" - "расходы"
    public Integer getEarningsByMonth(int monthNumber) {
        monthNumberValidation(monthNumber);

        HashMap<Integer, Integer> expensesByMonthNumber = new HashMap<>();
        HashMap<Integer, Integer> incomesByMonthNumber = new HashMap<>();

        for (YearlyReport data : yearlyData) {
            if (!data.isExpense) {
                incomesByMonthNumber.put(data.monthNumber, incomesByMonthNumber.getOrDefault(data.monthNumber, 0)
                        + data.amount);
            } else {
                expensesByMonthNumber.put(data.monthNumber, expensesByMonthNumber.getOrDefault(data.monthNumber, 0)
                        + data.amount);
            }
        }
        return incomesByMonthNumber.get(monthNumber) - expensesByMonthNumber.get(monthNumber);
    }

    public void printYearlyReportInformation(int year, int firstMonthNumber, int lastMonthNumber) {
        monthNumberValidation(firstMonthNumber);
        monthNumberValidation(lastMonthNumber);

        System.out.println("Информация по отчету за " + year + " год:");

        for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
            int monthNumber = i;
            System.out.println("Прибыль за " + monthNumberToMonthName.printMonthName(monthNumber)
                    + " (разница между доходами и расходами) по версии годового отчета составила "
                    + getEarningsByMonth(monthNumber) + " руб.");
        }
        System.out.println(" ");

        System.out.println("Средний расход за год по " + monthlyReportManager.getExpensesOperationsQuantity()
                + " операциям составил: "
                + getExpensesSumByYear() / monthlyReportManager.getExpensesOperationsQuantity()
                + " руб. за одну операцию.");

        System.out.println("Средний доход за год по " + monthlyReportManager.getIncomesOperationsQuantity()
                + " операциям составил: "
                + getIncomesSumByYear() / monthlyReportManager.getIncomesOperationsQuantity()
                + " руб. за одну операцию.");
    }

    private void yearNumberValidation(int year) {
        if ((year < 1990) || (year > 2025)) {
            System.out.println("Номер года должен быть от 1990 до 2025! Введено значение: " + year);
        }
    }

    private void monthNumberValidation(int monthNumber) {
        if ((monthNumber < 1) || (monthNumber > 12)) {
            System.out.println("Номер месяца должен быть от 1 до 12! Введено значение: " + monthNumber);
        }
    }
}
