package com.company.albatross;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;



public class EmployerActivity extends AppCompatActivity{


    EmpHomeFragment emphomeFragment;
    EmpAlarmFragment empalarmFragment;
    EmpMypageFragment empmypageFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_activity_list);



        emphomeFragment = new EmpHomeFragment();
        empalarmFragment = new EmpAlarmFragment();
        empmypageFragment = new EmpMypageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, emphomeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_employer_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.employer_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, emphomeFragment).commit();
                        return true;
                    case R.id.employer_alarm: getSupportFragmentManager().beginTransaction().replace(R.id.containers, empalarmFragment).commit();
                        return true;
                    case R.id.employer_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, empmypageFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}

