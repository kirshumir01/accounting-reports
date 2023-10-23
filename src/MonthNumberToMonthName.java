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
                System.out.println("Отчет по введенному месяцу отсутствует");
                break;
        }
        return monthName;
    }
}