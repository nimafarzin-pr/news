package com.a.p.i.newsapp.newsapp.modelweb;

import android.content.Context;
import android.util.Log;

import com.a.p.i.newsapp.newsapp.modelobjective.Source;
import com.a.p.i.newsapp.newsapp.singletoon.RequestQueueContainer;
import com.a.p.i.newsapp.newsapp.modelobjective.Article;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class WebApiClientimpl implements WebApiClient {
public static final String API_KEY = "38ad6228e16f4c74b92282cfec67cae0";
private Context context;
private static final String TAG = "WebApiClientimpl";

public WebApiClientimpl (Context context) {
      this.context = context;
}


@Override
public void getArticles (String sourcesId, final GetArticleCallBack getArticleCallBack) {
      JsonObjectRequest requestArticle = new JsonObjectRequest (GET,
                "http://newsapi.org/v2/top-headlines?sources=" + sourcesId + "&apiKey=" + API_KEY, null,
                new Response.Listener<JSONObject> () {
                      @Override
                      public void onResponse (JSONObject response) {
                            Log.i (TAG, "onResponse: " + response);
                            List<Article> articleList = new ArrayList<> ();
                            try {
                                  JSONArray jsonArrayArticle = response.getJSONArray ("articles");
                                  for (int i = 0; i < jsonArrayArticle.length (); i++) {
                                        Article article = new Article ();
                                        article = parsArticleJsonObject (jsonArrayArticle.getJSONObject (i));
                                        if (article != null) {
                                              articleList.add (article);
                                        }
                                  }
                                  getArticleCallBack.onArticlesReceived (articleList);
                            } catch (JSONException e) {
                                  getArticleCallBack.onGetArticlesError ();
                            }
                      }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse (VolleyError error) {
                  /*
                  //this error code get from stackoverfollow
                  // As of f605da3 the following should work
                  NetworkResponse response = error.networkResponse;
                  if (error instanceof ServerError && response != null) {
                        try {
                              String res = new String (response.data,
                                        HttpHeaderParser.parseCharset (response.headers, "utf-8"));
                              // Now you can use any deserializer to make sense of data
                              JSONObject obj = new JSONObject (res);
                        } catch (UnsupportedEncodingException e1) {
                              // Couldn't properly decode data to string
                              e1.printStackTrace ();
                        } catch (JSONException e2) {
                              // returned data is not JSONObject?
                              e2.printStackTrace ();
                        }
                  }
                  */

                  getArticleCallBack.onGetArticlesError ();
            }
      });
      RequestQueueContainer.getRequestQueue (context).add (requestArticle);
}


@Override
public void getSources (final GetSourceeCallBack getSourceeCallBack) {
      JsonObjectRequest requestSources = new JsonObjectRequest (GET,
                "https://newsapi.org/v2/sources?apiKey=38ad6228e16f4c74b92282cfec67cae0",
                null, new Response.Listener<JSONObject> () {
            @Override
            public void onResponse (JSONObject response) {
                  try {
                        JSONArray jsonArrySurces = response.getJSONArray ("sources");
                        List<Source> sources = new ArrayList<> ();
                        for (int i = 0; i < jsonArrySurces.length (); i++) {
                              Source source = parsSourceObject (jsonArrySurces.getJSONObject (i));
                              if (source != null) {
                                    sources.add (source);
                              }

                        }
                        getSourceeCallBack.onSourcesReceived (sources);
                  } catch (JSONException e) {
                        getSourceeCallBack.onGetSourcesError ();
                  }

            }
      }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse (VolleyError error) {
                  Log.e (TAG, "onErrorResponse: "+ error.toString ());
                  getSourceeCallBack.onGetSourcesError ();

            }
      });
      RequestQueueContainer.getRequestQueue (context).add (requestSources);
}

//get one by one of Article object inside of article
private Article parsArticleJsonObject (JSONObject ArticlejsonObject) {
      Article article = new Article ();
      try {
            article.setTitle (ArticlejsonObject.getString ("title"));
            article.setAuthor (ArticlejsonObject.getString ("author"));
            article.setDescription (ArticlejsonObject.getString ("description"));
            article.setUrl (ArticlejsonObject.getString ("url"));
            article.setImageUrl (ArticlejsonObject.getString ("urlToImage"));
            article.setDate (ArticlejsonObject.getString ("publishedAt"));
            return article;
      } catch (JSONException e) {
            Log.i (TAG, "parsArticleJsonObject: " + e.toString ());
            //this return for working on get article or complete don or do not get article
            return null;
      }
}

private Source parsSourceObject (JSONObject SourceJsonObject) {
      Source source = new Source ();
      try {
            source.setId (SourceJsonObject.getString ("id"));
            source.setTitle (SourceJsonObject.getString ("name"));
            return source;
      } catch (JSONException e) {
            Log.e (TAG, "parsSourceObject: " + e.toString ());
            return null;
      }
}
}
