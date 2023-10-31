import java.util.HashMap;

// создать класс, который сверяет данные из месячных отчетов и годового отчета
public class Verifier {
    MonthNumberToMonthName monthNumberToMonthName = new MonthNumberToMonthName();

    MonthlyReportManager monthlyReportManager;
    YearlyReportManager yearlyReportManager;

    // создать конструктор класса Verifier с параметрами
    public Verifier(MonthlyReportManager monthlyReportManager, YearlyReportManager yearlyReportManager) {
        this.monthlyReportManager = monthlyReportManager;
        this.yearlyReportManager = yearlyReportManager;
    }

    // выполнить сверку месячных и годового отчетов по тратам
    public void verifyByExpenses() {
        // задать переменную verify, которая вернет информацию об ошибке или успешном выполнении метода
        boolean verify = true;

        // создать хэш-таблицу, которая по ключу "monthNumber" хранит хэш-таблицу <isExpense, quantity * sumOfOne>
        // по версии месячного отчета
        HashMap<Integer, HashMap<Boolean, Integer>> monthlyExpenses = new HashMap<>();
        // выполнить переборку данных о тратах в месячном отчете
        for (MonthlyReport expense : monthlyReportManager.monthData) {
            // проверить, что в месячном отчете трата является тратой, иначе продолжить проверку
            if (!expense.isExpense) {
                continue;
            }
            // проверить, выбирались ли данные из месячного отчета
            if (!monthlyExpenses.containsKey(expense.monthNumber)) {
                // если данные по заданному месяцу не выбирались, записать "пустую" хэш-таблицу по ключу monthNumber
                monthlyExpenses.put(expense.monthNumber, new HashMap<>());
            }
            // записать выбранные данные по тратам из месячного отчета в хэш-таблицу
            HashMap<Boolean, Integer> isExpenseToValue = monthlyExpenses.get(expense.monthNumber);
            isExpenseToValue.put(expense.isExpense, isExpenseToValue.getOrDefault(expense.isExpense, 0)
                    + expense.quantity * expense.unitPrise);
        }

        // создать хэш-таблицу, которая по ключу "monthNumber" хранит хэш-таблицу <isExpense, amount>
        // по версии годового отчета
        HashMap<Integer, HashMap<Boolean, Integer>> yearlyExpenses = new HashMap<>();
        for (YearlyReport expense : yearlyReportManager.yearlyData) {
            // проверить, что в годовом отчете трата является тратой, иначе продолжить проверку
            if (!expense.isExpense) {
                continue;
            }
            // проверить, выбирались ли данные из годового отчета
            if (!yearlyExpenses.containsKey(expense.monthNumber)) {
                // если данные по заданному месяцу не выбирались, записать "пустую" хэш-таблицу по ключу monthNumber
                yearlyExpenses.put(expense.monthNumber, new HashMap<>());
            }
            // записать выбранные данные по тратам из годового отчета в хэш-таблицу
            HashMap<Boolean, Integer> isExpenseToValue = yearlyExpenses.get(expense.monthNumber);
            isExpenseToValue.put(expense.isExpense, isExpenseToValue.getOrDefault(expense.isExpense, 0)
                    + expense.amount);
        }

        // задать цикл по переборке данных по ключу "monthNumber" в отчете по тратам по версии месячного отчета
        for (int monthNumber : monthlyExpenses.keySet()) {
            // создать идентичные по структуре хэш-таблицы,
            // которые по ключу "isExpense" вынимают значения трат из месячного и годового отчетов
            HashMap<Boolean, Integer> isExpenseToValueByMonth = monthlyExpenses.get(monthNumber);
            HashMap<Boolean, Integer> isExpenseToToValueByYear = yearlyExpenses.get(monthNumber);

            // проверить, что данные о запрашиваемом месяце есть в годовом отчете
            if (isExpenseToToValueByYear == null) {
                System.out.println("Данные по тратам есть в отчете за "
                        + monthNumberToMonthName.printMonthName(monthNumber) + ", но отсутствуют в годовом отчете.");
                verify = false;
                continue;
            }

            // задать цикл по переборке данных по ключу "isExpense" в месячном отчете
            for (Boolean isExpense : isExpenseToValueByMonth.keySet()) {
                // задать переменную valueByMonth, которая хранят сумму трат в заданном месяце по версии месячного отчета
                int valueByMonth = isExpenseToValueByMonth.get(isExpense);
                // задать переменную valueByYear, которая хранит сумму трат в заданном месяце по версии годового отчета
                // с проверкой данных - "если в годовом отчете нет информации о запрашиваемом месяце, вернуть 0"
                int valueByYear = isExpenseToToValueByYear.getOrDefault(isExpense, 0);
                // сравнить значения переменных
                if (valueByMonth != valueByYear) {
                    System.out.println("В месячном отчете сумма трат за "
                            + monthNumberToMonthName.printMonthName(monthNumber) + " составила " + valueByMonth
                            + " руб., в годовом отчете сумма трат за "
                            + monthNumberToMonthName.printMonthName(monthNumber) + " составила " + valueByYear
                            + " руб.");
                    // вернуть ошибку
                    verify = false;
                }
            }
        }
        // вернуть результат сверки
        System.out.println("Результат сверки файлов годового и месячного отчетов по тратам: " + verify);
    }

