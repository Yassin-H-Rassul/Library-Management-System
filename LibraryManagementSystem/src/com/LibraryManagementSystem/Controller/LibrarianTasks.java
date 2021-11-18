package com.LibraryManagementSystem.Controller;

import java.io.IOException;

public interface LibrarianTasks {
    boolean addNewBook(int id, String bookName, String authorName, int quantity) throws IOException, ClassNotFoundException;
    boolean removeBook(int bookId) throws IOException, ClassNotFoundException;
    boolean issueBook_For_Existing_Student(int bookId, int studentId) throws IOException, ClassNotFoundException;
    boolean issueBook_For_New_Student(int bookId, int studentId, String studentName, String studentMobileNo) throws IOException, ClassNotFoundException;
    boolean returnBook(int bookId, int studentId) throws IOException, ClassNotFoundException;
    void viewAvailableBooks();
    void viewIssuedBooks();
    void viewReturnedBooks();
}
