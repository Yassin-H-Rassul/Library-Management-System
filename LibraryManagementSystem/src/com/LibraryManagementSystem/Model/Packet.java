package com.LibraryManagementSystem.Model;

import java.io.Serializable;

public class Packet<T> implements Serializable {

    private int message;
    private T content;

    public Packet(int message, T content){
        this.message = message;
        this.content = content;
    }

    public int getMessage() {
        return message;
    }

    public T getContent() {
        return content;
    }
}
