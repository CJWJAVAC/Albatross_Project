package com.company.albatross;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class ShowTimeTablesActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private ArrayList<String> timetables = new ArrayList<>();
    private TimetableView timetable;
    private Button saveBtn;
    private Button prevBtn;
    private Button nextBtn;
    private int idx;
    private int timetableSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        timetableSize=intent.getIntExtra("timetableSize", 0);
        init();
    }

    private void init(){
        this.context=this;
        timetable=findViewById(R.id.timetable);
        saveBtn=findViewById(R.id.save_btn);
        prevBtn=findViewById(R.id.prev_btn);
        nextBtn=findViewById(R.id.next_btn);
        initView();

    }

    private void initView(){
        saveBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        SharedPreferences mPref=PreferenceManager.getDefaultSharedPreferences(this);
        String savedData=mPref.getString(Integer.toString(idx), "");
        timetable.load(savedData);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save_btn:
                saveByPreference(timetable.createSaveData());
                break;
            case R.id.prev_btn:
                loadPrevData();
                break;
            case R.id.next_btn:
                loadNextData();
                break;
        }

    }

    private void saveByPreference(String data){
        SharedPreferences mPref= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=mPref.edit();
        editor.putString("timetable_demo", data);
        editor.commit();
        Toast.makeText(this,"saved!", Toast.LENGTH_SHORT).show();
    }
    private void loadPrevData(){
        idx-=1;
        if (idx<0){
            idx=timetableSize-1;
        }
        SharedPreferences mPref=PreferenceManager.getDefaultSharedPreferences(this);
//        String savedData=mPref.getString("timetable_demo", "");
        String savedData=mPref.getString(Integer.toString(idx), "");
        if(savedData==null&&savedData.equals("")) {
            Toast.makeText(this,"X", Toast.LENGTH_SHORT).show();
            return;
        }
        timetable.load(savedData);
        Toast.makeText(this,"⬅", Toast.LENGTH_SHORT).show();
    }
    private void loadNextData(){
        idx+=1;
        if(idx>=timetableSize){
            idx=0;
        }
        SharedPreferences mPref=PreferenceManager.getDefaultSharedPreferences(this);
//        String savedData=mPref.getString("timetable_demo", "");
        String savedData=mPref.getString(Integer.toString(idx), "");
        if(savedData==null&&savedData.equals("")) {
            Toast.makeText(this,"X", Toast.LENGTH_SHORT).show();
            return;
        }
        timetable.load(savedData);
        Toast.makeText(this,"➡", Toast.LENGTH_SHORT).show();
    }
}
