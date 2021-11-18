package com.LibraryManagementSystem.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4000)){
            while (true) {
                new PacketController(serverSocket.accept()).start();
            }
        }catch (IOException e){
            System.out.println("Server Exception "+e.getMessage());
        }

    }
}
