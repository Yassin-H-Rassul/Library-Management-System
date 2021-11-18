package com.LibraryManagementSystem.Server;

import com.LibraryManagementSystem.Controller.ReadFromFile;
import com.LibraryManagementSystem.Controller.SaveToFile;
import com.LibraryManagementSystem.Model.Book;
import com.LibraryManagementSystem.Model.Packet;
import com.LibraryManagementSystem.Model.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class PacketController extends Thread {
    private ReadFromFile readFromFile = new ReadFromFile();
    private Socket socket;

    public PacketController(Socket socket){
        this.socket=socket;
    }

    public void run() {
        try{
            System.out.println("Client Connected");
            ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            socket.setTcpNoDelay(true);
            Packet request=null;
            while(true) {
                request = (Packet) objectIn.readObject();
                System.out.println("1");

                int requestMessage = request.getMessage();
                Packet response = null;
                if(requestMessage == 100){
                    //call a method to save to file.
                    //call save book
                    response = saveBooks(request);

                } else if(requestMessage == 101){
                    //call save issued
                    response = saveIssuedBooks(request);
                } else if(requestMessage == 102){
                    //call a method to save to file.
                    //call save returned
                    response = saveReturnedBooks(request);
                } else if(requestMessage == 103){
                    //call a method to save to file.
                    //call save students
                    response = saveStudents(request);
                }
                else if(requestMessage == 2){
                    //call a method to read from file.
                    // send 4 packets in order to the client
                    Packet<ArrayList<Book>> booksPacket = readBooks();
                    Packet<ArrayList<Map<String, Object>>> issuedBooksPacket = readIssuedBooks();
                    Packet<ArrayList<Map<String, Object>>> returnedBooksPacket = readReturnedBooks();
                    Packet<ArrayList<Student>> studentsPacket = readStudents();

                    objectOut.writeObject(booksPacket);
                    objectOut.flush();
                    objectOut.writeObject(issuedBooksPacket);
                    objectOut.flush();
                    objectOut.writeObject(returnedBooksPacket);
                    objectOut.flush();
                    objectOut.writeObject(studentsPacket);
                    objectOut.flush();
                    return;

                } else if(requestMessage == 3){
                    // call a method to stop connection.
                    objectOut.close();
                    objectIn.close();
                    socket.close();
                }
//
                System.out.println("Before OutputStream");
                objectOut.writeObject(response);
                objectOut.flush();
                break;
            }
            objectOut.close();
            objectIn.close();
            socket.close();

        }catch (IOException e){
            System.out.println("IOException: "+e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("Class Not Found Exception: "+e.getMessage());
        }
        System.out.println("Disconnected");

    }

    public Packet saveBooks(Packet<ArrayList<Book>> packet){
        ArrayList<Book> books = packet.getContent();
        SaveToFile<ArrayList<Book>> listSaveToFile = new SaveToFile<>(books);
        return new Packet(1,null);

    }

    public Packet saveIssuedBooks(Packet<ArrayList<Map<String, Object>>> packet){
        ArrayList<Map<String, Object>> issuedBooks = packet.getContent();
        SaveToFile<ArrayList<Map<String, Object>>> listSaveToFile = new SaveToFile<>(issuedBooks);
        return new Packet(1,null);

    }
    public Packet saveReturnedBooks(Packet<ArrayList<Map<String, Object>>> packet){
        ArrayList<Map<String, Object>> returnedBooks = packet.getContent();
        SaveToFile<ArrayList<Map<String, Object>>> listSaveToFile = new SaveToFile<>(returnedBooks);
        return new Packet(1,null);

    }

    public Packet saveStudents(Packet<ArrayList<Student>> packet){
        ArrayList<Student> students = packet.getContent();
        SaveToFile<ArrayList<Student>> listSaveToFile = new SaveToFile<>(students);
        return new Packet(1,null);

    }

    public Packet<ArrayList<Book>> readBooks() throws IOException, ClassNotFoundException {
        ArrayList<Book> books = readFromFile.readBooks();
        return new Packet<>(1, books);

    }
    public Packet<ArrayList<Map<String, Object>>> readIssuedBooks() throws IOException, ClassNotFoundException {
        ArrayList<Map<String, Object>> issuedBooks = readFromFile.readIssuedBooks();
        return new Packet<>(1, issuedBooks);

    }

    public Packet<ArrayList<Map<String, Object>>> readReturnedBooks() throws IOException, ClassNotFoundException {
        ArrayList<Map<String, Object>> returnedBooks = readFromFile.readReturnedBooks();
        return new Packet<>(1, returnedBooks);
    }


    public Packet<ArrayList<Student>> readStudents() throws IOException, ClassNotFoundException {
        ArrayList<Student> students = readFromFile.readStudents();
        return new Packet<>(1, students);
    }


}
