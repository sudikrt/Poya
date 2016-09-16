package com.geekyint.instaadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekyint.instaadmin.Model.RoundListModel;
import com.geekyint.instaadmin.R;

import java.util.List;

/**
 * Created by geekyint on 5/8/16.
 */
public class RoundListAdapter extends RecyclerView.Adapter<RoundListAdapter.Holder> {

        protected List<RoundListModel> list;

        public class Holder extends RecyclerView.ViewHolder {
            public TextView j_title, jdate;
            public ImageView j_img;

            public Holder(View view) {
                super(view);
                jdate = (TextView) view.findViewById(R.id.dateandtime);
                j_title = (TextView) view.findViewById(R.id.title_round);
                j_img = (ImageView) view.findViewById(R.id.imgRList);
            }
        }

        public RoundListAdapter(List<RoundListModel> list) {
            this.list = list;
        }

        @Override
        public RoundListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_round, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RoundListAdapter.Holder holder, int position) {
            RoundListModel data = list.get(position);
            int num = data.getDat() % 10;
            if (num == 0) {
                holder.j_img.setImageResource(R.drawable.zeroo);
            } else if (num == 1) {
                holder.j_img.setImageResource(R.drawable.oneu);
            } else if (num ==2) {
                holder.j_img.setImageResource(R.drawable.twoo);
            } else if (num == 3) {
                holder.j_img.setImageResource(R.drawable.threeo);
            } else if (num == 4) {
                holder.j_img.setImageResource(R.drawable.fouro);
            } else if (num == 5) {
                holder.j_img.setImageResource(R.drawable.fiveo);
            } else if (num == 6) {
                holder.j_img.setImageResource(R.drawable.sixo);
            } else if (num == 7) {
                holder.j_img.setImageResource(R.drawable.seveno);
            } else if (num == 8) {
                holder.j_img.setImageResource(R.drawable.eighto);
            } else {
                holder.j_img.setImageResource(R.drawable.nineo);
            }

            String s = data.getKey();

            int starindex = s.indexOf('*');
            int andindex  = s.indexOf('&');
            if (starindex > 0 && andindex > 0) {
                String title = s.substring(0, starindex);
                String jdate = s.substring((1 + starindex), andindex );
                jdate = jdate.replace('_', '/');
                String stime = s.substring((1 + andindex));
                stime = stime.replace('_', ':');
                jdate = jdate + "\t" + stime;
                holder.jdate.setText(jdate);
                holder.j_title.setText(title);
            } else {
                holder.jdate.setText("---");
                holder.j_title.setText(s);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
