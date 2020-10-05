package com.example.themovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovie.R;
import com.example.themovie.adapters.CategoriesAdapter;
import com.example.themovie.model.ApiInfo;
import com.example.themovie.model.Categories;
import com.example.themovie.model.Category;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.OnCategoriesListener {

        private RecyclerView recyclerView;
        private CategoriesAdapter categoriesAdapter;
        private List<Category> listCategories;
        private RequestQueue requestQueue;
        private ConstraintLayout errorLayout;
        private Button errorButton;
        private String URL = ApiInfo.URL_BASE+"/genre/movie/list?api_key="+ ApiInfo.API_KEY+"&language="+ ApiInfo.LANGUAGE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        setActionBarTitle();

        setViews();

        errorLayout.setVisibility(View.INVISIBLE);

        listCategories = new ArrayList<>();

        getCategories();
        displayRecyclerView();

    }

    private void setViews() {
        recyclerView = findViewById(R.id.rcy_categories);

        errorLayout = findViewById(R.id.error_layout);
    }

    private void setActionBarTitle() {
        getSupportActionBar().setTitle(R.string.categoria);
    }

    private void getCategories() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Categories categories = gson.fromJson(response, Categories.class);
                listCategories.addAll(categories.getCategories());
                categoriesAdapter.notifyDataSetChanged();


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

    private void displayRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), listCategories, this);

        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onCategoriesClick(int position) {

        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("id", listCategories.get(position).getId());
        intent.putExtra("name", listCategories.get(position).getCategories());
        startActivity(intent);
    }
}