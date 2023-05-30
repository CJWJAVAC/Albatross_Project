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

public class ScreenSlidePageFragment extends Fragment {
    private TextView tv_text;
    private TextView wageTextView;
    private TextView timeTextView;
    private TextView periodTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private TextView educationTextView;
    private TextView eperiodTextView;
    private TextView dayTextView;
    private TextView jobTextView;
    private TextView numTextView;
    private TextView nameTextView;
    private String name;
    private String wage;
    private String startTime;
    private String endTime;
    private String region;
    private String phoneNumber;
    private String employerId;
    private String id;
    private String period;
    private String gender;
    private String age;
    private String education;
    private String eperiod;
    private String day;
    private String job;
    private String num;
    private String startMinute;
    private String endMinute;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String userIdToken;
    private DatabaseReference mDatabase;

    public ScreenSlidePageFragment(String name, String wage, String startTime, String endTime, String region, String phoneNumber, String employerId, String id, String period, String gender, String age, String education, String eperiod, String day, String job, String num, String startMinute, String endMinute){
        this.name=name;
        this.wage=wage;
        this.startTime=startTime;
        this.endTime=endTime;
        this.region=region;
        this.phoneNumber=phoneNumber;
        this.employerId=employerId;
        //this.id=String.format("%3s",id).replace(' ', '0');
        this.id=id;
        this.period=period;
        this.gender=gender;
        this.age=age;
        this.education=education;
        this.eperiod=eperiod;
        this.day=day;
        if (this.day.equals("mon"))
            this.day = "월";
        if (this.day.equals("tue"))
            this.day = "화";
        if (this.day.equals("wen"))
            this.day = "수";
        if (this.day.equals("tur"))
            this.day = "목";
        if (this.day.equals("fri"))
            this.day = "금";
        if (this.day.equals("sat"))
            this.day = "토";
        if (this.day.equals("sun"))
            this.day = "일";
        this.job=job;
        this.num=num;
        this.startMinute=startMinute;
        this.endMinute=endMinute;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        //tv_text=view.findViewById(R.id.tv_text);
        wageTextView = view.findViewById(R.id.wage_text);
        timeTextView = view.findViewById(R.id.time_text);
        periodTextView = view.findViewById(R.id.period_text);
        genderTextView = view.findViewById(R.id.gender_text);
        ageTextView = view.findViewById(R.id.age_text);
        educationTextView = view.findViewById(R.id.education_text);
        eperiodTextView = view.findViewById(R.id.eperiod_text);
        dayTextView = view.findViewById(R.id.day_text);
        jobTextView = view.findViewById(R.id.job_text);
        numTextView = view.findViewById(R.id.num_text);
        nameTextView = view.findViewById(R.id.name_text);
        String str=name+"\n"+"시급 "+wage+"원\n"+startTime+"시 ~ "+endTime+"시\n"+ "경기도 수원시 영통구 " + region+"\n"+phoneNumber;
        String time=startTime+":"+startMinute+"~"+endTime+":"+endMinute;
        //tv_text.setText(str);
        wageTextView.setText(wage);
        timeTextView.setText(time);
        periodTextView.setText(period);
        genderTextView.setText(gender);
        ageTextView.setText(age);
        educationTextView.setText(education);
        eperiodTextView.setText(eperiod);
        dayTextView.setText(day);
        jobTextView.setText(job);
        numTextView.setText(num);
        nameTextView.setText(name);
        userIdToken=currentUser.getUid();
        Log.i("name, wage, startTime, endTime, region, phoneNumber, employerId, id, userIdToken",String.valueOf(name)+" "+String.valueOf(wage)+" "+String.valueOf(startTime)+" "+String.valueOf(endTime)+" "+String.valueOf(employerId)+" "+String.valueOf(id)+" "+String.valueOf(userIdToken));

        Button mCall = (Button)view.findViewById(R.id.btn_call);

        mCall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
//                String mNum = str.substring(str.length()-13,str.length());
//                String tel = "tel:"+mNum;
                String pnum = phoneNumber;
                String tel = "tel:" + pnum;
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
                mDatabase.child("Notif").child(employerId).child(id).push().setValue(userIdToken);
            }
        });
        return view;
    }
}
