package com.LibraryManagementSystem.Controller;

import java.util.stream.Stream;

public class Librarian implements LibrarianTasks {
    private final String librarianUsername;
    private final String librarianPassword;
    ClientController clientController = new ClientController();

    public Librarian(String name, String password) {
        this.librarianUsername = name;
        this.librarianPassword = password;
        clientController.startConnection();
    }

    public String getLibrarianUsername() {
        return librarianUsername;
    }

    public String getLibrarianPassword() {
        return librarianPassword;
    }

    @Override
    public boolean addNewBook(int id, String bookName, String authorName, int quantity) {

        boolean bookFound = clientController.searchBookId(id);
        if (bookFound) {
            System.out.println("book id number: " + id + " already exists, adding new book failed.");
            return false;
        }

        if (bookName.isEmpty() || authorName.isEmpty()) {
            System.out.println("both book name and author name must be specified, adding new book failed.");
            return false;
        }


        if (clientController.bookAndAuthorExists(bookName, authorName)) {
            System.out.println("this book has been added before.");
            return false;
        }

        if (quantity == 0) {
            System.out.println("quantity can not be equal to zero, adding new book failed.");
            return false;
        }

        System.out.println("we will save the book to the database");

        clientController.saveBook(id, bookName, authorName, quantity);
        System.out.println("book successfully added.");

        return true;
    }

    @Override
    public boolean removeBook(int bookId) {

        return clientController.removeBook(bookId);
    }

    @Override
    public boolean issueBook_For_Existing_Student(int bookId, int studentId) {

        boolean bookFound = clientController.searchBookId(bookId);

        if (!bookFound) {
            System.out.println("no book with id: " + bookId + " is available, issuing failed.");
            return false;
        }

        boolean studentFound = clientController.searchForStudent(studentId);

        if (studentFound) {
            clientController.saveIssuedBook(studentId, bookId);
            System.out.println("book successfully issued.");
            return true;
        }
        return false;
    }

    @Override
    public boolean issueBook_For_New_Student(int bookId, int studentId, String studentName, String studentMobileNo) {
        boolean bookFound = clientController.searchBookId(bookId);
        // if book was found then
        if (bookFound) {
            clientController.saveStudent(studentId, studentName, studentMobileNo);
            // after having the book, and saving the student, just pass the studentId and the bookId
            clientController.saveIssuedBook(studentId, bookId);
            System.out.println("book successfully issued.");
            return true;
        } else {
            System.out.println("book was not found, issuing failed.");
            return false;
        }
    }

    @Override
    public boolean returnBook(int bookId, int studentId) {
        // we send the bookId, and studentID to server and save them in returned_books table,
        // and then we delete the studentID and bookId from issued books.
        boolean bookFound = clientController.searchBookId(bookId);
        if(!bookFound){
            System.out.println("book is not available, returning book failed.");
        }

        boolean studentFound = clientController.searchForStudent(studentId);

        if(!studentFound){
            System.out.println("student is not available, returning book failed.");
        }
        // check for student id in issuedBooks table, along with the book he issued.
        boolean hasStudentIssued = clientController.searchForStudentWhoIssued(studentId, bookId);
        // if student has issued:
        if(!hasStudentIssued){
            System.out.println("the student has not issued this book before, returning book failed.");
        }

        clientController.saveReturnedBook(studentId, bookId);
        System.out.println("returning book was successful");
        // the row is automatically deleted.
        return true;
    }

    @Override
    public void viewAvailableBooks() {
        String[] books = clientController.getAvailableBooks();
        System.out.println("\nall available books:\n");
        Stream.of(books).forEach((book) -> System.out.println(book + "\n"));

    }

    @Override
    public void viewIssuedBooks() {
        String[] studentAndIssuedBooks = clientController.getIssuedBooks();
        System.out.println("\nstudents and the books they issued: \n");
        Stream.of(studentAndIssuedBooks).forEach((e) -> System.out.println(e + "\n"));

    }

    @Override
    public void viewReturnedBooks() {
        String[] studentAndReturnedBooks = clientController.getReturnedBooks();
        System.out.println("\nstudents and the books they returned: \n");
        Stream.of(studentAndReturnedBooks).forEach((e) -> System.out.println(e + "\n"));

    }

    @Override
    public String toString() {
        return "Librarian{" +
                "librarianUsername='" + librarianUsername + '\'' +
                ", librarianPassword='" + librarianPassword + '\'' +
                '}';
    }
}
