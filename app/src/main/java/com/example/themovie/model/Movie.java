package com.example.themovie.model;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("title")
    private String movie;
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String imageURL;


    public Movie() {
    }

    public Movie(String movie, int id, String imageURL) {
        this.movie = movie;
        this.id = id;
        this.imageURL = imageURL;

    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}


