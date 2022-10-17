package com.turkeynews.Retrofit;

import com.turkeynews.Response.ArticleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import static com.turkeynews.Constants.AppConstants.API_KEY;

public interface ApiRequest {
    @GET("top-headlines?country=tr"+"&apiKey="+API_KEY)
    Call<ArticleResponse> getNewsByCategory(@Query("category") String category);
}