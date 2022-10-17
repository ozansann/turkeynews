package com.turkeynews.View;

import static com.turkeynews.Constants.AppConstants.NEWS_CATEGORIES;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.turkeynews.R;
import com.turkeynews.Adapter.ArticleAdapter;
import com.turkeynews.databinding.ActivityMainBinding;
import com.turkeynews.Model.ArticleModel;
import com.turkeynews.ViewModel.ArticleViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private LinearLayoutManager layoutManager;
    private ArrayList<ArticleModel> articleArrayList = new ArrayList<>();
    private ArticleViewModel articleViewModel;
    private ArticleAdapter movieAdapter;
    private List<ArticleModel> articleList;
    private String selectedCategory = "";
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeComponents();
        checkConnected();
        swipeRefresh();
        setOnClickListeners();
    }

    private void checkConnected(){
        if(isNetworkConnected()){
            movieAdapter = new ArticleAdapter(MainActivity.this, articleArrayList);
            binding.recyclerView.setAdapter(movieAdapter);
            articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
            getArticleList();
        } else{
            hideLoadingDialog();
            Toast.makeText(getBaseContext(),R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            binding.radioGroupCategories.setVisibility(View.GONE);
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return connectivityManager.getActiveNetwork() != null;
        } else{
            return  connectivityManager.getActiveNetworkInfo() != null;
        }
    }

    private void initializeComponents() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.rdbtnAll.setButtonDrawable(new StateListDrawable());
        binding.rdbtnScience.setButtonDrawable(new StateListDrawable());
        binding.rdbtnSports.setButtonDrawable(new StateListDrawable());
    }

    private void setOnClickListeners(){
        binding.rdbtnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCategory = NEWS_CATEGORIES[0];
                getArticleList();
            }
        });
        binding.rdbtnScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCategory = NEWS_CATEGORIES[1];
                getArticleList();
            }
        });
        binding.rdbtnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCategory = NEWS_CATEGORIES[2];
                getArticleList();
            }
        });
    }

    private void getArticleList() {
        showLoadingDialog();
        articleViewModel.getNewsByCategoryResponseLiveData(selectedCategory).observe(this, articleResponse -> {
            if (articleResponse != null  && articleResponse.getArticles() != null
                    && !articleResponse.getArticles().isEmpty()) {
                binding.radioGroupCategories.setVisibility(View.VISIBLE);
                articleList = articleResponse.getArticles();
                articleArrayList.clear();
                articleArrayList.addAll(articleList);
                movieAdapter.notifyDataSetChanged();
            } else{
                Toast.makeText(MainActivity.this,R.string.no_news_to_show, Toast.LENGTH_SHORT).show();
            }
            hideLoadingDialog();
        });
    }

    private void swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            if(isNetworkConnected()){
                getArticleList();
            }
        });
    }

    public void showLoadingDialog() {
        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_spinner);
        loadingDialog.findViewById(R.id.pb_circle).setVisibility(View.VISIBLE);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    public void hideLoadingDialog(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }
}