package com.company.albatross;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference(); // region이 싱가포르여서 uri 넣어줘야함.;
    private int albaNumber = 1;

    private ArrayList<Integer> LinearLayoutId = new ArrayList<Integer>();

    private int customJobSearchLayoutId;

    private int dayRadioButtonId;

    private ArrayList<ArrayList<String>> jobOptions = new ArrayList<ArrayList<String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        initDB(); // 데이터 생성 함수
        makeJobSearchLayout();
    }

    @SuppressLint("RestrictedApi")
    private LinearLayout makeCustomJobSearchLayout(int num) {
        LinearLayout layout = new LinearLayout(this);
        //Linearlayout id 생성
        customJobSearchLayoutId = View.generateViewId();
        layout.setId(customJobSearchLayoutId);
        LinearLayoutId.add(customJobSearchLayoutId);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.round_corner);


        // 제목 추가
        TextView titleTextView = new TextView(this);
        String strAlbaNum = Integer.toString(num);
        titleTextView.setText("조건 " + strAlbaNum);
        titleTextView.setTextSize(18);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTypeface(null, Typeface.BOLD);
        layout.addView(titleTextView);

        // 구분선을 추가
        View dividerView = new View(this);
        dividerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));
        dividerView.setBackgroundColor(Color.GRAY);
        layout.addView(dividerView);


        //직종 선택 추가
        TextView jobTitleTextView = new TextView(this);
        jobTitleTextView.setText("직종");
        layout.addView(jobTitleTextView);

        Spinner jobTitleSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> jobTitleAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.job_array,
                android.R.layout.simple_spinner_item);
        jobTitleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTitleSpinner.setAdapter(jobTitleAdapter);
        layout.addView(jobTitleSpinner);
        //요일 선택 추가
        TextView dayTextView = new TextView(this);
        dayTextView.setText("요일");
        layout.addView(dayTextView);

        RadioGroup dayRadioGroup = new RadioGroup(this);
        dayRadioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dayRadioGroup.setOrientation(RadioGroup.HORIZONTAL);

        String[] days = getResources().getStringArray(R.array.day_array);
        for (String day : days) {
            RadioButton dayRadioButton = new RadioButton(this);
            dayRadioButton.setPadding(dpToPx(0),dpToPx(0),dpToPx(0),dpToPx(0));
            Log.i("dpToPx()", String.valueOf(dpToPx(4)));
            dayRadioButtonId = View.generateViewId();
            dayRadioButton.setId(dayRadioButtonId);
            dayRadioButton.setText(day);
            dayRadioButton.setTextSize(dpToPx(3));
            dayRadioGroup.addView(dayRadioButton);
        }

//        layout.addView(dayLinearLayout);
        layout.addView(dayRadioGroup);

        //시작시간 입력 추가
        TextView startTimeTextView = new TextView(this);
        startTimeTextView.setText("시작시간");
        layout.addView(startTimeTextView);

