package com.LibraryManagementSystem.View;

import com.LibraryManagementSystem.Controller.Librarian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminView {

    public ArrayList<Librarian> listOfLibrarians = new ArrayList<>();
    public Scanner sc = new Scanner(System.in);

    public void login() throws IOException, ClassNotFoundException {
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
        Librarian currentLibrarian = listOfLibrarians.get(0);
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
        Librarian librarian = new Librarian(username,password);
        listOfLibrarians.add(librarian);
        System.out.println("librarian added!");
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
        Librarian librarianFound = findLibrarian(username);
        if(librarianFound !=null){
            listOfLibrarians.remove(librarianFound);
            System.out.println("librarian successfully deleted.");
        }
        else{
            System.out.println("there is no librarian with this name, removing failed.");
        }
    }

    public Librarian findLibrarian(String username){
        for (var librarian:
                listOfLibrarians) {
            String currentLibrarianUsername = librarian.getLibrarianUsername();
            if(currentLibrarianUsername.equals(username)){
                return librarian;
            }
        }
        return null;
    }

    public void viewLibrarians(){
        for (var librarian:
                listOfLibrarians) {
            System.out.println(librarian);
        }
    }
}
