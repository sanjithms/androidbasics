package com.example.studyapp1;

public class googleuser {
    String name,email,uid,phonenumber;

    public googleuser() {
    }

    public googleuser(String name, String email, String uid, String phonenumber) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
