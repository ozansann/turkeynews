package com.turkeynews.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.turkeynews.Repository.ArticleRepository;
import com.turkeynews.Response.ArticleResponse;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository articleResponse;
    private LiveData<ArticleResponse> articleResponseLiveData;
    private String selectedCategory;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleResponse = new ArticleRepository();
        this.articleResponseLiveData = articleResponse.getNewsByCategory(selectedCategory);
    }

    public LiveData<ArticleResponse> getNewsByCategoryResponseLiveData(String category) {
        this.selectedCategory = category;
        this.articleResponseLiveData = articleResponse.getNewsByCategory(selectedCategory);
        return articleResponseLiveData;
    }
}