package com.example.mood;

public class ContactsModal {

    String name,number;
    int pic;


    public ContactsModal(String name, String number, int pic) {
        this.name = name;
        this.number = number;
        this.pic = pic;
    }


    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getPic() {
        return pic;
    }
}
