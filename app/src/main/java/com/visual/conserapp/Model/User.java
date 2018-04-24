package com.visual.conserapp.Model;

/**
 * Created by daniel on 24/04/2018.
 */

public class User {
    private String Name;
    private String Password;

    public User(){

    }

    public User(String Name, String Password){
        this.Name = Name;
        this.Password = Password;
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

}


