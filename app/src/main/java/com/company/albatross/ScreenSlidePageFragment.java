package com.company.albatross;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScreenSlidePageFragment extends Fragment {
    private TextView tv_text;
    private String name;
    private String wage;
    private String startTime;
    private String endTime;
    private String region;
    private String phoneNumber;
    private String employerId;
    private String id;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String userIdToken;
    private DatabaseReference mDatabase;

    public ScreenSlidePageFragment(String name, String wage, String startTime, String endTime, String region, String phoneNumber, String employerId, String id){
        this.name=name;
        this.wage=wage;
        this.startTime=startTime;
        this.endTime=endTime;
        this.region=region;
        this.phoneNumber=phoneNumber;
        this.employerId=employerId;
        this.id=String.format("%3s",id).replace(' ', '0');
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        tv_text=view.findViewById(R.id.tv_text);
        String str=name+"\n"+"시급 "+wage+"원\n"+startTime+"시 ~ "+endTime+"시\n"+ "경기도 수원시 영통구 " + region+"\n"+phoneNumber;
        tv_text.setText(str);
        userIdToken=currentUser.getUid();
        Log.i("name, wage, startTime, endTime, region, phoneNumber, employerId, id, userIdToken",String.valueOf(name)+" "+String.valueOf(wage)+" "+String.valueOf(startTime)+" "+String.valueOf(endTime)+" "+String.valueOf(employerId)+" "+String.valueOf(id)+" "+String.valueOf(userIdToken));

        Button mCall = (Button)view.findViewById(R.id.btn_call);

        mCall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String mNum = str.substring(str.length()-13,str.length());
                String tel = "tel:"+mNum;
                Uri uri = Uri.parse(tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(callIntent);
            }
        });

        Button mApplication=(Button) view.findViewById(R.id.btn_application);
        mApplication.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                Map<String, String> insertData=new HashMap<>();
                insertData.put("jobId", id);
                insertData.put("userId", userIdToken);
                insertData.put("state", "reviewing");
                mDatabase.child("Notif").child(employerId).push().setValue(insertData);
            }
        });
        return view;
    }
}
