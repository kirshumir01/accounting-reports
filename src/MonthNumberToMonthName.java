// создать класс, который конвертирует номер месяца в название
public class MonthNumberToMonthName {
    int monthNumber;

    public MonthNumberToMonthName () {
        this.monthNumber = monthNumber;
    }

    public String printMonthName(int monthNumber) {
        String monthName = null;
        switch (monthNumber) {
            case (1):
                monthName = "январь";
                break;
            case (2):
                monthName = "февраль";
                break;
            case (3):
                monthName = "март";
                break;
            default:
                System.out.println("Номер месяца введен некорректно или находится вне заданного диапазона");
                break;
        }
        return monthName;
    }
}