package com.example.noidea.model;

public class User {

    String name;
    String email;
    String password;
    String address;
    String id;


    public User() {
    }

    public User(String name , String email , String password , String address , String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.id = id;

    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {
        this.address = address;
    }

}
