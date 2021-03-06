package me.dio.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.soccernews.R;
import me.dio.soccernews.databinding.NewsItemBinding;
import me.dio.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;
    private final favoriteListener favoriteListener;

    public NewsAdapter(List<News> news, NewsAdapter.favoriteListener favoriteListener){
        this.news = news;
        this.favoriteListener = favoriteListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater,parent, false );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.getTitle());
        holder.binding.tvDescription.setText(news.getDescription());
        Picasso.get().load(news.getImage()).fit().into(holder.binding.imageView);


        //botão que abre um link
        holder.binding.button.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLink()));
            holder.itemView.getContext().startActivity(i);
        });

        // botao compartilhar
        holder.binding.iconShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TITLE, news.getTitle());
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Share via"));
        });
        holder.binding.iconHeart.setOnClickListener(view -> {
            news.setFavorite(!news.isFavorite());
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });


         int favoriteColor = news.isFavorite() ? R.color.secundary_700 : R.color.color_gray;
         holder.binding.iconHeart.setColorFilter(context.getResources().getColor(favoriteColor));

    }
    

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;


        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
    public interface favoriteListener {
        void onFavorite(News news);
    }

}
