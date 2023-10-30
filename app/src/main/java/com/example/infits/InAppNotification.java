package com.example.infits;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InAppNotification extends AppCompatActivity {

    TextView noNotifications;
    RecyclerView notificationRV;
  //  String inAppUrl = String.format("%sgetInAppNotifications.php", DataFromDatabase.ipConfig);
  String inAppUrl = String.format("http://localhost/myproject/infits/getInAppNotifications.php.php", DataFromDatabase.ipConfig);

    Date date = new Date();

    ArrayList<InAppNotificationData> inAppData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_notification);
        noNotifications = findViewById(R.id.no_notifications);
        notificationRV = findViewById(R.id.in_app_notification_rv);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(InAppNotification.this, DashBoardMain.class);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        getInAppNotifications();
    }

    private void getInAppNotifications() {
        Log.d("Notif","Hel");
        StringRequest inAppRequest = new StringRequest(
                Request.Method.POST,
                inAppUrl,
                response -> {
                    try {
                        //Log.d("Notif",response);
                        JSONObject object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("inApp");

                        if(array.length() != 0) {
                            noNotifications.setVisibility(View.GONE);
                            notificationRV.setVisibility(View.VISIBLE);
                        } else {
                            noNotifications.setVisibility(View.VISIBLE);
                            notificationRV.setVisibility(View.GONE);
                        }

                        for(int i = 0; i < array.length(); i++) {
                            JSONObject currObject = array.getJSONObject(i);
                            Log.d("Notif",String.valueOf(currObject));
                            String type = currObject.getString("type");
                            Log.d("Notif",type);
                            String date = currObject.getString("date");
                            Log.d("Notif",date);
                            String[] parts = date.split(" ");
                            String datePart = parts[0]; // "2023-08-26"
                            String timePart = parts[1]; // "11:51:21"
                            String time = timePart;
                            Log.d("Notif",time);
                            String text = currObject.getString("text");
                            Log.d("Notif",text);

                            Log.i("Data from server", "type:"+type+" date:"+date+" time:"+time+" text:"+text);

                            InAppNotificationData inAppNotificationData = new InAppNotificationData(type, date, time, text);

                            Log.d("Notif",text+" "+type+" "+datePart+" "+time);
                            InAppNotificationData inAppNotificationData = new InAppNotificationData(type, datePart, time, text);
                            inAppData.add(inAppNotificationData);

                        }

                        InAppNotificationAdapter inAppNotificationAdapter = new InAppNotificationAdapter(inAppData, this);

                        notificationRV.setAdapter(inAppNotificationAdapter);
                        notificationRV.setLayoutManager(new LinearLayoutManager(this));
                        inAppNotificationAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> logError(error)
        ) {

            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                data.put("clientID", DataFromDatabase.clientuserID);
                data.put("dateandtime", String.valueOf(date));

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                sdf.format(date);
                Map<String, String> data = new HashMap<>();
                data.put("clientuserID", DataFromDatabase.clientuserID);
                data.put("date",String.valueOf(date));

 test-branch
                return data;
            }
        };
        Volley.newRequestQueue(this).add(inAppRequest);
    }

    private void logError(VolleyError error) {
        Log.e("InAppNotifications", error.toString());
    }
}