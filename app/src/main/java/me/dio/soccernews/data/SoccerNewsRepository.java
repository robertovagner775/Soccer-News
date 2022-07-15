package me.dio.soccernews.data;

import me.dio.soccernews.data.local.AppDatabase;
import me.dio.soccernews.MainActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {

    private static final String REMOTE_API_URL = "https://robertovagner775.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";

    public MainActivity activity;

    private Retrofit remoteApi;
    private AppDatabase db;

    public Retrofit getRemoteApi() {
        return remoteApi;
    }
    public AppDatabase getDb(){
        return db;
    }

    public SoccerNewsRepository () {
            remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }



    public static SoccerNewsRepository getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        public static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }



}
