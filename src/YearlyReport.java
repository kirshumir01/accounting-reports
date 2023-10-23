// создать класс, который хранит данные из годового отчета
public class YearlyReport {
    //month,amount,is_expense
    public int monthNumber;
    public int amount;
    public boolean isExpense;

    // создать конструктор с параметрами, с помощью которого можно создать объект и заполнить его считанными данными
    public YearlyReport(int monthNumber, int amount, boolean isExpense) {
        this.monthNumber = monthNumber;
        this.amount = amount;
        this.isExpense = isExpense;
    }
}