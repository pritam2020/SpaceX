package com.example.info_about_country;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.utils.Utils;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RETROFIT TRANSACTION";

    List<Root> list;
    Context context;
    Boolean cached;

    public MyRecyclerViewAdapter(Context context, List<Root> list,Boolean cached) {
        this.list = list;
        this.context = context;
        this.cached=cached;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name, capital, region, subregion, popilation, borders, language;

        try {


            String info = "";
            if (list.get(position).name.equals(""))
                info = info + " NAME: " + " ";
            else
                info = info + " NAME: " + list.get(position).name;

            if (list.get(position).capital.equals(""))
                info = info + " CAPITAL: " + " ";
            else
                info = info + " CAPITAL: " + list.get(position).capital;

            if (list.get(position).region.equals(""))
                info = info + " REGION: " + " ";
            else
                info = info + " REGION: " + list.get(position).region;

            if (list.get(position).subregion.equals(""))
                info = info + " SUBREGION: " + " ";
            else
                info = info + " SUBREGION: " + list.get(position).subregion;

            if (String.valueOf(list.get(position).population).equals(""))
                info = info + " POPULATION: " + " ";
            else
                info = info + " POPULATION: " + list.get(position).population;

            if (list.get(position).borders.equals(""))
                info = info + " BORDERS: " + " ";
            else
                info = info + " BORDERS: " + list.get(position).borders;

            if (list.get(position).languages.equals(""))
                info = info + " LANGUAGES: " + " ";
            else
                info = info + " LANGUAGES: " + list.get(position).languages;

            try {
                if (!list.get(position).flag.equals(null)) {
                    if (!cached) {
                        Utilities.fetchSvg(context,list.get(position).flag,holder.imageView);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            holder.TtextView.setText(info);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ;
        TextView TtextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TtextView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
