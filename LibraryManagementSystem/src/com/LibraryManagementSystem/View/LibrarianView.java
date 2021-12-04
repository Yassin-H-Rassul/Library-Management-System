package com.LibraryManagementSystem.View;

import com.LibraryManagementSystem.Controller.Librarian;

import java.util.Scanner;

public class LibrarianView {
    public Scanner sc = new Scanner(System.in);
    Librarian librarian;

    public void login(Librarian currentLibrarian){
        librarian = currentLibrarian;
        boolean quit = false;
        int choice;
        System.out.println("you are in Librarian account:");
        printLibrarianInstructions();
        while (!quit) {
            System.out.println("enter you choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 0 -> printLibrarianInstructions();
                case 1 -> addNewBook();
                case 2 -> removeBook();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> showAvailableBooks();
                case 6 -> showIssuedBooks();
                case 7 -> showReturnedBooks();
                case 8 -> {
                    System.out.println("Bye... Good Luck.");
                    quit = true;
                }
            }
        }
    }

    public void printLibrarianInstructions(){
        System.out.println("""
                the following instructions are available:\s
                0. print instructions
                1. add a new book
                2. remove a book
                3. issue a book
                4. return a book
                5. show available books
                6. show issued books
                7. show returned books
                8. quit
                """); // done
    }

    public void issueBook() {
        System.out.println("enter the id of the book: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        System.out.println("enter the id of the student: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        Librarian currentLibrarian = librarian;
        // call the issueBook method, if the student is available finish here.
        if(currentLibrarian.issueBook_For_Existing_Student(bookId,studentId)){
            return;
        }
        // else go through whole function.
        System.out.println("enter the name of the student: ");
        String studentName = sc.next();
        sc.nextLine();
        System.out.println("enter the mobile number of the student: ");
        String studentMobileNo= sc.next();
        sc.nextLine();
        currentLibrarian.issueBook_For_New_Student(bookId,studentId,studentName,studentMobileNo);

    }

    public void returnBook() {
        System.out.println("enter the id of the student: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        System.out.println("enter the id of the book: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        librarian.returnBook(bookId,studentId);

    }

    public void showAvailableBooks(){
        librarian.viewAvailableBooks();
    }
    public void showIssuedBooks(){
        librarian.viewIssuedBooks();
    }
    public void showReturnedBooks(){
        librarian.viewReturnedBooks();
    }

    public void addNewBook() {
        System.out.println("please enter the id of the book: ");
        int bookId= sc.nextInt();
        sc.nextLine();
        System.out.println("please enter the name of the book: ");
        String bookName= sc.next();
        sc.nextLine();
        System.out.println("enter the name of the author: ");
        String authorName = sc.next();
        sc.nextLine();
        System.out.println("please enter the quantity of the book: ");
        int bookQuantity= sc.nextInt();
        librarian.addNewBook(bookId,bookName,authorName,bookQuantity);
    }

    public void removeBook() {
        System.out.println("please enter the id of the book: ");
        int bookId= sc.nextInt();
        sc.nextLine();
        librarian.removeBook(bookId);
    }
}
