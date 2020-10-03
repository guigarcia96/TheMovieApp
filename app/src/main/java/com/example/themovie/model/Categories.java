package com.example.themovie.model;

public class Categories {

    private String categories, id;


    public Categories(){}

    public Categories(String categories, String id) {
        this.categories = categories;
        this.id = id;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
