package com.company.albatross;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.core.view.View;

public class DetailActivity extends AppCompatActivity {

    TextView tv_text;
    String str;
    String mNum;
    Button mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_text = findViewById(R.id.tv_text);

        Intent showDetail = getIntent();
        str = showDetail.getStringExtra("jobId");

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
    }

}