package ru.bstu.it32.bogunov.lab5;

import java.util.Scanner;

public class InputController {

    public static String getStrFromCon(String message){
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println(message);
            input = scanner.nextLine();
        } while (input.equals("") || input.length() > Main.Properties.MaxValuesLength);
        return input;
    }
}
