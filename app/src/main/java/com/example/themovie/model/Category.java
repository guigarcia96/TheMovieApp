package com.example.themovie.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("name")
    private String categories;

    @SerializedName("id")
    private int id;


    public Category(){}

    public Category(String categories, int id) {
        this.categories = categories;
        this.id = id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
