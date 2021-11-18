import com.LibraryManagementSystem.Controller.ClientController;
import com.LibraryManagementSystem.View.AdminView;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        ClientController clientController = new ClientController();
        clientController.startConnection();
        clientController.getAllData();

        System.out.println("com.LibraryManagementSystem.com.LibraryManagementSystem.Controller.Admin:");
        System.out.println("Enter the username: ");
        String username = sc.next();
        sc.nextLine();
        System.out.println("Enter the password: ");
        String password = sc.next();
        sc.nextLine();
        if (!username.equals("admin") || !password.equals("admin")) {
            System.out.println("username and password are incorrect, failed to log in as admin.");
            return;
        }

        AdminView adminUserInterface = new AdminView();
        adminUserInterface.login();
    }
}