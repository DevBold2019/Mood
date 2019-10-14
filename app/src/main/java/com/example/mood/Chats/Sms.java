package com.example.mood.Chats;

public class Sms {
    private String _id;
    private String _address;
    private String _msg;
   /* private String _readState; //"0" for have not read sms and "1" for have read sms
    private String _folderName;*/
      private String _date;



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

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
