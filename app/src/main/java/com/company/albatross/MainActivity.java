package com.company.albatross;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    ScheduleFragment scheduleFragment;
    MypageFragment mypageFragment;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String userIdToken;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        userIdToken=currentUser.getUid();
        homeFragment = new HomeFragment();
        scheduleFragment = new ScheduleFragment();
        mypageFragment = new MypageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, scheduleFragment).commit();
                        return true;
                    case R.id.mypage:
                        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        Log.i("userIdToken", userIdToken);
                        DatabaseReference userAccountRef = mDatabase.child("UserAccount").child(userIdToken);
                        userAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                HashMap<String, String> userData=(HashMap<String, String>)snapshot.getValue();
                                Log.i("userData", String.valueOf(userData));
                                FragmentTransaction mypageFT=getSupportFragmentManager().beginTransaction();
                                MypageFragment mFragment=MypageFragment.newInstance(userIdToken, userData);
                                mypageFT.replace(R.id.containers, mFragment).commit();

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Error: " + databaseError.getMessage());
                            }
                        });
                        return true;
                }
                return false;
            }
        });

    }
}
