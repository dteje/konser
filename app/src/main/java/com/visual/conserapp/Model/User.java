package com.visual.conserapp.Model;

/**
 * Created by daniel on 24/04/2018.
 */

public class User {
    private String Name;
    private String Password;
    private String Email;
    private Boolean Admin;

    public User() {

    }

    public User(String Name, String Password, String Email, Boolean Admin) {
        this.Name = Name;
        this.Password = Password;
        this.Email = Email;
        this.Admin = Admin;
    }

    public User(String Name, String Password, String Email) {
        this.Admin = false;
        this.Name = Name;
        this.Password = Password;
        this.Email = Email;
    }


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Boolean getAdmin() {
        return Admin;
    }

    public void setAdmin(Boolean admin) {
        Admin = admin;
    }
}


