import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReportManager {
    FileReader fileReader = new FileReader();
    MonthNumberToMonthName monthNumberToMonthName = new MonthNumberToMonthName();

    boolean checkLoadMonthlyReportFile = false;

    public ArrayList<MonthlyReport> monthData = new ArrayList<>();

    public void loadMonthlyReportFile(int monthNumber, String fileName) {
        monthNumberValidation(monthNumber);

        String content = fileReader.readFileContentsOrNull(fileName);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int unitPrice = Integer.parseInt(parts[3]);

            MonthlyReport monthlyReportString = new MonthlyReport(itemName, isExpense, quantity, unitPrice, monthNumber);
            monthData.add(monthlyReportString);
        }
    }

    public void readAllMonthlyReports(int firstMonthNumber, int lastMonthNumber) {
        monthNumberValidation(firstMonthNumber);
        monthNumberValidation(lastMonthNumber);

        checkLoadMonthlyReportFile = true;

        for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
            int monthNumber = i;
            String monthlyReportFileName = "m.20210" + monthNumber + ".csv";
            if (fileReader.readFileContentsOrNull(monthlyReportFileName) != null) {
                loadMonthlyReportFile(monthNumber, monthlyReportFileName);
                System.out.println("Файл отчета " + monthlyReportFileName + " считан.");
            } else {
                System.out.println("Файл с отчетом " + monthlyReportFileName + " не найден в текущей директории.");
                checkLoadMonthlyReportFile = false;
            }
        }
        if (!checkLoadMonthlyReportFile) {
            System.out.println(" ");
            System.out.println("ВНИМАНИЕ! Ошибка выполнения программы. Данные не сохранены.");
            System.out.println("Необходимо разместить недостающие файлы в директории './resources/'.");
        }
        System.out.println(" ");
    }

    public void loadMonthlyReportFileChecker(int firstMonthNumber, int lastMonthNumber) {
        monthNumberValidation(firstMonthNumber);
        monthNumberValidation(lastMonthNumber);

        if (!checkLoadMonthlyReportFile) {
            readAllMonthlyReports(firstMonthNumber, lastMonthNumber);
        } else {
            System.out.println("Файлы месячных отчетов считаны ранее. Считанные данные сохранены в память программы.");
            System.out.println(" ");
        }
    }

    public void getMaxProfitItemByMonth(int monthNumber) {
        monthNumberValidation(monthNumber);

        HashMap<String, Integer> dataByItem = new HashMap<>();

        for (MonthlyReport data : monthData) {
            if (data.monthNumber != monthNumber || !data.isExpense) {
                continue;
            }

            dataByItem.put(
                    data.itemName,
                    dataByItem.getOrDefault(data.itemName, 0) + data.quantity * data.unitPrice);
        }

        if (dataByItem.isEmpty()) {
            System.out.println("В месяце " + monthNumberToMonthName.printMonthName(monthNumber) + " не найдено прибыльных товаров.");
            return;
        }

        String maxProfitItemName = null;
        int maxProfitItemSum = 0;

        for (Map.Entry<String, Integer> entry : dataByItem.entrySet()) {
            if (entry.getValue() > maxProfitItemSum) {
                maxProfitItemName = entry.getKey();
                maxProfitItemSum = entry.getValue();
            }
        }

        System.out.println("Название самого прибыльного товара: " + maxProfitItemName);
        System.out.println("Доход, полученный от продажи товара: " + maxProfitItemSum + " руб.");
    }

    public void getMaxExpensiveItemByMonth(int monthNumber) {
        monthNumberValidation(monthNumber);

        HashMap<String, Integer> dataByItem = new HashMap<>();

        for (MonthlyReport data : monthData) {
            if (data.monthNumber != monthNumber || !data.isExpense) {
                continue;
            }

            dataByItem.put(
                    data.itemName,
                    dataByItem.getOrDefault(data.itemName, 0) + data.quantity * data.unitPrice);
        }

        if (dataByItem.isEmpty()) {
            System.out.println("В месяце " + monthNumberToMonthName.printMonthName(monthNumber) + " не найдено затратных товаров.");
            return;
        }

        String maxExpensiveItemName = null;
        int maxExpensiveItemSum = 0;

        for (Map.Entry<String, Integer> entry : dataByItem.entrySet()) {
            if (entry.getValue() > maxExpensiveItemSum) {
                maxExpensiveItemName = entry.getKey();
                maxExpensiveItemSum = entry.getValue();
            }
        }

        System.out.println("Название самого затратного товара: " + maxExpensiveItemName + ".");
        System.out.println("Размер самой большой траты: " + maxExpensiveItemSum + " руб.");
    }

    public Integer getExpensesOperationsQuantity () {
        ArrayList<Integer> expenses = new ArrayList<>();

        for (MonthlyReport data : monthData) {
            if (data.isExpense) {
                expenses.add(data.quantity);
            }
        }

        int expensesOperationsSum = 0;
        for (Integer quantity : expenses) {
            expensesOperationsSum += quantity;
        }
        return expensesOperationsSum;
    }

    public Integer getIncomesOperationsQuantity () {
        ArrayList<Integer> incomes = new ArrayList<>();

        for (MonthlyReport data : monthData) {
            if (!data.isExpense) {
                incomes.add(data.quantity);
            }
        }

        int incomesOperationsSum = 0;
        for (Integer quantity : incomes) {
            incomesOperationsSum += quantity;
        }
        return incomesOperationsSum;
    }

    public void printMonthlyReportInformation(int firstMonthNumber, int lastMonthNumber){
        monthNumberValidation(firstMonthNumber);
        monthNumberValidation(lastMonthNumber);

        for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
            int monthNumber = i;
            System.out.println("Информация по отчету за " + monthNumberToMonthName.printMonthName(monthNumber) + ":");
            getMaxExpensiveItemByMonth(monthNumber);
            getMaxProfitItemByMonth(monthNumber);
            System.out.println(" ");
        }
    }

    private void monthNumberValidation(int monthNumber) {
        if ((monthNumber < 1) || (monthNumber > 12)) {
            System.out.println("Номер месяца должен быть от 1 до 12! Введено значение: " + monthNumber);
        }
    }
}
