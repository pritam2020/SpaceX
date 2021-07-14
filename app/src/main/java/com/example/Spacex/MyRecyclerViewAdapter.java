package com.example.Spacex;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RETROFIT TRANSACTION";

    List<Root> list;
    Context context;
    Boolean cached;
    WebView webView;

    public MyRecyclerViewAdapter(Context context, List<Root> list,Boolean cached,WebView webView) {
        this.list = list;
        this.context = context;
        this.cached=cached;
        this.webView=webView;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name, capital, region, subregion, popilation, borders, language;

        try {



            if (list.get(position).name.equals(""))
                holder.name.setText("");
            else
                holder.name.setText("NAME : "+list.get(position).name);

            if (list.get(position).agency.equals(""))
                holder.agency.setText("");
            else
                holder.agency.setText("AGENCY : "+list.get(position).agency);

            if (list.get(position).id.equals(""))
                holder.id.setText("");
            else
                holder.id.setText("ID : "+list.get(position).id.trim());

            if (String.valueOf(list.get(position).status).equals(""))
                holder.status.setText("");
            else {
                if (list.get(position).status.equals("active")) {
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.green));
                }
                else {
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                }
                holder.status.setText(list.get(position).status);
            }

            if (list.get(position).launches.size()==0 ) {
                holder.launches.setText("");
            }
            else{
                StringBuilder info= new StringBuilder();
                info.append("LAUNCHES : ");
                for (String launch : list.get(position).launches) {
                    info.append(launch).append(", ");
                }
                holder.launches.setText(info);
            }


            try {
                if (list.get(position).image != null) {
                    Picasso.with(context).load(list.get(position).image).into(holder.imageView);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        holder.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(list.get(position).wikipedia);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ;
        TextView name;
        TextView agency;
        TextView id;
        TextView launches;
        TextView status;
        ImageView imageView;
        CardView web;
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.imageView);
            agency=itemView.findViewById(R.id.agency);
            id=itemView.findViewById(R.id.id);
            launches=itemView.findViewById(R.id.launches);
            status=itemView.findViewById(R.id.status);
            web=itemView.findViewById(R.id.card_wikipedia);
            //webView=itemView.findViewById(R.id.webView);
        }
    }
}
