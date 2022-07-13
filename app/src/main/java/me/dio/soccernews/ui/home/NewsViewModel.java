package me.dio.soccernews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URI;
import java.util.List;

import me.dio.soccernews.dataRemote.SoccerNewsApi;
import me.dio.soccernews.domain.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news  = new MutableLiveData<>();



    public NewsViewModel() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://robertovagner775.github.io/soccer-news-api/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        SoccerNewsApi api = retrofit.create(SoccerNewsApi.class);
        findNews(api);


    }

    private void findNews(SoccerNewsApi api) {
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    news.setValue(response.body());

                } else {
                    //TODO pensar numa estrategia de tratamento de erros
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}