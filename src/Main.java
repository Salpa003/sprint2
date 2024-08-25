import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String incorrectInput = "Некорректный ввод , попробуйте ещё !";
   public static FileReader fileReader = new FileReader();
   public static boolean error = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            int command;

            printMenu();

            command = scanner.nextInt();

            if (command <= 0 || command > 4) {
                System.out.println(incorrectInput);
                continue;
            }

            if (command == 1) {
                int report;
                while (true) {
                    printYearOrMonthReport();
                    report = scanner.nextInt();

                    if (report <= 0 || report > 2) {
                        System.out.println(incorrectInput);
                    } else break;

                }

                if (report == 1) {
                    int year;
                    while (true) {
                        printInputYear();
                        year = scanner.nextInt();

                        if (year <= 0 || year > 2025) {
                            System.out.println(incorrectInput);
                        } else break;
                    }
                    int month;
                    while (true) {
                        printInputMonthNumber();
                        month = scanner.nextInt();
                        if (month <= 0 || month > 12) {
                            System.out.println(incorrectInput);
                        } else break;
                    }
                    String e;
                    if (month < 10) {
                        e = "0" + month;
                    } else {
                        e = String.valueOf(month);
                    }
                    String form = "m." + year + e + ".csv";
                    ArrayList<String> lines = fileReader.readFileContents(form);
                    MonthlyReport.put(form, lines);
                    if (!error) {
                        System.out.println("Считывание отчёта за " +year+"год "+ month + " месяц успешно завершенно !");
                    }
                }

                if (report == 2) {
                    int year;
                    while (true) {
                        printInputYear();
                        year = scanner.nextInt();
                        if (year <= 0 || year > 2025) {
                            System.out.println(incorrectInput);
                        } else break;
                    }
                    String form =  "y."+year+".csv";
                    ArrayList<String> lines = fileReader.readFileContents(form);
                    YearlyReport.put(form, lines);
                    if (!error){
                        System.out.println("Считывание отчетё за "+year+" год успешно завершенно !");
                    }
                }
            } else if (command == 2) {

            }


        }
    }
    public static void printMenu() {
        System.out.println(" 1 - считать отчёты бухгалтерии из файлов \n" +
                           " 2 - сверить данные отчётов \n" +
                           " 3 - вывод информации\n" +
                           " 4 - выйти из приложения \n"
        );
    }

    public static void printYearOrMonthReport(){
        System.out.println(" 1 - Cчитать месецный отчёт \n 2 - Считать годовой отчёт \n ");
    }
    public static void printInputMonthNumber() {
        System.out.println("Введите номер месяца ! 1 - Январь  ,  12 - Декабрь \n");
    }
    public static void printInputYear(){
        System.out.println("Введите год !");
    }

}