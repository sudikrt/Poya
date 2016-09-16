package com.geekyint.instaadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekyint.instaadmin.Model.Data;
import com.geekyint.instaadmin.R;
import java.util.List;

/**
 * Created by geekyint on 1/8/16.
 */
public class RAdapter extends RecyclerView.Adapter<RAdapter.Holder> {

    protected List<Data> list;
    public class Holder extends RecyclerView.ViewHolder {
        public TextView j_title, j_gner;
        public ImageView j_img;
        public Holder (View view) {
            super(view);
            j_gner = (TextView) view.findViewById(R.id.gner);
            j_title = (TextView) view.findViewById(R.id.title);
            j_img = (ImageView) view.findViewById(R.id.imglist);
        }
    }

    public RAdapter (List<Data> list) {
        this.list = list;
    }
    @Override
    public RAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RAdapter.Holder holder, int position) {
        Data data = list.get(position);
        holder.j_title.setText(data.getTitle());
        holder.j_gner.setText(data.getGner());


        int num = data.getDat() % 10;
        if (num == 0) {
            holder.j_img.setImageResource(R.drawable.zero);
        } else if (num == 1) {
            holder.j_img.setImageResource(R.drawable.one);
        } else if (num ==2) {
            holder.j_img.setImageResource(R.drawable.two);
        } else if (num == 3) {
            holder.j_img.setImageResource(R.drawable.three);
        } else if (num == 4) {
            holder.j_img.setImageResource(R.drawable.four);
        } else if (num == 5) {
            holder.j_img.setImageResource(R.drawable.five);
        } else if (num == 6) {
            holder.j_img.setImageResource(R.drawable.six);
        } else if (num == 7) {
            holder.j_img.setImageResource(R.drawable.seven);
        } else if (num == 8) {
            holder.j_img.setImageResource(R.drawable.eight);
        } else {
            holder.j_img.setImageResource(R.drawable.nine);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
