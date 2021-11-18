package com.LibraryManagementSystem.Controller;

import com.LibraryManagementSystem.Model.Book;
import com.LibraryManagementSystem.Model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Librarian implements LibrarianTasks {
    private final String librarianUsername;
    private final String librarianPassword;
    private static final ArrayList<Book> availableBooks = Library.getAvailableBooks();
    private static final ArrayList<Map<String, Object>> issuedBooks = Library.getIssuedBooks();
    private static final ArrayList<Map<String, Object>> returnedBooks = Library.getReturnedBooks();
    private static final ArrayList<Student> students = Library.getStudents();

    public Librarian(String name, String password) {
        this.librarianUsername = name;
        this.librarianPassword = password;
    }

    public String getLibrarianUsername() {
        return librarianUsername;
    }

    public String getLibrarianPassword() {
        return librarianPassword;
    }

    @Override
    public boolean addNewBook(int id, String bookName, String authorName, int quantity) throws IOException, ClassNotFoundException {
        Book bookFound = searchBookId(id);
        if (bookFound != null) {
            System.out.println("book id number: " + id + " already exists, adding new book failed.");
            return false;
        }

        if (bookName.isEmpty() || authorName.isEmpty()) {
            System.out.println("both book name and author name must be specified, adding new book failed.");
            return false;
        }

        if (bookName_And_authorNameExists(bookName, authorName)) {
            System.out.println("this book has been added before.");
            return false;
        }

        if (quantity == 0) {
            System.out.println("quantity can not be equal to zero, adding new book failed.");
            return false;
        }




        // in construction:

        availableBooks.add(new Book(id, bookName, authorName, quantity));






//        // call a method to save the data in a file not in a collection.
//        if(saveDataToFile()){
//            System.out.println("data saved to file successfully.");
//        }
//
//        // then call another method to read from the file and save it into the collection.
//        if(readDataFromFile()){
//            System.out.println("data read from file successfully.");
//        }

        //update the available books in library class
        Library.setAvailableBooks(availableBooks, true);
        System.out.println("book successfully added.");

        return true;
    }

//    public static boolean saveDataToFile(){
//
//
//        SaveToFile<ArrayList<Book>> saveBooks = new SaveToFile<>(Library.getAvailableBooks());
//
//
//
//        try{
//            FileOutputStream fileOutputStream = new FileOutputStream("Z:\\BookObjects.txt");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(book);
//            fileOutputStream.close();
//            objectOutputStream.close();
//        } catch (FileNotFoundException e){
//            System.out.println("file not found.");
//            return false;
//        } catch (IOException e){
//            System.out.println("initializing the stream failed.");
//            return false;
//        }
//        return true;
//    }

    public static boolean readDataFromFile(){
//        try{
//            //in = read
//            //out = write
//            FileInputStream fileInputStream = new FileInputStream("Z:\\BookObjects.txt");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            while (true) {
//                try {
//                    Book book = (Book) objectInputStream.readObject();
//                    if (book != null) {
//                        availableBooks.add(book);
//                    }
//                } catch (EOFException e){
//                    return true;
//                }
////                    Optional<Book> bookOptional = Optional.ofNullable((Book) objectInputStream.readObject());
////                    if(bookOptional.isPresent()) availableBooks.add(bookOptional.get());
////                    else return true;
//            }
//
//        } catch (FileNotFoundException e){
//            System.out.println("file not found.");
//            return false;
//        } catch (IOException e){
//            System.out.println("initializing the stream failed.");
//            return false;
//        } catch (ClassNotFoundException e){
//            e.printStackTrace();
//            return false;
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        return true;
    }

    @Override
    public boolean removeBook(int bookId) throws IOException, ClassNotFoundException {
        Book bookFound = searchBookId(bookId);
        if (bookFound != null) {
            availableBooks.remove(bookFound);
            System.out.println("book with book id: " + bookId + " successfully removed.");

            //update available books
            Library.setAvailableBooks(availableBooks, true);


            return true;
        } else {
            System.out.println("no book is available with id: " + bookId + ", removing book failed.");
            return false;
        }
    }

    private static Book searchBookId(int bookId) {
        for (var book :
                availableBooks) {
            if (book.getBookID() == bookId) {
                return book;
            }
        }
        return null;
    }


    private static  boolean bookName_And_authorNameExists(String bookName, String authorName) {
        for (var book :
                availableBooks) {
            if (book.getBookName().equals(bookName) && book.getBookAuthor().equals(authorName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean issueBook_For_Existing_Student(int bookId, int studentId) throws IOException, ClassNotFoundException {
        Book bookFound = searchBookId(bookId);
        if (bookFound == null || bookFound.getBookQuantity() == 0) {
            System.out.println("no book with id: " + bookId + " is available, issuing failed.");
            return false;
        }
        Student studentFound = searchStudent(studentId);

        if (studentFound != null) {
            // when student is found, search for its map and add the book to the listOfBooks.
            Map<String, Object> mapFound = findMap(studentId, issuedBooks);
            int mapIndex = issuedBooks.indexOf(mapFound);
            add_IssuedBook_For_Existing_Student(bookFound, mapIndex);
            return true;
        }
        return false;
    }

    @Override
    public boolean issueBook_For_New_Student(int bookId, int studentId, String studentName, String studentMobileNo) throws IOException, ClassNotFoundException {
        Book bookFound = searchBookId(bookId);
        Student newStudent = new Student(studentId, studentName, studentMobileNo);
        students.add(newStudent);

        //update the students list
        Library.setStudents(students, true);



        addIssuedBook(bookFound, newStudent);
        return true;
    }


    private static  void addIssuedBook(Book book, Student student) throws IOException, ClassNotFoundException {
        Map<String, Object> bookAndBorrower = new LinkedHashMap<>();
        ArrayList<Book> listOfBooks = new ArrayList<>();
        listOfBooks.add(book);
        bookAndBorrower.put("book", listOfBooks);
        bookAndBorrower.put("borrowerStudent", student);
        issuedBooks.add(bookAndBorrower);
        // decrementing book quantity by 1.
        book.setBookQuantity(book.getBookQuantity() - 1);
        //update the issuedBooks
        Library.setIssuedBooks(issuedBooks, true);

        System.out.println("book successfully issued.");

    }


    private static void add_IssuedBook_For_Existing_Student(Book book, int mapIndex) throws IOException, ClassNotFoundException {
        Map<String, Object> bookAndBorrower = issuedBooks.get(mapIndex);
        ArrayList<Book> listOfBooks = new ArrayList<>();
        listOfBooks.add(book);
        ArrayList<Book> currentListOfBooks = (ArrayList<Book>) bookAndBorrower.get("book");
        // if it is not the first book to be issued to this student
        if(bookAndBorrower.get("book") != null) {
            // concatenate listOfBooks with current books.
            listOfBooks.addAll(currentListOfBooks);
        }
        bookAndBorrower.put("book", listOfBooks);
        // decrementing book quantity by 1.
        book.setBookQuantity(book.getBookQuantity() - 1);
        //update issued books
        Library.setIssuedBooks(issuedBooks,true);

        System.out.println("book successfully issued.");
    }


    @Override
    public boolean returnBook(int bookId, int studentId) throws IOException, ClassNotFoundException {
        Book bookFound = searchBookId(bookId);
        Student studentFound = searchStudent(studentId);
        Map<String, Object> mapFound = findMap(studentId, returnedBooks);
        Map<String, Object> returnedBookAndBorrower = new LinkedHashMap<>();
        if (bookFound == null || studentFound == null) {
            System.out.println("book or student is not available, returning process failed.");
            return false;
        }
        // if the student have returned some books before:
        if(mapFound != null){
            ArrayList<Book> previouslyReturnedBooks = (ArrayList<Book>) mapFound.get("book");
            previouslyReturnedBooks.add(bookFound);
            returnedBookAndBorrower.put("book", previouslyReturnedBooks);
            returnedBookAndBorrower.put("returnerStudent", studentFound);
            System.out.println("returning book was successful.");
            // incrementing quantity by 1.
            bookFound.setBookQuantity(bookFound.getBookQuantity() + 1);
            //update returned books
            Library.setReturnedBooks(returnedBooks,true);



            return true;
        }
        // if the student haven't returned any books.
        ArrayList<Book> bookArrayList = new ArrayList<>();
        bookArrayList.add(bookFound);
        returnedBookAndBorrower.put("book", bookArrayList);
        returnedBookAndBorrower.put("returnerStudent", studentFound);
        returnedBooks.add(returnedBookAndBorrower);
        //update returned books
        Library.setReturnedBooks(returnedBooks,true);



        // when a student returns a book, we have to delete him from students who issued books.
        Map<String, Object> mapFoundForStudentWhoIssued= findMap(studentId, issuedBooks);


        if (mapFoundForStudentWhoIssued == null) {
            System.out.println("student ID: " + studentId + " has not taken any book and it is not available, returning process failed.");
            return false;
        }
        // make Arraylistofbooks and check how many books are available, if size == 1, remove the entire map.
        ArrayList<Book> listOfBooks = (ArrayList<Book>) mapFoundForStudentWhoIssued.get("book");

        if (listOfBooks.size() == 1) {
            issuedBooks.remove(mapFoundForStudentWhoIssued);
            System.out.println("returning process succeeded.");
            // incrementing quantity by 1.
            bookFound.setBookQuantity(bookFound.getBookQuantity() + 1);
            //update issued books
            Library.setIssuedBooks(issuedBooks, true);


            return true;
        }

        int mapIndex = issuedBooks.indexOf(mapFoundForStudentWhoIssued);
        ArrayList<Book> thisStudentBooks = (ArrayList<Book>) issuedBooks.get(mapIndex).get("book");
        thisStudentBooks.remove(bookFound);
        System.out.println("returning process succeeded.");

        // incrementing quantity by 1.
        bookFound.setBookQuantity(bookFound.getBookQuantity() + 1);
        //update issued books
        Library.setIssuedBooks(issuedBooks, true);

        return true;
    }


    private static  Map<String, Object> findMap(int studentId, ArrayList<Map<String, Object>> mapArrayList) {
        Student currentStudent;
        int currentStudentId;
        for (var mapItem :
                mapArrayList) {

            if(mapArrayList.get(0).containsKey("borrowerStudent"))
            currentStudent = (Student) mapItem.get("borrowerStudent");
            else currentStudent = (Student) mapItem.get("returnerStudent");

            currentStudentId = currentStudent.getStudentID();
            if (currentStudentId == studentId) {
                return mapItem;
            }

        }
        return null;
    }

    private static Student searchStudent(int studentId) {
        for (var student :
                students) {
            if (student.getStudentID() == studentId) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void viewAvailableBooks() {
        Library.viewAvailableBooks();
    }

    @Override
    public void viewIssuedBooks() {
        Library.viewIssuedBooks();
    }

    @Override
    public void viewReturnedBooks() {
        Library.viewReturnedBooks();
    }

    @Override
    public String toString() {
        return "com.LibraryManagementSystem.com.LibraryManagementSystem.Controller.Librarian{" + "librarian username: "+ librarianUsername +
                "\nlibrarian password: " + librarianPassword + "}";

    }
}
