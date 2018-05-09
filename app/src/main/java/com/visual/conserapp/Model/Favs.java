package com.visual.conserapp.Model;

public class Favs {

    private String nameSandwichOfficial;
    private String nameSandwichUser;
    private String ingredients;
    private String price;

    public Favs(String nameSandwichOfficial, String nameSandwichUser, String ingredients, String price) {
        this.nameSandwichOfficial = nameSandwichOfficial;
        this.nameSandwichUser = nameSandwichUser;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getNameSandwichOfficial() {
        return nameSandwichOfficial;
    }

    public void setNameSandwichOfficial(String nameSandwichOfficial) {
        this.nameSandwichOfficial = nameSandwichOfficial;
    }

    public String getNameSandwichUser() {
        return nameSandwichUser;
    }

    public void setNameSandwichUser(String nameSandwichUser) {
        this.nameSandwichUser = nameSandwichUser;
    }

    public String getIngredientes() {
        return ingredients;
    }

    public void setIngredientes(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
