package com.example.info_about_country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>


{
    ArrayList<String> list;
    Context context;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> list) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater=LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name,capital,region,subregion,popilation,borders,language;

        try {
            JSONObject jsonObject=new JSONObject(list.get(position));

            String info="";
            if(jsonObject.getString("name").equals(""))
              info = info +" NAME:"+ " " ;
            else
                info=info + " NAME:"+jsonObject.getString("name");

            if(jsonObject.getString("capital").equals(""))
                info = info + " CAPITAL:"+" " ;
            else
                info=info + " CAPITAL:"+jsonObject.getString("capital");

            if(jsonObject.getString("region").equals(""))
                info = info + " REGION:"+" " ;
            else
                info=info + " REGION:"+jsonObject.getString("region");

            if(jsonObject.getString("subregion").equals(""))
                info = info + " SUBREGION:"+" " ;
            else
                info=info + " SUBREGION:"+jsonObject.getString("subregion");

            if(jsonObject.getString("population").equals(""))
                info = info + " POPULATION:"+" " ;
            else
                info=info + " POPULATION:"+jsonObject.getString("population");

            if(jsonObject.getString("borders").equals(""))
                info = info + " BORDERS:"+" " ;
            else
                info=info + " BORDERS:"+jsonObject.getString("borders");

            if(jsonObject.getString("languages").equals(""))
                info = info + " LANGUAGES:"+" " ;
            else
                info=info + " LANGUAGES:"+jsonObject.getString("languages");
            if(!jsonObject.getString("flag").equals("")) {
                String url=jsonObject.getString("flag");
                //GlideT.justLoadImage(activity, IMAGE_URI, targetImageView)
            }


            holder.TtextView.setText(info);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {;
        TextView TtextView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TtextView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
