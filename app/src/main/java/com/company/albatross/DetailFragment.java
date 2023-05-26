package com.company.albatross;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DetailFragment extends Fragment {

//    private TextView itemNameTextView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//        itemNameTextView = rootView.findViewById(R.id.item_name_textview);
//
//        // 전달받은 아이템 정보 가져오기
//        Bundle args = getArguments();
//        if (args != null) {
//            String item = args.getString("item");
//            if (item != null) {
//                // 아이템 이름을 텍스트 뷰에 설정
//                itemNameTextView.setText(item);
//            }
//        }
//
//        return rootView;
//    }
    TextView tv_text;
    String str;
    String mNum;
    Button mCall;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userIdToken;
    private TextView mItemTextView;
    private TextView itemIdTextView;
    private TextView wageTextView;
    private TextView timeTextView;
    //private TextView pnumTextView;
    private TextView periodTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private TextView educationTextView;
    private TextView eperiodTextView;
    private TextView day2TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mItemTextView = rootView.findViewById(R.id.item_textview);

        //itemIdTextView = rootView.findViewById(R.id.item_Id_textview);
        wageTextView = rootView.findViewById(R.id.wage_text);
        timeTextView = rootView.findViewById(R.id.time_text);
        periodTextView = rootView.findViewById(R.id.period_text);
        genderTextView = rootView.findViewById(R.id.gender_text);
        ageTextView = rootView.findViewById(R.id.age_text);
        educationTextView = rootView.findViewById(R.id.education_text);
        eperiodTextView = rootView.findViewById(R.id.eperiod_text);
        day2TextView = rootView.findViewById(R.id.day2_text);



        // 파이어베이스에서 데이터 가져오기
//        mDatabase = FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
//        DatabaseReference itemRef = mDatabase.child("items").child("ID");
//
//        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // 데이터를 가져와서 상세화면에 표시하는 작업을 수행합니다
//                String itemId = dataSnapshot.child("name").getValue(String.class);
//
//                TextView itemIdTextView = rootView.findViewById(R.id.item_Id_textview);
//
//                itemIdTextView.setText(itemId);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("Error: " + databaseError.getMessage());
//            }
//        });


        Intent showDetail = getActivity().getIntent();
        str = showDetail.getStringExtra("jobId");
        String employerIdToken = showDetail.getStringExtra("employerIdToken");
        userIdToken = currentUser.getUid();
        String id = showDetail.getStringExtra("id");
        String wage = showDetail.getStringExtra("wage");
        String time = showDetail.getStringExtra("time");
        String period = showDetail.getStringExtra("period");
        String gender = showDetail.getStringExtra("gender");
        String age = showDetail.getStringExtra("age");
        String education = showDetail.getStringExtra("education");
        String eperiod = showDetail.getStringExtra("eperiod");
        String day2 = showDetail.getStringExtra("day2");



        //itemIdTextView.setText(id);
        wageTextView.setText(wage);
        timeTextView.setText(time);
        periodTextView.setText(period);
        genderTextView.setText(gender);
        ageTextView.setText(age);
        educationTextView.setText(education);
        eperiodTextView.setText(eperiod);
        day2TextView.setText(day2);





        Button mCall = rootView.findViewById(R.id.btn_call);
        Button mApplication = rootView.findViewById(R.id.btn_application);

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mNum = str.substring(str.length() - 11, str.length());
//                String tel = "tel:" + mNum;
                String pnum = showDetail.getStringExtra("phoneNumber");
                String tel = "tel:" + pnum;
                Uri uri = Uri.parse(tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(callIntent);
            }
        });

        mApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                Map<String, String> insertData=new HashMap<>();
                insertData.put("jobId", id);
                insertData.put("userId", userIdToken);
                insertData.put("state", "reviewing");
                mDatabase.child("Notif").child(employerIdToken).push().setValue(insertData);
            }
        });

        // 전달된 아이템 내용 가져오기
        Bundle args = getArguments();
        if (args != null) {
            String item = args.getString("item");
            //String time = args.getString("time");
            if (item != null) {
                mItemTextView.setText(item);
                //timeTextView.setText(time);
            }
        }


        return rootView;
    }
}
