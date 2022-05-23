package com.example.todoappcuoiki;

import java.io.Serializable;

public class Item implements Serializable {

    private String todo, date, time, childKey;
    private boolean isCheck;

    public Item() {
    }

    public Item(String todo, String date, String time, String childKey, boolean isCheck){
        this.todo = todo;
        this.date = date;
        this.time = time;
        this.childKey = childKey;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}