package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.adapters.MoviesAdapter;
import com.example.themovie.model.ApiInfo;
import com.example.themovie.model.Categories;
import com.example.themovie.model.Movie;
import com.example.themovie.model.Movies;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.OnMoviesListener {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Movie> listMovies;
    private ProgressBar progressBarPage, progressBarUpdateMovies;
    private int initialPage = 1;
    private int finalPage;
    private RequestQueue requestQueue;
    private ConstraintLayout errorLayout;
    private int id;
    private Button errorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        setActionBarTitle();

        listMovies = new ArrayList<>();

        setViews();

        progressBarPage.setVisibility(View.VISIBLE);

        progressBarUpdateMovies.setVisibility(View.GONE);

        recyclerView.setVisibility(View.GONE);

        errorLayout.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        getMovies(id);
        displayRecyclerView();
        updateRecyclerView();

    }

    private void setViews() {
        progressBarPage = findViewById(R.id.progressBar_movies);
        recyclerView = findViewById(R.id.rcy_movies);
        errorLayout = findViewById(R.id.error_layout);
        progressBarUpdateMovies = findViewById(R.id.progressBar_update_movies);
    }

    private void displayRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        moviesAdapter = new MoviesAdapter(getApplicationContext(), listMovies, this);

        recyclerView.setAdapter(moviesAdapter);

        recyclerView.setVisibility(View.VISIBLE);
    }

    private void getMovies(int id) {
        requestQueue = Volley.newRequestQueue(this);

        final String  URL= ApiInfo.URL_BASE+"/discover/movie?api_key="+ ApiInfo.API_KEY+"&with_genres="+id+"&language="+ ApiInfo.LANGUAGE+"&page="+initialPage;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Movies movies = gson.fromJson(response, Movies.class);
                listMovies.addAll(movies.getMovies());
                Log.d("TAG",URL);
                finalPage = movies.getFinalPage();
                progressBarPage.setVisibility(View.GONE);
                progressBarUpdateMovies.setVisibility(View.GONE);
                moviesAdapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                setErrorPage();

            }
        });

        requestQueue.add(stringRequest);

    }
    private void setErrorPage() {

        setContentView(R.layout.error_layout);
        errorButton = findViewById(R.id.btn_error);

        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

    }
    private void setActionBarTitle() {
        getSupportActionBar().setTitle(R.string.filmes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void updateRecyclerView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    initialPage++;
                    progressBarUpdateMovies.setVisibility(View.VISIBLE);
                    if(initialPage <= finalPage) {

                        getMovies(id);

                    }

                }
            }
        });

    }





    @Override
    public void onMoviesClick(int position) {

        Intent intent = new Intent(this, DetailsMovieActivity.class);
        intent.putExtra("id_movie", listMovies.get(position).getId());


        startActivity(intent);

    }
}