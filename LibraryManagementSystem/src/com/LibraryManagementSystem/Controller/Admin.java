package com.LibraryManagementSystem.Controller;

import java.util.ArrayList;

public class Admin implements AdminTasks{
    private String adminUsername;
    private String adminPassword;
    private ArrayList<Librarian> librarianArrayList;


    public Admin(String name, String password) {
        this.adminUsername = name;
        this.adminPassword = password;
        librarianArrayList = new ArrayList<Librarian>();
    }

    @Override
    public boolean addNewLibrarian(String librarianUsername, String librarianPassword) {
        if (!librarianUsername.isEmpty() && !librarianPassword.isEmpty()) {
            if (findLibrarian(librarianUsername) > -1) {
                System.out.println("librarian was not added, another librarian with this username is already available.");
                return false;
            } else {
                librarianArrayList.add(new Librarian(librarianUsername, librarianPassword));
                System.out.println("librarian successfully added.");
                return true;
            }
        }
        System.out.println("librarian was not added, both librarian's username and password must be specified.");
        return false;
    }

    @Override
    public boolean deleteLibrarian(String librarianUsername) {
        int elementIndex = findLibrarian(librarianUsername);
        if (elementIndex == -1) {
            System.out.println("no librarian with name: " + librarianUsername + " is available.\ndeleting librarian failed.");
            return false;
        }

        librarianArrayList.remove(elementIndex);
        System.out.println("librarian : " + librarianUsername + " was successfully removed.");
        return true;

    }


    @Override
    public void viewLibrarians() {
        int n = 0;
        System.out.println("the following librarians are available: ");
        for (var lib :
                librarianArrayList) {
            System.out.println(++n + "." + lib.getLibrarianUsername());
        }
    }

    private int findLibrarian(String librarianUsername) {
        for (int i = 0; i < librarianArrayList.size(); i++) {
            Librarian currentLibrarian = librarianArrayList.get(i);
            if (currentLibrarian.getLibrarianUsername().equals(librarianUsername)) {
                return i;
            }

        }
        return -1;
    }
}
