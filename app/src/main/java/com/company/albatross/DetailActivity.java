package com.company.albatross;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DetailActivity extends AppCompatActivity {

    TextView tv_text;
    String str;
    String mNum;
    Button mCall;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userIdToken;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_text = findViewById(R.id.tv_text);

        Intent showDetail = getIntent();
        str = showDetail.getStringExtra("jobId");
        String employerIdToken=showDetail.getStringExtra("employerIdToken");
        userIdToken=currentUser.getUid();
        String id=showDetail.getStringExtra("id");

        if(showDetail.hasExtra("jobId")){
            tv_text.setText(str);
        }

        Button mCall = (Button)findViewById(R.id.btn_call);

        mCall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mNum = str.substring(str.length()-11,str.length());
                String tel = "tel:"+mNum;
                Uri uri = Uri.parse(tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(callIntent);
            }
        });

        Button mApplication=(Button) findViewById(R.id.btn_application);
        mApplication.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                mDatabase.child("Notif").child(employerIdToken).child(id).push().setValue(userIdToken);
            }
        });


    }

}