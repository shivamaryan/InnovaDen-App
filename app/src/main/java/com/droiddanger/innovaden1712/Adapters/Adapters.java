package com.droiddanger.innovaden1712.Adapters;

/**
 * Created by Shahin Abdulla on 5/26/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droiddanger.innovaden1712.R;
import com.droiddanger.innovaden1712.pojo.Datas;

import java.util.List;

public class Adapters extends RecyclerView.Adapter<Adapters.MyViewHolder> {

    private List<Datas> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);

        }
    }


    public Adapters(List<Datas> moviesList) {
        this.list = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Datas movie = list.get(position);
        holder.title.setText(movie.getTitle());
        holder.description.setText(movie.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}