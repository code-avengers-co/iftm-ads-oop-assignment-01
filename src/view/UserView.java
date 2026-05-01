package view;

import controller.UserController;
import model.User;
import utils.CreationIdUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserView {
    private UserController userController;
    private Scanner scanner;

    public UserView(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option;
        do {
            option = getOption();
            switch (option) {
                case 1:
                    renderRegisterUser();
                    break;
                case 2:
                    renderListUsers();
                    break;
                case 0:
                    System.out.println("Exiting User Menu...");
                    break;
            }
        } while (option != 0);
    }

    private int getOption() {
        String menu = """
                
                --- USER MENU ---
                1 - Register User
                2 - List Users
                0 - Back
                """;
        System.out.print(menu);

        int option = -1;
        do {
            System.out.print("Choose an option: ");
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input! Please enter a number.");
            }
        } while (option < 0 || option > 2);

        return option;
    }

    private void renderRegisterUser() {
        System.out.println("\n--- REGISTER NEW USER ---");

        int personId = CreationIdUtils.generatePersonId();
        int userId = CreationIdUtils.generateUserId();

        System.out.println("[ Personal Data ]");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print("Birth Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD (e.g., 2000-05-25).");
            }
        }

        System.out.print("Document (CPF/RG): ");
        String document = scanner.nextLine();

        System.out.println("\n[ System Data ]");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = userController.registerUser(personId, name, birthDate, document, userId, username, password);

        if (success) {
            System.out.println("\nUser registered successfully!");
        } else {
            System.out.println("\nError: Could not register user. Database might be full.");
        }
    }

    private void renderListUsers(){
        System.out.println("Listing all users...");
        User[] users = userController.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
