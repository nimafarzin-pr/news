package com.a.p.i.newsapp.newsapp.modelview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a.p.i.newsapp.R;
import com.a.p.i.newsapp.newsapp.modelobjective.Article;
import com.a.p.i.newsapp.newsapp.modelobjective.Source;
import com.a.p.i.newsapp.newsapp.modelweb.WebApiClient;
import com.a.p.i.newsapp.newsapp.modelweb.WebApiClientimpl;

import java.util.List;

public class NewsListActivity extends AppCompatActivity implements WebApiClientimpl.GetArticleCallBack
                                                                             , WebApiClientimpl.GetSourceeCallBack {
private WebApiClient webApiClient;
private static final String TAG = "NewsListActivity";
//because we use newslistrecyclerview each time of navigate on recyclerviewsource most decelerate field.
private RecyclerView newsrecyclerView;
NewsAdapter newsAdapter;
private ProgressBar progressBar;

@Override
protected void onCreate (Bundle savedInstanceState) {
      super.onCreate (savedInstanceState);
      setContentView (R.layout.activity_news_list);
      init ();
      setupViews ();
      getAriclefromServer ();
      getSourcesFromServer ();

}

private void init () {
      webApiClient = new WebApiClientimpl (this);
}


private void setupViews () {
      newsrecyclerView = findViewById (R.id.recycle_newslist);
      newsrecyclerView.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
      progressBar = findViewById (R.id.progres);
}

private void getAriclefromServer () {
      progressBar.setVisibility (View.VISIBLE);
      webApiClient.getArticles ("business-insider", this);

}


@Override
public void onArticlesReceived (List<Article> articles) {
      //we use log before level because want see how many article received.
      // Log.d (TAG, "onArticlesReceived() called with: articles = [" + articles + "]");

      newsAdapter = new NewsAdapter (this, articles);
      newsrecyclerView.setAdapter (newsAdapter);
      progressBar.setVisibility (View.GONE);
}

@Override
public void onGetArticlesError () {
      Toast.makeText (this, getString (R.string.newslistgetAreicleError), Toast.LENGTH_SHORT).show ();
}

private void getSourcesFromServer () {
      webApiClient.getSources (this);
}

@Override
public void onSourcesReceived (List<Source> sources) {
      //because we use this recyclerview once decelerate in hear not in field.
      RecyclerView sourcesrecyclerView = findViewById (R.id.recycle_newslist_source);
      sourcesrecyclerView.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false));
      SourceAdapter sourceAdapter = new SourceAdapter (this,sources, new SourceAdapter.Sourceviewcallback () {
            @Override
            public void onsourceitemclick (String sourceID) {
                  webApiClient.getArticles (sourceID, NewsListActivity.this);
            }
      });
      sourcesrecyclerView.setAdapter (sourceAdapter);
}

@Override
public void onGetSourcesError () {
      Toast.makeText (this, getString (R.string.newslistGetSourcesError), Toast.LENGTH_SHORT).show ();

}
}