    // выполнить сверку месячных и годового отчетов по доходам
    public void verifyByIncomes() {
        // задать переменную verify, которая вернет информацию об ошибке или успешном выполнении метода
        boolean verify = true;

        // создать хэш-таблицу, которая по ключу "monthNumber" хранит хэш-таблицу <!isExpense, quantity * sumOfOne>
        // по версии месячного отчета
        HashMap<Integer, HashMap<Boolean, Integer>> monthlyIncomes = new HashMap<>();
        // выполнить переборку данных о тратах в месячном отчете
        for (MonthlyReport income : monthlyReportManager.monthData) {
            // проверить, что в месячном отчете доход является доходом, иначе продолжить проверку
            if (income.isExpense) {
                continue;
            }
            // проверить, выбирались ли данные из месячного отчета
            if (!monthlyIncomes.containsKey(income.monthNumber)) {
                // если данные по заданному месяцу не выбирались, записать "пустую" хэш-таблицу по ключу monthNumber
                monthlyIncomes.put(income.monthNumber, new HashMap<>());
            }
            // записать выбранные данные по тратам из месячного отчета в хэш-таблицу
            HashMap<Boolean, Integer> isIncomeToValue = monthlyIncomes.get(income.monthNumber);
            isIncomeToValue.put(!income.isExpense, isIncomeToValue.getOrDefault(!income.isExpense, 0)
                    + income.quantity * income.unitPrise);
        }
        // создать хэш-таблицу, которая по ключу "monthNumber" хранит хэш-таблицу <!isExpense, amount>
        // по версии годового отчета
        HashMap<Integer, HashMap<Boolean, Integer>> yearlyIncomes = new HashMap<>();
        // выполнить переборку данных о тратах по каждому месяцу в годовом отчете
        for (YearlyReport income : yearlyReportManager.yearlyData) {
            // проверить, что в годовом отчете доход является доходом, иначе продолжить проверку
            if (income.isExpense) {
                continue;
            }
            // проверить, выбирались ли данные из годового отчета
            if (!yearlyIncomes.containsKey(income.monthNumber)) {
                // если данные по заданному месяцу не выбирались, записать "пустую" хэш-таблицу по ключу monthNumber
                yearlyIncomes.put(income.monthNumber, new HashMap<>());
            }
            // записать выбранные данные по тратам из годового отчета в хэш-таблицу
            HashMap<Boolean, Integer> isIncomeToValue = yearlyIncomes.get(income.monthNumber);
            isIncomeToValue.put(!income.isExpense, isIncomeToValue.getOrDefault(!income.isExpense, 0) + income.amount);
        }

        // задать цикл по переборке данных по ключу "monthNumber" в отчете по тратам по версии месячного отчета
        for (int monthNumber : monthlyIncomes.keySet()) {
            // создать идентичные по структуре хэш-таблицы,
            // которые по ключу "!isExpense" вынимают значения трат из месячного и годового отчетов
            HashMap<Boolean, Integer> isIncomeToValueByMonth = monthlyIncomes.get(monthNumber);
            HashMap<Boolean, Integer> isIncomeToValueByYear = yearlyIncomes.get(monthNumber);

            // проверить, что данные о запрашиваемом месяце есть в годовом отчете
            if (isIncomeToValueByYear == null) {
                System.out.println("Данные по тратам есть в отчете за  "
                        + monthNumberToMonthName.printMonthName(monthNumber) + ", но отсутствуют в годовом отчете.");
                verify = false;
                continue;
            }
            // задать цикл по переборке данных по ключу "isExpense" в месячном отчете
            for (Boolean isIncome : isIncomeToValueByMonth.keySet()) {
                // задать переменную valueByMonth, которая хранят сумму доходов в заданном месяце по версии месячного отчета
                int valueByMonth = isIncomeToValueByMonth.get(isIncome);
                // задать переменную valueByYear, которая хранит сумму доходов в заданном месяце по версии годового отчета
                // с проверкой данных - "если в годовом отчете нет информации о запрашиваемом месяце, вернуть 0"
                int valueByYear = isIncomeToValueByYear.getOrDefault(isIncome, 0);
                // сравнить значения переменных
                if (valueByMonth != valueByYear) {
                    System.out.println("В месячном отчете сумма доходов за "
                            + monthNumberToMonthName.printMonthName(monthNumber) + " составила " + valueByMonth
                            + " руб., в годовом отчете сумма доходов за "
                            + monthNumberToMonthName.printMonthName(monthNumber) + " составила " + valueByYear
                            + " руб.");
                    // вернуть ошибку
                    verify = false;
                }
            }
        }
        // вернуть результат сверки
        System.out.println("Результат сверки файлов годового и месячного отчетов по доходам: " + verify);
    }
}