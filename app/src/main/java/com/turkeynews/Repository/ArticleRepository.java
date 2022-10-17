package com.turkeynews.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.turkeynews.Response.ArticleResponse;
import com.turkeynews.Retrofit.ApiRequest;
import com.turkeynews.Retrofit.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private final ApiRequest request;
    public ArticleRepository() {
        request = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }
    public LiveData<ArticleResponse> getNewsByCategory(String category) {
        final MutableLiveData<ArticleResponse> data = new MutableLiveData<>();
        request.getNewsByCategory(category)
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticleResponse> call,
                                           @NonNull Response<ArticleResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        } else{
                            data.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                        data.setValue(null);

                    }
                });
        return data;
    }
}