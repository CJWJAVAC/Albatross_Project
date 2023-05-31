package com.company.albatross;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ApplicationStatusActivity extends AppCompatActivity {
    private String userIdToken;
    private ArrayList<HashMap<String, String>> accept = new ArrayList<>();
    private ArrayList<HashMap<String, String>> reviewing = new ArrayList<>();
    private ArrayList<HashMap<String, String>> refusal = new ArrayList<>();
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        Intent intent=getIntent();
        userIdToken=intent.getStringExtra("userIdToken");
        getApplicationStatus();
    }

    public void getApplicationStatus(){
        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference notifRef=mDatabase.child("Notif");
        notifRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long employerCount=0;
                long applicationCount=0;
                Log.i("snapshot.getChildrenCount()",String.valueOf(snapshot.getChildrenCount()));
                for(DataSnapshot employer: snapshot.getChildren()){
                    Log.i("employer.getChildrenCount()",String.valueOf(employer.getChildrenCount()));
                    employerCount+=1;
                    for(DataSnapshot application: employer.getChildren()){
                        applicationCount+=1;
                        String userId = application.child("userId").getValue().toString();
                        Log.i("userId",userId);
                        Log.i("userIdToken",userIdToken);

                        if (userId.equals(userIdToken)){
                            String jobId = application.child("jobId").getValue().toString();
                            String state = application.child("state").getValue().toString();
                            Log.i("jobId", jobId);
                            Log.i("state",state);
                            DatabaseReference idRef = mDatabase.child("ID").child(jobId);
                            long finalEmployerCount = employerCount;
                            long finalApplicationCount = applicationCount;
                            idRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot idsnapshot) {
                                    if(state.equals("accept"))
                                        accept.add((HashMap<String, String>) idsnapshot.getValue());
                                    else if (state.equals("reviewing")){
                                        reviewing.add((HashMap<String, String>) idsnapshot.getValue());
                                    }
                                    else if (state.equals("refusal"))
                                        refusal.add((HashMap<String, String>) idsnapshot.getValue());

                                    Log.i("finalEmployerCount",String.valueOf(finalEmployerCount));
                                    Log.i("finalApplicationCount",String.valueOf(finalApplicationCount));
                                    Log.i("snapshot.getChildrenCount()",String.valueOf(idsnapshot.getChildrenCount()));
                                    if(finalEmployerCount ==snapshot.getChildrenCount() && finalApplicationCount ==employer.getChildrenCount()){
                                        Log.i("accept",String.valueOf(accept));
                                        Log.i("reviewing",String.valueOf(reviewing));
                                        Log.i("refusal",String.valueOf(refusal));
                                        setListView();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setListView(){
        ArrayList<String> acceptList = new ArrayList<>();
        ArrayList<String> reviewingList = new ArrayList<>();
        ArrayList<String> refusalList = new ArrayList<>();
        for(HashMap<String,String> acceptElement: accept)
            acceptList.add(acceptElement.get("name")+"\n"+"시급 "+acceptElement.get("wage")+"원\n"+acceptElement.get("startHour")+"시 ~ "+acceptElement.get("endHour")+"시\n"+ "경기도 수원시 영통구 " + acceptElement.get("region")+"\n"+acceptElement.get("phoneNumber"));
        for(HashMap<String,String> reviewingElement: reviewing)
            reviewingList.add(reviewingElement.get("name")+"\n"+"시급 "+reviewingElement.get("wage")+"원\n"+reviewingElement.get("startHour")+"시 ~ "+reviewingElement.get("endHour")+"시\n"+ "경기도 수원시 영통구 " + reviewingElement.get("region")+"\n"+reviewingElement.get("phoneNumber"));
        for(HashMap<String,String> refusalElement: refusal)
            refusalList.add(refusalElement.get("name")+"\n"+"시급 "+refusalElement.get("wage")+"원\n"+refusalElement.get("startHour")+"시 ~ "+refusalElement.get("endHour")+"시\n"+ "경기도 수원시 영통구 " + refusalElement.get("region")+"\n"+refusalElement.get("phoneNumber"));

        // accept 리스트뷰 설정
        ListView acceptListView = findViewById(R.id.acceptListView);
        ArrayAdapter<String> acceptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, acceptList);
        acceptListView.setAdapter(acceptAdapter);

        // reviewing 리스트뷰 설정
        ListView reviewingListView = findViewById(R.id.reviewingListView);
        ArrayAdapter<String> reviewingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviewingList);
        reviewingListView.setAdapter(reviewingAdapter);

        // refusal 리스트뷰 설정
        ListView refusalListView = findViewById(R.id.refusalListView);
        ArrayAdapter<String> refusalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, refusalList);
        refusalListView.setAdapter(refusalAdapter);


    }
}
