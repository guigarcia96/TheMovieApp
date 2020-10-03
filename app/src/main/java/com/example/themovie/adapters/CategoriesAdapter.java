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
import com.example.themovie.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Category> categories;
    private OnCategoriesListener onCategoriesListener;

    public CategoriesAdapter(Context context, List<Category> categories, OnCategoriesListener onCategoriesListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.categories = categories;
        this.onCategoriesListener = onCategoriesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.categorie_row_layout, parent, false);
        return new ViewHolder(view, onCategoriesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.textTitle.setText(categories.get(position).getCategories());
        Picasso.get().load(R.drawable.filme).into(holder.imgCategories);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitle;
        ImageView imgCategories;
        OnCategoriesListener onCategoriesListener;

        public ViewHolder(@NonNull View itemView, OnCategoriesListener onCategoriesListener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.txt_categorie);
            imgCategories = itemView.findViewById(R.id.img_categorie);
            this.onCategoriesListener  = onCategoriesListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoriesListener.onCategoriesClick(getAdapterPosition());
        }
    }

    public interface OnCategoriesListener{
        void onCategoriesClick(int position);
    }

}
