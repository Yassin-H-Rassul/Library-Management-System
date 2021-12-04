package com.LibraryManagementSystem.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClientController {

    private Socket socket = null;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();
    private String JSONin;
    private String JSONout;
    private String message;
    private String JSONinContent;

    public void startConnection() {

        int PORT_NUMBER = 4000;
        try {
            socket = new Socket("localhost", PORT_NUMBER);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("host is not known.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connecting to server Successful");

    }

    public void endConnection() throws IOException {
        out.close();
        in.close();
        socket.close();
    }


    public boolean searchBookId(int id) {
        message = "01";
        JSONout = message + " " + id;
        out.println(JSONout);
        try {
            JSONin = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !JSONin.contains("00");
    }


    public void saveBook(int bookId, String bookName, String bookAuthor, int quantity) {
        Map<String, String> bookDetails = new LinkedHashMap<>();
        bookDetails.put("bookID", String.valueOf(bookId));
        bookDetails.put("bookName", bookName);
        bookDetails.put("bookAuthor", bookAuthor);
        bookDetails.put("bookQuantity", String.valueOf(quantity));

        String bookAsJSON = null;
        try {
            bookAsJSON = mapper.writeValueAsString(bookDetails);
        } catch (JsonProcessingException e) {
            System.out.println("could not convert an object to json. **saveBook()");
            e.printStackTrace();
        }

        message = "02";
        JSONout = message + " " + bookAsJSON;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // we check the first two characters for the response message
        } catch (IOException e) {
            System.out.println("IOException: in client savebook() " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean removeBook(int bookId) {
        message = "03";
        JSONout = message + " " + bookId;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // we check the first two characters for the response message
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (JSONin.contains("01")) {
            System.out.println("book removed.");
            return true;
        } else {
            System.out.println("book is not available in the database, removing book failed.");
            return false;
        }

    }

    public boolean bookAndAuthorExists(String bookName, String authorName) {
        message = "04";
        JSONout = message + " " + bookName + "," + authorName;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // 00 or 01
        } catch (IOException e) {
            System.out.println("IOException in Client bookAndAuthorNameExists" + e.getMessage());
        }


        // checking:
        return !JSONin.contains("00");
    }

    public void saveStudent(int studentId, String studentName, String mobileNo) {
        LinkedHashMap<String, String> studentDetails = new LinkedHashMap<>();
        studentDetails.put("studentID", String.valueOf(studentId));
        studentDetails.put("studentName", studentName);
        studentDetails.put("studentMobileNo", mobileNo);

        String studentAsJSON = null;
        try {
            studentAsJSON = mapper.writeValueAsString(studentDetails);
        } catch (JsonProcessingException e) {
            System.out.println("could not convert student to JSON. **saveStudent()");
            e.printStackTrace();
        }

        String message = "05";
        JSONout = message + " " + studentAsJSON;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // first two characters
        } catch (IOException e) {
            System.out.println("IOException at Client SaveStudent()");
            e.printStackTrace();
        }

    }


    public boolean searchForStudent(int studentId) {
        // check if the student exists or not in the students table.
        message = "06";
        JSONout = message + " " + studentId;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // whether the student exists or not.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONin.contains("01");
    }

    public boolean searchForStudentWhoIssued(int studentId, int bookId) {
        // check if the student and issued book exists or not in the issuedBooks table.
        message = "07";
        JSONout = message + " " + studentId + "," + bookId;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // whether the student exists or not.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONin.contains("01");
    }

    public String[] getAvailableBooks() {
        // return all books in availableBooks table,
        message = "08";
        JSONout = message;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // message + books.
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONinContent = JSONin.substring(3);
        String[] availableBooks = JSONinContent.split("/");
        return availableBooks;
    }


    public String[] getIssuedBooks() {
        // return all books and students in issuedBooks table,
        message = "09";
        return getStudentsWithIssuedBook(message);
    }

    public String[] getReturnedBooks() {
        // return all books in returnedBooks table,
        message = "10";
        return getStudentsWithIssuedBook(message);
    }

    private String[] getStudentsWithIssuedBook(String message) {
        JSONout = message;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); //   [student + book]/[student + book].
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONinContent = JSONin.substring(3);
        String[] studentsWithIssuedBook = JSONinContent.split("/"); // each row will be separated.
        return studentsWithIssuedBook;
    }


    public void saveIssuedBook(int studentId, int bookId) {
        String message = "11";
        JSONout = message + " " + studentId + "," + bookId;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // first two characters
        } catch (IOException e) {
            System.out.println("IOException at Client SaveStudent()");
            e.printStackTrace();
        }
    }

    public void saveReturnedBook(int studentId, int bookId) {
        String message = "12";
        JSONout = message + " " + studentId + "," + bookId;
        out.println(JSONout);
        try {
            JSONin = in.readLine(); // first two characters
        } catch (IOException e) {
            System.out.println("IOException at Client SaveStudent()");
            e.printStackTrace();
        }
    }
}