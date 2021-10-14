package com.makhabatusen;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    /** Course Work "Device Repair"
    * done by:
    * Almazbekov Aktilek (WIN-1-20)
    * Mamatov Nurtilek (WIN-1-20)
    * Tuigunbek kyzy Makhabat (AINm-1-21)
    * */

    // Account types
    public static final String CLIENT = "Client";
    public static final String REPAIRER = "Repairer";
    public static final String WORKER = "Worker";
    public static final String SUPPLIER = "Supplier";


    public static void main(String[] args) {

        String path = "src/com/makhabatusen/passwords.txt";
        Scanner scanUserInput = new Scanner(System.in);

        startProgram(scanUserInput, path);

    }

    private static void startProgram(Scanner scanUserInput, String path) {

        String userType = getCategory(scanUserInput, path);


        if (userType.equalsIgnoreCase(CLIENT)) {
            startClientAccount(scanUserInput);
        } else if (userType.equalsIgnoreCase(REPAIRER)) {
            startRepairerAccount();
        } else if (userType.equalsIgnoreCase(WORKER)) {
            startWorkerAccount();
        } else if (userType.equalsIgnoreCase(SUPPLIER)) {
            startSupplierAccount();
        }

    }

    private static String getCategory(Scanner scanUserInput, String path) {
        System.out.println("Для запуска программы, пожалуйста введите тип аккаунта: \n");
        String userType = scanUserInput.next().trim();

        String[] categories = {CLIENT, REPAIRER, WORKER, SUPPLIER};

        if (Arrays.asList(categories).contains(userType))
            verifyAccount(userType, scanUserInput, path);
        else {
            System.out.println("Извините, но мы не нашли такой тип аккаунта, пожалуйста повторите.");
            getCategory(scanUserInput, path);
        }
        return userType;
    }

    private static void verifyAccount(String userType, Scanner scanUserInput, String path) {
        boolean accountFound = false;
        String tempUserAccount;
        String tempPassword;
        System.out.println("Введите пароль: ");
        String userPassword = scanUserInput.next().trim();

        try {
            Scanner scanFile = new Scanner(new File(path));
            scanFile.useDelimiter("[,\n]");
            while (scanFile.hasNext() && !accountFound) {
                tempUserAccount = scanFile.next();
                tempPassword = scanFile.next();

                if (tempUserAccount.trim().equalsIgnoreCase(userType.trim()) &&
                        tempPassword.trim().equals(userPassword)) {
                    accountFound = true;
                }

            }
            scanFile.close();
            if (!accountFound) {
                System.out.println("Wrong Password");
            }

        } catch (Exception ignored) {
        }
    }


    // CLIENT
    private static void startClientAccount(Scanner scannerUserInput) {

        System.out.print("Приветствую дорогой, Клиент!\n" +
                "Пожалуйста наберите номер меню для работы с программой, если закончили, то наберите 5. ");
        chooseClientOption(scannerUserInput);

    }

    private static int chooseClientOption(Scanner scannerUserInput) {
        int option = 0;
        showAllServices();
       option = scannerUserInput.nextInt();
        switch (option) {
            case 1 -> {
                chooseClientOption(scannerUserInput);
            }
            case 2 -> {
                giveToRepair(scannerUserInput);
            }
        }
        return option;
    }

    private static void showAllServices() {
        System.out.println("\n1. Показать все услуги \n2. Отдать на ремонт \n3. Замена комплектующего\n"
                + "4. Обслуживание\n5. Проверить статус");
    }

    private static void giveToRepair(Scanner scannerInput) {
        double finalCoefficient = chooseDevice(scannerInput);
        chooseRepairMethod(finalCoefficient, scannerInput);
    }

    private static double chooseDevice(Scanner scannerInput) {
        double finalCoefficient = 0;
        System.out.println("Пожалуйста выберите категорию техники для ремонта\n");
        System.out.println("1. Персональный Компьютер \n2. Ноутбук  \n3. Планшет");
        int deviceCategory = scannerInput.nextInt();
        switch (deviceCategory) {
            case 1 -> {
                finalCoefficient = 1.5;
            }
            case 2 -> {

                finalCoefficient = chooseLaptop2(scannerInput);
            }
            case 3 -> {
                finalCoefficient = 2;
            }

            default -> {
                System.out.println("Please choose again:");
                chooseDevice(scannerInput);
            }
        }

        return finalCoefficient;


    }

    private static void chooseRepairMethod(double finalCoefficient, Scanner scannerInput) {
        System.out.println("Пожалуйста, выберите  какой ремонт требуется:" +
                "\n1. Починить дисплей" +
                "\n2. Починить клавиатуру" +
                "\n3. Починить внутренности (Материнскую плату, процессор и т.д)");

        int repairMethod = scannerInput.nextInt();
        double price = 0;

        switch (repairMethod) {
            case 1 -> price = 50 * finalCoefficient;
            case 2 -> price = 25 * finalCoefficient;
            case 3 -> price = 40 * finalCoefficient;
        }

        System.out.println(price);

    }


    // with coefficient
    private static double chooseLaptop2(Scanner scannerInput) {
        double coefficient = 0;
        System.out.println("Какой ноутбук? >>>  \n1. Apple  \n2. Asus \n3. Acer" +
                "\n4. Dell  \n5. HP");
        int laptop = scannerInput.nextInt();

        switch (laptop) {
            case 1 -> coefficient = 3.0;
            case 2 -> coefficient = 2.5;
            case 3 -> coefficient = 1.8;
            case 4 -> coefficient = 2.3;
            case 5 -> coefficient = 2;
        }

        return coefficient;
    }


    // REPAIRER
    private static void startRepairerAccount() {
        System.out.println(REPAIRER);
    }


    //WORKER
    private static void startWorkerAccount() {
        System.out.println(WORKER);
    }

    //SUPPLIER
    private static void startSupplierAccount() {
        System.out.println(SUPPLIER);
    }

}


