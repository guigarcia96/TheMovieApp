package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.model.ApiInfo;
import com.example.themovie.model.MovieDetails;
import com.example.themovie.model.Movies;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsMovieActivity extends AppCompatActivity {

    private TextView txt_details_title, txt_details_description, txt_details_average;
    private ImageView imgDetails;
    private RequestQueue requestQueue;
    private int id;
    private ConstraintLayout errorLayout;
    private Button errorButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_movie", 0);

        setViews();

        progressBar.setVisibility(View.VISIBLE);

        errorLayout.setVisibility(View.INVISIBLE);

        //seta para voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMovieDetails(id);

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

    private void setViews() {
        txt_details_title = findViewById(R.id.txt_details);
        txt_details_description = findViewById(R.id.txt_details_description);
        txt_details_average = findViewById(R.id.txt_details_average);
        imgDetails = findViewById(R.id.img_details);
        progressBar = findViewById(R.id.progressbar_details);
        errorLayout = findViewById(R.id.error_layout);

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


    private void getMovieDetails(int id) {
        final String URL = ApiInfo.URL_BASE+"/movie/" + id + "?api_key="+ ApiInfo.API_KEY+"&language="+ ApiInfo.LANGUAGE;

        requestQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                MovieDetails movieDetails = gson.fromJson(response, MovieDetails.class);
                displayData(movieDetails);
                progressBar.setVisibility(View.GONE);




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
    private void setActionBarTitle(String title)  {

        getSupportActionBar().setTitle(title);
    }


    private void displayData(MovieDetails movieDetails) {

            txt_details_title.setText(movieDetails.getTitle());

            if(movieDetails.getOverview().equals("")) {
                txt_details_description.setText("Nenhuma Descrição disponivel");
            } else {
                txt_details_description.setText(movieDetails.getOverview());
            }

            txt_details_average.setText(Double.toString(movieDetails.getAverage()));
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movieDetails.getImageURL()).into(imgDetails);

            txt_details_description.setMovementMethod(new ScrollingMovementMethod());

            progressBar.setVisibility(View.GONE);
            setActionBarTitle(movieDetails.getTitle());


    }


}


