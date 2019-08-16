package com.example.mood;

public class inboxModel  {

    String message,reply;

    public inboxModel(String message, String reply) {

        this.message = message;
        this.reply = reply;
    }




    public String getMessage() {

        return message;
    }

    public String getReply() {

        return reply;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
