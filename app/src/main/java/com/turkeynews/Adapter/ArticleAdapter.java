package com.turkeynews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.turkeynews.Model.ArticleModel;
import com.turkeynews.databinding.RowArticleBinding;
import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ArticleModel> articleArrayList;

    public ArticleAdapter(Context context, ArrayList<ArticleModel> articleArrayList) {
        this.context = context;
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(RowArticleBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArticleModel article = articleArrayList.get(i);
        viewHolder.binding.textViewNew.setText(article.getTitle());
        Picasso.with(context)
                .load(article.getUrlToImage())
                .into(viewHolder.binding.imageViewNew);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowArticleBinding binding;

        public ViewHolder(RowArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}