package com.geekyint.instaadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.geekyint.instaadmin.Adapter.RAdapter;
import com.geekyint.instaadmin.Model.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RAdapter adapter;
    private ArrayList<Data> list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> round_list = new ArrayList<>();
    private ArrayList<String> ref_list = new ArrayList<>();
    private ArrayList<String> mref_list = new ArrayList<>();
    private static final String TAG = "User List";
    private HashMap<String, Object> round_object;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "Inside on start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e (TAG, "Inside onCreate");
        setContentView(R.layout.activity_user_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        ref_list = (ArrayList<String>) intent.getSerializableExtra("ref_list");
        list = (ArrayList<Data>) intent.getSerializableExtra("user_detail");
        round_list = (ArrayList<HashMap<String , Object>>)
                                                    intent.getSerializableExtra("round_list");

        for (int index = 0; index < ref_list.size(); index++) {
            String s = ref_list.get(index);
            s = s.concat("/round_trip/");
            mref_list.add(s);
        }
        if (list.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "I Think List was empty try again",
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, "List was empty");
            return;
        }
        adapter = new RAdapter(list);

        recyclerView.addItemDecoration(new DividerItemDecoration(UserList.this,
                                            LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new
                RecycleTouchListener(getApplicationContext(),
                                    recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                round_object = round_list.get(position);
                if (round_object != null) {
                    Log.e (TAG, String.valueOf(round_object));
                    Intent intent = new Intent(UserList.this, Round.class);
                    intent.putExtra("hashMap", round_object);
                    intent.putExtra("ref_list", mref_list.get(position));
                    startActivity(intent);
                }
                else {
                    Log.e (TAG, "No items");
                    Toast.makeText(getApplicationContext(),
                            "No more round trip history here",
                            Toast.LENGTH_LONG).show();
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
        private UserList.ClickListener clickListener;

        public RecycleTouchListener (Context context,
                                     final RecyclerView recyclerView,
                                     final UserList.ClickListener clickListener) {
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
