package com.a.p.i.newsapp.newsapp.modelweb;

import com.a.p.i.newsapp.newsapp.modelobjective.Article;
import com.a.p.i.newsapp.newsapp.modelobjective.Source;

import java.util.List;

public interface WebApiClient {

void getArticles (String sourcesId, GetArticleCallBack getArticleCallBack);
void getSources (GetSourceeCallBack getSourceeCallBack);

public interface GetArticleCallBack {
      void onArticlesReceived (List<Article> articles);

      void onGetArticlesError ();

}

public interface GetSourceeCallBack {
      void onSourcesReceived (List<Source> sources);

      void onGetSourcesError ();
}

}
