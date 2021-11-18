package com.LibraryManagementSystem.Controller;

import com.LibraryManagementSystem.Model.Book;
import com.LibraryManagementSystem.Model.Packet;
import com.LibraryManagementSystem.Model.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

public class ClientController {

    private Socket socket = null;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    public void startConnection() {

        int PORT_NUMBER = 4000;
        try {
            socket = new Socket("localhost", PORT_NUMBER);
            objectIn = new ObjectInputStream(socket.getInputStream());  //
            objectOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e){
            System.out.println("host is not known.");
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Connecting to server Successful");

    }

    public void endConnection() throws IOException {
        Packet packet = new Packet(3, null);
        objectOut.close();
        objectIn.close();
        socket.close();
    }

    public boolean setData(ArrayList<?> arrayList) throws IOException, ClassNotFoundException {
//        startConnection();
        int message = 0;
        // check the type of the packet
        if (arrayList != null) {
            if (arrayList.size() > 0 && arrayList.get(0) instanceof Student)
                message = 103;

            else if (arrayList.size() > 0 && arrayList.get(0) instanceof Book) {
                message = 100;

            } else if (arrayList.size() > 0 && arrayList.get(0) instanceof Map<?, ?>) {
                if (((Map<?, ?>) arrayList.get(0)).containsKey("borrowerStudent"))
                    message = 101;
                else
                    message = 102;
            }
        }


        Packet<ArrayList<?>> packet = new Packet<>(message, arrayList);
        objectOut.writeObject(packet);
        objectOut.flush();
        Packet response = (Packet) objectIn.readObject();
        int responseMessage = response.getMessage();
        if (responseMessage == 1) {
            System.out.println("setting Data to file succeeded.");
            return true;
        } else {
            System.out.println("setting data to file failed.");
            return false;
        }
    }


    public boolean getAllData() throws IOException, ClassNotFoundException {
//        startConnection();
        // packets will be received in order of (available books, issued books, returned books, students).
        Packet packetToBeSent = new Packet(2, null);
        objectOut.writeObject(packetToBeSent);
        Packet<ArrayList<Book>> availableBooksPacket = (Packet<ArrayList<Book>>) objectIn.readObject();
        Packet<ArrayList<Map<String, Object>>> issuedBooksPacket = (Packet<ArrayList<Map<String, Object>>>) objectIn.readObject();
        Packet<ArrayList<Map<String, Object>>> returnedBooksPacket = (Packet<ArrayList<Map<String, Object>>>) objectIn.readObject();
        Packet<ArrayList<Student>> studentsPacket = (Packet<ArrayList<Student>>) objectIn.readObject();
        //after receiving all data from file, we set them to our collections.
        Library.setAvailableBooks(availableBooksPacket.getContent(), false);
        Library.setIssuedBooks(issuedBooksPacket.getContent(), false);
        Library.setReturnedBooks(returnedBooksPacket.getContent(), false);
        Library.setStudents(studentsPacket.getContent(), false);
//        System.out.println("all data fetched successfully.");
        return true;

    }
}
