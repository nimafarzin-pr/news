package com.a.p.i.newsapp.newsapp.modelview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a.p.i.newsapp.R;
import com.a.p.i.newsapp.newsapp.modelobjective.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
private Context context;
private List<Article> articles = new ArrayList<> ();

public NewsAdapter (Context context, List<Article> articles) {
      this.context = context;
      this.articles = articles;
}

@NonNull
@Override
public NewsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from (context).inflate (R.layout.articles_item, parent, false);
      return new NewsViewHolder (view);
}

@Override
public void onBindViewHolder (@NonNull NewsViewHolder holder, int position) {
      holder.bindViews (articles.get (position));
}

@Override
public int getItemCount () {
      return articles.size ();
}

public class NewsViewHolder extends RecyclerView.ViewHolder {
      private ImageView imageView;
      private TextView title, description, author, date;

      public NewsViewHolder (@NonNull View itemView) {
            super (itemView);
            imageView = itemView.findViewById (R.id.iv_itemnews_image);
            title = itemView.findViewById (R.id.tv_itemnews_title);
            description = itemView.findViewById (R.id.tv_itemnews_description);
            author = itemView.findViewById (R.id.tv_itemnews_author);
            date = itemView.findViewById (R.id.tv_itemnews_date);
      }

      //if we use this view holder another most static define and get context from itemview.context
      //but if we use just inside hear we dont define static and we can get context from itemview.context or just context
      public void bindViews (Article article) {
            //  Picasso.with (context).load (Uri.parse (article.getImageUrl ())).into (imageView);
            Picasso.with (itemView.getContext ()).load (Uri.parse (article.getImageUrl ())).into (imageView);
            title.setText (article.getTitle ());
            description.setText (article.getDescription ());
            author.setText (article.getAuthor ());
            date.setText (article.getDate ());
      }
}
}
