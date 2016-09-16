package com.geekyint.instaadmin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.geekyint.instaadmin.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Panel extends AppCompatActivity {

    private ImageButton btn_viewVehicles;
    private ImageButton btn_viewJourney;


    private AlertDialog mAlertDialog;

    private static final String TAG = "Panel";

    private ArrayList<Data> list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> round_list = new ArrayList<>();
    private ArrayList<String> ref_list = new ArrayList<>();

    private static FirebaseDatabase database;

    private static int data = 0;
    private void putData () {
        Log.e (TAG, "Entering to putData()");
        if (!list.isEmpty()) {
            list.clear();
        }
        if (!ref_list.isEmpty()) {
            ref_list.clear();
        }
        if (!round_list.isEmpty()) {
            round_list.clear();
        }
        database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e (TAG, "Entering to onDataChange()");
                if (dataSnapshot != null) {
                    int i = 0;
                    for (DataSnapshot temp : dataSnapshot.getChildren()) {
                        ref_list.add("users/" + temp.getKey());

                        HashMap<String, Object> vehicle = (HashMap <String, Object>) temp.getValue();
                        HashMap<String, Object> user_det = (HashMap<String, Object>) vehicle.get("userInfo");
                        HashMap<String, Object> round_trip = (HashMap<String, Object>) vehicle.get("round_trip");

                        if (round_trip != null) {
                            round_list.add(round_trip);
                            i++;
                            list.add(new Data(String.valueOf(user_det.get("name").toString()),
                                    String.valueOf(user_det.get("email").toString()), i));
                        }
                    }
                    data = 1;
                }
                Log.e (TAG, "Exiting from onDataChange()");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                data = 2;
                Log.e (TAG, "Error in retrieving data" + String.valueOf(databaseError.toString()));
            }
        });
        Log.e (TAG, "Exiting from putData()");
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        btn_viewVehicles = (ImageButton) findViewById(R.id.btn_viewVehicles);
        btn_viewJourney = (ImageButton) findViewById(R.id.btn_viewJourney);

        btn_viewVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Panel.this, MapsActivity.class));
            }
        });

        btn_viewJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog = new ProgressDialog(Panel.this, ProgressDialog.STYLE_SPINNER);
                mAlertDialog.setMessage("Fetching the data");
                mAlertDialog.show();
                /*progress = ProgressDialog.show(Panel.this, "dialog title",
                        "dialog message", true);
                */
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        putData();
                        for (;;) {
                            if (data == 2 || data == 1) {
                                break;
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                if (data == 1) {
                                    data = 0;
                                    //progress.dismiss();
                                    mAlertDialog.dismiss();
                                    if (list != null) {
                                        Intent intent = new Intent(Panel.this, UserList.class);
                                        intent.putExtra("ref_list", ref_list);
                                        intent.putExtra("user_detail", list);
                                        intent.putExtra("round_list", round_list);

                                        Log.e (TAG, "UserList Activity Started");

                                        startActivity(intent);
                                    }else  {
                                        Toast.makeText(Panel.this,
                                                        "I think it was empty check later",
                                                        Toast.LENGTH_LONG).show();
                                        Log.e(TAG, "Opps not possible go ahead");
                                    }
                                } else {
                                    data = 0;
                                    mAlertDialog.dismiss();
                                    Toast.makeText(Panel.this,
                                            "Opps!! Some thing went wrong",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Panel.this);
            builder.setTitle("About");
            builder.setMessage("Poya a vehicle tracking app. Here your device is monitored " +
                    "by your administrator. It also registers your journey details which" +
                    " is viewed by your administrator. \n"
                    + "Its an open source project. If you think you have better idea, then share with us."
                    + "\nNice to c you.");
            builder.setPositiveButton("Yep i Got it !!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            Dialog alDialog = builder.create();
            alDialog.setCanceledOnTouchOutside(false);
            alDialog.show();
            return true;
        }
        if (id == R.id.action_exit) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                auth.signOut();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
