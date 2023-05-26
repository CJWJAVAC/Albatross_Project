package com.company.albatross;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateMyDataActivity extends AppCompatActivity {
    private EditText editTextAge, editTextMilitary, editTextPhoneNumber, editTextResidence;
    private Button buttonSubmit;
    private HashMap<String, String> userData;

    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        Intent intent=getIntent();
        userData= (HashMap<String, String>) intent.getSerializableExtra("userData");

        editTextAge = findViewById(R.id.editTextAge);
        editTextMilitary = findViewById(R.id.editTextMilitary);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextResidence = findViewById(R.id.editTextResidence);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = editTextAge.getText().toString();
                String military = editTextMilitary.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String residence = editTextResidence.getText().toString();

                mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                DatabaseReference userAccountRef=mDatabase.child("UserAccount");
                userAccountRef.child(userData.get("idToken")).child("age").setValue(age);
                userAccountRef.child(userData.get("idToken")).child("military").setValue(military);
                userAccountRef.child(userData.get("idToken")).child("phoneNumber").setValue(phoneNumber);
                userAccountRef.child(userData.get("idToken")).child("residence").setValue(residence);

                String message = "정보수정 완료";
                Toast.makeText(UpdateMyDataActivity.this, message, Toast.LENGTH_SHORT).show();
                UpdateMyDataActivity.this.onBackPressed();
            }
    });
}
}
