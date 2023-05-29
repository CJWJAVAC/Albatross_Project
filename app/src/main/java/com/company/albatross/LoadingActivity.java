package com.company.albatross;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LoadingActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private ArrayList<ArrayList<String>> options=new ArrayList<ArrayList<String>>();
    private static int timetableViewInputIndex=0;
    private static int caseNum=0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Log.i("start LoadingActivity", "");
        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        Log.i("start mDatabase", "");
        Intent intent=getIntent();
        options=(ArrayList<ArrayList<String>>) intent.getSerializableExtra("jobOptions");
        Log.i("start options", String.valueOf(options));
        getDataFromFirebase(0, new HashMap<Integer, ArrayList<String>>());
    }

    private void getDataFromFirebase(int option_idx, HashMap<Integer, ArrayList<String>> results){
        if (option_idx==options.size()){
            Log.i("results", String.valueOf(results));
            SharedPreferences mPref= PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=mPref.edit();
            getAllOfCase(results, new ArrayList<>(),0, editor);
        }
        else {
            results.put(option_idx, new ArrayList<>());
            String job = options.get(option_idx).get(0);
            String day = options.get(option_idx).get(1);
            int startTime = Integer.parseInt(options.get(option_idx).get(2));
            int endTime = Integer.parseInt(options.get(option_idx).get(3));
            String region = options.get(option_idx).get(4);
            int wage = Integer.parseInt(options.get(option_idx).get(5));

            Log.i("start getDataFromFirebase", "");
            DatabaseReference dayRef = mDatabase.child("Day").child(day);
            dayRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Set<String> monIds = new HashSet<>();

                    for (DataSnapshot idSnapshot : dataSnapshot.getChildren()) {
                        String id = idSnapshot.getValue(String.class);
                        monIds.add(id);
                    }

                    // delivery 노드의 id 데이터 가져오기
                    DatabaseReference jobRef = mDatabase.child("Job").child(job);
                    jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Set<String> deliveryIds = new HashSet<>();

                            for (DataSnapshot idSnapshot : dataSnapshot.getChildren()) {
                                String id = idSnapshot.getValue(String.class);
                                deliveryIds.add(id);
                            }

                            // sun 노드의 id 데이터 가져오기
                            DatabaseReference regionRef = mDatabase.child("Region").child(region);
                            regionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Set<String> sunIds = new HashSet<>();

                                    for (DataSnapshot idSnapshot : dataSnapshot.getChildren()) {
                                        String id = idSnapshot.getValue(String.class);
                                        sunIds.add(id);
                                    }

                                    // 공통된 id 데이터 찾기
                                    Set<String> commonIds = findCommonIds(monIds, deliveryIds, sunIds);
                                    int i = 0;
                                    for (String id : commonIds) {
                                        Log.i("commonIds", id);
                                        i += 1;
                                        DatabaseReference idRef = mDatabase.child("ID").child(id);
                                        int finalI = i;
                                        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                HashMap<String, String> idValues = (HashMap<String, String>) snapshot.getValue();
                                                Log.i("idValues.get('day')", idValues.get("day"));
                                                Log.i("idValues.get('job')", idValues.get("job"));
                                                Log.i("idValues.get('region')", idValues.get("region"));
                                                if (Integer.parseInt(idValues.get("wage")) >= wage)
                                                    if (Integer.parseInt(idValues.get("startHour") + idValues.get("startMinute")) >= startTime)
                                                        if (Integer.parseInt(idValues.get("endHour") + idValues.get("endMinute")) <= endTime) {
                                                            results.get(option_idx).add(getTimetableViewInput(idValues));
                                                        }
                                                if (finalI == commonIds.size())
                                                    getDataFromFirebase(option_idx + 1, results);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("Error: " + databaseError.getMessage());
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("Error: " + databaseError.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Error: " + databaseError.getMessage());
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Error: " + databaseError.getMessage());
                }
            });
        }
    }

    // 세 집합에서 공통된 요소 찾기
    private static Set<String> findCommonIds(Set<String> set1, Set<String> set2, Set<String> set3) {
        Set<String> commonIds = new HashSet<>();

        // 첫 번째 집합과 두 번째 집합의 공통된 요소 찾기
        for (String id : set1) {
            if (set2.contains(id) && set3.contains(id)) {
                commonIds.add(id);
            }
        }
        return commonIds;
    }

    private static String getTimetableViewInput(HashMap<String, String> hashMapValue){
//        String timetableViewInput="{'sticker':[";
        String timetableViewInput="{'idx':"+Integer.toString(timetableViewInputIndex)+",'schedule':[{";
        timetableViewInput+="'classTitle':'"+hashMapValue.get("name")+"',";
        timetableViewInput+="'classPlace':'"+hashMapValue.get("region")+"',";
        timetableViewInput+="'professorName':'',";
        timetableViewInput+="'day':";
        switch (hashMapValue.get("day")){
            case "mon":
                timetableViewInput+="0";
                break;
            case "tue":
                timetableViewInput+="1";
                break;
            case "wen":
                timetableViewInput+="2";
                break;
            case "thu":
                timetableViewInput+="3";
                break;
            case "fri":
                timetableViewInput+="4";
                break;
            case "sat":
                timetableViewInput+="5";
                break;
            case "sun":
                timetableViewInput+="6";
                break;
            default:
                timetableViewInput+="0";
                break;
        }
        timetableViewInput+=",'startTime':{'hour':"+hashMapValue.get("startHour")+",'minute':"+hashMapValue.get("startMinute")+"},";
        timetableViewInput+="'endTime':{'hour':"+hashMapValue.get("endHour")+",'minute':"+hashMapValue.get("endMinute")+"}}]}";
        timetableViewInputIndex+=1;
        return timetableViewInput;
    }

    private void getAllOfCase(HashMap<Integer, ArrayList<String>> results, ArrayList<String> combination, int index, SharedPreferences.Editor editor){
        if (index == results.size()) {
            // 조합 완성됨
            Log.i("combination","{'sticker':"+String.valueOf(combination)+"}");
            editor.putString(Integer.toString(caseNum++),"{'sticker':"+String.valueOf(combination)+"}" );
            editor.commit();
            return;
        }
        List<String> currentList = results.get(index);

        for (int i = 0; i < currentList.size(); i++) {
            combination.add(currentList.get(i));
            getAllOfCase(results, combination, index + 1, editor);
            combination.remove(combination.size() - 1);
        }

        if (index==0){
            Intent intent=new Intent(this, ShowTimeTablesActivity.class);
            intent.putExtra("timetableSize", caseNum );
            startActivity(intent);
        }
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
//                        if (option_idx+1<OPTIONSIZE) {
//                            option_idx += 1;
////                            getId(options, option_idx, finalIds);
//                        }else{
//                            Log.i("getPossibleIdHashMaps", "end");
//                            setTimeTableViewInput(finalIds);
//                        }
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
    }
}
