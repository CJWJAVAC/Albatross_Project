package com.company.albatross;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LoadingActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private ArrayList<ArrayList<String>> options=new ArrayList<ArrayList<String>>();
    private int OPTIONSIZE=-1;
    private int option_idx=0;
    private ArrayList<HashMap<String, String>> possibleIdHashMaps=new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        ArrayList<ArrayList<HashMap<String, String>>> finalIds= new ArrayList<>();
        getDataFromMainActivity(option_idx, finalIds);
    }


    private void getDataFromMainActivity(int i, ArrayList finalIds){
        Intent intent=getIntent();
        options=(ArrayList<ArrayList<String>>) intent.getSerializableExtra("jobOptions");
        OPTIONSIZE=options.size();
        getId(options,i, finalIds);
    }

    private void getId(ArrayList<ArrayList<String>> options, int i, ArrayList finalIds){
        getJobFromFirebase(options.get(i).get(0),options.get(i).get(1),options.get(i).get(2),options.get(i).get(3),options.get(i).get(4), options.get(i).get(5), finalIds);
    }

    private void getJobFromFirebase(String job, String day, String st, String et, String region, String wage, ArrayList finalIds){
        ArrayList<String> IdFromJobNodeArrayList=new ArrayList<String>();
        mDatabase.child("Job").child(job).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    Log.d("firebase", String.valueOf(task.getResult().getValue().getClass())); // HashMap
                    HashMap<String, String> jobHashMap= (HashMap<String, String>) task.getResult().getValue();
                    Log.i("sunday",String.valueOf(jobHashMap));
                    Iterator<String> keys=jobHashMap.keySet().iterator();
                    while(keys.hasNext()){
                        IdFromJobNodeArrayList.add(jobHashMap.get(keys.next()));
                    }
                    Log.i("idsFromJobNode",String.valueOf(IdFromJobNodeArrayList));
                    getDayFromFirebase(IdFromJobNodeArrayList, day, st, et, region, wage, finalIds);
                }
            }
        });
    }

    private void getDayFromFirebase(ArrayList<String> idj, String day, String st, String et, String region, String wage, ArrayList finalIds){
        ArrayList<String> IdFromDayNodeArrayList=new ArrayList<String>();
        mDatabase.child("Day").child(day).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    Log.d("firebase", String.valueOf(task.getResult().getValue().getClass())); // HashMap
                    HashMap<String, String> dayHashMap= (HashMap<String, String>) task.getResult().getValue();
                    Iterator<String> keys=dayHashMap.keySet().iterator();
                    while(keys.hasNext()){
                        IdFromDayNodeArrayList.add(dayHashMap.get(keys.next()));
                    }
                    Log.i("idsFromDayNode",String.valueOf(IdFromDayNodeArrayList));
                    getRegionFromFirebase(idj,IdFromDayNodeArrayList, st, et, region,wage, finalIds );
                }
            }
        });
    }

    private void getRegionFromFirebase(ArrayList<String> idj, ArrayList<String> idd, String st, String et, String region, String wage, ArrayList finalIds){
        ArrayList<String> IdFromRegionNodeArrayList=new ArrayList<String>();
        mDatabase.child("Region").child(region).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    Log.d("firebase", String.valueOf(task.getResult().getValue().getClass())); // HashMap
                    HashMap<String, String> regionHashMap= (HashMap<String, String>) task.getResult().getValue();
                    Iterator<String> keys=regionHashMap.keySet().iterator();
                    while(keys.hasNext()){
                        IdFromRegionNodeArrayList.add(regionHashMap.get(keys.next()));
                    }
                    Log.i("idsFromRegionNode",String.valueOf(IdFromRegionNodeArrayList));
                    //TODO 고쳐야함.
                    getPossibleIdHashMaps(idj, idj, st, et, idj, wage,0, finalIds); //테스트: 데이터양이 너무 없어 중복된 값이 없음...

                }
            }
        });
    }

    private void getPossibleIdHashMaps(ArrayList<String> idFromJob, ArrayList<String> idFromDay, String st, String et, ArrayList<String> idFromRegion, String wage, int idx, ArrayList finalIds) {
        ArrayList<String> tmp = new ArrayList<String>();
        ArrayList<String> selectedIds = new ArrayList<String>();
        Log.i("idFromJob in getPossibleIdHashMaps", String.valueOf(idFromJob));
        Log.i("idFromDay in getPossibleIdHashMaps", String.valueOf(idFromDay));
        for (String idj : idFromJob) {
            for (String idd : idFromDay) {
                if (idj.equals(idd)) {
                    tmp.add(idj);
                    break;
                }
            }
        }
        Log.i("tmp",String.valueOf(tmp));
        for (String s : tmp) {
            for (String idr : idFromRegion) {
                if (s.equals(idr)) {
                    selectedIds.add(idr);
                    break;
                }
            }
        }
        Log.i("selectedIds", String.valueOf(selectedIds));

        mDatabase.child("ID").child(selectedIds.get(idx)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    HashMap<String, String> idHashMap = (HashMap<String, String>) task.getResult().getValue();
                    Log.i("idHashMap", String.valueOf(idHashMap));
                    int stData = Integer.parseInt(idHashMap.get("startHour")+idHashMap.get("startMinute"));
                    int etData = Integer.parseInt(idHashMap.get("endHour")+idHashMap.get("endMinute"));
                    int wageData=Integer.parseInt(idHashMap.get("wage"));

                    if (Integer.parseInt(st)<=stData && Integer.parseInt(et)>=etData && Integer.parseInt(wage)>=wageData) {
                        ArrayList<HashMap<String, String>> tmpArray=new ArrayList<>();
                        tmpArray.add(idHashMap);
                        finalIds.add(tmpArray);
                    }
                    else{
                        Log.i("st", st);
                        Log.i("stData", String.valueOf(stData));
                        Log.i("et", et);
                        Log.i("etData", String.valueOf(etData));
                    }
                    if (idx+1<selectedIds.size())
                        getPossibleIdHashMaps(idFromJob, idFromDay, st, et, idFromRegion, wage,idx+1, finalIds);
                    else{
                        Log.i("finalIds", String.valueOf(finalIds));
                        if (option_idx+1<OPTIONSIZE) {
                            option_idx += 1;
                            getId(options, option_idx, finalIds);
                        }else{
                            Log.i("getPossibleIdHashMaps", "end");
                            setTimeTableViewInput(finalIds);
                        }
                    }
                }
            }
        });

    }

    private void setTimeTableViewInput(ArrayList finalIds){
        //TODO 같은 요일일때, 시간 체크하는 로직 추가해야함.
        ArrayList<String> timetableViewInput = new ArrayList<>();
        SharedPreferences mPref= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=mPref.edit();
        int finalIdsSize=finalIds.size();
        for(int i=0;i<finalIdsSize-1;i++){
            ArrayList<HashMap<String , String>> refArr=(ArrayList<HashMap<String , String>>)finalIds.get(i);
            HashMap<String , String> refValue=refArr.get(0);
            for (int j=i+1;j<finalIdsSize;j++){
                ArrayList<HashMap<String , String>> arr=(ArrayList<HashMap<String, String>>) finalIds.get(j);
                Log.i("arr",String.valueOf(arr));
                boolean overlap=false;
                for(int k=0;k<arr.size();k++){
                    if (((HashMap<String, String>)arr.get(k)).get("day")==refValue.get("day")){
                        overlap=true;
                        break;
                    }
                }
                if (!overlap){
                    arr.add(refValue);
                    finalIds.add(arr);
                }
            }
        }
        Log.i("really_finalIds", String.valueOf(finalIds));

        for(int i=1;i<finalIds.size();i++){
            ArrayList<HashMap<String, String>> inputs=(ArrayList<HashMap<String, String>>)finalIds.get(i);
            String input="{'sticker':[";
            int inputIndex=0;
            for (int j=0;j<inputs.size();j++){
                HashMap<String,String> values=inputs.get(j);
                input+="{'idx':"+Integer.toString(inputIndex)+",'schedule':[{";
                input+="'classTitle':'"+values.get("name")+"',";
                input+="'classPlace':'"+values.get("region")+"',";
                input+="'professorName':'',";
                input+="'day':";
                switch (values.get("day")){
                    case "mon":
                        input+="0";
                        break;
                    case "tue":
                        input+="1";
                        break;
                    case "wen":
                        input+="2";
                        break;
                    case "thu":
                        input+="3";
                        break;
                    case "fri":
                        input+="4";
                        break;
                    case "sat":
                        input+="5";
                        break;
                    case "sun":
                        input+="6";
                        break;
                    default:
                        input+="0";
                        break;
                }
                input+=",'startTime':{'hour':"+values.get("startHour")+",'minute':"+values.get("startMinute")+"},";
                input+="'endTime':{'hour':"+values.get("endHour")+",'minute':"+values.get("endMinute")+"}}]},";
                inputIndex+=1;
            }
            input=input.substring(0, input.length() - 1);
            input+="]}";
            editor.putString(Integer.toString(i-1), input);
            editor.commit();
        }
        Intent intent=new Intent(this, ShowTimeTablesActivity.class);
        intent.putExtra("timetableSize", finalIds.size() );
        startActivity(intent);
    }

//    private void getSelectedIdHashMaps(HashMap allOfIds, ArrayList sIds){
//        Log.i("zzidHashMap", String.valueOf(allOfIds));
//        for (Object id : sIds){
////            HashMap<String, String> value = allOfIds.get(id);
//            Log.i("zzgetSelectedValue",String.valueOf(allOfIds.get(id)));
//        }
//        Log.i("zz", "done");
//
//    }

}
