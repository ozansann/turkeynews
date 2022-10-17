package com.turkeynews.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.turkeynews.Model.ArticleModel;

import java.util.List;

public class ArticleResponse {
    @SerializedName("articles")
    @Expose
    private List<ArticleModel> articles;

    public List<ArticleModel> getArticles() {
        return articles;
    }
}
