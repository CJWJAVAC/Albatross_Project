package com.company.albatross;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.company.albatross.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText mTitle, mContents;

    ArrayList<String> postKey = null;
    ArrayList<String> postvalue = null;




    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_activity_post);

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);

        findViewById(R.id.post_save_button).setOnClickListener(this);
        postKey = new ArrayList<>();
        postvalue = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if (mTitle.getText().toString().equals("")) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mContents.getText().toString().equals("")) {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        //날짜 포맷
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        Date time = new Date();

        String today = dateFormat.format(time);
        String order_today = dateFormat2.format(time);


        Post post = new Post();
        post.setTitle(mTitle.getText().toString());
        post.setContents(mContents.getText().toString());
        post.setDate(today);
        post.setDocumentId(uid);
        mDatabaseRef.child("post").child(uid).push().setValue(post);
        finish();
    }
}
