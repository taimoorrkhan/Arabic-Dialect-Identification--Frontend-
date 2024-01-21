package com.example.arabicdialectidentificationproject;

public class UserModel {

    private String fname;
    private String email;
    private String phone;

    UserModel(String fname, String email, String phone)
    {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return this.fname;
    }

    public void setName(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserModel(){}
}
