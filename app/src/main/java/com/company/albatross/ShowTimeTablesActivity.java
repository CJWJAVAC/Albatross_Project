package com.company.albatross;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowTimeTablesActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private ArrayList<String> timetables = new ArrayList<>();
    private TimetableView timetable;
    private Button saveBtn;
    private Button prevBtn;
    private Button nextBtn;
    private Button detailBtn;
    private int idx;
    private int timetableSize;
    private JSONObject jsonSavedData;
    private ArrayList<String> ids = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        timetableSize=intent.getIntExtra("timetableSize", 0);
        try {
            init();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws JSONException {
        this.context=this;
        timetable=findViewById(R.id.timetable);
        saveBtn=findViewById(R.id.save_btn);
        prevBtn=findViewById(R.id.prev_btn);
        nextBtn=findViewById(R.id.next_btn);
        detailBtn=findViewById(R.id.detail_btn);
        initView();

    }

    private void initView() throws JSONException {
        saveBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        detailBtn.setOnClickListener(this);
        SharedPreferences mPref=PreferenceManager.getDefaultSharedPreferences(this);
        String savedData=mPref.getString(Integer.toString(idx), "");
        Log.i("savedData", savedData);
        jsonSavedData = new JSONObject(savedData);
        ids.clear();
        for (int i=0;i<jsonSavedData.getJSONArray("sticker").length();i++){
            Log.i("jsonSavedData", jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
            ids.add(jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
        }
        timetable.load(savedData);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save_btn:
                saveByPreference(timetable.createSaveData());
                break;
            case R.id.prev_btn:
                try {
                    loadPrevData();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.next_btn:
                try {
                    loadNextData();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.detail_btn:
                loadJobDetail();
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
    private void loadPrevData() throws JSONException {
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
        jsonSavedData = new JSONObject(savedData);
        ids.clear();
        for (int i=0;i<jsonSavedData.getJSONArray("sticker").length();i++){
            Log.i("jsonSavedData", jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
            ids.add(jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
        }
        timetable.load(savedData);
        Toast.makeText(this,"⬅", Toast.LENGTH_SHORT).show();
    }
    private void loadNextData() throws JSONException {
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
        jsonSavedData = new JSONObject(savedData);
        ids.clear();
        for (int i=0;i<jsonSavedData.getJSONArray("sticker").length();i++){
            Log.i("jsonSavedData", jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
            ids.add(jsonSavedData.getJSONArray("sticker").getJSONObject(i).getString("idx"));
        }
        timetable.load(savedData);
        Toast.makeText(this,"➡", Toast.LENGTH_SHORT).show();
    }

    private void loadJobDetail(){
        Log.i("loadJobDetail", "start");
        Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
        intent.putExtra("ids", ids);
        intent.putExtra("jsonSavedData", jsonSavedData.toString());
        startActivity(intent);
    }
}
