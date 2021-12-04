package com.LibraryManagementSystem.View;

import com.LibraryManagementSystem.Controller.Admin;
import com.LibraryManagementSystem.Controller.Librarian;

import java.util.Scanner;

public class AdminView {
    Admin admin = new Admin("admin", "admin");
    public Scanner sc = new Scanner(System.in);

    public void login() {
        System.out.println("Admin:");
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
        System.out.println("Successfully Logged in as an admin!");
        printAdminInstructions();
        boolean quit = false;
        int choice;
        while (!quit) {
            System.out.println("enter you choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 0 -> printAdminInstructions();
                case 1 -> addNewLibrarian();
                case 2 -> removeLibrarian();
                case 3 -> viewLibrarians();
                case 4 -> quit = true;
            }
        }
        LibrarianView librarianUserInterface = new LibrarianView();
        Librarian currentLibrarian = admin.getLibrarianArrayList().get(0);
        librarianUserInterface.login(currentLibrarian);
    }

    public void addNewLibrarian(){
        System.out.println("Librarian: ");
        System.out.println("Enter the username: ");
        String username = sc.next();
        sc.nextLine();
        System.out.println("Enter the password: ");
        String password = sc.next();
        sc.nextLine();
        admin.addNewLibrarian(username, password);
    }

    public void printAdminInstructions(){
        System.out.println("""
                the following instructions are available for admin:\s
                0. print instructions for admin
                1. add a new librarian
                2. remove a librarian
                3. view librarians
                4. quit
                """);
    }


    public void removeLibrarian(){
        // enter the username of librarian, and check availability.
        // if it is available, remove it from the list.
        System.out.println("enter the username of the librarian: ");
        String username = sc.next();
        admin.deleteLibrarian(username);
    }

    public void viewLibrarians() {
        admin.viewLibrarians();
    }
}
