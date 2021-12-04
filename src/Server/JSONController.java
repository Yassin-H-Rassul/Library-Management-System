package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import Model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.List;

public class JSONController extends Thread {


    private final Socket socket;
    private final ObjectMapper mapper = new ObjectMapper();
    private String JSONout;


    public JSONController(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Client Connected");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socket.setTcpNoDelay(true);


            // connecting to database.
            Connection connection;
            PreparedStatement statement;
            ResultSet resultSet;

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_database", "root", "Yassin H Rassul");
            String sqlQuery;

            while (true) {
                String JSONin = in.readLine(); // read from client
                System.out.println(JSONin);
                String requestMessage = JSONin.substring(0, 2);
                String JSONinContent = null;
                if(JSONin.length() >2) {
                    JSONinContent = JSONin.substring(3);
                }
                String respondMessage;
                switch (requestMessage) {
                    case "01" -> { // searchBookId(bookId)
                        int id = Integer.parseInt(JSONinContent); // converting the id of the book to int.
                        sqlQuery = "select * from books where bookId=?";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, id);
                        resultSet = statement.executeQuery();
                        if(resultSet.isBeforeFirst()) {
                            List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                            StringBuilder retrievedFromDatabase = new StringBuilder();
                            for (JSONObject obj :
                                    JSONList) {
                                retrievedFromDatabase.append(obj);
                            }
                            String retrievedStringFromDB = retrievedFromDatabase.toString();

                            respondMessage = "01";
                            JSONout = respondMessage + " " + retrievedStringFromDB;
                        } else {
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }
                    }
                    case "02" -> {  //saveBook()
                        // convert received book to Book object, and send fields data to query.
                        System.out.println(JSONinContent);
                        Book book = mapper.readValue(JSONinContent, Book.class);
                        sqlQuery = "insert into books values(?,?,?,?)";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, book.getBookID());
                        statement.setString(2, book.getBookName());
                        statement.setString(3, book.getBookAuthor());
                        statement.setInt(4, book.getBookQuantity());
                        statement.executeUpdate();                    // now the book is saved into database.
                        respondMessage = "01";
                        JSONout = respondMessage;
                    }
                    case "03" -> {// removeBook(bookId)
                        int id = Integer.parseInt(JSONinContent); // converting the id of the book to int.

                        sqlQuery = "select * from books where bookId=?";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, id);
                        resultSet = statement.executeQuery();
                        // if the book was available:
                        if(resultSet.isBeforeFirst()){
                            sqlQuery = "delete from books where bookId=?";
                            statement = connection.prepareStatement(sqlQuery);
                            statement.setInt(1, id);
                            statement.executeUpdate();
                            respondMessage = "01";
                            JSONout = respondMessage;
                        }
                        else {
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }

                    }
                    case "04" -> {//bookAndAuthorExists(bookName, authorName)
                        String[] bookAndAuthor = JSONinContent.split(",");
                        String bookName = bookAndAuthor[0];
                        String bookAuthor = bookAndAuthor[1];
                        sqlQuery = "select * from books where bookName=? AND bookAuthor=?";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setString(1, bookName);
                        statement.setString(2, bookAuthor);
                        resultSet = statement.executeQuery();
                        if(resultSet.isBeforeFirst()) {
                            List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                            StringBuilder retrievedFromDatabase = new StringBuilder();
                            for (JSONObject obj :
                                    JSONList) {
                                retrievedFromDatabase.append(obj).append("/");
                            }
                            String retrievedStringFromDB = retrievedFromDatabase.toString();
                            respondMessage = "01";
                            JSONout = respondMessage + " " + retrievedStringFromDB;
                        } else {
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }
                    }

