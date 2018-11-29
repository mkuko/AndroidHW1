package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    private final LayoutInflater mInflater;
    List<NewsItem> mNewsItems;
    private NewsItemViewModel viewModel;

    public NewsRecyclerViewAdapter(Context context, NewsItemViewModel viewModel){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        if (mNewsItems != null) {
            NewsItem current = mNewsItems.get(position);
            holder.title.setText(current.getTitle());
            holder.description.setText(current.getDescription());
            holder.bind(position);
        } else {
            holder.title.setText("No Word");
        }
    }

    void setNewsItems(List<NewsItem> newsItem){
        mNewsItems = newsItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNewsItems != null)
            return mNewsItems.size();
        else return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        ImageView img;

        public NewsViewHolder(View newItemView) {
            super(newItemView);
            title = (TextView) newItemView.findViewById(R.id.title);
            description = (TextView) newItemView.findViewById(R.id.description);
            img = (ImageView) newItemView.findViewById(R.id.img);
        }

        void bind(final int listIndex) {
            title.setText(mNewsItems.get(listIndex).getTitle());
            description.setText(mNewsItems.get(listIndex).getDate() + " . " + mNewsItems.get(listIndex).getDescription());
            String urlToImage = mNewsItems.get(listIndex).getUrlToImage();
            if(urlToImage != null){
                Picasso.get()
                        .load(urlToImage)
                        .into(img);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String urlString = mNewsItems.get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            mContext.startActivity(intent);
        }
    }
}
