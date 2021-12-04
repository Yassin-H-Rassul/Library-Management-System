//package com.LibraryManagementSystem.Controller;
//
//import com.LibraryManagementSystem.Model.Book;
//import com.LibraryManagementSystem.Model.Student;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class ReadFromFile {
//
//    public ArrayList<Book> readBooks() throws IOException, ClassNotFoundException {
//        FileInputStream inputStream1 = new FileInputStream("LibraryManagementSystem/Files/books.txt");
//        ObjectInputStream objectInputStream1 = new ObjectInputStream(inputStream1);
//        ArrayList<Book> books = (ArrayList<Book>) objectInputStream1.readObject();
//        if (books != null) {
//            return books;
//        }
//        return null;
//    }
//
//    public ArrayList<Map<String, Object>> readIssuedBooks() throws IOException, ClassNotFoundException {
//        FileInputStream inputStream3 = new FileInputStream("LibraryManagementSystem/Files/issuedBooks.txt");
//        ObjectInputStream objectInputStream3 = new ObjectInputStream(inputStream3);
//        ArrayList<Map<String , Object>> issuedBooks = (ArrayList<Map<String ,Object>>) objectInputStream3.readObject();
//        if (issuedBooks != null) {
//            return issuedBooks;
//        }
//        return null;
//    }
//
//    public ArrayList<Map<String, Object>> readReturnedBooks() throws IOException, ClassNotFoundException {
//        FileInputStream inputStream4 = new FileInputStream("LibraryManagementSystem/Files/returnedBooks.txt");
//        ObjectInputStream objectInputStream4 = new ObjectInputStream(inputStream4);
//        ArrayList<Map<String , Object>> returnedBooks = (ArrayList<Map<String ,Object>>) objectInputStream4.readObject();
//        if (returnedBooks != null) {
//            return returnedBooks;
//        }
//        return null;
//
//
//
//
//    }
//
//    public ArrayList<Student> readStudents() throws IOException, ClassNotFoundException {
//        FileInputStream inputStream2 = new FileInputStream("LibraryManagementSystem/Files/students.txt");
//        ObjectInputStream objectInputStream2 = new ObjectInputStream(inputStream2);
//        ArrayList<Student> students = (ArrayList<Student>) objectInputStream2.readObject();
//        if (students != null) {
//            return students;
//        }
//        return null;
//
//    }
//}
