package com.LibraryManagementSystem.Model;

import java.io.Serializable;

public class Book implements Serializable {
    private int bookID;
    private String bookName;
    private String bookAuthor;
    private int bookQuantity;

    public Book(int bookID, String bookName, String bookAuthor, int bookQuantity) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookQuantity = bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public int getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                "\n, bookAuthor='" + bookAuthor + '\'' +
                ", bookQuantity=" + bookQuantity +
                '}';
    }
}
