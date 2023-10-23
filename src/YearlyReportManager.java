import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReportManager {
    FileReader fileReader = new FileReader();
    public ArrayList<YearlyReport> yearlyData = new ArrayList<>();

    // создать метод, который считывает данные из *.csv-файла
    public void loadYearlyReportFile(String fileName) {
        String path = "./resources/" + fileName;
        ArrayList<String> content = fileReader.readFileContents(fileName);
        for (int i = 1; i < content.size(); i++) {
            String line = content.get(i);
            String[] parts = line.split(",");
            int monthNumber = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            // создать объект, который хранит считанную строку из годового отчета
            YearlyReport yearlyReportString = new YearlyReport(monthNumber, amount, isExpense);
            // записать строки из годового отчета в список
            yearlyData.add(yearlyReportString);
        }
    }

    // проверить считывание файла отчета
    // если отчет не считан, считать его
    public void loadYearlyReportFileChecker(String fileName) {
        if (yearlyData.size() == 0) {
            loadYearlyReportFile(fileName);
            System.out.println("Файл годового отчета " + fileName + " считан.");
        } else {
            System.out.println("Файл годового отчета " + fileName + " считан ранее.");
        }
    }

    // создать метод, который посчитает сумму расходов за год
    public Integer getExpensesSumByYear() {
        HashMap<Integer, Integer> expensesByMonthNumber = new HashMap<>();
        // записать в список значения трат
        for (YearlyReport data : yearlyData) {
            if (data.isExpense) {
                // записать значения расходов
                expensesByMonthNumber.put(data.monthNumber, expensesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            }
        }

        // посчитать сумму трат
        int expensesSum = 0;
        for (int expense : expensesByMonthNumber.values()) {
            expensesSum += expense;
        }
        return expensesSum;
    }

    // создать метод, который посчитает сумму доходов за год
    public Integer getIncomesSumByYear() {
        HashMap<Integer, Integer> incomesByMonthNumber = new HashMap<>();

        for (YearlyReport data : yearlyData) {
            // записать значения доходов
            if (!data.isExpense) {
                incomesByMonthNumber.put(data.monthNumber, incomesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            }
        }

        // посчитать сумму доходов
        int incomesSum = 0;
        for (int income : incomesByMonthNumber.values()) {
            incomesSum += income;
        }
        return incomesSum;
    }

    // создать метод, который посчитает прибыль по каждому месяцу
    // "прибыль" = "доходы" - "расходы"
    public Integer getEarningsByMonth(int monthNumber) {
        HashMap<Integer, Integer> expensesByMonthNumber = new HashMap<>();
        HashMap<Integer, Integer> incomesByMonthNumber = new HashMap<>();

        // записать в список значения трат
        for (YearlyReport data : yearlyData) {
            // записать значения доходов
            if (!data.isExpense) {
                incomesByMonthNumber.put(data.monthNumber, incomesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            } else {
                // записать значения расходов
                expensesByMonthNumber.put(data.monthNumber, expensesByMonthNumber.getOrDefault(data.monthNumber, 0) +
                        data.amount);
            }
        }
        // прибыль за месяц
        return incomesByMonthNumber.get(monthNumber) - expensesByMonthNumber.get(monthNumber);
    }
}