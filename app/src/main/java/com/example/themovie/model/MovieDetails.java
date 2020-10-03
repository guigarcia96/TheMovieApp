package com.example.themovie.model;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double average;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String imageURL;

    public MovieDetails(String title, double average, String overview, String imageURL) {
        this.title = title;
        this.average = average;
        this.overview = overview;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
