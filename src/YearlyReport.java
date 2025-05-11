// создать класс, который хранит данные из годового отчета
public class YearlyReport {
    public int monthNumber;
    public int amount;
    public boolean isExpense;

    public YearlyReport(int monthNumber, int amount, boolean isExpense) {
        this.monthNumber = monthNumber;
        this.amount = amount;
        this.isExpense = isExpense;
    }
}