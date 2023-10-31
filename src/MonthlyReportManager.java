import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReportManager {
    FileReader fileReader = new FileReader();
    MonthNumberToMonthName monthNumberToMonthName = new MonthNumberToMonthName();

    boolean checkLoadMonthlyReportFile = false;

    public ArrayList<MonthlyReport> monthData = new ArrayList<>();

    public void loadMonthlyReportFile(int monthNumber, String fileName) {
        String content = fileReader.readFileContentsOrNull(fileName);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int unitPrise = Integer.parseInt(parts[3]);

            // создать объект, который хранит считанную строку из месячного отчета
            MonthlyReport monthlyReportString = new MonthlyReport(itemName, isExpense, quantity, unitPrise, monthNumber);
            // записать строки из месячного отчета в список
            monthData.add(monthlyReportString);
        }
    }

    // задать метод, который считает все месячные отчеты и, если файл отсутствует, сообщит об ошибке
    public void readAllMonthlyReports(int firstMonthNumber, int lastMonthNumber) {
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

    // проверить считывание файла отчета
    // если отчет не считан, считать его
    public void loadMonthlyReportFileChecker(int firstMonthNumber, int lastMonthNumber) {
        if (!checkLoadMonthlyReportFile) {
            // заполнить список monthData данными из отчетов
            readAllMonthlyReports(firstMonthNumber, lastMonthNumber);
        } else {
            System.out.println("Файлы месячных отчетов считаны ранее. Считанные данные сохранены в память программы.");
            System.out.println(" ");
        }
    }

    // создать метод, который выводит информацию о прибыльном товаре в месячном отчете
    public void getMaxProfitItemByMonth(int monthNumber) {
        // создать хэш-таблицу, которая по ключу "itemName" хранит <quantity * unitPrice>
        HashMap<String, Integer> dataByItem = new HashMap<>();
        // выполнить переборку данных по товарам в месячном отчете
        for (MonthlyReport data : monthData) {
            // проверить условие, что номер месяца соответствует заданному,
            // иначе продолжаем переборку значений из месячного отчета, иначе - продолжить перебор
            if (data.monthNumber != monthNumber) {
                continue;
            }
            // проверить условие, что доход не является тратой, иначе продолжаем переборку значений из месячного отчета
            if (data.isExpense) {
                continue;
            }
            // если условие выполняется, записать данные в хэш-таблицу dataByItem
            dataByItem.put(data.itemName, dataByItem.getOrDefault(data.itemName, 0) + data.quantity * data.unitPrise);
        }
        // задать пустые переменные, в которые будут записаны название самого прибыльного товара и его доход от продажи
        String maxProfitItemName = null;
        int maxProfitItemSum = 0;
        // выполнить переборку ключей по хэш-таблице dataByItem
        for (String itemName : dataByItem.keySet()) {
            // после первой итерации цикла записываем в itemMax название товара
            if (maxProfitItemName == null) {
                maxProfitItemName = itemName;
                continue;
            }
            // если при последующих итерациях суммарный доход от продажи товара больше предыдущего,
            // записываем в maxProfitItemName и в maxProfitItemSum его название и доход от продажи
            if (dataByItem.get(itemName) > dataByItem.get(maxProfitItemName)) {
                maxProfitItemName = itemName;
                maxProfitItemSum = dataByItem.get(itemName);
            }
        }
        System.out.println("Название самого прибыльного товара: " + maxProfitItemName);
        System.out.println("Доход, полученный от продажи товара: " + maxProfitItemSum + " руб.");
    }

    // создать аналогичный предыдущему метод, который выводит информацию о дорогой покупке в месячном отчете
    public void getMaxExpensiveItemByMonth(int monthNumber) {
        HashMap<String, Integer> dataByItem = new HashMap<>();

        for (MonthlyReport data : monthData) {
            if (data.monthNumber != monthNumber) {
                continue;
            }

            if (!data.isExpense) {
                continue;
            }

            dataByItem.put(data.itemName, dataByItem.getOrDefault(data.itemName, 0) + data.quantity * data.unitPrise);
        }

        String maxExpensiveItemName = null;
        int maxExpensiveItemSum = 0;

        for (String itemName : dataByItem.keySet()) {
            if (maxExpensiveItemName == null) {
                maxExpensiveItemName = itemName;
                continue;
            }

            if (dataByItem.get(itemName) > dataByItem.get(maxExpensiveItemName)) {
                maxExpensiveItemName = itemName;
                maxExpensiveItemSum = dataByItem.get(itemName);
            }
        }
        System.out.println("Название самого затратного товара: " + maxExpensiveItemName + ".");
        System.out.println("Размер самой большой траты: " + maxExpensiveItemSum + " руб.");
    }

    // создать метод, который посчитает количество операций по расходам
    public Integer getExpensesOperationsQuantity () {
        ArrayList<Integer> expenses = new ArrayList<>();

        // записать в список количество операций по тратам
        for (MonthlyReport data : monthData) {
            if (data.isExpense) {
                // количество операций по тратам
                expenses.add(data.quantity);
            }
        }

        // посчитать суммарное количество операций по тратам
        int expensesOperationsSum = 0;
        for (Integer quantity : expenses) {
            expensesOperationsSum += quantity;
        }
        return expensesOperationsSum;
    }

    // создать метод, который посчитает количество операций по доходам
    public Integer getIncomesOperationsQuantity () {
        ArrayList<Integer> incomes = new ArrayList<>();

        // записать в список количество операций по доходам
        for (MonthlyReport data : monthData) {
            if (!data.isExpense) {
                // количество операций по доходам
                incomes.add(data.quantity);
            }
        }

        // посчитать количество операций по доходам
        int incomesOperationsSum = 0;
        for (Integer quantity : incomes) {
            incomesOperationsSum += quantity;
        }
        return incomesOperationsSum;
    }

    public void printMonthlyReportInformation(int firstMonthNumber, int lastMonthNumber){
        for (int i = firstMonthNumber; i <= lastMonthNumber; i++) {
            int monthNumber = i;
            System.out.println("Информация по отчету за " + monthNumberToMonthName.printMonthName(monthNumber) + ":");
            getMaxExpensiveItemByMonth(monthNumber);
            getMaxProfitItemByMonth(monthNumber);
            System.out.println(" ");
        }
    }
}
