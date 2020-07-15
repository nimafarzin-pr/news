package com.a.p.i.newsapp.newsapp.modelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a.p.i.newsapp.R;
import com.a.p.i.newsapp.newsapp.modelobjective.Source;

import java.util.ArrayList;
import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHoldr> {
private Context context;
private List<Source> sources ;
private Sourceviewcallback sourceviewcallback;

public SourceAdapter (Context context, List<Source> sources ,Sourceviewcallback sourceviewcallback) {
      this.context = context;
      this.sources = sources;
      this.sourceviewcallback = sourceviewcallback;
}


@NonNull
@Override
public SourceViewHoldr onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from (context).inflate (R.layout.item_source, parent, false);
      return new SourceViewHoldr (view);
}

@Override
public void onBindViewHolder (@NonNull SourceViewHoldr holder, int position) {
holder.bindSource (sources.get (position));
}

@Override
public int getItemCount () {
      return sources.size ();
}

public class SourceViewHoldr extends RecyclerView.ViewHolder {
      private TextView sourcetextview;

      public SourceViewHoldr (@NonNull View itemView) {
            super (itemView);
            //because we have one textview just can write below code
            sourcetextview = (TextView) itemView;
      }

      public void bindSource (final Source source) {
            sourcetextview.setText (source.getTitle ());
            itemView.setOnClickListener (new View.OnClickListener () {
                  @Override
                  public void onClick (View v) {
                        sourceviewcallback.onsourceitemclick (source.getId ());
                  }
            });
      }
}

public interface Sourceviewcallback {
      void onsourceitemclick (String sourceID);
}
}
