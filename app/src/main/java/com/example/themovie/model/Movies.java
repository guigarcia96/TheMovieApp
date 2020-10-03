package com.example.themovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movies {

    @SerializedName("results")
    private ArrayList<Movie> movies;

    @SerializedName("total_pages")
    private int finalPage;

    public Movies(ArrayList<Movie> movies, int finalPage) {
        this.movies = movies;
        this.finalPage = finalPage;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public int getFinalPage() {
        return finalPage;
    }

    public void setFinalPage(int finalPage) {
        this.finalPage = finalPage;
    }
}
