package me.dio.soccernews.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import me.dio.soccernews.MainActivity;
import me.dio.soccernews.R;
import me.dio.soccernews.databinding.FragmentFavoritesBinding;
import me.dio.soccernews.domain.News;
import me.dio.soccernews.ui.adapter.NewsAdapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel  favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);


        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        MainActivity activity = (MainActivity) getActivity();

        List<News> favoriteNews =  activity.getDb().newsDao().loadFavoriteNews();
        binding.recycler2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler2.setAdapter(new NewsAdapter( favoriteNews, updatedNews -> activity.getDb().newsDao().save(updatedNews)));



        return binding.getRoot();
    }
}