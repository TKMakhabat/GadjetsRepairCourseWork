package com.makhabatusen;

import java.util.Scanner;

public class MainTrial {

    public static void main(String[] args) {

        //тип аккаунта
        String client = "Client";
        //пароль клиента
        String passwordClient = "passwordClient1";
        Scanner in = new Scanner(System.in);

        System.out.println("Для запуска программы, пожалуйста введите тип аккаунта: ");
        String inputAccountType = in.nextLine();
        if (inputAccountType.equals(client)) {
            System.out.print("Введите пароль: ");
            String password = in.next();
            if (passwordClient.equals(password)) {
                //Меню для клиента:
                System.out.print("Приветствую дорогой, Клиент!\n" +
                        "Пожалуйста наберите номер меню для работы с программой, если закончили, то наберите 5:: ");
                System.out.print("1. Показать все услуги\n" + "2. Отдать на ремонт\n" + "3. Замена комплектующего\n" + "4. Обслуживание\n" + "5. Проверить статус");

                int category = in.nextInt();
                switch (category) {
                    case 1 -> {
                    // show all services
                        System.out.println("Services");
                    }
                    case 2 -> {
                        System.out.println("2 choice");
                    }
                }

            } else {
                System.out.print("Неверный пароль");
            }
        } else {
            System.out.println("Извините, но мы не нашли такой тип аккаунта, пожалуйста повторите.");
        }
    }
}
