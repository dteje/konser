package com.visual.conserapp.Model;

import java.util.ArrayList;

public class UserFavs {

    private String username;
    private ArrayList<Favs> listFavs;
    private String id;

    public UserFavs(String username, ArrayList<Favs> listFavs, String id){
        this.username = username;
        this.listFavs = listFavs;
        this.id = id;
    }
    public UserFavs(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Favs> getListFavs() {
        return listFavs;
    }

    public void setListFavs(ArrayList<Favs> listFavs) {
        this.listFavs = listFavs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
