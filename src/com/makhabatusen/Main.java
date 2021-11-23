package com.makhabatusen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    /**
     * Course Work "Device Repair"
     * done by:
     * Almazbekov Aktilek (WIN-1-20)
     * Mamatov Nurtilek (WIN-1-20)
     * Tuigunbek kyzy Makhabat (AINm-1-21)
     */

    // Account types
    public static final String CLIENT = "Client";
    public static final String REPAIRER = "Repairer";
    public static final String WORKER = "Worker";
    public static final String SUPPLIER = "Supplier";

    public static final String PC = "Персональный Компьютер";
    public static final String LAPTOP = "Ноутбук";
    public static final String TABLET = "Планшет";

    public static final String APPLE = "Apple";
    public static final String ASUS = "Asus";
    public static final String ACER = "Acer";
    public static final String DELL = "Dell";
    public static final String HP = "HP";

    public static int COUNTER_ID;

    public static final String DISPLAY = "Display";
    public static final String KEYBOARD = "KeyBoard";
    public static final String INNER_DETAILS = "Inner detail";


    public static Scanner scanUserInput;

    private static File REPAIRER_NEEDED;
    private static File COUNTER;


    public static void main(String[] args) {

        String path = "src/com/makhabatusen/passwords.txt";
        scanUserInput = new Scanner(System.in);
        REPAIRER_NEEDED = new File("repairer_needed.txt");
        COUNTER = new File("counter_id.txt");
        COUNTER_ID = getCounter();

        startProgram(path);

    }

    private static int getCounter() {

        String path = "counter_id.txt";
        int counterID = 0;

        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }

        if (scanFile != null) {
            scanFile.useDelimiter("[ \n]");
            while (scanFile.hasNext()) {
                String counter = scanFile.next();
                if (counter != null)
                    COUNTER_ID = (Integer.parseInt(counter));
            }
            scanFile.close();
        }

        return counterID;
    }

    // Step 1
    private static void startProgram(String path) {


        // Step 2 and Step 3
        String userType = getUserType(path);


        if (userType.equalsIgnoreCase(CLIENT)) {
            startClientAccount();
        } else if (userType.equalsIgnoreCase(REPAIRER)) {
            startRepairerAccount();
        } else if (userType.equalsIgnoreCase(WORKER)) {
            startWorkerAccount();
        } else if (userType.equalsIgnoreCase(SUPPLIER)) {
            startSupplierAccount();
        } else {
            System.out.println("Please try again");
        }

    }

    // Step 2
    // Getting USer Type Info and verifying Password
    private static String getUserType(String path) {
        System.out.println("Для запуска программы, пожалуйста введите тип аккаунта: \n");
        String userType = scanUserInput.next().trim();

        String[] categories = {CLIENT, REPAIRER, WORKER, SUPPLIER};

        if (Arrays.asList(categories).contains(userType))
            verifyPassword(userType, path);
        else {
            System.out.println("Извините, но мы не нашли такой тип аккаунта, пожалуйста повторите.");
            getUserType(path);
        }
        return userType;
    }

    // Step 3
    // Verifying Password
    private static void verifyPassword(String userType, String path) {
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
                System.out.println("Wrong Password. Try again.");
                verifyPassword(userType, path);

            }

        } catch (FileNotFoundException ignored) {
            System.out.println("File Not Found");
        }
    }


    // CLIENT
    private static void startClientAccount() {

        System.out.print("Приветствую дорогой, Клиент!\n" +
                "Пожалуйста наберите номер меню для работы с программой, если закончили, то наберите 5. ");

        try {
            chooseClientOption();
        } catch (InputMismatchException e) {
            System.out.println("Please try again");
        }


    }

    // Step 1 for Client
    private static void showAllServices() {
        System.out.println("\n1. Показать все услуги \n2. Отдать на ремонт \n3. Замена комплектующего\n"
                + "4. Обслуживание\n5. Проверить статус");
    }

    // Step 2 for Client
    public static void chooseClientOption() {
        showAllServices();
        int option = scanUserInput.nextInt();

        switch (option) {
            case 1 -> {
                chooseClientOption();
            }
            case 2 -> {
                //TODO
                giveToRepair();
            }
            case 3 -> {
                //TODO
                changeTheDetail();
            }
            case 4 -> {
                //TODO
                service();
            }
            case 5 -> {
                //TODO
                checkTheStatus();
            }
        }


    }


    // REPAIR Option#2
    private static void giveToRepair() {

        String device = chooseDevice();
        String laptop = "";

        double deviceCoefficient = 0.0;
        switch (device) {
            case PC -> deviceCoefficient = 1.5;
            case LAPTOP -> {
                laptop = getLaptopName();
                deviceCoefficient = getCoefficientLaptop(laptop);
            }
            case TABLET -> deviceCoefficient = 2;
        }

        String repairMethod = getRepairMethod("Пожалуйста, выберите  какой ремонт требуется:" +
                "\n1. Починить дисплей" +
                "\n2. Починить клавиатуру" +
                "\n3. Починить внутренности (Материнскую плату, процессор и т.д)");

        double price = getPrice(deviceCoefficient, repairMethod);

        LocalDate date = LocalDate.now();
        String toDB = "";


        if (device.equals(PC) || device.equals(TABLET)) {
            toDB = toDB.concat(COUNTER_ID + "," + date.toString() + repairMethod + "," + device + ", - " + "," + price + "\n");
            System.out.printf("\nDate: %s Device: %s Repair Method: %s Price: %.2f ", date.toString(), device, repairMethod, price);
        } else if (device.equals(LAPTOP)) {
            toDB = toDB.concat(COUNTER_ID + "," + date.toString() + repairMethod + "," + device + "," + laptop + "," + price + "\n");
            System.out.printf("\nDate: %s  Repair Method: %s Device: %s Brand: %s Price: %.2f", date.toString(), repairMethod, device, laptop, price);
        }
        saveData(toDB);
        COUNTER_ID++;
        saveCounter(COUNTER_ID);

    }


    // TODO Counter
    private static void saveCounter(int counterId) {
        try {
            FileWriter fileWriter = new FileWriter(COUNTER);
            fileWriter.write(String.valueOf(counterId));
            fileWriter.close();
            System.out.println("\nCounter was saved successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void saveData(String toDB) {
        try {
            FileWriter fileWriter = new FileWriter(REPAIRER_NEEDED, true);
            fileWriter.write(toDB);
            fileWriter.close();
            System.out.println("\nData was saved successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static String chooseDevice() {
        String device = "";
        System.out.println("Пожалуйста выберите категорию техники для ремонта\n");
        System.out.println("1. Персональный Компьютер \n2. Ноутбук  \n3. Планшет");
        int deviceCategory = scanUserInput.nextInt();
        switch (deviceCategory) {
            case 1 -> {
                device = PC;
            }
            case 2 -> {
                device = LAPTOP;
            }
            case 3 -> {
                device = TABLET;
            }

            default -> {
                System.out.println("Please choose again:");
                chooseDevice();
            }
        }

        return device;

    }


    private static String getLaptopName() {
        String laptopName = "";
        System.out.println("Какой ноутбук? >>>  \n1. Apple  \n2. Asus \n3. Acer" +
                "\n4. Dell  \n5. HP");
        int laptop = scanUserInput.nextInt();

        switch (laptop) {
            case 1 -> laptopName = APPLE;
            case 2 -> laptopName = ASUS;
            case 3 -> laptopName = ACER;
            case 4 -> laptopName = DELL;
            case 5 -> laptopName = HP;
            default -> System.out.println("Please choose from the list");
        }
        return laptopName;
    }

    private static double getCoefficientLaptop(String laptop) {
        double coefficient = 0.0;
        switch (laptop) {
            case APPLE -> coefficient = 3.0;
            case ASUS -> coefficient = 2.5;
            case ACER -> coefficient = 1.8;
            case DELL -> coefficient = 2.3;
            case HP -> coefficient = 2;
        }
        return coefficient;
    }


    private static double getPrice(double coefficient, String repairMethod) {
        double price = 0;
        switch (repairMethod) {
            case DISPLAY -> price = 50 * coefficient;
            case KEYBOARD -> price = 25 * coefficient;
            case INNER_DETAILS -> price = 40 * coefficient;
        }
        return price;
    }


    private static String getRepairMethod(String s) {
        System.out.println(s);
        String method = "";
        int methodInput = scanUserInput.nextInt();
        switch (methodInput) {
            case 1 -> method = DISPLAY;
            case 2 -> method = KEYBOARD;
            case 3 -> method = INNER_DETAILS;
            default -> {
                System.out.println("Please choose from the list");
                getRepairMethod(s);
            }
        }
        return method;
    }


    private static void checkTheStatus() {
    }

    private static void service() {
    }

    private static void changeTheDetail() {
        System.out.println("Выберите что вы хотите заменить в своей технике");
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


