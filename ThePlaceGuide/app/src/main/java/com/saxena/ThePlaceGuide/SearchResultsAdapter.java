package com.saxena.ThePlaceGuide;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saxena.ThePlaceGuide.Pojo.Item;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dell pc on 7/6/2017.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    android.content.Context c;
    List<Item> itemArrayList;

    int adCount=0;

    public SearchResultsAdapter(android.content.Context c, List<Item> itemArrayList) {
        this.c = c;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.search_result,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Item item = itemArrayList.get(position);
        holder.textView.setText(""+item.getTitle());
        String vicinities = item.getVicinity();
        final String vicinity = vicinities.replace("<br/>",", ");
        Log.e(TAG, "onBindViewHolder: "+vicinity.indexOf("<br/>") );
        holder.detailsTextView.setText(vicinity);
        final String href = item.getHref();
        final Double distance = item.getDistance();
        final Double lat = item.getPosition().get(0);
        final Double longi = item.getPosition().get(1);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCount++;
                Intent intent  = new Intent(holder.context,DetailedActivity.class);
                intent.putExtra("KEY", href);
                intent.putExtra("ADDRESS",vicinity);
                intent.putExtra("LATITUDE", lat);
                intent.putExtra("LONGITUDE", longi);
                intent.putExtra("DISTANCE",distance);
                intent.putExtra("AD_COUNT", adCount);
                if(adCount==3){adCount=0;}
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, detailsTextView;
        LinearLayout cardView;
        android.content.Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            detailsTextView = (TextView) itemView.findViewById(R.id.searchResultDetails);
            textView = (TextView) itemView.findViewById(R.id.searchResult);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardView);
            context = itemView.getContext();
        }
    }
}
