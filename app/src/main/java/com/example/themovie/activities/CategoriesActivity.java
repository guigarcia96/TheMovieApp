package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.adapters.CategoriesAdapter;
import com.example.themovie.model.ApiInfo;
import com.example.themovie.model.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.OnCategoriesListener {

        private RecyclerView recyclerView;
        private CategoriesAdapter categoriesAdapter;
        private List<Categories> listCategories;
        private RequestQueue requestQueue;
        private String URL = ApiInfo.URL_BASE+"/genre/movie/list?api_key="+ ApiInfo.API_KEY+"&language="+ ApiInfo.LANGUAGE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBarTitle();
        recyclerView = findViewById(R.id.rcy_categories);
        listCategories = new ArrayList<>();

        getCategories();
        displayRecyclerView();

    }

    private void setActionBarTitle() {
        getSupportActionBar().setTitle(R.string.categoria);
    }

    private void getCategories() {
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("genres");
                    for(int i = 0; i <jsonArray.length(); i++) {
                        JSONObject categorie = jsonArray.getJSONObject(i);
                        Categories categorieMovie = new Categories();
                        categorieMovie.setCategories(categorie.getString("name").toString());
                        categorieMovie.setId(categorie.getString("id").toString());

                        listCategories.add(categorieMovie);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                categoriesAdapter.notifyDataSetChanged();


            }





        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);


    }

    private void displayRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), listCategories, this);

        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onCategoriesClick(int position) {

        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("id", listCategories.get(position).getId());
        startActivity(intent);
    }
}