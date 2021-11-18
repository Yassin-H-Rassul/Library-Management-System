package com.LibraryManagementSystem.Controller;

public interface AdminTasks {
    boolean addNewLibrarian(String librarianUsername, String librarianPassword);
    boolean deleteLibrarian(String librarianUsername);
    void viewLibrarians();

}
