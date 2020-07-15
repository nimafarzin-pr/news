package com.a.p.i.newsapp.newsapp.singletoon;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//single ton
//every time in memory and keep your value for do  this command we create static instance of self like below
//
public class RequestQueueContainer {
private static RequestQueue requestQueue;

//because single ton itself call itself we most put private on constructor
private RequestQueueContainer () {
}

//so we can use without empty Of course can null if memory is empty
public static RequestQueue getRequestQueue (Context context) {
      if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue (context);
      }
      return requestQueue;
}
}