//        LinearLayout timeLinearLayout = new LinearLayout(this);
//        timeLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//        timeLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText startTimeEditText = new EditText(this);
        startTimeEditText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
        layout.addView(startTimeEditText);

        //종료시간 입력 추가
        TextView endTimeTextView = new TextView(this);
        endTimeTextView.setText("종료시간");
        layout.addView(endTimeTextView);

        EditText endTimeEditText = new EditText(this);
        endTimeEditText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
        layout.addView(endTimeEditText);

        //지역 선택 추가
        TextView locationTextView = new TextView(this);
        locationTextView.setText("지역");
        layout.addView(locationTextView);

        Spinner locationSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.location_array,
                android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        layout.addView(locationSpinner);

        //시급 선택 추가
        TextView wageTextView = new TextView(this);
        wageTextView.setText("시급");
        layout.addView(wageTextView);

        Spinner wageSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> wageAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.wage_array,
                android.R.layout.simple_spinner_item);
        wageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wageSpinner.setAdapter(wageAdapter);
        layout.addView(wageSpinner);

        return layout;
    }

    private void makeJobSearchLayout() {
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        // 제목
        TextView titleTextView = new TextView(this);
        titleTextView.setText("시간표 마법사");
        titleTextView.setTextSize(24);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTypeface(null, Typeface.BOLD);
        linearLayout.addView(titleTextView);

        // 설명
        TextView introTextView = new TextView(this);
        introTextView.setText("조건을 입력해주시면 \n모든 경우의 수를 조합해본 후\n만들어질수 있는 시간표를 알려드릴게요.");
        introTextView.setTextSize(15);
        introTextView.setGravity(Gravity.CENTER);
        introTextView.setTextColor(Color.GRAY);
        linearLayout.addView(introTextView);

        linearLayout.addView(makeCustomJobSearchLayout(albaNumber));


        Button addButton = new Button(this);
        addButton.setText("+");
        addButton.setOnClickListener(view -> {
            linearLayout.addView(makeCustomJobSearchLayout(++albaNumber));
        });
        linearLayout.addView(addButton);

        Button searchButton = new Button(this);
        searchButton.setText("검색");
        searchButton.setOnClickListener(view -> {
            // TODO: 검색 버튼 클릭 시 동작 구현
            for (int i = 0; i < LinearLayoutId.size(); i++) {
                LinearLayout lLayout = findViewById(LinearLayoutId.get(i));
                ArrayList<String> jobOption = new ArrayList<String>();
                for (int j = 0; j < lLayout.getChildCount(); j++) {
                    View childView = lLayout.getChildAt(j);
                    if (childView instanceof Spinner) {
                        Spinner spinner = (Spinner) childView;
                        jobOption.add(spinner.getSelectedItem().toString());
                    }
                    if (childView instanceof RadioGroup) {
                        RadioGroup radioGroup = (RadioGroup) childView;
                        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                        jobOption.add(radioButton.getText().toString());
                    }
                    if (childView instanceof EditText) {
                        EditText editText = (EditText) childView;
                        jobOption.add(editText.getText().toString());
                    }
                }
                jobOptions.add(jobOption);
            }

            Log.i("jobOptions", jobOptions.toString());
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtra("jobOptions", jobOptions);
            startActivity(intent);

        });
        linearLayout.addView(searchButton);

        setContentView(scrollView);
    }

    private void setSpinnerData(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResourceId,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    void initDB() {
        //모두삭제
//        mDatabase.removeValue();
        //쓰기
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","000","스타벅스","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","53","15","40","010-2337-7727","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","001","이디야커피","serving","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","39","7","23","010-9658-1053","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","002","올리브영","cafe","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","16","20","28","010-5015-9322","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","003","새마을식당","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","13","24","45","010-9646-8883","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","004","새마을식당","cooking","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","55","11","38","010-6286-8129","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","005","BBQ치킨","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","2","15","50","010-6152-1620","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","006","이디야커피","cafe","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","36","22","37","010-9124-8983","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","007","BBQ치킨","serving","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","58","24","44","010-5359-3446","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","008","스타벅스","cooking","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","5","18","55","010-0331-8989","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","009","CU편의점","serving","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","6","24","14","010-9677-1821","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","010","이디야커피","store","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","59","21","55","010-9939-1701","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","011","올리브영","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","56","22","20","010-9973-7424","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","012","새마을식당","cooking","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","36","23","18","010-9704-2627","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","013","이디야커피","store","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","55","21","43","010-9592-7770","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","014","BBQ치킨","delivery","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","53","16","52","010-1377-4155","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","015","파리바게트","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","35","15","36","010-0735-1153","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","016","GS25","cooking","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","26","20","49","010-8027-3780","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","017","이디야커피","delivery","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","30","15","20","010-1443-2741","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","018","BBQ치킨","serving","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","38","10","22","010-2432-8739","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","019","파리바게트","serving","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","12","19","27","010-1639-3023","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","020","올리브영","cafe","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","59","7","2","010-8237-8192","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","021","스타벅스","store","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","27","14","48","010-2911-0101","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","022","올리브영","delivery","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","20","24","33","010-0796-4590","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","023","올리브영","cooking","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","56","24","40","010-5472-1016","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","024","이디야커피","delivery","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","28","23","43","010-9953-7837","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","025","GS25","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","17","54","20","33","010-9146-9243","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","026","CU편의점","delivery","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","52","23","22","010-9212-2135","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","027","스타벅스","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","4","2","25","010-1429-3522","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","028","파리바게트","store","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","12","18","4","010-0659-1839","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","029","새마을식당","delivery","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","52","22","49","010-7382-9279","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","030","올리브영","cooking","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","41","10","19","010-1214-9413","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","031","올리브영","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","50","11","57","010-6939-6281","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","032","GS25","delivery","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","37","7","35","010-6452-3526","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","033","올리브영","serving","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","10","24","21","010-4113-4528","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","034","올리브영","serving","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","12","23","41","010-3516-5887","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","035","이디야커피","delivery","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","57","24","43","010-9732-6306","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","036","BBQ치킨","store","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","0","24","7","010-9392-7523","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","037","스타벅스","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","21","22","25","010-1497-6071","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","038","새마을식당","cafe","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","2","23","8","010-1595-8831","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","039","새마을식당","cooking","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","22","30","23","24","010-9892-8241","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","040","이디야커피","delivery","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","58","13","31","010-7637-3253","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","041","새마을식당","cafe","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","29","15","38","010-8453-9570","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","042","스타벅스","store","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","44","5","26","010-2057-0616","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","043","파리바게트","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","33","21","21","010-7192-5534","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","044","올리브영","cooking","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","13","20","34","010-7714-1915","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","045","새마을식당","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","9","14","57","010-0558-0499","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","046","이디야커피","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","47","11","24","010-9696-6258","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","047","파리바게트","delivery","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","7","23","44","010-4186-4128","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","048","새마을식당","delivery","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","39","20","21","010-3703-2420","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","049","스타벅스","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","46","17","3","010-2232-1214","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","050","BBQ치킨","serving","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","22","23","6","010-7018-0190","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","051","스타벅스","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","42","20","31","010-5887-6667","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","052","BBQ치킨","delivery","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","7","11","49","010-3244-0997","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","053","파리바게트","serving","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","9","14","50","010-8083-9236","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","054","GS25","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","50","14","20","010-6817-4288","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","055","파리바게트","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","58","8","47","010-0127-9120","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","056","파리바게트","cooking","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","16","2","18","8","010-2900-3700","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","057","CU편의점","serving","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","47","24","52","010-9289-9243","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","058","스타벅스","cafe","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","4","18","59","010-7868-7371","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","059","파리바게트","cafe","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","43","21","42","010-4099-5377","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","060","스타벅스","cooking","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","1","18","59","010-0568-3000","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","061","CU편의점","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","1","17","25","010-2409-3988","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","062","GS25","delivery","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","48","10","38","010-7627-4099","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","063","GS25","cafe","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","33","10","29","010-5376-8883","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","064","이디야커피","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","48","12","55","010-6459-7334","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","065","파리바게트","serving","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","2","18","23","010-0603-4685","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","066","파리바게트","cooking","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","35","16","13","010-5929-0821","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","067","GS25","delivery","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","39","10","32","010-9863-9900","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","068","BBQ치킨","serving","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","54","8","38","010-1403-2457","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","069","스타벅스","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","21","18","13","010-0265-1270","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","070","올리브영","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","47","17","26","010-9296-5548","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","071","파리바게트","cooking","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","19","21","45","010-5908-8575","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","072","BBQ치킨","cooking","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","4","21","26","010-1003-1546","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","073","BBQ치킨","cafe","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","26","22","13","010-8806-3708","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","074","새마을식당","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","34","22","40","010-1132-3061","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","075","CU편의점","serving","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","25","10","46","010-5319-3140","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","076","새마을식당","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","19","22","23","010-2554-1546","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","077","이디야커피","store","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","51","15","17","010-0885-1901","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","078","BBQ치킨","delivery","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","41","17","16","010-6806-6059","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","079","BBQ치킨","cooking","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","18","23","50","010-1892-9414","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","080","스타벅스","store","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","33","24","1","010-4542-4855","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","081","이디야커피","delivery","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","0","17","12","010-3546-0866","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","082","BBQ치킨","cooking","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","58","24","37","010-0607-0818","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","083","GS25","serving","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","45","18","2","010-9274-7699","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","084","새마을식당","cafe","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","58","24","16","010-5179-3151","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","085","스타벅스","store","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","54","23","7","010-4614-9166","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","086","새마을식당","store","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","52","23","36","010-4990-2968","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","087","파리바게트","serving","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","4","20","16","010-8558-8724","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","088","GS25","store","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","32","20","9","010-9168-6044","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","089","GS25","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","45","23","47","010-7541-9132","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","090","GS25","cafe","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","44","22","37","010-3541-7677","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","091","파리바게트","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","48","21","38","010-3907-7291","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","092","BBQ치킨","cafe","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","34","24","55","010-2388-1945","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","093","새마을식당","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","2","22","33","010-7572-2725","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","094","파리바게트","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","12","12","16","010-3598-0602","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","095","CU편의점","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","57","19","2","010-6015-4732","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","096","파리바게트","cooking","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","42","17","39","010-8393-6074","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","097","GS25","serving","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","13","21","44","010-3134-0626","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","098","CU편의점","cooking","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","6","24","50","010-4068-9062","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","099","이디야커피","serving","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","57","10","49","010-5078-5419","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","100","스타벅스","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","40","24","25","010-8281-1120","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","101","새마을식당","store","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","12","18","15","010-1469-4887","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","102","파리바게트","serving","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","57","8","3","010-1078-2278","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","103","올리브영","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","52","24","23","010-4626-7877","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","104","이디야커피","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","25","3","7","010-0825-8504","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","105","새마을식당","delivery","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","24","24","39","010-2381-4701","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","106","파리바게트","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","55","24","15","010-5238-5485","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","107","파리바게트","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","18","9","20","010-8324-0807","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","108","올리브영","delivery","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","8","24","20","010-5478-9671","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","109","BBQ치킨","store","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","17","23","37","010-4672-2243","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","110","GS25","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","48","22","32","010-6397-7421","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","111","이디야커피","cooking","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","53","24","43","010-3585-2972","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","112","새마을식당","cooking","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","21","22","6","010-2521-7703","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","113","CU편의점","delivery","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","31","24","37","010-6879-4648","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","114","BBQ치킨","serving","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","54","13","42","010-6032-9911","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","115","파리바게트","serving","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","41","21","14","010-4219-6873","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","116","새마을식당","cooking","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","31","24","32","010-5770-4543","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","117","BBQ치킨","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","1","23","38","010-2690-7939","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","118","새마을식당","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","42","9","50","010-8395-3941","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","119","CU편의점","serving","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","9","24","2","010-4395-7033","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","120","GS25","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","4","18","36","010-5301-1566","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","121","올리브영","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","51","10","10","010-3255-1236","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","122","CU편의점","cafe","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","11","24","11","010-1948-5158","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","123","스타벅스","serving","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","37","21","36","010-9937-5872","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","124","CU편의점","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","6","11","11","010-5006-1763","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","125","스타벅스","delivery","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","21","7","15","010-7832-8149","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","126","올리브영","serving","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","53","22","56","010-5217-9335","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","127","올리브영","delivery","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","2","18","12","010-0944-6993","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","128","파리바게트","cooking","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","24","11","28","010-2633-6479","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","129","올리브영","store","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","49","6","4","010-0603-4808","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","130","GS25","delivery","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","29","17","34","010-1984-4672","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","131","CU편의점","delivery","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","12","24","33","010-8461-0118","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","132","스타벅스","cooking","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","53","23","55","010-0556-4320","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","133","CU편의점","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","8","18","8","010-3240-8197","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","134","스타벅스","store","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","31","21","28","010-4987-7176","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","135","새마을식당","cooking","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","55","15","39","010-4316-7566","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","136","스타벅스","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","3","23","14","010-6435-3184","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","137","GS25","store","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","50","18","27","010-1655-9039","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","138","CU편의점","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","59","20","15","010-0669-7824","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","139","파리바게트","store","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","2","23","42","010-5929-2803","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","140","BBQ치킨","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","8","17","55","010-2964-7866","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","141","올리브영","delivery","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","40","12","56","010-7179-8380","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","142","스타벅스","serving","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","8","20","23","010-9692-0417","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","143","이디야커피","cafe","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","18","16","51","010-1001-2770","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","144","올리브영","store","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","11","20","56","010-9390-1625","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","145","스타벅스","serving","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","10","10","54","010-9187-0931","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","146","CU편의점","store","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","11","24","4","010-9908-1204","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","147","파리바게트","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","24","24","1","010-4007-1013","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","148","파리바게트","store","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","50","15","50","010-8480-6399","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","149","새마을식당","serving","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","12","7","41","010-6969-9677","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","150","올리브영","cafe","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","14","48","21","6","010-0093-6181","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","151","이디야커피","cooking","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","46","19","0","010-1589-1122","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","152","새마을식당","serving","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","41","21","39","010-7606-7019","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","153","GS25","store","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","42","23","52","010-8119-0381","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","154","스타벅스","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","29","21","22","010-3866-2761","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","155","스타벅스","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","10","9","42","010-7253-1472","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","156","CU편의점","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","4","21","51","010-4522-3228","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","157","CU편의점","delivery","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","50","23","46","010-5085-2461","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","158","올리브영","cafe","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","38","17","25","010-7127-4184","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","159","새마을식당","delivery","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","49","21","13","010-9403-4636","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","160","스타벅스","serving","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","13","24","37","010-7989-4399","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","161","이디야커피","store","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","14","17","53","010-0034-5611","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","162","CU편의점","serving","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","55","24","11","010-9380-4095","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","163","GS25","cooking","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","56","24","29","010-5308-5879","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","164","새마을식당","serving","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","12","17","2","010-0173-3431","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","165","BBQ치킨","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","47","9","51","010-9595-8598","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","166","CU편의점","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","24","20","59","010-8739-7700","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","167","GS25","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","11","16","16","010-1653-0983","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","168","이디야커피","delivery","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","39","22","6","010-2911-7917","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","169","CU편의점","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","31","4","43","010-1212-4767","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","170","BBQ치킨","cafe","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","5","24","58","010-3339-2055","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","171","파리바게트","delivery","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","51","16","46","010-6536-0820","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","172","스타벅스","delivery","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","59","19","0","010-0715-2121","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","173","BBQ치킨","store","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","57","24","2","010-8168-7397","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","174","스타벅스","cafe","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","49","18","13","010-1620-0866","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","175","스타벅스","store","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","59","18","54","010-5733-7446","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","176","새마을식당","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","48","22","45","010-1446-8053","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","177","이디야커피","cooking","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","54","18","53","010-3016-7172","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","178","BBQ치킨","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","18","24","43","010-7699-9476","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","179","파리바게트","store","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","48","22","1","010-9442-3235","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","180","GS25","store","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","40","5","43","010-3965-0374","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","181","GS25","cafe","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","16","21","27","010-9162-9007","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","182","올리브영","store","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","40","18","51","010-5167-1679","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","183","새마을식당","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","1","16","56","010-3658-8854","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","184","CU편의점","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","33","23","12","010-2649-3808","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","185","파리바게트","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","32","24","59","010-4317-0496","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","186","올리브영","cooking","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","23","23","49","010-8433-1657","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","187","파리바게트","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","2","17","6","41","010-0548-4329","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","188","스타벅스","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","28","22","25","010-6911-2618","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","189","CU편의점","store","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","30","16","59","010-7505-2702","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","190","이디야커피","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","25","23","53","010-3318-6858","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","191","올리브영","delivery","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","28","22","23","010-8417-4744","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","192","BBQ치킨","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","46","23","50","010-6752-3146","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","193","GS25","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","27","24","32","010-6419-7136","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","194","새마을식당","cafe","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","28","24","36","010-9349-3410","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","195","BBQ치킨","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","17","24","26","010-9693-2251","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","196","이디야커피","cooking","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","4","18","4","010-3086-0748","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","197","새마을식당","serving","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","9","21","26","010-7861-7171","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","198","올리브영","cafe","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","15","8","57","010-9217-2339","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","199","BBQ치킨","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","23","19","39","010-0084-5033","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","200","스타벅스","serving","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","41","20","24","010-0099-1449","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","201","BBQ치킨","cooking","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","32","6","50","010-1533-7297","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","202","파리바게트","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","9","13","3","010-8989-4302","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","203","새마을식당","store","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","38","21","15","010-3386-8685","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","204","GS25","store","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","0","22","3","010-8811-2220","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","205","BBQ치킨","serving","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","39","16","2","010-4948-1397","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","206","GS25","cafe","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","43","10","49","010-4940-4959","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","207","GS25","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","36","20","53","010-1108-7791","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","208","파리바게트","cafe","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","34","11","10","010-1998-7158","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","209","이디야커피","serving","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","12","20","14","010-9386-1699","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","210","BBQ치킨","cooking","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","48","20","31","010-5864-8486","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","211","스타벅스","serving","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","59","11","19","010-5664-5920","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","212","올리브영","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","33","24","30","010-4341-6603","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","213","CU편의점","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","47","11","3","010-8139-4413","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","214","스타벅스","serving","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","34","16","19","010-7766-0316","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","215","이디야커피","cafe","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","26","22","57","010-2510-5150","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","216","CU편의점","store","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","40","8","15","010-7164-1427","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","217","스타벅스","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","36","13","37","010-1814-0059","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","218","CU편의점","delivery","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","49","20","5","010-5313-3588","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","219","이디야커피","serving","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","42","15","45","010-7748-5332","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","220","올리브영","delivery","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","11","24","12","010-6127-7607","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","221","새마을식당","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","12","17","50","010-3761-0128","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","222","파리바게트","cafe","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","57","15","11","010-5862-6753","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","223","이디야커피","cooking","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","50","9","9","010-5498-2716","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","224","GS25","cooking","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","3","51","23","53","010-3399-3096","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","225","올리브영","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","32","16","10","010-8121-1663","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","226","BBQ치킨","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","16","21","51","010-6525-3741","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","227","스타벅스","cafe","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","28","23","36","010-6774-3714","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","228","파리바게트","delivery","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","32","23","7","010-3337-1484","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","229","CU편의점","store","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","17","8","11","010-2020-6905","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","230","BBQ치킨","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","38","22","31","010-9491-7871","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","231","BBQ치킨","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","49","13","6","010-9078-4062","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","232","새마을식당","serving","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","25","18","52","010-4074-4283","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","233","새마을식당","cafe","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","42","24","15","010-2052-7085","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","234","새마을식당","serving","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","58","19","58","010-5406-7086","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","235","이디야커피","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","42","10","2","010-9459-6218","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","236","CU편의점","store","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","32","20","43","010-4325-6045","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","237","스타벅스","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","20","3","22","55","010-6426-3881","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","238","스타벅스","delivery","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","33","21","50","010-7347-1340","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","239","스타벅스","store","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","6","24","46","010-5533-4487","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","240","GS25","cafe","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","5","12","32","010-8203-6222","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","241","새마을식당","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","20","9","6","010-9624-2922","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","242","새마을식당","store","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","59","18","10","010-4189-6439","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","243","스타벅스","cafe","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","39","13","12","010-9713-6887","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","244","파리바게트","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","54","17","22","010-9591-8461","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","245","스타벅스","serving","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","29","22","36","010-0394-8907","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","246","파리바게트","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","46","21","45","010-2757-7694","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","247","올리브영","delivery","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","41","11","56","010-7546-8649","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","248","BBQ치킨","delivery","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","29","20","18","010-9207-8962","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","249","새마을식당","serving","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","58","15","19","010-8714-3540","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","250","올리브영","cooking","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","29","20","23","010-0971-5128","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","251","파리바게트","cooking","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","44","21","17","010-2691-6515","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","252","올리브영","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","41","23","9","010-9503-9071","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","253","스타벅스","cooking","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","14","24","5","010-0733-0330","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","254","올리브영","cooking","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","16","23","21","010-1198-7903","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","255","스타벅스","store","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","52","13","3","010-9408-7808","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","256","올리브영","store","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","13","24","12","010-7632-2053","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","257","새마을식당","delivery","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","37","23","1","010-8546-0086","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","258","이디야커피","delivery","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","11","20","12","010-2765-5812","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","259","스타벅스","cooking","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","36","17","4","010-7789-2513","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","260","새마을식당","store","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","22","12","25","010-0422-6727","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","261","GS25","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","3","24","12","54","010-7523-6828","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","262","파리바게트","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","2","19","40","010-5572-3121","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","263","이디야커피","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","30","16","6","010-2574-5281","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","264","새마을식당","cafe","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","20","7","28","010-8521-4493","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","265","스타벅스","serving","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","0","24","33","010-7983-3041","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","266","GS25","delivery","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","55","21","46","010-7559-7684","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","267","올리브영","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","57","10","19","010-8045-7372","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","268","GS25","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","58","23","1","010-4402-1232","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","269","올리브영","cafe","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","59","22","3","010-3164-5105","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","270","스타벅스","serving","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","23","19","35","010-0111-4170","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","271","파리바게트","serving","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","57","11","57","010-6173-5703","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","272","CU편의점","cafe","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","25","19","44","010-6141-3526","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","273","이디야커피","serving","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","4","2","59","010-8791-5564","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","274","GS25","cooking","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","41","23","26","010-5052-8277","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","275","올리브영","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","28","20","49","010-3286-5362","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","276","이디야커피","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","36","14","33","010-4183-3212","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","277","GS25","cooking","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","30","17","22","010-5754-1295","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","278","CU편의점","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","7","16","38","010-5733-3676","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","279","스타벅스","cooking","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","3","13","56","010-8603-4294","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","280","CU편의점","store","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","46","24","3","010-7987-9760","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","281","CU편의점","store","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","22","22","55","010-9091-2508","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","282","BBQ치킨","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","12","14","37","010-9955-9845","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","283","BBQ치킨","delivery","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","12","23","52","010-1263-4604","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","284","BBQ치킨","delivery","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","3","18","0","010-3795-6783","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","285","GS25","store","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","37","24","27","010-9549-6991","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","286","새마을식당","cooking","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","59","20","56","010-0810-4297","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","287","이디야커피","store","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","17","24","3","010-1617-1371","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","288","스타벅스","cooking","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","4","23","24","010-3498-5707","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","289","GS25","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","57","13","10","010-8418-9423","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","290","스타벅스","store","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","53","24","9","010-1280-2821","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","291","파리바게트","serving","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","46","22","58","010-7899-3128","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","292","새마을식당","cooking","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","24","11","21","010-6542-5274","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","293","BBQ치킨","serving","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","9","21","34","010-6781-4788","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","294","스타벅스","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","9","12","9","010-4385-6304","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","295","파리바게트","delivery","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","24","23","9","010-1036-3465","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","296","GS25","cafe","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","8","21","10","010-3110-5733","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","297","새마을식당","delivery","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","59","20","37","010-4755-0754","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","298","이디야커피","serving","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","0","23","40","010-5814-9839","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","299","BBQ치킨","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","40","15","27","010-8745-8176","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","300","파리바게트","cafe","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","30","1","1","010-6965-5477","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","301","BBQ치킨","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","22","15","15","010-9698-2095","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","302","이디야커피","cafe","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","34","17","39","010-9389-1348","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","303","BBQ치킨","store","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","59","15","42","010-9970-9155","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","304","BBQ치킨","cooking","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","33","21","20","010-7029-8213","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","305","이디야커피","cooking","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","37","11","59","010-4463-4903","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","306","이디야커피","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","28","8","18","010-2983-0619","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","307","GS25","serving","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","10","22","8","010-2637-8497","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","308","스타벅스","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","30","23","29","010-8242-8731","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","309","GS25","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","9","21","34","010-9030-8736","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","310","BBQ치킨","serving","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","49","19","29","010-5223-6258","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","311","스타벅스","cooking","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","45","20","6","010-6555-0654","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","312","스타벅스","store","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","50","24","8","010-0827-0095","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","313","파리바게트","cafe","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","5","21","6","010-6477-5528","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","314","CU편의점","store","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","8","16","2","010-3196-3669","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","315","BBQ치킨","delivery","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","9","14","1","010-7333-7020","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","316","이디야커피","delivery","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","10","23","13","010-2249-7086","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","317","CU편의점","delivery","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","23","24","40","010-1840-2406","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","318","스타벅스","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","10","24","47","010-3255-4536","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","319","스타벅스","cooking","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","7","8","36","010-0143-9840","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","320","스타벅스","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","13","18","16","010-2116-7750","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","321","파리바게트","cafe","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","6","12","24","010-8423-7288","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","322","BBQ치킨","store","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","26","19","13","010-4067-2766","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","323","올리브영","cafe","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","53","6","7","010-7505-2766","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","324","파리바게트","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","53","24","42","010-4789-6181","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","325","GS25","cooking","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","29","16","46","010-4026-7365","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","326","새마을식당","cafe","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","52","12","21","010-8213-1744","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","327","새마을식당","serving","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","4","18","55","010-9481-4656","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","328","CU편의점","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","20","24","32","010-8676-2953","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","329","BBQ치킨","cafe","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","31","23","22","010-3709-8498","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","330","CU편의점","store","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","1","12","24","010-2867-3464","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","331","이디야커피","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","22","19","59","010-9474-6421","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","332","새마을식당","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","52","22","29","010-7650-2554","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","333","GS25","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","43","21","8","010-9554-7128","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","334","CU편의점","store","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","17","19","44","010-9758-9398","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","335","올리브영","store","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","32","24","17","010-8907-3684","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","336","GS25","delivery","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","53","21","24","010-4746-1878","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","337","파리바게트","cafe","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","34","11","39","010-2660-6569","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","338","파리바게트","store","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","37","14","7","010-6665-7337","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","339","파리바게트","store","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","6","11","37","010-0665-2965","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","340","이디야커피","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","27","24","5","010-9281-3408","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","341","스타벅스","store","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","52","20","44","010-2394-3460","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","342","CU편의점","cooking","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","55","19","50","010-0334-1509","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","343","스타벅스","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","52","21","52","010-6686-4795","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","344","이디야커피","cafe","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","48","8","32","010-8753-3423","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","345","이디야커피","serving","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","44","21","27","010-1697-7946","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","346","스타벅스","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","29","17","30","010-8273-9200","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","347","CU편의점","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","14","22","51","010-0088-9122","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","348","새마을식당","store","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","20","21","3","010-1282-0878","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","349","BBQ치킨","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","36","21","48","010-9761-9698","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","350","BBQ치킨","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","40","19","0","010-7729-5370","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","351","BBQ치킨","cafe","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","52","12","9","010-4847-7351","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","352","CU편의점","serving","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","52","15","13","010-5331-2489","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","353","새마을식당","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","4","21","8","010-2921-6976","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","354","BBQ치킨","cooking","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","52","24","37","010-8074-5114","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","355","CU편의점","store","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","21","22","33","010-6715-6078","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","356","CU편의점","store","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","14","14","34","010-0942-6127","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","357","CU편의점","cooking","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","31","18","56","010-3714-1496","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","358","CU편의점","serving","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","56","24","21","010-2657-8211","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","359","새마을식당","store","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","22","54","24","13","010-8997-3944","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","360","GS25","cafe","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","24","23","15","010-6607-0269","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","361","스타벅스","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","51","18","3","010-5529-6295","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","362","파리바게트","store","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","18","15","6","010-5543-7281","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","363","CU편의점","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","4","21","22","010-7094-3101","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","364","스타벅스","delivery","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","38","21","22","010-9638-3765","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","365","스타벅스","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","54","21","13","010-9747-6430","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","366","올리브영","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","6","21","0","010-7188-4417","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","367","CU편의점","cooking","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","48","19","18","010-1204-6817","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","368","CU편의점","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","43","20","38","010-0981-5492","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","369","GS25","cafe","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","19","23","21","010-5604-7638","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","370","스타벅스","cooking","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","51","23","15","010-6824-6524","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","371","CU편의점","serving","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","24","7","33","010-6730-4473","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","372","CU편의점","cooking","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","20","9","13","010-1130-8239","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","373","스타벅스","delivery","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","44","22","4","010-2945-5471","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","374","새마을식당","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","57","23","6","010-9013-0968","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","375","GS25","delivery","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","40","23","0","010-2279-8865","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","376","이디야커피","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","56","21","41","010-7153-6817","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","377","CU편의점","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","43","23","34","010-5159-6653","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","378","올리브영","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","29","18","1","010-7101-9460","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","379","파리바게트","serving","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","55","23","21","010-3061-3548","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","380","이디야커피","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","55","15","41","010-5139-5786","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","381","새마을식당","delivery","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","51","15","9","010-1654-0983","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","382","이디야커피","cooking","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","8","20","22","010-9803-1975","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","383","새마을식당","store","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","41","7","14","010-7584-1651","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","384","스타벅스","serving","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","10","18","14","010-5780-3178","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","385","BBQ치킨","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","25","20","32","010-2638-3664","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","386","새마을식당","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","22","40","24","10","010-0528-6576","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","387","파리바게트","serving","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","17","24","3","010-1807-2546","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","388","BBQ치킨","serving","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","2","24","52","010-0062-8153","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","389","GS25","serving","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","6","19","4","010-2780-4639","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","390","올리브영","cafe","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","35","16","20","010-1790-5086","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","391","CU편의점","serving","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","59","24","46","010-3247-3506","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","392","새마을식당","delivery","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","53","12","3","010-3732-1411","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","393","올리브영","delivery","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","33","22","41","010-4584-0690","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","394","파리바게트","store","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","19","21","5","010-2327-3431","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","395","올리브영","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","26","23","57","010-9268-6325","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","396","올리브영","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","10","23","16","010-8433-6328","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","397","올리브영","serving","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","14","7","53","010-7551-6853","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","398","BBQ치킨","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","43","22","4","010-8251-5758","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","399","CU편의점","cafe","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","34","24","20","010-5987-1019","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","400","GS25","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","10","22","50","010-9121-6919","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","401","파리바게트","store","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","43","4","36","010-0002-0190","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","402","CU편의점","serving","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","0","23","29","010-3222-0339","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","403","BBQ치킨","store","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","26","21","10","010-3436-9935","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","404","새마을식당","serving","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","14","22","48","010-0508-2009","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","405","이디야커피","cafe","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","43","17","51","010-2175-0832","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","406","파리바게트","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","22","14","24","8","010-1518-7069","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","407","스타벅스","delivery","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","8","24","45","010-7693-5437","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","408","이디야커피","serving","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","2","24","47","010-6553-8653","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","409","GS25","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","57","18","26","010-9088-7780","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","410","새마을식당","delivery","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","15","19","17","010-0098-6471","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","411","올리브영","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","35","23","10","010-8095-4184","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","412","스타벅스","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","54","24","6","010-0576-2921","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","413","올리브영","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","31","15","42","010-4767-3197","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","414","이디야커피","serving","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","29","11","42","010-3632-7512","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","415","스타벅스","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","36","23","10","010-8324-4203","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","416","GS25","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","46","24","50","010-0083-2061","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","417","이디야커피","cafe","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","16","18","25","010-1393-3943","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","418","CU편의점","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","3","22","0","010-4300-6157","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","419","스타벅스","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","15","20","22","010-1351-6344","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","420","스타벅스","store","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","12","24","45","010-7161-0914","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","421","CU편의점","cooking","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","6","20","4","010-9392-2979","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","422","CU편의점","serving","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","12","21","44","010-6061-2983","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","423","새마을식당","cooking","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","52","18","23","010-2331-0119","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","424","올리브영","cafe","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","29","22","20","010-9940-8313","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","425","이디야커피","cooking","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","26","23","7","010-6062-5116","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","426","스타벅스","delivery","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","8","22","14","010-4701-6531","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","427","GS25","serving","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","14","14","1","010-8615-2231","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","428","BBQ치킨","cafe","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","11","20","13","010-0138-2120","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","429","새마을식당","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","43","19","9","010-2358-7339","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","430","새마을식당","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","51","21","50","010-7670-7024","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","431","BBQ치킨","cafe","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","49","24","22","010-9737-2930","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","432","이디야커피","serving","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","3","18","24","010-5157-7207","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","433","이디야커피","cafe","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","37","8","38","010-2975-1379","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","434","새마을식당","cooking","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","32","24","10","010-8107-6324","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","435","CU편의점","serving","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","17","24","24","010-8045-3079","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","436","새마을식당","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","21","9","8","010-6490-4494","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","437","GS25","cooking","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","21","24","26","010-2321-6979","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","438","CU편의점","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","36","9","18","010-5181-1395","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","439","파리바게트","cafe","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","2","24","2","010-5631-7449","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","440","새마을식당","serving","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","31","9","56","010-9430-5556","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","441","올리브영","cafe","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","44","19","17","010-0824-6526","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","442","올리브영","cafe","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","0","23","22","010-7946-2962","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","443","올리브영","serving","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","16","24","19","010-6381-4576","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","444","파리바게트","delivery","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","30","16","20","010-3009-5063","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","445","올리브영","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","10","16","31","010-6600-7698","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","446","새마을식당","cafe","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","22","27","24","13","010-2137-9856","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","447","BBQ치킨","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","37","11","26","010-3213-6439","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","448","GS25","delivery","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","51","18","35","010-4265-0630","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","449","파리바게트","store","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","2","50","8","27","010-6179-7287","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","450","파리바게트","serving","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","25","18","2","010-2469-0421","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","451","CU편의점","cafe","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","6","16","55","010-1863-0001","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","452","이디야커피","store","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","21","10","37","010-3853-1440","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","453","스타벅스","serving","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","6","24","18","010-8964-4349","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","454","BBQ치킨","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","16","7","24","010-0552-6408","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","455","새마을식당","serving","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","10","21","53","010-0705-6977","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","456","올리브영","cooking","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","34","24","41","010-5668-9675","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","457","이디야커피","cafe","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","33","23","58","010-2428-9218","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","458","새마을식당","cooking","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","39","4","23","010-5352-5483","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","459","새마을식당","delivery","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","0","21","50","010-3013-2003","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","460","CU편의점","delivery","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","51","19","54","010-2096-3164","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","461","CU편의점","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","22","23","45","010-2124-5964","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","462","올리브영","cafe","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","56","17","35","010-3996-3590","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","463","올리브영","store","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","23","16","42","010-5894-9508","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","464","BBQ치킨","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","44","23","40","010-8275-2034","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","465","GS25","store","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","41","12","47","010-5780-7613","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","466","GS25","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","1","2","2","28","010-6419-2925","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","467","파리바게트","cafe","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","54","11","13","010-3621-3300","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","468","새마을식당","cooking","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","4","23","57","010-8565-2407","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","469","CU편의점","store","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","58","21","32","010-5565-5589","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","470","스타벅스","cooking","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","48","13","24","010-9922-2391","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","471","BBQ치킨","serving","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","45","12","53","010-9460-1996","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","472","새마을식당","cafe","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","13","21","3","010-9119-3506","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","473","이디야커피","serving","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","14","23","28","010-3011-1389","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","474","CU편의점","store","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","17","21","23","010-2054-7558","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","475","새마을식당","cafe","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","9","24","58","010-5453-7667","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","476","올리브영","cooking","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","29","13","10","010-3224-9148","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","477","CU편의점","cafe","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","28","24","20","010-6250-2766","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","478","새마을식당","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","2","6","49","010-1052-7956","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","479","스타벅스","cafe","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","42","14","13","010-8922-4427","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","480","새마을식당","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","15","22","1","010-7205-8866","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","481","새마을식당","delivery","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","57","21","56","010-6685-7193","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","482","파리바게트","serving","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","23","23","10","010-7073-0884","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","483","새마을식당","store","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","40","16","32","010-6453-7831","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","484","GS25","store","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","21","20","3","010-3987-0819","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","485","파리바게트","store","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","41","14","43","010-0125-9937","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","486","이디야커피","cafe","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","14","19","15","010-3426-9512","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","487","새마을식당","delivery","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","21","24","2","010-1433-4576","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","488","GS25","store","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","17","20","58","010-3627-5119","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","489","GS25","store","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","17","15","24","010-9006-1754","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","490","BBQ치킨","delivery","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","13","20","34","010-1220-1137","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","491","파리바게트","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","22","23","23","15","010-6354-9720","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","492","이디야커피","store","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","57","13","37","010-8544-0664","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","493","올리브영","store","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","25","12","0","010-4477-2869","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","494","스타벅스","cafe","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","55","19","11","010-8296-9468","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","495","올리브영","serving","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","45","20","45","010-3901-7636","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","496","GS25","serving","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","49","21","46","010-2596-5981","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","497","이디야커피","cafe","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","40","16","25","010-4369-6397","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","498","BBQ치킨","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","1","19","27","010-9876-2375","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","499","GS25","serving","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","44","24","23","010-8528-7221","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","500","GS25","delivery","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","1","23","55","010-6274-0249","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","501","스타벅스","store","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","1","24","3","010-5891-8235","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","502","CU편의점","serving","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","7","20","8","010-5509-7937","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","503","올리브영","cafe","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","38","24","33","010-9950-8492","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","504","파리바게트","delivery","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","13","14","23","010-7442-7987","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","505","BBQ치킨","store","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","53","24","30","010-0928-6784","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","506","새마을식당","cafe","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","18","24","33","010-6392-0644","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","507","새마을식당","cafe","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","18","19","27","010-5653-5092","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","508","BBQ치킨","delivery","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","9","2","50","010-7826-8028","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","509","BBQ치킨","cafe","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","33","22","27","010-1707-0564","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","510","스타벅스","store","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","50","14","0","010-7696-6662","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","511","이디야커피","cooking","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","44","14","14","010-9586-3841","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","512","새마을식당","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","21","21","55","010-7590-7007","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","513","새마을식당","delivery","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","42","11","31","010-2371-7906","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","514","이디야커피","serving","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","18","21","34","010-7167-6184","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","515","파리바게트","cooking","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","14","15","38","010-2326-4467","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","516","파리바게트","delivery","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","11","23","41","010-7867-7005","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","517","새마을식당","store","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","18","21","16","010-2286-7764","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","518","GS25","cooking","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","12","17","15","010-2366-3621","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","519","올리브영","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","56","12","39","010-3627-4539","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","520","이디야커피","store","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","20","17","32","010-9751-5153","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","521","파리바게트","cooking","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","30","20","2","010-9336-7935","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","522","CU편의점","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","15","13","24","010-9724-2753","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","523","BBQ치킨","serving","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","24","23","25","010-7181-6927","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","524","새마을식당","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","26","16","7","010-4565-8896","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","525","CU편의점","serving","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","10","15","27","010-0560-0460","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","526","BBQ치킨","cooking","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","18","22","59","010-9000-0916","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","527","GS25","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","40","24","14","010-3276-9977","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","528","이디야커피","serving","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","19","8","26","010-9492-6139","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","529","새마을식당","cooking","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","1","13","8","010-8559-7054","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","530","이디야커피","delivery","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","16","6","47","010-3947-7132","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","531","이디야커피","cafe","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","46","24","49","010-4018-6216","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","532","올리브영","delivery","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","3","22","20","010-0970-1471","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","533","스타벅스","store","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","34","16","23","010-0122-9351","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","534","CU편의점","cooking","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","57","24","30","010-5093-4977","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","535","이디야커피","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","25","19","42","010-1757-2104","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","536","파리바게트","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","42","24","56","010-1888-3123","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","537","파리바게트","cafe","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","20","21","33","010-7106-0263","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","538","파리바게트","delivery","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","44","24","7","010-4607-8824","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","539","파리바게트","delivery","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","47","22","58","010-6591-3008","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","540","스타벅스","cafe","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","16","20","46","010-3338-8205","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","541","새마을식당","cooking","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","17","16","46","010-2973-6892","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","542","스타벅스","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","45","24","6","010-8004-0832","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","543","CU편의점","delivery","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","40","12","21","010-6163-0803","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","544","올리브영","cafe","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","8","20","5","010-0372-5179","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","545","스타벅스","delivery","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","38","15","49","010-0654-8312","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","546","올리브영","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","29","18","49","010-3890-2867","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","547","CU편의점","cafe","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","2","18","50","010-5807-5190","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","548","새마을식당","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","52","22","11","010-7639-1275","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","549","GS25","serving","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","59","9","52","010-6082-5840","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","550","CU편의점","cooking","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","22","23","27","010-5894-2670","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","551","GS25","cooking","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","17","52","24","42","010-6338-5529","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","552","새마을식당","cooking","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","26","20","5","010-1886-6715","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","553","스타벅스","serving","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","11","20","45","010-7366-7346","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","554","BBQ치킨","store","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","27","14","34","010-6267-5084","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","555","파리바게트","delivery","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","54","16","1","010-2096-0690","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","556","파리바게트","serving","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","27","21","59","010-2704-4453","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","557","GS25","serving","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","59","24","37","010-1360-6274","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","558","스타벅스","cooking","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","29","20","32","010-1720-9761","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","559","스타벅스","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","58","20","16","010-0401-3005","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","560","CU편의점","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","23","20","57","010-1336-4957","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","561","새마을식당","cafe","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","25","21","29","010-8337-0769","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","562","파리바게트","cafe","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","38","19","22","010-2931-9196","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","563","올리브영","serving","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","31","23","27","010-1614-7495","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","564","스타벅스","cooking","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","29","24","59","010-3767-8580","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","565","GS25","serving","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","8","23","45","010-8813-4662","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","566","GS25","cooking","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","15","11","6","010-0638-5130","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","567","스타벅스","cafe","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","55","14","41","010-7086-6122","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","568","새마을식당","cafe","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","13","17","44","010-5198-2954","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","569","CU편의점","serving","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","21","23","30","010-7230-1872","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","570","이디야커피","delivery","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","42","23","12","010-0048-2100","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","571","이디야커피","delivery","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","29","23","59","010-2038-9576","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","572","스타벅스","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","32","18","1","010-5236-8432","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","573","GS25","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","21","21","37","010-4927-9957","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","574","BBQ치킨","serving","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","0","23","27","010-1017-1067","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","575","새마을식당","cooking","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","36","19","56","010-4297-6799","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","576","이디야커피","serving","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","20","21","49","010-2954-4997","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","577","새마을식당","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","36","22","10","010-6076-6695","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","578","파리바게트","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","14","24","32","010-8230-9053","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","579","올리브영","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","35","22","1","010-3400-2150","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","580","GS25","cafe","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","3","18","4","28","010-3845-4400","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","581","이디야커피","cafe","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","21","16","49","010-5898-7923","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","582","올리브영","delivery","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","34","4","43","010-4489-3372","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","583","스타벅스","serving","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","13","20","39","010-8327-5476","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","584","스타벅스","serving","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","7","12","8","010-9724-8651","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","585","스타벅스","cooking","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","53","1","54","010-9229-9816","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","586","이디야커피","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","18","23","28","010-1124-2032","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","587","이디야커피","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","29","24","18","010-3499-1365","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","588","이디야커피","cafe","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","7","20","54","010-6104-1688","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","589","파리바게트","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","48","7","50","010-8881-6917","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","590","새마을식당","delivery","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","33","21","25","010-8862-6367","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","591","이디야커피","cooking","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","36","21","23","010-9383-0494","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","592","스타벅스","delivery","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","11","18","53","010-3326-4924","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","593","올리브영","cooking","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","57","14","22","010-0865-8789","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","594","스타벅스","cafe","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","20","6","31","010-4228-4965","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","595","파리바게트","cafe","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","52","16","27","010-0099-5354","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","596","파리바게트","cafe","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","35","17","30","010-7894-9307","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","597","CU편의점","cooking","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","16","9","24","010-8005-6842","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","598","GS25","store","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","10","24","35","010-7647-6225","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","599","올리브영","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","2","15","42","010-6013-2002","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","600","BBQ치킨","store","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","27","10","33","010-8940-1726","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","601","파리바게트","delivery","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","6","11","56","010-2780-3228","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","602","스타벅스","serving","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","6","21","59","010-5771-5219","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","603","올리브영","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","31","16","32","010-8686-5028","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","604","스타벅스","delivery","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","50","22","31","010-0110-7718","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","605","BBQ치킨","cooking","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","22","24","28","010-2822-0962","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","606","GS25","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","2","23","43","010-7729-1749","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","607","이디야커피","serving","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","57","20","19","010-4485-3324","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","608","파리바게트","cafe","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","39","10","2","010-8883-5969","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","609","BBQ치킨","delivery","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","57","23","56","010-0564-3461","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","610","이디야커피","cafe","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","52","23","31","010-5022-0764","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","611","스타벅스","delivery","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","43","19","11","010-4747-6304","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","612","이디야커피","cafe","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","32","23","15","010-5314-0419","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","613","올리브영","serving","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","11","15","22","010-2582-3248","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","614","CU편의점","cooking","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","7","21","50","010-3697-3384","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","615","새마을식당","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","59","14","36","010-0338-3836","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","616","스타벅스","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","49","19","48","010-3348-1894","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","617","파리바게트","store","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","16","43","18","48","010-8591-2757","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","618","이디야커피","serving","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","24","8","49","010-5262-3863","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","619","새마을식당","cooking","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","23","22","8","010-3822-2708","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","620","GS25","cafe","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","9","21","12","010-4615-1864","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","621","새마을식당","store","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","1","23","4","010-8902-3164","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","622","GS25","cooking","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","24","12","7","010-1486-9759","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","623","이디야커피","cafe","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","24","19","20","010-2085-4616","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","624","올리브영","cooking","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","39","21","1","010-1859-3818","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","625","GS25","cafe","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","50","24","46","010-5427-4636","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","626","이디야커피","delivery","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","5","15","27","010-2008-9589","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","627","스타벅스","store","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","39","18","46","010-3713-2700","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","628","CU편의점","delivery","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","25","24","21","010-6317-0936","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","629","CU편의점","cafe","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","54","24","47","010-2772-0724","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","630","CU편의점","cafe","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","42","17","4","010-3767-7666","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","631","스타벅스","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","29","18","4","010-8735-6707","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","632","GS25","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","6","9","22","010-2899-1127","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","633","GS25","store","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","33","24","17","010-6190-2904","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","634","스타벅스","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","16","24","25","010-8493-6251","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","635","스타벅스","serving","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","57","22","39","010-4981-5036","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","636","파리바게트","cafe","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","47","10","49","010-3591-8507","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","637","CU편의점","cafe","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","7","3","50","010-9439-9521","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","638","이디야커피","cafe","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","17","18","44","010-0069-7308","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","639","CU편의점","cafe","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","54","17","44","010-4673-0434","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","640","이디야커피","delivery","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","58","20","31","010-3901-0078","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","641","BBQ치킨","serving","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","0","24","51","010-9423-1554","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","642","스타벅스","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","58","10","43","010-6043-7298","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","643","새마을식당","cafe","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","56","20","26","010-9600-7594","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","644","BBQ치킨","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","53","21","4","010-0279-4817","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","645","올리브영","cooking","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","31","15","34","010-3945-9963","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","646","BBQ치킨","delivery","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","9","20","16","010-1923-0171","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","647","스타벅스","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","21","16","40","010-1687-9061","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","648","GS25","store","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","51","10","43","010-3941-8338","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","649","CU편의점","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","3","23","23","010-0347-3128","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","650","파리바게트","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","41","9","48","010-8340-5355","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","651","올리브영","delivery","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","14","51","21","16","010-8331-6454","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","652","스타벅스","cafe","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","17","24","19","010-5038-4185","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","653","새마을식당","serving","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","8","10","23","010-7620-7683","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","654","스타벅스","cooking","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","56","22","2","010-1461-4066","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","655","파리바게트","serving","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","44","24","26","010-4759-2588","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","656","CU편의점","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","19","21","17","010-7006-0687","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","657","CU편의점","store","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","2","24","37","010-8659-1099","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","658","이디야커피","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","21","21","57","010-1879-2592","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","659","CU편의점","cooking","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","13","22","2","010-6906-3694","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","660","BBQ치킨","serving","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","32","9","14","010-6057-8606","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","661","GS25","cafe","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","15","5","16","010-3141-7819","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","662","새마을식당","store","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","46","24","14","010-2464-8712","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","663","이디야커피","delivery","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","56","11","34","010-3181-8461","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","664","새마을식당","store","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","7","18","6","010-0509-7820","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","665","BBQ치킨","cafe","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","13","16","18","010-0371-4871","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","666","BBQ치킨","cafe","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","0","16","15","010-2973-6740","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","667","파리바게트","cooking","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","41","22","10","010-3807-7394","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","668","파리바게트","cooking","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","57","20","49","010-8795-8267","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","669","BBQ치킨","cooking","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","43","23","38","010-4820-9013","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","670","파리바게트","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","24","19","12","010-9569-6101","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","671","올리브영","serving","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","12","5","32","010-7115-7978","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","672","CU편의점","store","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","59","19","44","010-2343-5372","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","673","CU편의점","store","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","53","22","7","010-9226-1881","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","674","파리바게트","delivery","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","1","13","59","010-1490-6481","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","675","새마을식당","store","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","55","23","19","010-9482-9677","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","676","CU편의점","serving","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","28","10","15","010-7793-9677","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","677","스타벅스","cafe","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","34","11","26","010-9813-6041","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","678","새마을식당","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","38","7","53","010-4869-2822","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","679","새마을식당","delivery","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","46","24","16","010-3684-3443","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","680","올리브영","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","19","23","59","010-5588-2029","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","681","이디야커피","delivery","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","23","48","24","57","010-9401-1758","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","682","이디야커피","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","23","27","24","10","010-3445-5605","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","683","스타벅스","delivery","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","42","15","57","010-3680-6763","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","684","CU편의점","cooking","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","15","19","22","010-4632-8047","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","685","파리바게트","cooking","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","56","15","22","010-2471-1345","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","686","올리브영","cafe","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","18","18","20","010-9721-2268","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","687","올리브영","store","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","48","9","7","010-2445-0512","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","688","스타벅스","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","53","18","3","010-4780-2837","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","689","BBQ치킨","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","28","21","56","010-6268-9546","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","690","올리브영","serving","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","0","20","43","010-0233-0144","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","691","이디야커피","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","26","22","55","010-8947-9467","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","692","파리바게트","delivery","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","5","23","54","010-0082-9521","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","693","새마을식당","store","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","13","18","38","010-1436-6486","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","694","BBQ치킨","serving","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","33","19","59","010-2170-8206","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","695","BBQ치킨","cooking","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","59","20","17","010-6767-8438","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","696","CU편의점","delivery","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","22","10","51","010-6308-8365","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","697","CU편의점","cooking","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","8","24","24","010-2979-9361","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","698","이디야커피","cafe","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","36","19","26","010-7614-6217","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","699","파리바게트","serving","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","39","18","30","010-2189-2141","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","700","파리바게트","serving","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","8","10","19","010-8004-8032","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","701","CU편의점","serving","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","5","12","14","010-0970-8079","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","702","새마을식당","cafe","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","49","22","9","010-2116-7429","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","703","이디야커피","serving","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","6","18","5","010-1705-7839","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","704","이디야커피","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","30","13","13","010-5032-2566","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","705","이디야커피","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","30","15","1","010-4569-2423","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","706","CU편의점","delivery","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","2","11","35","010-0761-2844","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","707","BBQ치킨","delivery","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","33","15","6","010-3857-7412","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","708","이디야커피","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","17","21","57","010-8185-2975","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","709","올리브영","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","7","9","4","010-0245-5156","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","710","BBQ치킨","cooking","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","15","15","35","010-5843-9189","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","711","이디야커피","cafe","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","53","18","19","010-2894-6781","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","712","스타벅스","delivery","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","22","7","53","010-3133-8007","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","713","BBQ치킨","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","0","24","18","010-5994-4582","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","714","CU편의점","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","57","22","2","010-4876-2092","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","715","BBQ치킨","serving","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","49","17","33","010-1393-9568","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","716","파리바게트","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","16","23","6","010-8297-5674","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","717","BBQ치킨","delivery","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","16","16","34","010-6427-4389","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","718","CU편의점","cafe","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","20","24","40","010-0532-0507","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","719","CU편의점","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","23","24","51","010-7926-8981","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","720","CU편의점","store","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","39","20","33","010-7287-2081","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","721","올리브영","cooking","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","43","13","36","010-3662-7699","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","722","올리브영","cafe","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","30","18","35","010-2788-2578","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","723","파리바게트","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","28","20","55","010-3140-1654","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","724","파리바게트","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","28","8","59","010-8076-4704","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","725","이디야커피","delivery","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","49","20","57","010-6687-7792","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","726","CU편의점","serving","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","46","7","1","010-5058-9715","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","727","이디야커피","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","56","19","46","010-7638-9468","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","728","파리바게트","cafe","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","16","32","21","18","010-1016-0619","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","729","스타벅스","cafe","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","35","13","42","010-0389-0805","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","730","스타벅스","delivery","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","8","3","7","010-4657-8344","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","731","GS25","serving","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","28","17","47","010-7023-0472","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","732","BBQ치킨","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","50","19","35","010-7875-1114","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","733","스타벅스","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","15","18","57","010-9056-6460","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","734","GS25","serving","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","59","21","56","010-9414-4183","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","735","BBQ치킨","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","43","22","26","010-8781-1981","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","736","CU편의점","cooking","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","38","22","12","010-6364-9858","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","737","올리브영","delivery","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","55","21","31","010-8596-3571","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","738","CU편의점","cooking","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","10","23","44","010-2277-4789","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","739","스타벅스","delivery","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","56","24","27","010-8593-0748","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","740","이디야커피","serving","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","53","22","32","010-9177-7341","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","741","CU편의점","cafe","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","56","21","32","010-9826-6222","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","742","GS25","cafe","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","39","18","39","010-1445-1174","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","743","파리바게트","store","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","16","17","42","010-3361-9109","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","744","파리바게트","serving","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","37","22","53","010-2207-4313","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","745","GS25","store","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","24","8","52","010-5100-0465","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","746","GS25","cooking","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","6","24","44","010-9466-9469","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","747","이디야커피","cafe","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","3","18","1","010-3140-9833","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","748","올리브영","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","41","24","6","010-8673-3339","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","749","BBQ치킨","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","14","23","21","010-2051-1903","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","750","CU편의점","cafe","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","36","23","13","010-0670-3361","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","751","올리브영","cafe","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","42","10","37","010-2452-6170","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","752","스타벅스","store","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","45","21","36","010-2749-9698","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","753","이디야커피","store","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","30","19","48","010-7462-2125","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","754","올리브영","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","8","17","10","010-5598-0636","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","755","CU편의점","store","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","9","13","6","010-6298-0641","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","756","파리바게트","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","16","38","17","36","010-6224-2428","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","757","파리바게트","delivery","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","14","22","57","010-3879-5908","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","758","BBQ치킨","serving","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","40","24","25","010-0319-2842","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","759","이디야커피","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","59","23","5","010-3197-2477","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","760","올리브영","serving","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","42","20","31","010-0106-4097","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","761","CU편의점","cafe","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","5","23","30","010-6617-4404","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","762","GS25","cafe","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","45","24","30","010-1258-2380","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","763","스타벅스","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","17","7","22","010-9691-0911","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","764","올리브영","cafe","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","55","24","8","010-4521-9963","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","765","스타벅스","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","43","5","53","010-6595-7891","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","766","올리브영","delivery","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","8","15","30","010-5498-5221","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","767","새마을식당","cafe","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","47","24","19","010-0450-0930","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","768","새마을식당","store","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","47","23","59","010-0638-2924","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","769","GS25","cafe","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","59","24","51","010-4673-1764","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","770","스타벅스","store","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","39","17","27","010-8398-0056","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","771","GS25","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","17","13","38","010-9316-6870","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","772","BBQ치킨","cafe","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","38","24","20","010-0121-7131","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","773","스타벅스","cafe","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","59","23","28","010-4168-5880","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","774","BBQ치킨","serving","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","42","20","40","010-3526-0202","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","775","BBQ치킨","serving","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","53","21","5","010-8981-4409","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","776","올리브영","serving","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","58","24","35","010-3237-3755","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","777","이디야커피","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","4","22","47","010-2284-7415","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","778","GS25","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","10","16","25","010-3286-4596","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","779","새마을식당","serving","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","18","18","11","010-8256-1307","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","780","CU편의점","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","37","22","12","010-1117-7273","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","781","스타벅스","delivery","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","20","24","13","010-5755-9327","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","782","GS25","serving","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","18","24","5","010-4811-3885","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","783","스타벅스","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","2","18","56","010-0346-1089","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","784","스타벅스","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","23","17","53","010-8035-0383","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","785","새마을식당","store","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","24","22","17","010-8289-9264","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","786","스타벅스","serving","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","33","23","51","010-6813-2294","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","787","새마을식당","cooking","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","31","16","44","010-5829-5770","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","788","올리브영","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","30","23","52","010-0873-7224","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","789","GS25","cooking","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","32","22","32","010-6790-8671","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","790","새마을식당","serving","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","39","21","57","010-3777-8351","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","791","BBQ치킨","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","53","19","11","010-9475-3964","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","792","BBQ치킨","cafe","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","30","23","21","010-3366-1927","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","793","GS25","serving","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","20","21","58","010-5445-4280","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","794","새마을식당","cooking","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","8","18","50","010-1217-5770","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","795","GS25","serving","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","48","20","42","010-8187-4963","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","796","CU편의점","cafe","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","48","17","36","010-2423-7380","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","797","스타벅스","cooking","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","46","21","10","010-2406-8383","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","798","올리브영","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","22","19","54","010-1112-6655","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","799","GS25","delivery","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","9","23","57","010-1835-4278","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","800","GS25","delivery","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","27","14","40","010-8217-3838","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","801","GS25","serving","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","54","24","50","010-7726-3875","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","802","GS25","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","4","16","5","010-4860-3526","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","803","파리바게트","delivery","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","23","18","7","010-4747-5086","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","804","파리바게트","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","43","20","10","010-9481-7100","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","805","이디야커피","store","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","11","21","20","010-3056-2222","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","806","CU편의점","cafe","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","47","7","57","010-7432-6318","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","807","스타벅스","serving","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","2","24","22","010-4361-9099","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","808","스타벅스","delivery","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","38","4","27","010-9984-1396","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","809","새마을식당","cafe","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","28","10","11","010-5202-3845","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","810","CU편의점","store","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","50","24","22","010-0764-4064","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","811","스타벅스","cooking","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","40","16","58","010-6726-4860","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","812","새마을식당","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","21","23","40","010-9217-0195","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","813","GS25","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","33","7","29","010-5706-6848","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","814","올리브영","cafe","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","12","13","3","010-6711-7479","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","815","CU편의점","cooking","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","37","21","23","010-0021-8535","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","816","이디야커피","store","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","43","23","3","010-7884-8207","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","817","이디야커피","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","50","23","42","010-0677-2774","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","818","새마을식당","store","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","38","1","35","010-0144-9905","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","819","CU편의점","delivery","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","14","18","48","010-1973-0481","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","820","올리브영","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","4","20","57","010-9429-3228","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","821","스타벅스","delivery","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","10","21","56","010-6745-9587","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","822","BBQ치킨","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","26","20","56","010-6694-9644","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","823","CU편의점","serving","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","37","19","55","010-3785-8805","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","824","새마을식당","cafe","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","49","19","43","010-3804-5035","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","825","CU편의점","delivery","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","35","9","55","010-3340-3516","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","826","GS25","cafe","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","28","7","12","010-1150-6161","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","827","스타벅스","store","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","3","21","19","010-9649-2989","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","828","새마을식당","serving","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","23","24","33","010-6347-2070","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","829","스타벅스","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","15","8","22","010-8379-4812","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","830","BBQ치킨","serving","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","4","12","51","010-8496-2970","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","831","새마을식당","serving","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","31","15","28","010-3073-0998","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","832","파리바게트","serving","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","47","15","7","010-0336-7817","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","833","CU편의점","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","22","24","50","010-8572-3815","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","834","올리브영","delivery","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","48","20","26","010-6117-3267","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","835","이디야커피","store","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","54","1","16","010-3153-5273","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","836","GS25","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","13","11","46","010-0356-4268","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","837","새마을식당","cafe","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","18","23","50","010-5419-1560","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","838","CU편의점","delivery","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","37","20","9","010-3881-6033","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","839","GS25","store","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","1","17","22","1","010-2726-5706","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","840","이디야커피","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","57","24","50","010-7529-7062","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","841","BBQ치킨","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","25","21","59","010-5905-7269","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","842","GS25","cooking","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","52","22","37","010-4802-5435","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","843","새마을식당","cafe","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","13","18","46","010-9265-8279","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","844","CU편의점","store","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","37","23","53","010-7469-2855","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","845","BBQ치킨","cafe","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","23","24","56","010-7909-3199","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","846","CU편의점","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","46","23","50","010-1371-9113","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","847","CU편의점","store","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","36","19","28","010-4803-9523","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","848","이디야커피","serving","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","5","24","46","010-2718-9207","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","849","새마을식당","delivery","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","0","22","58","010-7681-4237","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","850","BBQ치킨","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","47","16","35","010-2229-9125","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","851","스타벅스","store","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","21","21","14","010-7560-3202","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","852","BBQ치킨","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","51","11","51","010-1680-4780","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","853","올리브영","store","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","21","11","48","010-6578-3157","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","854","파리바게트","delivery","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","55","9","25","010-6006-9860","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","855","CU편의점","serving","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","26","24","59","010-3106-9780","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","856","올리브영","delivery","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","53","11","24","010-9656-3407","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","857","CU편의점","cafe","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","10","23","20","010-8022-2051","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","858","스타벅스","cooking","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","24","23","7","010-3747-0703","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","859","이디야커피","cafe","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","25","15","5","010-0187-8355","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","860","스타벅스","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","26","18","50","010-4143-9006","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","861","CU편의점","delivery","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","27","12","48","010-3584-0701","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","862","새마을식당","store","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","44","21","56","010-3651-5352","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","863","파리바게트","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","14","10","57","010-6006-5810","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","864","이디야커피","delivery","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","38","23","49","010-0837-8114","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","865","파리바게트","serving","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","6","21","0","010-2449-7523","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","866","BBQ치킨","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","36","13","41","010-9509-1062","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","867","스타벅스","delivery","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","11","22","53","010-0394-5991","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","868","GS25","store","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","30","17","46","010-8184-6499","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","869","GS25","cooking","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","8","13","15","010-1452-3572","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","870","올리브영","serving","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","14","55","19","32","010-1736-9567","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","871","BBQ치킨","store","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","51","24","20","010-6766-0847","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","872","새마을식당","store","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","15","20","6","010-1679-6863","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","873","CU편의점","cooking","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","23","8","21","010-6003-8448","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","874","올리브영","delivery","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","56","20","14","010-8952-9828","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","875","이디야커피","serving","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","8","7","59","010-4682-8100","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","876","스타벅스","cafe","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","47","12","25","010-7332-2311","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","877","GS25","cooking","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","16","24","37","010-1097-8985","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","878","올리브영","store","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","19","20","57","010-1706-6320","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","879","스타벅스","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","45","24","42","010-4278-4780","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","880","올리브영","serving","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","6","20","34","010-0986-3169","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","881","이디야커피","store","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","42","24","47","010-9346-3707","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","882","CU편의점","cooking","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","51","17","11","010-4004-8770","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","883","BBQ치킨","cooking","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","4","16","23","010-5492-1693","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","884","GS25","delivery","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","58","6","41","010-3448-2176","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","885","CU편의점","cooking","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","25","23","21","010-2356-1340","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","886","올리브영","delivery","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","3","15","46","010-8121-7441","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","887","새마을식당","cafe","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","20","23","10","010-7655-4785","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","888","파리바게트","store","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","20","17","31","010-4704-8109","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","889","GS25","store","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","2","15","10","010-0746-5924","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","890","새마을식당","store","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","53","22","32","010-1474-7939","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","891","CU편의점","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","2","24","55","010-6233-9274","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","892","파리바게트","delivery","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","24","18","1","010-4011-0396","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","893","CU편의점","cooking","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","52","21","17","010-2923-1818","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","894","파리바게트","delivery","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","57","19","29","010-1045-7109","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","895","BBQ치킨","delivery","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","18","24","30","010-6185-6331","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","896","BBQ치킨","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","10","22","33","010-2936-5278","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","897","올리브영","cooking","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","31","18","3","010-6145-1517","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","898","올리브영","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","16","19","18","010-7802-3731","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","899","CU편의점","delivery","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","22","21","38","010-8196-6543","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","900","BBQ치킨","cafe","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","53","10","20","010-3646-4892","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","901","올리브영","cafe","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","13","24","35","010-7293-1879","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","902","새마을식당","delivery","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","2","14","19","010-6970-2272","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","903","BBQ치킨","serving","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","31","11","16","010-2138-8277","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","904","CU편의점","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","53","24","18","010-2072-2432","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","905","이디야커피","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","55","9","48","010-1830-8666","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","906","새마을식당","store","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","53","24","29","010-2968-8687","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","907","새마을식당","delivery","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","45","21","2","010-5453-7598","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","908","파리바게트","store","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","17","20","45","010-4300-4692","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","909","스타벅스","delivery","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","16","24","48","010-2749-2069","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","910","CU편의점","store","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","17","20","43","010-9125-7907","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","911","새마을식당","delivery","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","0","14","33","010-5669-5344","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","912","이디야커피","cooking","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","6","23","15","010-8751-6383","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","913","올리브영","delivery","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","47","16","38","010-8446-0993","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","914","GS25","cooking","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","22","21","55","010-7837-2322","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","915","올리브영","store","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","19","18","47","010-6054-5268","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","916","파리바게트","delivery","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","32","18","58","010-1814-7155","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","917","GS25","cooking","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","7","22","34","010-7328-1443","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","918","이디야커피","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","48","23","23","010-3083-1110","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","919","새마을식당","delivery","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","21","22","10","010-1322-6523","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","920","BBQ치킨","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","11","24","48","010-6987-7602","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","921","올리브영","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","35","11","0","010-1246-6219","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","922","BBQ치킨","serving","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","35","17","47","010-1234-4042","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","923","새마을식당","cafe","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","45","16","31","010-4141-0138","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","924","GS25","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","4","3","40","010-2331-8548","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","925","새마을식당","serving","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","46","21","59","010-9175-7031","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","926","파리바게트","serving","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","31","22","47","010-6330-3914","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","927","GS25","store","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","38","9","44","010-6888-7307","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","928","이디야커피","store","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","51","18","13","010-6058-4782","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","929","GS25","delivery","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","26","23","23","010-2736-6520","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","930","BBQ치킨","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","4","12","14","010-4379-1914","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","931","스타벅스","store","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","27","24","8","010-3356-7488","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","932","GS25","delivery","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","52","14","31","010-9231-2671","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","933","새마을식당","cafe","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","29","16","6","010-4609-7768","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","934","이디야커피","delivery","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","44","17","28","010-4663-2553","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","935","이디야커피","delivery","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","36","16","7","010-9903-1172","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","936","이디야커피","cafe","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","24","23","18","010-0777-2121","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","937","GS25","serving","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","36","11","11","010-7899-8724","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","938","스타벅스","delivery","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","34","19","56","010-7829-1931","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","939","새마을식당","cooking","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","4","19","11","010-1363-6056","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","940","파리바게트","serving","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","5","19","47","010-0590-4305","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","941","BBQ치킨","cooking","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","12","18","3","010-0239-2820","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","942","올리브영","store","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","32","24","1","010-5255-0034","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","943","올리브영","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","43","22","33","010-8070-5197","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","944","올리브영","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","20","22","6","010-0221-7960","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","945","올리브영","cooking","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","32","13","25","010-6739-5168","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","946","GS25","cooking","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","12","24","10","010-3072-7667","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","947","파리바게트","delivery","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","16","44","23","43","010-7006-2882","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","948","스타벅스","serving","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","56","22","46","010-2107-2067","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","949","CU편의점","cafe","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","42","24","0","010-2763-0425","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","950","올리브영","store","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","35","24","26","010-9637-7993","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","951","BBQ치킨","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","2","10","49","010-4337-9516","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","952","GS25","store","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","26","22","21","010-4568-7235","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","953","올리브영","cafe","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","16","24","26","010-3529-9199","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","954","새마을식당","delivery","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","17","19","5","010-3717-7679","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","955","이디야커피","cafe","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","52","21","30","010-7621-6433","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","956","올리브영","cafe","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","15","22","47","010-5743-5726","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","957","CU편의점","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","42","24","23","010-1671-4215","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","958","스타벅스","cooking","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","49","24","3","010-0250-3830","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","959","새마을식당","cooking","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","38","19","23","010-7156-2891","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","960","스타벅스","cafe","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","33","24","14","010-1033-2018","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","961","파리바게트","cafe","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","19","22","12","010-6947-6244","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","962","GS25","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","12","14","2","010-0438-3415","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","963","이디야커피","store","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","27","22","7","010-2592-7570","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","964","올리브영","serving","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","57","14","27","010-1143-0900","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","965","올리브영","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","37","18","33","010-2891-5807","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","966","이디야커피","cooking","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","10","23","53","010-1295-0006","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","967","새마을식당","store","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","37","12","14","010-9487-5594","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","968","스타벅스","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","37","12","59","010-4569-0319","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","969","GS25","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","59","14","20","010-3614-6308","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","970","CU편의점","cafe","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","44","14","24","010-5709-2409","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","971","GS25","delivery","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","15","37","17","2","010-8851-4954","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","972","스타벅스","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","37","16","8","010-5834-8534","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","973","올리브영","cooking","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","5","17","23","010-3802-5689","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","974","GS25","delivery","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","53","20","26","010-1484-5229","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","975","BBQ치킨","serving","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","4","22","43","010-2455-1616","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","976","이디야커피","delivery","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","18","15","19","010-4150-8279","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","977","GS25","delivery","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","36","23","58","010-7918-3693","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","978","스타벅스","cooking","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","51","12","19","010-6639-5366","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","979","스타벅스","serving","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","3","24","6","010-1614-1562","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","980","파리바게트","delivery","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","4","7","12","010-0821-8399","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","981","GS25","delivery","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","16","6","7","010-9590-1174","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","982","스타벅스","store","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","20","22","9","010-3911-3036","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","983","GS25","serving","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","32","12","49","010-4109-3723","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","984","올리브영","cafe","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","24","15","17","010-7755-1966","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","985","BBQ치킨","cafe","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","52","24","31","010-4405-4292","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","986","BBQ치킨","store","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","20","24","11","010-8475-2774","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","987","BBQ치킨","serving","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","57","23","48","010-5472-8733","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","988","이디야커피","cooking","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","10","17","23","010-2450-6119","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","989","BBQ치킨","store","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","55","19","5","010-3146-6397","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","990","GS25","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","28","22","2","010-4822-6087","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","991","올리브영","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","42","18","27","010-3070-0864","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","992","이디야커피","serving","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","18","20","28","010-1727-2727","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","993","새마을식당","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","47","17","5","010-4326-0619","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","994","새마을식당","cafe","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","11","16","9","010-6512-6175","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","995","CU편의점","delivery","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","32","23","12","010-4679-1121","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","996","파리바게트","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","24","22","46","010-9069-6744","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","997","올리브영","cooking","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","35","5","13","010-7506-9799","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","998","이디야커피","serving","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","19","12","10","010-5497-2115","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","999","새마을식당","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","34","23","53","010-4829-0216","망포동","17000");


    }

    void writeDB(String employerIdToken, String id, String name, String job, String day, String education, String eperiod, String gender, String image, String period, String startHour, String startMinute, String endHour, String endMinute, String phoneNumber, String region, String wage) {

        // ID
        mDatabase.child("ID").child(id).child("id").setValue(id);
        mDatabase.child("ID").child(id).child("employerIdToken").setValue(employerIdToken);
        mDatabase.child("ID").child(id).child("name").setValue(name);
        mDatabase.child("ID").child(id).child("job").setValue(job);
        mDatabase.child("ID").child(id).child("day").setValue(day);
        mDatabase.child("ID").child(id).child("startHour").setValue(startHour);
        mDatabase.child("ID").child(id).child("startMinute").setValue(startMinute);
        mDatabase.child("ID").child(id).child("endHour").setValue(endHour);
        mDatabase.child("ID").child(id).child("endMinute").setValue(endMinute);
        mDatabase.child("ID").child(id).child("phoneNumber").setValue(phoneNumber);
        mDatabase.child("ID").child(id).child("region").setValue(region);
        mDatabase.child("ID").child(id).child("education").setValue(education);
        mDatabase.child("ID").child(id).child("eperiod").setValue(eperiod);
        mDatabase.child("ID").child(id).child("gender").setValue(gender);
        mDatabase.child("ID").child(id).child("image").setValue(image);
        mDatabase.child("ID").child(id).child("period").setValue(period);
        mDatabase.child("ID").child(id).child("wage").setValue(wage);



        //Job
        mDatabase.child("Job").child(job).push().setValue(id);

        //Day
        mDatabase.child("Day").child(day).push().setValue(id);

        //Region
        mDatabase.child("Region").child(region).push().setValue(id);

    }
}