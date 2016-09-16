package com.geekyint.instaadmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.geekyint.instaadmin.Adapter.RoundListAdapter;
import com.geekyint.instaadmin.Model.RoundListModel;
import com.geekyint.instaadmin.Model.Round_obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Round extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RoundListAdapter mRoundListAdapter;
    private static final String TAG = "Round";

    private String ref_list;
    private ArrayList<String> mref_list = new ArrayList<>();

    private static HashMap<String, Object> round_object;
    private List<Round_obj> list = new ArrayList<>();
    private List<RoundListModel> mStringList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_round);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        round_object = (HashMap<String, Object>) intent.getSerializableExtra("hashMap");
        ref_list = intent.getStringExtra("ref_list");

        if (round_object.isEmpty() || round_object == null) {
            Toast.makeText(getApplicationContext(),
                            "No more Round trip history",
                            Toast.LENGTH_LONG).show();
            return;
        }
        int l = 1;
        for (String key : round_object.keySet()) {
            String s = ref_list;
            list.add(new Round_obj(key, round_object.get(key)));
            mStringList.add(new RoundListModel(key, l));
            s = s + key;
            mref_list.add(s);
            l++;
        }

        for (int i = 0; i < mref_list.size(); i++) {
            Log.e(TAG, mref_list.get(i));
        }

        mRoundListAdapter = new RoundListAdapter(mStringList);
        mRecyclerView.setAdapter(mRoundListAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Round.this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecycleTouchListener(
                getApplicationContext(),
                mRecyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        HashMap<String, Object> objectMap = new HashMap<String, Object>();

                        HashMap<String, Object> map = (HashMap<String, Object>)
                                            list.get(position).getObject();

                        if (map != null) {
                            List<String> mStringList = new ArrayList<String>(map.keySet());
                            ArrayList<Object> latLong = new ArrayList<Object>();
                            if (!mStringList.isEmpty()) {
                                for (String key : mStringList) {
                                    latLong.add(map.get(key));
                                }
                                Intent mapIntent = new Intent(Round.this, RoundMap.class);
                                mapIntent.putExtra("mapData", latLong);
                                startActivity(mapIntent);
                            }
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));

    }

    public interface ClickListener {
        void onClick (View view, int position);
        void onLongClick (View view, int position);
    }

    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecycleTouchListener (Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            //return super.onSingleTapUp(e);
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(e.getX(),
                                    e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
