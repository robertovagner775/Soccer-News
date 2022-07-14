package me.dio.soccernews.dataRemote;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import me.dio.soccernews.domain.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SoccerNewsApi {


    @GET("news.json")
    Call<List<News>> getNews();

}
