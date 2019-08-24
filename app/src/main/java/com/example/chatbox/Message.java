package com.example.chatbox;

import android.widget.Button;

import java.util.Date;

public class Message {

String name;
    String message;
    String time;
    Boolean like;
    Boolean active;

    public Message(String name, String message, String time, Boolean like, Boolean active) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.like = like;
        this.active = active;
    }

    Message(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

