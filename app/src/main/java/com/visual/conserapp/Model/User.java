package com.visual.conserapp.Model;

/**
 * Created by daniel on 24/04/2018.
 */

public class User {
    private String Name;
    private String Password;

    private String Email;

    public User(){

    }

    public User(String Name, String Password){
        this.Name = Name;
        this.Password = Password;
    }

    public User(String Name, String Password, String Email){
        this.Name = Name;
        this.Password = Password;
        this.Email = Email;
    }


    public String getName() {        return Name;    }

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
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}


