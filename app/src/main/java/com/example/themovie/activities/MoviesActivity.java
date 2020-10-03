package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.adapters.MoviesAdapter;
import com.example.themovie.model.ApiInfo;
import com.example.themovie.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.OnMoviesListener {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Movies> listMovies;
    private ProgressBar progressBar;
    private int initialPage = 1;
    private int finalPage;
    private RequestQueue requestQueue;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        setActionBarTitle();

        listMovies = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar_movies);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.rcy_movies);
        recyclerView.setVisibility(View.GONE);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        getMovies(id);
        displayRecyclerView();
        updateRecyclerView();

    }

    private void displayRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        moviesAdapter = new MoviesAdapter(getApplicationContext(), listMovies, this);

        recyclerView.setAdapter(moviesAdapter);

        recyclerView.setVisibility(View.VISIBLE);
    }

    private void getMovies(String id) {
        requestQueue = Volley.newRequestQueue(this);

        String  URL= ApiInfo.URL_BASE+"/discover/movie?api_key="+ ApiInfo.API_KEY+"&with_genres="+id+"&language="+ ApiInfo.LANGUAGE+"="+initialPage;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    finalPage = response.getInt("total_pages");
                       JSONArray jsonArray = response.getJSONArray("results");
                       for(int i = 0; i <jsonArray.length(); i++) {

                           JSONObject movie = jsonArray.getJSONObject(i);
                           Movies movies = new Movies();
                           movies.setMovie(movie.getString("title"));
                           movies.setImageURL("https://image.tmdb.org/t/p/w500/"+movie.getString("poster_path"));
                           movies.setId(movie.getString("id"));

                           listMovies.add(movies);

                       }

                   }
                   catch (JSONException e) {
                       e.printStackTrace();
                   }

                progressBar.setVisibility(View.GONE);
                moviesAdapter.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        requestQueue.add(request);

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