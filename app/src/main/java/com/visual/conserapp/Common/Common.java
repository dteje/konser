package com.visual.conserapp.Common;

import com.visual.conserapp.Model.User;

/**
 * Created by daniel on 26/04/2018.
 */

public class Common {
    private static Common instance;

    public User currentUser;

    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

    private Common(){

    }

    public static Common getInstance(){
        if(instance == null){
            instance = new Common();
        }
        return instance;
    }
}
