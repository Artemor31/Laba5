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

    private static void tryToParseToInt(Scanner scanner, int[] vars, int i) {
        try {
            vars[i] = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException ignored){
            return;
        }
    }

    public static int getIntFromString(String something) {
        String result;
        int length = something.length();
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char character = something.charAt(i);
            if (Character.isDigit(character)) {
                resultBuilder.append(character);
            }
        }
        result = resultBuilder.toString();
        return Integer.parseInt(result);
    }

}
