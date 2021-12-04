//package com.LibraryManagementSystem.Controller;
//
//
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class SaveToFile<T> {
//    private final T object;
//    private FileOutputStream fileOutputStream;
//    private ObjectOutputStream objectOutputStream;
//    private String pathOfStudentsFile = "LibraryManagementSystem/Files/students.txt";
//    private String pathOfBooksFile = "LibraryManagementSystem/Files/books.txt";
//    private String pathOfIssuedBooksFile = "LibraryManagementSystem/Files/issuedBooks.txt";
//    private String pathOfReturnedBooksFile = "LibraryManagementSystem/Files/returnedBooks.txt";
//
//
//
//    public SaveToFile(T object) {
//        this.object = object;
//        store();
//    }
//
//    public boolean store() {
//        try {
//            // check the type of the received object, and save it to a file corresponding to its type.
//            // below condition works.
//            if (object instanceof ArrayList<?>) {
//                if (((ArrayList<?>) object).size() > 0 && ((ArrayList<?>) object).get(0) instanceof Student)
//                    fileOutputStream = new FileOutputStream(pathOfStudentsFile);
//                    // below condition works.
//                else if (((ArrayList<?>) object).size() > 0 && ((ArrayList<?>) object).get(0) instanceof Book) {
//                    fileOutputStream = new FileOutputStream(pathOfBooksFile);
//                    // below condition works.
//                } else if (((ArrayList<?>) object).size() > 0 && ((ArrayList<?>) object).get(0) instanceof Map<?, ?>) {
//                    if (((Map<?, ?>) ((ArrayList<?>) object).get(0)).containsKey("borrowerStudent"))
//                        fileOutputStream = new FileOutputStream(pathOfIssuedBooksFile);
//                    //below condition works.
//                    else
//                        fileOutputStream = new FileOutputStream(pathOfReturnedBooksFile);
//                }
//            }
//            //below code snippet also works.
//            if(fileOutputStream != null) {
//                objectOutputStream = new ObjectOutputStream(fileOutputStream);
//                objectOutputStream.writeObject(object);
//                fileOutputStream.close();
//                objectOutputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("file not found.");
//            return false;
//        } catch (IOException e) {
//            System.out.println("initializing the stream failed.");
//            return false;
//        }
//        return true;
//    }
//}
