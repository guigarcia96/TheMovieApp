package com.example.themovie.model;

public class Movies {

    private String movie, id, imageURL;

    public Movies() {}

    public Movies(String movie, String id, String imageURL) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
