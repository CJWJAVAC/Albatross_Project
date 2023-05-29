package com.company.albatross;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.company.albatross.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity implements View.OnClickListener  {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private EditText memployerIdToken, mid, mname,
             mimage, mstartHour, mstartMinute, mendHour, mendMinute, mphoneNumber,
             mwage;

    ArrayList<String> postKey = null;
    ArrayList<String> postvalue = null;


    String id,sday, sregion, seducation, sjob, sgender, speriod, seperiod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_activity_post);

        Spinner region_spinner = findViewById(R.id.region_spinner);
        ArrayAdapter<CharSequence> regionadapter = ArrayAdapter.createFromResource(this, R.array.location_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        regionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(regionadapter);
        region_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               sregion = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner job_spinner = findViewById(R.id.job_spinner);
        ArrayAdapter<CharSequence> jobadapter = ArrayAdapter.createFromResource(this, R.array.job_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        jobadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_spinner.setAdapter(jobadapter);
        job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sjob = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner day_spinner = findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> dayadapter = ArrayAdapter.createFromResource(this, R.array.day_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_spinner.setAdapter(dayadapter);
        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sday = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner education_spinner = findViewById(R.id.education_spinner);
        ArrayAdapter<CharSequence> educationadapter = ArrayAdapter.createFromResource(this, R.array.education_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        educationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education_spinner.setAdapter(educationadapter);
        education_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seducation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner gender_spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> genderadapter = ArrayAdapter.createFromResource(this, R.array.gender_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(genderadapter);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sgender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner period_spinner = findViewById(R.id.period_spinner);
        ArrayAdapter<CharSequence> periodadapter = ArrayAdapter.createFromResource(this, R.array.period_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        periodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period_spinner.setAdapter(periodadapter);
        period_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speriod = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner eperiod_spinner = findViewById(R.id.eperiod_spinner);
        ArrayAdapter<CharSequence> eperiodadapter = ArrayAdapter.createFromResource(this, R.array.eperiod_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        eperiodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eperiod_spinner.setAdapter(eperiodadapter);
        eperiod_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seperiod = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        id = findViewById(R.id.post_contents_id).toString();
       // memployerIdToken = findViewById(R.id.post_contents_employerIdToken);
        mname = findViewById(R.id.post_contents_name);
        mid = findViewById(R.id.post_contents_id);
        mimage = findViewById(R.id.post_contents_image);
        mstartHour = findViewById(R.id.post_contents_starthour);
        mstartMinute = findViewById(R.id.post_contents_startminute);
        mendHour = findViewById(R.id.post_contents_endhour);
        mendMinute = findViewById(R.id.post_contents_endminute);
        mphoneNumber = findViewById(R.id.post_contents_phonenumber);
        mwage = findViewById(R.id.post_contents_wage);




        findViewById(R.id.post_save_button).setOnClickListener(this);
        postKey = new ArrayList<>();
        postvalue = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        Post post = new Post();
        //post.setEmployerIdToken(memployerIdToken.getText().toString());
        post.setName(mname.getText().toString());
        post.setId(mid.getText().toString());
        post.setImage(mimage.getText().toString());
        post.setStartHour(mstartHour.getText().toString());
        post.setStartMinute(mstartMinute.getText().toString());
        post.setEndHour(mendHour.getText().toString());
        post.setEndMinute(mendMinute.getText().toString());
        post.setPhoneNumber(mphoneNumber.getText().toString());
        post.setRegion(sregion);
        post.setGender(sgender);
        post.setEducation(seducation);
        post.setPeriod(speriod);
        post.setJob(sjob);
        post.setEperiod(seperiod);
        post.setDay(sday);
        post.setWage(mwage.getText().toString());
        post.setEmployerIdToken(user.getUid());

        FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference()
                .child("ID")
                .child(post.getId())
                .setValue(post);

        finish();
    }
}
