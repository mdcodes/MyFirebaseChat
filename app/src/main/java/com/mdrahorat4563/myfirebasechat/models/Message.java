package com.mdrahorat4563.myfirebasechat.models;

/**
 * Created by Michal Drahorat on 4/28/2018.
 */

public class Message {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String user;
    private String message;
    private String timeStamp;
}
