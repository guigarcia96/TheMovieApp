package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.model.ApiInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsMovieActivity extends AppCompatActivity {

    private TextView txt_details_title, txt_details_description, txt_details_average;
    private ImageView imgDetails;
    private RequestQueue requestQueue;
    private String id;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        id = intent.getStringExtra("id_movie");

        setViews();

        progressBar.setVisibility(View.VISIBLE);

        //seta para voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMovieDetails(id);

    }

    private void setViews() {
        txt_details_title = findViewById(R.id.txt_details);
        txt_details_description = findViewById(R.id.txt_details_description);
        txt_details_average = findViewById(R.id.txt_details_average);
        imgDetails = findViewById(R.id.img_details);
        progressBar = findViewById(R.id.progressbar_details);
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


    private void getMovieDetails(String id) {
        requestQueue = Volley.newRequestQueue(this);

        final String URL = ApiInfo.URL_BASE+"/movie/" + id + "?api_key="+ ApiInfo.API_KEY+"&language="+ ApiInfo.LANGUAGE;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    displayData(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   error.printStackTrace();
                }
            });
            requestQueue.add(jsonObjectRequest);

    }
    private void setActionBarTitle(JSONObject response) throws JSONException {

        getSupportActionBar().setTitle(response.getString("title"));
    }


    private void displayData(JSONObject response) {

        try {

            txt_details_title.setText(response.getString("title"));

            if(response.getString("overview").equals("")) {
                txt_details_description.setText("Nenhuma Descrição disponivel");
            } else {
                txt_details_description.setText(response.getString("overview"));
            }

            txt_details_average.setText(response.getString("vote_average").toString());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+response.getString("poster_path")).into(imgDetails);

            txt_details_description.setMovementMethod(new ScrollingMovementMethod());

            progressBar.setVisibility(View.GONE);
            setActionBarTitle(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}


