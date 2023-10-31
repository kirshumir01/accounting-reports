// создать класс, который хранит данные из месячных отчетов
public class MonthlyReport {
    //item_name,is_expense,quantity,sum_of_one
    public String itemName;
    public boolean isExpense;
    public int quantity;
    public int unitPrise;
    public int monthNumber;

    // создать конструктор с параметрами, с помощью которого можно создать объект и заполнить его считанными данными
    public MonthlyReport(String itemName, boolean isExpense, int quantity, int unitPrise, int monthNumber) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.unitPrise = unitPrise;
        // добавить поле с номером месяца
        // номер месяца позволит привести считанные данные к единой форме с годовым отчетом
        this.monthNumber = monthNumber;
    }

}
