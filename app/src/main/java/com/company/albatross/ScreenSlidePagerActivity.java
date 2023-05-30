package com.company.albatross;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;
    private ArrayList<String> ids;
    private ArrayList<String> gNames;
    private ArrayList<String> gWages;
    private ArrayList<String> gStartTimes;
    private ArrayList<String> gEndTimes;
    private ArrayList<String> gRegions;
    private ArrayList<String> gPhoneNumbers;
    private ArrayList<String> gEmployerIds;
    private ArrayList<String> gPeriod;
    private ArrayList<String> gGender;
    private ArrayList<String> gAge;
    private ArrayList<String> gEducation;
    private ArrayList<String> gEperiod;
    private ArrayList<String> gDay;
    private ArrayList<String> gJob;
    private ArrayList<String> gNum;
    private ArrayList<String> gStartMinute;
    private ArrayList<String> gEndMinute;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        Intent intent=getIntent();
        ids=(ArrayList<String>) intent.getSerializableExtra("ids");
        NUM_PAGES=ids.size();
        getDataFromFirebase(0, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    }

    private void getDataFromFirebase(int idsIdx, ArrayList<String> names, ArrayList<String> wages, ArrayList<String> startTimes, ArrayList<String> endTimes, ArrayList<String> regions, ArrayList<String> phoneNumbers, ArrayList<String> employerIds, ArrayList<String> periods, ArrayList<String> genders, ArrayList<String> ages, ArrayList<String> educations, ArrayList<String> eperiods, ArrayList<String> days, ArrayList<String> jobs, ArrayList<String> nums, ArrayList<String> startMinutes, ArrayList<String> endMinutes){
        String id= String.format("%3s",ids.get(idsIdx)).replace(' ', '0');
        Log.i("String.format", id);
        DatabaseReference idRef = mDatabase.child("ID").child(id);
        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap<String, String> idValues = (HashMap<String, String>) snapshot.getValue();
                Log.i("idValues", String.valueOf(idValues));
                names.add(idValues.get("name"));
                wages.add(idValues.get("wage"));
                startTimes.add(idValues.get("startHour"));
                endTimes.add(idValues.get("endMinute"));
                regions.add(idValues.get("region"));
                phoneNumbers.add(idValues.get("phoneNumber"));
                employerIds.add(idValues.get("employerIdToken"));
                periods.add(idValues.get("period"));
                genders.add(idValues.get("gender"));
                ages.add(idValues.get("age"));
                educations.add(idValues.get("education"));
                eperiods.add(idValues.get("eperiod"));
                days.add(idValues.get("day"));
                jobs.add(idValues.get("job"));
                nums.add(idValues.get("num"));
                startMinutes.add(idValues.get("startMinute"));
                endMinutes.add(idValues.get("endMinute"));

                if (idsIdx+1 < NUM_PAGES)
                    getDataFromFirebase(idsIdx + 1, names, wages, startTimes, endTimes, regions, phoneNumbers, employerIds, periods,genders,ages,educations,eperiods,days,jobs,nums,startMinutes,endMinutes);
                else
                    setViewPager(names, wages, startTimes, endTimes, regions, phoneNumbers, employerIds, periods,genders,ages,educations,eperiods,days,jobs,nums,startMinutes,endMinutes);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void setViewPager(ArrayList<String> names, ArrayList<String> wages, ArrayList<String> startTimes, ArrayList<String> endTimes, ArrayList<String> regions, ArrayList<String> phoneNumbers, ArrayList<String> employerIds, ArrayList<String> periods, ArrayList<String> genders, ArrayList<String> ages, ArrayList<String> educations, ArrayList<String> eperiods, ArrayList<String> days, ArrayList<String> jobs, ArrayList<String> nums, ArrayList<String> startMinutes, ArrayList<String> endMinutes){
        this.gNames=names;
        this.gWages=wages;
        this.gStartTimes=startTimes;
        this.gEndTimes=endTimes;
        this.gRegions=regions;
        this.gPhoneNumbers=phoneNumbers;
        this.gEmployerIds=employerIds;
        this.gPeriod=periods;
        this.gGender=genders;
        this.gAge=ages;
        this.gEducation=educations;
        this.gEperiod=eperiods;
        this.gDay=days;
        this.gJob=jobs;
        this.gNum=nums;
        this.gStartMinute=startMinutes;
        this.gEndMinute=endMinutes;
        viewPager = findViewById(R.id.pager);
        // Instantiate a ViewPager2 and a PagerAdapter.
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Log.i("starTime, endTime, employerId", String.valueOf(gStartTimes)+" "+String.valueOf(gEndTimes)+" "+String.valueOf(gEmployerIds));
            return new ScreenSlidePageFragment(gNames.get(position), gWages.get(position), gStartTimes.get(position), gEndTimes.get(position), gRegions.get(position), gPhoneNumbers.get(position), gEmployerIds.get(position), ids.get(position), gPeriod.get(position), gGender.get(position), gAge.get(position),gEducation.get(position),gEperiod.get(position),gDay.get(position),gJob.get(position),gNum.get(position),gStartMinute.get(position),gEndMinute.get(position));
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

