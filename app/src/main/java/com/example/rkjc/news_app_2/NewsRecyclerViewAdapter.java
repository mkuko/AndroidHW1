package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    ArrayList<NewsItem> mNewsItems;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> newsItems){
        this.mContext = context;
        this.mNewsItems = newsItems;
    }

    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView date;

        public NewsViewHolder(View newItemView) {
            super(newItemView);
            title = (TextView) newItemView.findViewById(R.id.title);
            description = (TextView) newItemView.findViewById(R.id.description);
            date = (TextView) newItemView.findViewById(R.id.date);
        }

        void bind(final int listIndex) {
            title.setText("Title: " + mNewsItems.get(listIndex).getTitle());
            description.setText("Description: " + mNewsItems.get(listIndex).getDescription());
            date.setText("Date: " + mNewsItems.get(listIndex).getDate());
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
