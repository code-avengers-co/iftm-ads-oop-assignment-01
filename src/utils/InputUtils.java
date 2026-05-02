package utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input; // Valid input!
                } else {
                    System.out.println("Error: Please choose an option between " + min + " and " + max + ".\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input! Please enter a valid number.\n");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
