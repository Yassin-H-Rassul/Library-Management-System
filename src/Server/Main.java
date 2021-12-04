package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4000)){
            while (true) {
                new JSONController(serverSocket.accept()).start();
            }
        } catch (IOException e){
            System.out.println("Server Exception "+e.getMessage());
        }
    }
}
