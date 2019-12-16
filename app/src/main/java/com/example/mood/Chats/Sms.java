package com.example.mood.Chats;

public class Sms {
    private String _id;
    private String _address;
    private String _msg;
    private String thread;
   /* private String _readState; //"0" for have not read sms and "1" for have read sms
    private String _folderName;*/
      private String _date;
      private String _senderName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_msg() {
        return _msg;
    }

    public void set_msg(String _msg) {
        this._msg = _msg;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_senderName() {
        return _senderName;
    }

    public void set_senderName(String _senderName) {
        this._senderName = _senderName;
    }
}
