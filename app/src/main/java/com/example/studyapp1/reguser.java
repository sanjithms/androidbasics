package com.example.studyapp1;

public class reguser {
    String name,email,phonenumber,pass,uid;

    public reguser() {
    }

    public reguser(String name, String email, String phonenumber, String pass,String uid) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.pass = pass;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