                    case "05" -> {  // saveStudent(student);
                        Student student = mapper.readValue(JSONinContent, Student.class);
                        sqlQuery = "insert into students values(?,?,?)";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, student.getStudentID());
                        statement.setString(2, student.getStudentName());
                        statement.setString(3, student.getStudentMobileNo());
                        statement.executeUpdate();                    // now the student is saved into database.
                        respondMessage = "01";
                        JSONout = respondMessage;
                    }
                    case "06" -> { // searchForStudent(studentId)
                        int id = Integer.parseInt(JSONinContent); // converting the id of the book to int.
                        sqlQuery = "select * from students where studentId=?";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, id);
                        resultSet = statement.executeQuery();
                        if(resultSet.isBeforeFirst()) {
                            List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                            String retrievedFromDatabase = "";
                            for (JSONObject obj :
                                    JSONList) {
                                retrievedFromDatabase += obj;
                            }
                            respondMessage = "01";
                            JSONout = respondMessage + " " + retrievedFromDatabase;
                        } else {
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }
                    }
                    case "07" -> { // searchForStudentWhoIssued(studentId, bookId)
                        String[] studentAndBookId = JSONinContent.split(",");
                        int studentId = Integer.parseInt(studentAndBookId[0]);
                        int bookId = Integer.parseInt(studentAndBookId[1]);

                        sqlQuery = "select * from issued_books where studentIdF=? AND bookIdF=? ";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, studentId);
                        statement.setInt(2, bookId);
                        resultSet = statement.executeQuery();
                        if (resultSet.isBeforeFirst()) { // if the student has issued books before:
                            List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                            StringBuilder retrievedFromDatabase = new StringBuilder();
                            for (JSONObject obj :
                                    JSONList) {
                                retrievedFromDatabase.append(obj);
                            }

                            String retrievedStringFromDB = retrievedFromDatabase.toString();

                            respondMessage = "01";
                            JSONout = respondMessage + " " + retrievedStringFromDB;
                        } else { // otherwise:
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }
                    }
                    case "08" -> { // getAvailableBooks()
                        sqlQuery = "select * from books";
                        statement = connection.prepareStatement(sqlQuery);
                        resultSet = statement.executeQuery();
                        if(resultSet.isBeforeFirst()) {
                            List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                            StringBuilder retrievedFromDatabaseSB = new StringBuilder();
                            for (JSONObject obj :
                                    JSONList) {
                                retrievedFromDatabaseSB.append(obj).append("/");
                            }
                            String retrievedFromDatabaseString = retrievedFromDatabaseSB.toString();



                            respondMessage = "01";
                            JSONout = respondMessage + " " + retrievedFromDatabaseString;
                        }
                        else {
                            respondMessage = "00";
                            JSONout = respondMessage;
                        }

                    }
                    case "09" -> { // getIssuedBooks()
                        sqlQuery = "SELECT s.studentName,b.bookName  FROM students s" +
                                "  JOIN issued_books i ON s.studentId = i.studentIdF" +
                                "  JOIN books b ON i.bookIdF = b.bookId";
                        statement = connection.prepareStatement(sqlQuery);
                        resultSet = statement.executeQuery();

                        List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                        StringBuilder retrievedFromDatabase = new StringBuilder();
                        for (JSONObject obj :
                                JSONList) {
                            retrievedFromDatabase.append(obj).append("/");
                        }

                        String retrievedStringFromDB = retrievedFromDatabase.toString();

                        respondMessage = "01";
                        JSONout = respondMessage + " " + retrievedStringFromDB;
                    }
                    case "10" -> { // getReturnedBooks()
                        sqlQuery = "SELECT s.studentName,b.bookName FROM students s" +
                                "  JOIN returned_books r ON s.studentId = r.studentIdFK" +
                                "  JOIN books b ON r.bookIdFK = b.bookId";
                        statement = connection.prepareStatement(sqlQuery);
                        resultSet = statement.executeQuery();

                        List<JSONObject> JSONList = Formatter.getFormattedResult(resultSet);
                        StringBuilder retrievedFromDatabase = new StringBuilder();
                        for (JSONObject obj :
                                JSONList) {
                            retrievedFromDatabase.append(obj).append("/");
                        }

                        String retrievedStringFromDB = retrievedFromDatabase.toString();


                        respondMessage = "01";
                        JSONout = respondMessage + " " + retrievedStringFromDB;
                    }

                    case "11" -> { // saveIssuedBook()
                        String[] studentAndBookId = JSONinContent.split(",");
                        int studentId = Integer.parseInt(studentAndBookId[0]);
                        int bookId = Integer.parseInt(studentAndBookId[1]);
                        sqlQuery = "insert into issued_books values(?,?)";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, studentId);
                        statement.setInt(2, bookId);
                        statement.executeUpdate();          // now the student is saved into database.
                        respondMessage = "01";
                        JSONout = respondMessage;
                    }

                    case "12" -> { // saveReturnedBook()
                        String[] studentAndBookId = JSONinContent.split(",");
                        int studentId = Integer.parseInt(studentAndBookId[0]);
                        int bookId = Integer.parseInt(studentAndBookId[1]);
                        sqlQuery = "insert into returned_books values(?,?)";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, studentId);
                        statement.setInt(2, bookId);
                        statement.executeUpdate();          // now the student is saved into database.


                        // delete student and book from issued_book table
                        sqlQuery = "delete from issued_books where studentIdF =? AND bookIdF =?";
                        statement = connection.prepareStatement(sqlQuery);
                        statement.setInt(1, studentId);
                        statement.setInt(2, bookId);
                        statement.executeUpdate();
                        respondMessage = "01";
                        JSONout = respondMessage;
                    }
                }
                out.println(JSONout);
                if(requestMessage.contains("00")){
                    break;
                }
            }
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Disconnected");
    }

}
