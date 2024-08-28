import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String incorrectInput = "Некорректный ввод , попробуйте ещё !\n";
    public static FileReader fileReader = new FileReader();
    public static boolean error = false;

    public static ArrayList<String> strings = new ArrayList<>();
    public static ArrayList<String> strings2 = new ArrayList<>();

    public static void main(String[] args) {
        Collections.addAll(strings, "m.202101.csv", "m.202102.csv", "m.202103.csv", "y.2021.csv");
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
                        System.out.println("Считывание отчёта за " + month + " месяц " + year + " года успешно завершенно !\n");
                    }

                } else if (report == 2) {

                    int year;
                    while (true) {
                        printInputYear();
                        year = scanner.nextInt();
                        if (year <= 0 || year > 2025) {
                            System.out.println(incorrectInput);
                        } else break;
                    }

                    String form = "y." + year + ".csv";
                    ArrayList<String> lines = fileReader.readFileContents(form);
                    YearlyReport.put(form, lines);
                    if (!error) {
                        System.out.println("Считывание отчета за " + year + " год успешно завершенно !\n");
                    }
                }
            } else if (command == 2) {

                if (strings.size() != strings2.size()) {
                    System.out.println("Для начало необходимо считать отчёты со всех файлов !\n");
                    continue;
                }

                int year;
                while (true) {
                    printInputYear();
                    year = scanner.nextInt();
                    if (year <= 0 || year > 2025) {
                        System.out.println(incorrectInput);
                    } else break;
                }

                boolean error = false;
                for (int i = 1; i <= 3; i++) {
                    long profit = 0, expense = 0;
                    ArrayList<String> arrayList = MonthlyReport.get("m." + year + "0" + i + ".csv");
                    for (int j = 1; j < arrayList.size(); j++) {
                        String[] lines = arrayList.get(j).split(",");
                        if (Boolean.parseBoolean(lines[1])) {
                            expense += ((long) Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]));
                        } else {
                            profit += ((long) Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]));
                        }
                    }

                    ArrayList<String> yearList = YearlyReport.get("y." + year + ".csv");
                    for (int j = 1; j < yearList.size(); j++) {
                        String[] lines = yearList.get(j).split(",");
                        if (lines[0].equals("0" + i)) {
                            if (Boolean.parseBoolean(lines[2])) {
                                if (expense != Integer.parseInt(lines[1])) {
                                    error = true;
                                    String e = expense > Integer.parseInt(lines[1]) ? "больше" : "меньше";
                                    System.out.println("Отчет за " + i + " месяц : траты за месяц " + e + " чем в годовом отчете на " + (Math.abs(expense - Integer.parseInt(lines[1]))) + "\n");
                                }

                            } else {
                                if (profit != Integer.parseInt(lines[1])) {
                                    error = true;
                                    String e = profit > Integer.parseInt(lines[1]) ? "больше" : "меньше";
                                    System.out.println("Отчет за " + i + " месяц : прибыль " + e + " чем в годовом отчете ! \n");
                                }

                            }

                        }
                    }

                }
                if (!error) {
                    System.out.println("Отчёты за месяцы и за год равны ! ("+year+")\n");
                }
            } else if (command == 3) {

                int report;
                while (true) {
                    printInfoMonthOrYear();
                    report = scanner.nextInt();
                    if (report > 0 && report < 3) {
                        break;
                    } else {
                        System.out.println(incorrectInput);
                    }
                }

                if (report == 1) {

                    int month;
                    while (true) {
                        printInputMonthNumber();
                        month = scanner.nextInt();
                        if (month > 0 && month < 13)
                            break;
                        else
                            System.out.println(incorrectInput);
                    }

                    String e = month < 10 ? "0" + month : String.valueOf(month);
                    if (!strings2.contains("m.2021" + e + ".csv")) {
                        System.out.println("Для начало необходимо считать отчёт за " + month + " месяц из файла !\n");
                        continue;
                    }

                    int profitMax = 0;
                    String profitName = "ошибка";
                    int expenseMax = 0;
                    String expenseName = "ошибка";

                    ArrayList<String> arrayList = MonthlyReport.get("m.2021" + e + ".csv");

                    for (int i = 1; i < arrayList.size(); i++) {
                        String[] lines = arrayList.get(i).split(",");
                        if (Boolean.parseBoolean(lines[1])) {
                            if (expenseMax < (Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]))) {
                                expenseMax = (Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]));
                                expenseName = lines[0];
                            }
                        } else if (profitMax < (Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]))) {
                            profitMax = (Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]));
                            profitName = lines[0];
                        }
                    }

                    System.out.println("Информация за " + getMonthNameFromInt(month) + "!");
                    System.out.println("Самый прибыльный товар : " + profitName + " , был продан на суму = " + profitMax);
                    System.out.println("Самая большая трата : " + expenseName + " , было потраченно = " + expenseMax + "\n");

                } else if (report == 2) {

                    int year;
                    while (true) {
                        printInputYear();
                        year = scanner.nextInt();
                        if (year <= 0 || year > 2025) {
                            System.out.println(incorrectInput);
                        } else break;
                    }

                    if (strings.size() != strings2.size()) {
                        System.out.println("Для начало необходимо считать отчёты  из  файлов !\n");
                        continue;
                    }

                    System.out.println("Информация за " + year + " год !");
                    ArrayList<String> profitYear = YearlyReport.get("y." + year + ".csv");
                    for (int i = 1; i <= 3; i++) {
                        String monthName = getMonthNameFromInt(i);
                        long profit = 0;
                        String[] lines = profitYear.get(i).split(",");
                        if (Boolean.parseBoolean(lines[2])) {
                            profit -= Integer.parseInt(lines[1]);
                        } else {
                            profit += Integer.parseInt(lines[1]);
                        }
                        System.out.println("Прибыль за " + monthName + " = " + profit);
                    }
                    Map<String, ArrayList<String>> map = MonthlyReport.getMap();
                    int iteratorP = 0;
                    long sumP = 0;
                    int iteratorE = 0;
                    long sumE = 0;

                    for (var a : map.entrySet()) {
                        ArrayList<String> arrayList = a.getValue();
                        for (int j = 1; j < arrayList.size(); j++) {
                            String[] lines = arrayList.get(j).split(",");
                            if (Boolean.parseBoolean(lines[1])) {
                                iteratorE += Integer.parseInt(lines[2]);
                                sumE += Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]);
                            } else {
                                iteratorP += Integer.parseInt(lines[2]);
                                sumP += Integer.parseInt(lines[2]) * Integer.parseInt(lines[3]);
                            }
                        }
                    }
                    System.out.println("Cредний расход за все имеющиеся операции в году = " + (sumE / iteratorE));
                    System.out.println("Cредний доход за все имеющиеся операции в году = "+(sumP/iteratorP)+"\n");


                }

            } else if (command ==4){
                break;
            }
        }//while
    }

    public static void printMenu() {
        System.out.println(" 1 - считать отчёты бухгалтерии из файлов \n" +
                " 2 - сверить данные отчётов \n" +
                " 3 - вывод информации\n" +
                " 4 - выйти из приложения \n"
        );
    }

    public static void printYearOrMonthReport() {
        System.out.println(" 1 - Cчитать месецный отчёт \n 2 - Считать годовой отчёт \n ");
    }

    public static void printInputMonthNumber() {
        System.out.println("Введите номер месяца ! 1 - Январь  ,  12 - Декабрь \n");
    }

    public static void printInputYear() {
        System.out.println("Введите год !\n");
    }

    public static void printInfoMonthOrYear() {
        System.out.println(" 1 - Вывести информацию за месяц \n 2 - Вывести информацию за год \n");
    }

    public static String getMonthNameFromInt(int e) {
        switch (e) {
            case 1:
                return "Январь";

            case 2:
                return "Февраль";

            case 3:
                return "Март";

            default:
                return String.valueOf(e);
        }
    }
}