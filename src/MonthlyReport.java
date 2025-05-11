// создать класс, который хранит данные из месячных отчетов
public class MonthlyReport {
    public String itemName;
    public boolean isExpense;
    public int quantity;
    public int unitPrice;
    public int monthNumber;

    public MonthlyReport(String itemName, boolean isExpense, int quantity, int unitPrice, int monthNumber) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.monthNumber = monthNumber;
    }

}
