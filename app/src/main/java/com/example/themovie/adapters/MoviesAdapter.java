package com.example.themovie.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovie.R;
import com.example.themovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Movie> movies;
    private OnMoviesListener onMoviesListener;

    public MoviesAdapter(Context context, List<Movie> movies, OnMoviesListener onMoviesListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.movies = movies;
        this.onMoviesListener = onMoviesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.movie_row_layout, parent, false);
        return new ViewHolder(view, onMoviesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textMovieTitle.setText(movies.get(position).getMovie());
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movies.get(position).getImageURL()).into(holder.imgMovies);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textMovieTitle;
        ImageView imgMovies;
        OnMoviesListener onMoviesListener;

        public ViewHolder(@NonNull View itemView, OnMoviesListener onMoviesListener) {
            super(itemView);
            textMovieTitle = itemView.findViewById(R.id.txt_movie);
            imgMovies = itemView.findViewById(R.id.img_movie);
            this.onMoviesListener  = onMoviesListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMoviesListener.onMoviesClick(getAdapterPosition());
        }
    }

    public interface OnMoviesListener{
        void onMoviesClick(int position);
    }

}
