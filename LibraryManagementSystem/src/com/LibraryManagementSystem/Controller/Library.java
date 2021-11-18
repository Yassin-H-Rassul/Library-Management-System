package com.LibraryManagementSystem.Controller;

import com.LibraryManagementSystem.Model.Book;
import com.LibraryManagementSystem.Model.Student;

import java.io.IOException;
import java.util.*;

public final class Library {
    private static ClientController clientController = new ClientController();


    private static ArrayList<Book> availableBooks = new ArrayList<>();
    private static ArrayList<Map<String, Object>> issuedBooks = new ArrayList<>();
    private static ArrayList<Map<String, Object>> returnedBooks = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();

    public static ArrayList<Book> getAvailableBooks() {
        return availableBooks;
    }

    public static ArrayList<Map<String, Object>> getIssuedBooks() {
        return issuedBooks;
    }

    public static ArrayList<Map<String, Object>> getReturnedBooks() {
        return returnedBooks;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void setAvailableBooks(ArrayList<Book> availableBooks, boolean saveDataToFile) throws IOException, ClassNotFoundException {
        Library.availableBooks = availableBooks;
        if (saveDataToFile) {
            clientController.setData(availableBooks);
            clientController.getAllData();
        }
    }

    public static void setIssuedBooks(ArrayList<Map<String, Object>> issuedBooks, boolean saveDataToFile) throws IOException, ClassNotFoundException {
        Library.issuedBooks = issuedBooks;
        //save collections to file.
        if (saveDataToFile) {
            clientController.setData(issuedBooks);
            clientController.getAllData();
        }

    }

    public static void setReturnedBooks(ArrayList<Map<String, Object>> returnedBooks, boolean saveDataToFile) throws IOException, ClassNotFoundException {
        Library.returnedBooks = returnedBooks;
        //save collections to file.
        if (saveDataToFile) {
            clientController.setData(returnedBooks);
            clientController.getAllData();
        }

    }

    public static void setStudents(ArrayList<Student> students, boolean saveDataToFile) throws IOException, ClassNotFoundException {
        Library.students = students;
        //save collections to file.
        if (saveDataToFile) {
            clientController.setData(students);
            clientController.getAllData();
        }
    }


    public static void viewAvailableBooks() {
        for (var book :
                availableBooks) {
            System.out.println(book);
        }
        System.out.println("==============");
    }

    public static void viewIssuedBooks() {
        for (var bookAndBorrower :
                issuedBooks) {
            Student currentStudent = (Student) bookAndBorrower.get("borrowerStudent");
            System.out.println("========================");
            System.out.println("student name: " + currentStudent.getStudentName());
            System.out.println("taken books: ");
            ArrayList<Book> currentStudentBooks = (ArrayList<Book>) bookAndBorrower.get("book");

            if (currentStudentBooks != null)
                for (var book :
                        currentStudentBooks) {
                    System.out.println(book);
                }
            System.out.println("========================");
        }
    }

    public static void viewReturnedBooks() {
        for (var bookAndReturner :
                returnedBooks) {
            Student currentStudent = (Student) bookAndReturner.get("returnerStudent");
            System.out.println("student name: " + currentStudent.getStudentName());
            ArrayList<Book> currentStudentBooks = (ArrayList<Book>) bookAndReturner.get("book");
            System.out.println("student books:");
            for (var book :
                    currentStudentBooks) {
                System.out.println(book);
            }
        }
        System.out.println("===========================");
    }

}
