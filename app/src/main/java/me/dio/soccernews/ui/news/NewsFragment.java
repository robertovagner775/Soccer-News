package me.dio.soccernews.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import me.dio.soccernews.MainActivity;
import me.dio.soccernews.data.SoccerNewsRepository;
import me.dio.soccernews.data.remote.SoccerNewsApi;
import me.dio.soccernews.databinding.FragmentNewsBinding;
import me.dio.soccernews.domain.News;
import me.dio.soccernews.ui.adapter.NewsAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private FragmentNewsBinding binding;
    private SoccerNewsApi api;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        find();


        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state){
                case DONE:
                    binding.srlnews.setRefreshing(false);
                    break;
                case DOING:
                    binding.srlnews.setRefreshing(true);
                    break;
                case ERROR:
                    binding.srlnews.setRefreshing(false);
                    Snackbar.make(binding.srlnews, "Network Error", Snackbar.LENGTH_SHORT).show();

            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://robertovagner775.github.io/soccer-news-api/").addConverterFactory( GsonConverterFactory.create()).build();
        SoccerNewsApi api = retrofit.create(SoccerNewsApi.class);
        binding.srlnews.setOnRefreshListener(() -> newsViewModel.findNews(api));

        return root;
    }

    private void find() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.recycler.setAdapter(new NewsAdapter(news, updateNews -> {
                MainActivity activity = (MainActivity) getActivity();
                activity.getDb().newsDao().save(updateNews);

            }));
        });
    }

    private void onFavorite(News updateNews) {
        newsViewModel.saveNews(updateNews);

    }
}