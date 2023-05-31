package com.company.albatross;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


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
    private int addViewIndex = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initDB(); // 데이터 생성 함수
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
        introTextView.setTextSize(8);
        introTextView.setGravity(Gravity.CENTER);
        introTextView.setTextColor(Color.GRAY);
        linearLayout.addView(introTextView);

        linearLayout.addView(makeCustomJobSearchLayout(albaNumber));

        Button addButton = new Button(this);
        addButton.setText("+");
        addButton.setOnClickListener(view -> {
            linearLayout.addView(makeCustomJobSearchLayout(++albaNumber), addViewIndex++);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    //scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
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
        //ID, Day, Job, Region 노드 삭제
        mDatabase.child("Day").removeValue();
        mDatabase.child("ID").removeValue();
        mDatabase.child("Job").removeValue();
        mDatabase.child("Region").removeValue();
        //쓰기
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","000","이디야커피","store","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","36","19","9","010-7252-5289","매탄동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","001","스타벅스","delivery","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","24","14","4","010-8891-7018","하동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","002","CU편의점","cafe","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","45","23","52","010-2269-7819","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","003","CU편의점","serving","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","10","22","28","010-2613-1633","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","004","이디야커피","cooking","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","13","20","54","010-6115-7232","원천동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","005","새마을식당","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","4","23","23","010-8286-0815","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","006","CU편의점","store","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","10","23","43","010-0165-0205","원천동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","007","GS25","delivery","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","0","15","50","010-9493-1736","하동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","008","올리브영","cafe","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","56","16","59","010-3317-3289","태장동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","009","CU편의점","store","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","13","21","20","010-0681-5572","광교동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","010","이디야커피","cooking","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","45","20","45","010-8695-8568","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","011","스타벅스","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","4","17","1","010-7459-8411","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","012","GS25","store","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","8","21","7","010-7478-1658","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","013","GS25","delivery","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","31","22","26","010-0163-1509","광교동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","014","GS25","cooking","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","15","15","21","010-9693-8005","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","015","올리브영","delivery","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","55","20","42","010-6863-9469","이의동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","016","GS25","cooking","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","27","9","17","010-0276-8501","광교동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","017","올리브영","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","37","9","41","010-9079-1831","광교동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","018","이디야커피","store","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","27","15","48","010-9124-0280","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","019","올리브영","cafe","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","51","23","24","010-3462-5715","매탄동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","020","CU편의점","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","42","19","1","010-7363-2309","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","021","BBQ치킨","store","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","2","22","54","010-7917-8057","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","022","GS25","store","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","8","16","43","010-6348-0383","망포동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","023","새마을식당","store","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","44","13","59","010-7380-3105","신동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","024","BBQ치킨","store","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","19","9","7","010-0582-0291","매탄동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","025","GS25","store","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","24","15","51","010-8247-3176","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","026","파리바게트","delivery","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","33","21","49","010-0037-5107","하동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","027","이디야커피","serving","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","22","23","4","010-2557-4645","광교동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","028","CU편의점","cafe","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","54","24","36","010-3660-4691","하동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","029","새마을식당","serving","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","23","15","37","010-5170-8541","이의동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","030","이디야커피","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","20","12","17","010-3081-6199","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","031","CU편의점","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","55","24","38","010-6593-5027","태장동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","032","파리바게트","cooking","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","45","21","9","010-2812-1975","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","033","이디야커피","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","43","20","0","010-1850-1706","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","034","BBQ치킨","serving","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","11","20","13","010-9411-3175","이의동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","035","GS25","store","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","2","24","33","010-1000-4471","망포동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","036","BBQ치킨","cooking","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","8","18","35","010-1795-5533","광교동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","037","CU편의점","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","17","22","32","010-3840-6114","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","038","GS25","serving","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","58","17","16","010-4005-9037","영통동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","039","파리바게트","serving","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","36","24","45","010-6370-8907","매탄동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","040","새마을식당","store","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","43","22","25","010-7982-1405","원천동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","041","BBQ치킨","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","36","11","1","010-5333-0637","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","042","이디야커피","serving","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","12","12","8","010-8393-7549","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","043","BBQ치킨","cafe","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","10","11","9","010-2460-9889","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","044","CU편의점","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","46","22","25","010-9593-5818","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","045","파리바게트","serving","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","51","15","0","010-8104-3105","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","046","CU편의점","cafe","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","32","16","28","010-1916-8141","하동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","047","파리바게트","cooking","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","57","7","3","010-0333-9847","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","048","이디야커피","cafe","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","36","20","28","010-7280-2334","하동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","049","새마을식당","store","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","31","17","43","010-8881-5385","하동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","050","올리브영","store","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","9","14","42","010-2925-7170","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","051","이디야커피","cafe","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","48","22","26","010-1643-8132","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","052","CU편의점","delivery","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","17","13","9","010-0587-5859","태장동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","053","이디야커피","cooking","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","31","23","41","010-6883-3856","신동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","054","올리브영","cafe","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","50","21","49","010-0255-6668","하동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","055","이디야커피","cooking","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","43","16","45","010-8157-6539","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","056","GS25","store","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","17","49","22","52","010-7043-1629","이의동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","057","CU편의점","delivery","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","46","23","40","010-6289-9119","광교동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","058","GS25","cafe","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","17","34","18","39","010-9808-1682","영통동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","059","이디야커피","cooking","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","51","22","5","010-8604-3477","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","060","새마을식당","delivery","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","35","14","40","010-9521-3324","신동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","061","새마을식당","cafe","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","56","10","29","010-6504-2278","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","062","파리바게트","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","55","17","2","010-2350-7857","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","063","새마을식당","delivery","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","45","11","1","010-4158-3156","이의동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","064","올리브영","serving","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","1","20","56","010-0371-6736","망포동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","065","파리바게트","serving","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","31","21","55","010-9520-2728","원천동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","066","이디야커피","cooking","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","42","17","41","010-3351-2172","영통동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","067","새마을식당","cafe","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","4","6","50","010-0518-7118","영통동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","068","이디야커피","delivery","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","58","18","50","010-2835-9382","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","069","BBQ치킨","cafe","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","26","24","44","010-2418-0876","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","070","CU편의점","store","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","50","12","8","010-8445-0896","영통동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","071","올리브영","cooking","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","23","24","50","010-5621-8060","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","072","GS25","serving","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","56","20","26","010-3835-1988","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","073","파리바게트","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","58","24","33","010-4028-8044","신동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","074","새마을식당","store","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","40","20","49","010-5635-9311","원천동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","075","올리브영","cafe","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","2","24","36","010-0106-8054","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","076","CU편의점","serving","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","36","16","31","010-2377-0151","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","077","CU편의점","cooking","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","53","6","44","010-3917-8618","망포동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","078","올리브영","delivery","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","51","20","18","010-3629-8439","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","079","파리바게트","delivery","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","40","7","35","010-4031-4190","이의동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","080","올리브영","delivery","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","28","23","31","010-7900-6495","영통동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","081","BBQ치킨","cafe","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","12","24","58","010-9301-4543","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","082","스타벅스","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","15","10","24","010-7557-1286","광교동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","083","새마을식당","delivery","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","38","17","14","010-6655-6753","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","084","올리브영","cafe","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","5","16","18","010-5700-2821","하동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","085","스타벅스","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","46","21","24","010-2739-6857","매탄동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","086","스타벅스","store","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","37","17","54","010-1913-7320","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","087","새마을식당","store","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","19","21","45","010-2842-9281","신동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","088","GS25","delivery","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","59","20","41","010-0762-2594","하동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","089","이디야커피","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","36","10","50","010-5634-7592","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","090","파리바게트","cafe","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","3","10","23","010-6644-8797","신동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","091","올리브영","delivery","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","22","20","18","010-2747-1167","하동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","092","GS25","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","15","5","19","46","010-6735-1743","신동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","093","BBQ치킨","cooking","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","11","4","38","010-2897-1160","광교동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","094","CU편의점","serving","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","55","17","6","010-4074-8689","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","095","올리브영","cooking","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","19","13","32","010-5725-6228","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","096","GS25","serving","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","15","32","16","18","010-3573-5423","태장동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","097","BBQ치킨","cafe","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","44","24","21","010-8852-2712","매탄동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","098","새마을식당","serving","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","21","20","39","010-3645-5803","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","099","GS25","delivery","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","50","24","40","010-8342-3404","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","100","CU편의점","serving","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","7","23","27","010-3956-6947","태장동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","101","BBQ치킨","cafe","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","35","20","41","010-2288-6304","하동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","102","스타벅스","cafe","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","17","16","14","010-1604-5381","하동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","103","올리브영","delivery","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","27","20","0","010-7082-2773","신동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","104","스타벅스","cooking","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","5","17","25","010-6300-6147","신동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","105","이디야커피","store","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","42","12","41","010-2147-5358","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","106","GS25","delivery","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","17","24","44","010-7809-3589","하동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","107","GS25","cooking","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","46","23","11","010-0603-4180","원천동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","108","새마을식당","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","52","13","40","010-0555-6622","태장동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","109","BBQ치킨","cafe","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","0","6","33","010-2760-4100","영통동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","110","올리브영","cooking","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","53","17","56","010-2716-5640","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","111","올리브영","delivery","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","10","23","0","010-3471-7520","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","112","파리바게트","delivery","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","58","13","54","010-6892-2837","광교동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","113","GS25","cooking","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","21","22","16","010-2283-5163","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","114","올리브영","delivery","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","56","23","57","010-3243-0990","광교동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","115","스타벅스","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","8","21","11","010-0170-6569","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","116","BBQ치킨","store","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","19","19","14","010-0338-4985","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","117","올리브영","store","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","14","4","48","010-6119-3025","망포동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","118","스타벅스","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","21","5","48","010-4886-7396","망포동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","119","스타벅스","store","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","17","20","1","010-7253-0126","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","120","이디야커피","cooking","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","7","23","13","010-4022-9830","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","121","GS25","cafe","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","20","24","59","010-3080-2398","광교동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","122","GS25","cooking","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","6","22","49","010-8432-5745","신동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","123","CU편의점","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","46","18","0","010-0859-4596","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","124","올리브영","delivery","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","49","18","19","010-6325-9457","매탄동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","125","올리브영","cafe","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","10","18","19","010-3235-0187","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","126","올리브영","cafe","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","8","21","22","010-1465-9641","영통동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","127","파리바게트","cafe","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","13","22","22","010-4380-9386","신동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","128","새마을식당","delivery","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","28","24","51","010-8440-1777","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","129","GS25","serving","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","59","24","29","010-3089-5692","신동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","130","파리바게트","cooking","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","42","19","9","010-5614-1244","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","131","CU편의점","cafe","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","2","24","39","010-9553-5276","광교동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","132","새마을식당","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","33","15","50","010-0698-2960","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","133","CU편의점","delivery","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","10","23","25","010-5291-3486","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","134","올리브영","cooking","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","13","10","53","010-9654-6660","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","135","스타벅스","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","44","21","4","010-5763-2181","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","136","올리브영","serving","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","27","23","13","010-1298-6478","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","137","파리바게트","cafe","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","21","15","22","010-0760-6167","하동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","138","올리브영","cafe","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","52","24","52","010-2974-6011","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","139","올리브영","serving","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","22","18","40","010-0828-2755","영통동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","140","BBQ치킨","delivery","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","22","19","35","010-7348-6151","매탄동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","141","새마을식당","cafe","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","9","23","38","010-0395-9858","하동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","142","GS25","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","7","24","7","010-3276-2043","하동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","143","스타벅스","serving","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","55","24","36","010-0142-6273","하동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","144","BBQ치킨","delivery","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","29","14","15","010-2857-3778","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","145","GS25","serving","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","58","15","47","010-5144-8337","하동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","146","BBQ치킨","cooking","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","17","21","41","010-3501-9584","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","147","스타벅스","store","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","6","17","37","010-1479-6265","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","148","파리바게트","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","33","21","11","010-7202-0349","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","149","BBQ치킨","cafe","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","19","19","0","010-6797-3769","이의동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","150","파리바게트","cooking","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","51","16","0","010-9974-4966","태장동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","151","BBQ치킨","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","36","20","54","010-8943-5058","영통동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","152","파리바게트","delivery","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","42","10","56","010-2429-6668","하동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","153","GS25","cafe","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","2","18","30","010-8610-5565","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","154","이디야커피","cafe","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","18","12","56","010-9121-9250","이의동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","155","이디야커피","delivery","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","52","23","48","010-5487-1311","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","156","GS25","cooking","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","36","20","41","010-7127-7484","태장동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","157","파리바게트","store","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","22","18","45","010-3616-4771","망포동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","158","BBQ치킨","store","sat","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","21","22","53","010-2484-0724","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","159","올리브영","cafe","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","17","24","15","010-4236-0281","신동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","160","새마을식당","store","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","0","24","35","010-1201-4453","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","161","GS25","cooking","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","33","13","16","010-4612-3629","하동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","162","파리바게트","serving","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","7","24","4","010-2462-4158","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","163","GS25","cafe","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","2","22","25","010-1253-2066","광교동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","164","스타벅스","cooking","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","10","13","18","010-5573-5340","망포동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","165","올리브영","cooking","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","46","17","5","010-0426-0299","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","166","GS25","cafe","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","28","12","4","010-2497-5220","영통동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","167","BBQ치킨","cafe","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","18","7","14","010-3647-8137","하동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","168","BBQ치킨","cafe","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","45","14","26","010-1241-6597","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","169","스타벅스","delivery","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","14","17","39","010-3332-7552","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","170","새마을식당","delivery","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","11","17","29","010-4550-2439","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","171","BBQ치킨","cooking","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","55","20","28","010-1202-7515","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","172","CU편의점","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","9","23","31","010-9397-0191","광교동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","173","올리브영","serving","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","21","18","15","010-5128-4379","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","174","스타벅스","cafe","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","48","24","13","010-7565-1885","하동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","175","올리브영","delivery","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","1","23","39","010-5733-1725","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","176","올리브영","cooking","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","45","16","2","010-7572-0159","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","177","CU편의점","serving","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","48","13","24","010-1133-7926","영통동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","178","새마을식당","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","29","23","20","010-3608-4943","신동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","179","파리바게트","cooking","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","12","24","37","010-8345-4243","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","180","CU편의점","cafe","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","19","2","8","010-4661-3434","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","181","스타벅스","cafe","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","20","20","7","010-7637-3148","태장동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","182","GS25","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","18","24","15","010-2919-1476","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","183","스타벅스","cooking","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","41","23","15","010-9905-4658","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","184","BBQ치킨","cooking","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","4","14","55","010-7021-8171","망포동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","185","이디야커피","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","12","23","39","010-4497-9469","매탄동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","186","GS25","cafe","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","11","20","39","010-1133-5405","광교동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","187","이디야커피","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","44","23","31","010-8575-9598","망포동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","188","GS25","serving","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","2","20","51","010-0367-6586","신동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","189","BBQ치킨","cafe","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","17","11","37","010-5263-9951","하동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","190","새마을식당","delivery","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","25","13","41","010-0331-4974","신동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","191","새마을식당","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","58","14","14","010-4523-4261","원천동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","192","새마을식당","cafe","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","24","17","33","010-9441-1126","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","193","BBQ치킨","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","58","21","20","010-7892-8381","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","194","스타벅스","cafe","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","2","17","49","010-0237-7939","영통동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","195","GS25","cooking","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","7","23","0","010-4832-2735","영통동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","196","GS25","delivery","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","24","20","12","010-9502-9484","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","197","올리브영","serving","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","12","12","49","010-4111-1479","영통동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","198","이디야커피","store","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","23","16","26","010-5615-8100","원천동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","199","올리브영","serving","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","44","23","32","010-1245-2595","하동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","200","파리바게트","store","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","6","7","15","010-5609-8867","이의동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","201","GS25","cafe","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","20","23","55","010-2515-4996","태장동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","202","올리브영","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","43","23","38","010-6709-3958","매탄동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","203","새마을식당","cafe","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","31","22","9","010-5922-0447","신동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","204","GS25","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","35","21","13","010-2595-3949","원천동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","205","BBQ치킨","delivery","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","55","20","44","010-5757-0547","광교동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","206","올리브영","cooking","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","47","24","39","010-6075-5265","광교동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","207","BBQ치킨","serving","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","50","15","51","010-7446-7586","영통동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","208","새마을식당","store","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","8","21","15","010-0293-2660","매탄동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","209","파리바게트","serving","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","18","20","34","010-6796-1268","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","210","BBQ치킨","cafe","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","48","17","29","010-0086-8641","영통동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","211","스타벅스","store","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","23","23","54","010-7335-6347","망포동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","212","새마을식당","store","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","49","14","5","010-2078-3016","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","213","올리브영","store","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","50","14","0","010-7463-3035","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","214","스타벅스","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","31","19","30","010-5379-5027","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","215","올리브영","cooking","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","12","14","55","010-9929-5022","매탄동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","216","스타벅스","store","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","23","17","21","010-8973-4764","망포동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","217","스타벅스","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","56","22","23","010-6215-9422","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","218","GS25","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","11","24","3","010-9850-0460","광교동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","219","파리바게트","cooking","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","31","15","12","010-9086-8713","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","220","스타벅스","cooking","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","41","15","6","010-1664-9627","광교동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","221","GS25","cafe","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","38","22","35","010-4954-8435","망포동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","222","스타벅스","cafe","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","28","23","59","010-2272-6971","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","223","스타벅스","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","49","15","8","010-8593-1734","원천동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","224","GS25","cooking","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","33","22","31","010-7224-8334","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","225","CU편의점","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","30","22","23","010-0289-4724","영통동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","226","GS25","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","5","20","0","010-6338-3151","광교동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","227","파리바게트","serving","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","40","21","36","010-6443-4694","태장동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","228","스타벅스","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","28","22","25","010-8059-7618","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","229","새마을식당","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","42","5","39","010-4131-0873","이의동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","230","GS25","cafe","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","56","24","0","010-4966-7265","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","231","GS25","store","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","37","20","44","010-0974-7265","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","232","스타벅스","serving","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","38","14","17","010-4431-5851","영통동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","233","스타벅스","delivery","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","49","20","9","010-2667-6214","신동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","234","새마을식당","cafe","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","30","15","10","010-6908-5172","광교동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","235","BBQ치킨","cooking","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","40","18","40","010-9756-2647","망포동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","236","BBQ치킨","delivery","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","21","25","23","18","010-7295-3122","이의동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","237","GS25","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","4","23","36","010-8899-8974","원천동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","238","올리브영","cooking","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","0","17","37","010-7064-6476","매탄동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","239","파리바게트","serving","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","32","12","36","010-3424-0369","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","240","새마을식당","cafe","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","54","13","58","010-6622-7950","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","241","CU편의점","cafe","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","59","22","27","010-4430-1855","원천동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","242","이디야커피","delivery","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","50","22","11","010-5849-7673","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","243","이디야커피","cooking","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","13","23","16","010-2961-3501","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","244","이디야커피","delivery","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","57","24","40","010-8648-4758","원천동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","245","CU편의점","store","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","25","14","45","010-7099-6950","태장동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","246","새마을식당","serving","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","56","21","57","010-7303-2301","원천동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","247","스타벅스","cooking","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","12","19","43","010-5093-0505","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","248","GS25","cooking","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","9","21","42","010-1249-3171","영통동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","249","새마을식당","store","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","47","16","26","010-5081-3142","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","250","이디야커피","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","52","24","37","010-6623-3571","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","251","GS25","delivery","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","12","22","55","010-7525-1557","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","252","파리바게트","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","3","21","55","010-0387-2380","망포동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","253","GS25","serving","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","59","24","3","010-5757-8297","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","254","GS25","cooking","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","11","23","41","010-2674-9231","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","255","새마을식당","cafe","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","42","11","43","010-1258-1134","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","256","CU편의점","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","3","22","49","010-9021-8914","태장동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","257","파리바게트","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","27","16","38","010-8215-0977","태장동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","258","새마을식당","cafe","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","24","14","36","010-3483-9972","영통동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","259","스타벅스","store","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","38","20","53","010-5988-2718","원천동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","260","새마을식당","store","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","58","23","38","010-8076-8351","영통동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","261","새마을식당","serving","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","51","23","22","010-3788-9660","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","262","스타벅스","cafe","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","7","14","42","010-3348-1850","망포동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","263","올리브영","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","22","13","28","010-6842-7658","원천동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","264","BBQ치킨","serving","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","13","14","9","010-1203-3362","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","265","스타벅스","delivery","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","20","6","21","13","010-1288-4766","영통동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","266","GS25","cafe","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","25","17","11","010-9491-8420","매탄동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","267","이디야커피","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","43","18","39","010-4969-0667","영통동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","268","파리바게트","store","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","2","23","8","010-4703-9184","신동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","269","새마을식당","delivery","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","21","17","10","010-6417-7908","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","270","CU편의점","delivery","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","40","24","34","010-4058-2326","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","271","파리바게트","store","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","17","10","41","010-8374-3166","광교동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","272","올리브영","delivery","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","48","14","2","010-7241-8691","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","273","스타벅스","store","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","57","24","49","010-6950-0049","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","274","BBQ치킨","cooking","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","52","24","33","010-8183-2399","원천동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","275","새마을식당","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","45","11","55","010-3444-2833","망포동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","276","CU편의점","delivery","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","13","24","37","010-1093-0383","망포동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","277","이디야커피","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","27","22","54","010-8256-0333","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","278","파리바게트","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","40","14","23","010-5570-9577","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","279","BBQ치킨","store","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","21","24","41","010-9577-2341","원천동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","280","GS25","store","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","32","22","24","010-3125-4796","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","281","올리브영","cafe","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","36","24","51","010-8988-1477","이의동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","282","올리브영","serving","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","16","24","42","010-9268-1697","하동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","283","이디야커피","store","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","4","20","27","010-9216-2515","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","284","파리바게트","serving","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","0","23","34","010-2530-0578","영통동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","285","스타벅스","delivery","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","20","6","23","55","010-8201-9155","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","286","새마을식당","cooking","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","16","22","45","010-7638-1876","신동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","287","새마을식당","delivery","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","14","16","40","010-1465-9018","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","288","새마을식당","serving","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","44","12","34","010-6136-2357","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","289","올리브영","serving","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","53","21","57","010-1161-8103","이의동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","290","CU편의점","delivery","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","13","23","6","010-1901-0341","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","291","이디야커피","cooking","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","23","32","24","8","010-4669-1682","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","292","이디야커피","serving","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","49","16","17","010-7032-0988","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","293","BBQ치킨","delivery","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","1","13","52","010-3895-9279","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","294","CU편의점","cafe","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","3","18","31","010-1527-1055","광교동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","295","파리바게트","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","54","24","9","010-0832-2109","영통동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","296","CU편의점","serving","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","15","23","37","010-4778-4030","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","297","새마을식당","cooking","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","49","12","17","010-6673-5941","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","298","BBQ치킨","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","21","13","16","010-0035-7671","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","299","CU편의점","delivery","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","15","14","55","010-4796-1833","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","300","CU편의점","cooking","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","51","22","8","010-0583-9455","광교동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","301","올리브영","cooking","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","12","18","16","010-1832-3351","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","302","파리바게트","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","56","22","53","010-3049-5049","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","303","새마을식당","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","39","12","35","010-6693-7830","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","304","GS25","store","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","19","18","45","010-1047-0889","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","305","BBQ치킨","cafe","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","37","9","23","010-7412-3365","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","306","새마을식당","store","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","8","10","24","13","010-9152-4210","망포동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","307","올리브영","cafe","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","35","23","6","010-8780-9472","영통동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","308","GS25","serving","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","17","19","5","010-4736-2044","광교동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","309","스타벅스","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","7","18","24","46","010-6432-3046","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","310","새마을식당","delivery","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","35","23","16","010-8293-5417","광교동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","311","올리브영","delivery","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","37","20","9","010-2422-2649","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","312","GS25","cafe","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","23","14","10","010-1251-6285","원천동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","313","GS25","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","1","22","56","010-8515-5630","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","314","올리브영","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","36","23","11","010-5255-9471","매탄동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","315","CU편의점","store","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","1","15","8","010-2630-3361","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","316","올리브영","delivery","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","35","12","56","010-9920-7937","신동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","317","올리브영","delivery","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","57","13","31","010-8583-9359","매탄동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","318","새마을식당","delivery","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","45","22","16","010-2866-3246","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","319","올리브영","cafe","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","35","15","9","010-1891-8211","망포동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","320","이디야커피","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","5","16","41","010-3538-4707","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","321","새마을식당","cooking","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","12","12","58","010-7355-2273","광교동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","322","이디야커피","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","11","20","56","010-4890-2896","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","323","새마을식당","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","23","22","34","010-4139-0919","신동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","324","이디야커피","cooking","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","13","12","19","36","010-3021-4390","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","325","CU편의점","store","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","24","17","25","010-2263-9825","태장동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","326","파리바게트","cooking","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","7","20","7","010-3335-7076","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","327","BBQ치킨","serving","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","1","11","25","010-1678-9117","망포동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","328","이디야커피","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","40","15","42","010-3541-2078","원천동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","329","BBQ치킨","cooking","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","40","13","14","010-2978-3766","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","330","이디야커피","cafe","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","4","21","21","010-8501-7001","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","331","이디야커피","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","10","11","32","010-8078-3119","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","332","GS25","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","40","21","17","010-7902-6733","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","333","이디야커피","cooking","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","13","18","24","010-1817-0600","하동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","334","CU편의점","cooking","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","52","19","12","010-0994-1433","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","335","새마을식당","store","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","14","23","4","010-6573-1722","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","336","파리바게트","serving","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","38","24","35","010-5822-3013","이의동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","337","이디야커피","cafe","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","17","11","44","010-4562-9912","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","338","BBQ치킨","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","41","24","23","010-8348-3377","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","339","올리브영","store","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","18","21","21","010-7481-4115","이의동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","340","GS25","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","36","22","3","010-3160-3272","영통동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","341","GS25","delivery","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","29","22","59","010-6352-7216","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","342","새마을식당","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","4","16","4","010-0248-0954","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","343","CU편의점","serving","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","45","19","41","010-1788-1525","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","344","이디야커피","cafe","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","49","18","8","010-6517-6654","하동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","345","이디야커피","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","40","22","40","010-6499-4027","하동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","346","GS25","store","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","43","23","27","010-7924-5575","망포동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","347","새마을식당","serving","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","38","11","35","010-8712-1636","신동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","348","GS25","serving","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","33","20","13","010-6924-2484","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","349","이디야커피","cafe","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","45","18","22","010-9335-4251","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","350","이디야커피","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","39","23","53","010-1381-4803","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","351","GS25","serving","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","8","23","9","010-2999-0090","영통동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","352","올리브영","store","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","38","17","44","010-2736-3093","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","353","BBQ치킨","cooking","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","45","24","54","010-3054-3129","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","354","올리브영","serving","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","22","9","12","010-5690-0009","하동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","355","BBQ치킨","store","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","19","19","40","010-0085-3554","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","356","CU편의점","delivery","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","37","24","56","010-9513-8450","태장동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","357","올리브영","store","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","36","20","3","010-9579-0421","망포동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","358","이디야커피","cooking","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","33","17","49","010-2858-8104","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","359","이디야커피","cafe","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","44","6","53","010-5801-7126","신동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","360","새마을식당","cafe","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","4","18","2","010-4294-8855","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","361","CU편의점","cooking","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","32","23","34","010-1484-5255","영통동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","362","CU편의점","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","4","23","11","010-5432-8947","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","363","CU편의점","serving","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","26","23","47","010-5269-1527","매탄동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","364","CU편의점","cafe","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","3","19","47","010-2893-1380","광교동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","365","올리브영","store","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","37","22","52","010-1279-1152","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","366","파리바게트","cafe","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","48","9","33","010-0439-2584","매탄동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","367","BBQ치킨","store","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","14","21","10","010-0074-3672","원천동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","368","GS25","serving","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","16","20","49","010-2014-3793","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","369","파리바게트","cafe","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","56","16","0","010-1255-4469","태장동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","370","올리브영","cafe","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","58","14","27","010-6182-2822","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","371","BBQ치킨","cafe","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","29","9","27","010-2610-8891","하동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","372","스타벅스","serving","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","11","53","22","13","010-6449-2116","매탄동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","373","새마을식당","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","7","13","57","010-7851-7448","하동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","374","파리바게트","cafe","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","49","15","24","010-1620-8036","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","375","새마을식당","serving","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","7","14","0","010-5490-1191","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","376","스타벅스","delivery","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","54","20","50","010-7114-2790","하동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","377","파리바게트","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","19","12","12","010-6406-1133","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","378","새마을식당","delivery","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","47","9","54","010-3681-4973","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","379","올리브영","cooking","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","14","15","23","010-8006-7418","하동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","380","GS25","store","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","28","7","8","010-1282-9789","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","381","파리바게트","serving","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","22","55","24","18","010-3351-9153","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","382","스타벅스","cooking","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","0","18","6","010-3133-6452","태장동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","383","스타벅스","cafe","wen","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","19","24","48","010-6167-2462","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","384","올리브영","cafe","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","26","20","8","010-9841-6887","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","385","BBQ치킨","store","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","54","17","5","010-9087-0761","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","386","GS25","cafe","tue","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","3","15","43","010-6508-7435","영통동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","387","파리바게트","cooking","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","25","23","44","010-1040-9094","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","388","올리브영","delivery","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","22","17","40","010-2924-0392","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","389","파리바게트","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","2","16","44","010-2880-9502","원천동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","390","새마을식당","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","53","23","53","010-9863-6618","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","391","CU편의점","cooking","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","26","16","42","010-2634-7524","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","392","스타벅스","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","13","24","29","010-8219-2584","광교동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","393","올리브영","store","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","44","7","44","010-0675-6429","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","394","BBQ치킨","serving","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","43","5","47","010-9920-4239","광교동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","395","BBQ치킨","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","15","11","59","010-9000-1461","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","396","올리브영","cafe","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","30","11","12","010-1483-2786","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","397","새마을식당","cooking","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","18","13","28","010-4422-7372","망포동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","398","올리브영","cooking","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","43","23","20","010-0444-7772","이의동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","399","스타벅스","delivery","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","7","6","4","010-9375-0807","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","400","새마을식당","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","22","22","10","010-7797-3738","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","401","CU편의점","store","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","15","20","2","010-6237-2572","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","402","BBQ치킨","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","33","18","56","010-5981-0559","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","403","CU편의점","serving","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","45","18","49","010-6730-3165","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","404","파리바게트","delivery","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","27","11","0","010-7565-6348","원천동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","405","파리바게트","store","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","59","22","43","010-3181-6064","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","406","새마을식당","cooking","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","48","24","47","010-4943-6035","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","407","올리브영","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","30","9","43","010-3699-2508","하동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","408","파리바게트","store","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","50","13","29","010-0172-3439","원천동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","409","스타벅스","serving","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","54","21","43","010-1814-2263","망포동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","410","파리바게트","delivery","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","22","49","23","42","010-7476-5558","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","411","올리브영","delivery","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","53","23","58","010-3677-4149","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","412","새마을식당","cooking","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","30","21","30","010-0757-5402","원천동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","413","GS25","store","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","29","8","15","010-6925-6557","원천동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","414","파리바게트","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","47","11","23","010-2661-3310","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","415","GS25","store","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","15","14","19","54","010-5965-2359","이의동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","416","CU편의점","store","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","11","21","10","010-2557-0205","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","417","새마을식당","cooking","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","55","23","20","010-8993-8242","망포동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","418","새마을식당","delivery","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","50","21","52","010-3474-2918","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","419","파리바게트","cafe","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","23","9","22","010-7722-5359","하동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","420","CU편의점","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","30","23","5","010-7385-7054","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","421","올리브영","store","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","11","21","7","010-8560-7241","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","422","GS25","cafe","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","48","18","18","010-4695-4848","이의동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","423","파리바게트","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","4","13","51","010-9610-3553","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","424","스타벅스","delivery","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","50","24","3","010-6636-4523","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","425","새마을식당","cooking","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","5","24","34","010-5880-1919","신동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","426","이디야커피","delivery","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","40","19","30","010-6179-0110","신동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","427","BBQ치킨","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","12","23","47","010-8043-8167","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","428","올리브영","delivery","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","50","21","48","010-5301-2363","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","429","CU편의점","serving","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","10","19","10","010-6346-3121","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","430","새마을식당","store","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","16","47","23","19","010-4247-4675","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","431","스타벅스","store","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","46","20","37","010-4488-2655","하동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","432","CU편의점","delivery","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","15","19","0","010-6221-8370","광교동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","433","이디야커피","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","55","19","56","010-3376-0624","이의동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","434","스타벅스","store","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","21","6","44","010-4898-9014","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","435","이디야커피","serving","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","56","23","38","010-9391-1979","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","436","CU편의점","delivery","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","10","14","38","010-5847-4369","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","437","GS25","cafe","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","0","21","21","010-8440-1022","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","438","새마을식당","cooking","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","39","24","28","010-1358-3329","매탄동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","439","파리바게트","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","8","21","53","010-4349-8093","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","440","파리바게트","delivery","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","50","18","19","010-0874-9689","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","441","CU편의점","serving","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","27","2","16","010-0340-7225","매탄동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","442","GS25","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","23","23","33","010-2968-5038","영통동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","443","새마을식당","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","24","23","23","010-9970-7890","영통동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","444","스타벅스","store","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","17","19","59","010-1658-6299","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","445","GS25","delivery","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","44","19","27","010-4273-4071","하동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","446","올리브영","cooking","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","27","18","42","010-7709-5471","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","447","CU편의점","serving","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","26","16","33","010-3407-0607","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","448","GS25","serving","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","17","7","24","32","010-3759-6081","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","449","BBQ치킨","cafe","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","51","23","9","010-7957-4107","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","450","이디야커피","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","35","9","51","010-4115-2561","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","451","파리바게트","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","44","24","3","010-1139-6749","망포동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","452","새마을식당","delivery","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","50","13","6","010-7639-0208","광교동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","453","BBQ치킨","cooking","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","55","22","3","010-8742-7637","망포동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","454","GS25","delivery","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","57","18","43","010-2862-3768","이의동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","455","스타벅스","cafe","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","46","24","57","010-3527-6591","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","456","올리브영","store","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","21","24","17","010-3068-0503","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","457","파리바게트","store","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","19","25","20","35","010-3495-1883","광교동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","458","새마을식당","serving","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","37","20","51","010-1770-6169","신동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","459","이디야커피","store","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","47","23","56","010-2497-2009","원천동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","460","스타벅스","cafe","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","32","23","22","010-2957-3981","매탄동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","461","CU편의점","cafe","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","8","23","24","010-4687-2098","영통동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","462","GS25","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","51","23","40","010-8992-8688","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","463","파리바게트","store","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","2","49","13","27","010-6572-2642","신동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","464","CU편의점","cafe","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","13","24","23","010-2373-2749","매탄동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","465","CU편의점","cafe","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","49","24","0","010-3157-4604","이의동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","466","GS25","cafe","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","33","23","54","010-7287-4802","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","467","CU편의점","serving","sun","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","10","13","3","010-2374-4011","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","468","파리바게트","store","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","5","19","50","010-0631-3362","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","469","CU편의점","delivery","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","25","22","47","010-8837-4625","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","470","BBQ치킨","cafe","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","33","18","5","010-8501-6841","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","471","BBQ치킨","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","47","21","51","010-8514-0620","신동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","472","GS25","store","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","36","7","59","010-9677-3641","신동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","473","이디야커피","store","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","22","23","43","010-9247-5909","하동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","474","GS25","serving","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","4","10","15","010-7314-9245","매탄동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","475","스타벅스","cooking","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","18","18","31","010-5969-0168","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","476","이디야커피","delivery","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","20","10","42","010-2258-4347","망포동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","477","이디야커피","serving","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","47","19","34","010-0498-3320","태장동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","478","CU편의점","store","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","21","21","19","010-0580-6892","신동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","479","CU편의점","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","21","24","6","010-9996-5039","태장동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","480","스타벅스","delivery","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","41","13","48","010-7126-9619","신동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","481","파리바게트","cafe","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","9","8","56","010-3612-3636","매탄동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","482","이디야커피","cafe","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","8","8","49","010-5172-6757","하동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","483","이디야커피","store","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","6","6","13","010-1757-0413","태장동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","484","CU편의점","cooking","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","17","20","12","010-5582-2116","영통동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","485","BBQ치킨","store","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","34","14","2","010-7828-1104","원천동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","486","새마을식당","store","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","56","22","34","010-5386-6821","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","487","GS25","cooking","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","4","21","4","010-2567-2807","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","488","BBQ치킨","serving","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","22","17","27","010-9489-2374","신동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","489","파리바게트","cafe","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","3","11","20","40","010-8230-4914","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","490","새마을식당","cafe","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","40","16","27","010-9350-8448","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","491","새마을식당","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","34","15","50","010-8015-3322","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","492","올리브영","cooking","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","41","24","9","010-0386-1350","이의동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","493","CU편의점","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","52","24","53","010-1845-9501","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","494","새마을식당","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","47","3","51","010-1983-3042","신동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","495","이디야커피","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","15","17","52","010-1453-3845","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","496","CU편의점","cafe","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","49","14","21","010-6301-5337","영통동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","497","CU편의점","cooking","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","39","16","5","010-6117-9074","망포동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","498","BBQ치킨","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","27","4","41","010-6902-8201","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","499","GS25","cooking","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","17","19","12","010-5547-1873","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","500","파리바게트","delivery","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","18","20","48","010-6097-3772","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","501","이디야커피","delivery","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","52","10","30","010-0552-0370","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","502","CU편의점","store","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","14","24","46","010-8737-8877","매탄동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","503","파리바게트","cooking","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","23","23","45","010-3313-6543","이의동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","504","BBQ치킨","cafe","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","16","15","35","010-9282-7880","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","505","스타벅스","delivery","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","32","19","24","010-7455-9686","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","506","BBQ치킨","serving","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","4","18","31","010-0139-4746","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","507","스타벅스","delivery","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","35","23","25","010-7917-5905","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","508","GS25","delivery","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","22","15","21","010-1092-0686","하동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","509","파리바게트","delivery","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","57","24","53","010-6862-6428","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","510","CU편의점","serving","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","19","21","40","010-4805-1938","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","511","파리바게트","cooking","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","12","22","46","010-7380-7825","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","512","새마을식당","cafe","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","16","21","5","010-8862-2406","하동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","513","CU편의점","cooking","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","46","5","30","010-4604-3356","매탄동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","514","새마을식당","cafe","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","20","11","20","010-6951-2081","원천동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","515","새마을식당","serving","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","27","20","58","010-7074-0862","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","516","올리브영","delivery","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","8","22","49","010-8811-3624","이의동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","517","새마을식당","store","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","45","20","55","010-0734-1758","원천동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","518","올리브영","cooking","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","34","24","16","010-9476-6897","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","519","CU편의점","cooking","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","53","7","47","010-8236-3983","신동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","520","새마을식당","cooking","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","43","19","53","010-9655-8132","태장동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","521","파리바게트","delivery","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","11","22","40","010-3267-9083","하동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","522","새마을식당","cooking","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","44","10","17","010-3986-4769","신동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","523","GS25","delivery","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","38","21","40","010-3060-8239","원천동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","524","올리브영","delivery","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","14","41","18","43","010-7325-6900","망포동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","525","BBQ치킨","cooking","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","59","23","4","010-9329-1084","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","526","파리바게트","cooking","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","26","24","3","010-7889-6963","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","527","파리바게트","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","11","16","42","010-6286-9589","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","528","새마을식당","delivery","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","7","27","23","54","010-8212-8499","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","529","CU편의점","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","50","18","24","010-9346-0228","태장동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","530","파리바게트","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","10","22","13","38","010-3488-7549","태장동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","531","올리브영","serving","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","0","31","9","31","010-7122-1560","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","532","스타벅스","delivery","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","19","23","27","010-8076-1578","이의동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","533","BBQ치킨","serving","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","32","17","26","010-8537-6823","하동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","534","이디야커피","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","9","14","8","010-7047-2012","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","535","이디야커피","serving","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","1","22","39","010-5592-9362","신동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","536","스타벅스","delivery","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","24","19","26","010-2361-0800","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","537","BBQ치킨","store","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","3","5","16","44","010-2256-3599","광교동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","538","GS25","cooking","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","10","24","52","010-1177-3481","하동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","539","GS25","delivery","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","18","22","14","010-4825-4934","하동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","540","BBQ치킨","cooking","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","20","4","24","42","010-0698-9428","태장동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","541","올리브영","cooking","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","31","16","14","010-0724-3455","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","542","이디야커피","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","16","24","32","010-0515-7362","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","543","BBQ치킨","delivery","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","14","34","22","43","010-1942-4507","망포동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","544","파리바게트","serving","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","54","24","5","010-2071-9028","영통동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","545","BBQ치킨","serving","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","36","22","22","010-5692-9116","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","546","BBQ치킨","cafe","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","25","16","20","010-8605-9444","이의동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","547","이디야커피","delivery","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","1","19","25","010-6482-5623","원천동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","548","GS25","cafe","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","36","21","36","010-3371-2482","태장동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","549","스타벅스","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","46","21","9","010-6018-5140","광교동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","550","올리브영","cooking","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","32","13","13","010-2284-2379","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","551","GS25","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","25","16","48","010-9271-6791","하동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","552","스타벅스","store","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","16","23","11","010-2043-1537","매탄동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","553","GS25","cooking","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","22","21","8","010-1338-4279","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","554","GS25","serving","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","29","12","3","010-9841-1368","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","555","BBQ치킨","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","18","5","22","010-2844-6493","영통동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","556","스타벅스","store","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","9","24","35","010-6176-2474","영통동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","557","GS25","cafe","sat","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","30","21","18","010-6983-0003","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","558","파리바게트","serving","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","7","20","17","010-3801-1782","매탄동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","559","새마을식당","store","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","10","2","20","010-3023-1102","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","560","CU편의점","cafe","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","41","24","53","010-8774-5927","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","561","올리브영","serving","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","21","18","27","010-3718-5174","영통동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","562","스타벅스","cafe","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","25","24","58","010-9514-8748","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","563","BBQ치킨","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","16","6","23","23","010-6132-5163","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","564","파리바게트","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","3","23","53","010-4253-8390","망포동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","565","이디야커피","cooking","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","4","19","40","010-6998-3303","망포동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","566","GS25","store","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","32","24","15","010-8018-5624","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","567","CU편의점","serving","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","27","24","41","010-0333-2029","광교동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","568","BBQ치킨","cooking","sun","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","7","41","15","24","010-6320-4244","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","569","이디야커피","cooking","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","1","13","13","53","010-3238-0987","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","570","새마을식당","cooking","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","44","20","0","010-8261-8863","영통동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","571","파리바게트","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","1","17","19","42","010-9087-1328","이의동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","572","새마을식당","cafe","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","16","12","56","010-3847-0809","태장동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","573","새마을식당","serving","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","24","7","37","010-9212-5370","망포동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","574","GS25","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","55","23","38","010-4853-9511","원천동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","575","스타벅스","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","33","8","36","010-8895-0057","하동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","576","올리브영","cafe","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","43","23","15","010-3900-5313","매탄동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","577","새마을식당","cooking","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","26","17","51","010-5435-2195","하동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","578","CU편의점","serving","thu","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","48","13","4","010-2679-6175","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","579","올리브영","cooking","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","52","19","12","010-3120-5007","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","580","올리브영","cafe","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","26","23","29","010-9250-5005","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","581","CU편의점","cooking","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","55","23","6","010-9872-0514","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","582","올리브영","cooking","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","58","24","10","010-9407-8394","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","583","스타벅스","delivery","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","56","23","16","010-6829-2456","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","584","파리바게트","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","35","20","36","010-9923-5214","신동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","585","CU편의점","cafe","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","31","19","16","010-8814-0053","신동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","586","새마을식당","cafe","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","40","20","16","010-0608-1991","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","587","새마을식당","cooking","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","35","17","11","010-4372-9903","망포동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","588","새마을식당","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","44","10","11","010-1334-6331","하동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","589","올리브영","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","22","12","40","010-5666-7533","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","590","파리바게트","store","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","12","18","17","010-3344-5593","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","591","새마을식당","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","29","9","1","010-7798-0314","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","592","올리브영","delivery","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","10","8","5","010-6153-8319","영통동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","593","새마을식당","cooking","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","18","24","19","010-2348-2710","태장동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","594","BBQ치킨","delivery","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","5","21","50","010-7278-9313","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","595","GS25","delivery","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","1","21","0","010-2844-9947","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","596","BBQ치킨","serving","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","46","23","1","010-3899-1666","원천동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","597","파리바게트","cooking","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","42","23","59","010-5410-3411","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","598","스타벅스","store","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","0","24","45","010-9151-1659","매탄동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","599","CU편의점","serving","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","48","22","10","010-0018-3447","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","600","BBQ치킨","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","23","49","24","42","010-4438-0953","영통동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","601","GS25","delivery","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","6","23","13","010-9089-8574","이의동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","602","CU편의점","cafe","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","34","18","58","010-2526-1412","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","603","올리브영","cooking","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","25","16","50","010-0374-4965","이의동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","604","이디야커피","cafe","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","21","18","6","010-5504-4820","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","605","GS25","delivery","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","14","24","21","010-1850-6101","광교동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","606","스타벅스","serving","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","55","9","37","010-3239-9932","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","607","CU편의점","serving","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","35","24","58","010-9407-1792","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","608","이디야커피","cooking","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","6","19","23","010-2248-8420","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","609","스타벅스","serving","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","49","23","44","010-2604-0866","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","610","GS25","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","1","9","22","32","010-9588-0917","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","611","파리바게트","cafe","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","2","54","17","33","010-8801-3092","광교동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","612","GS25","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","58","22","37","010-0149-4949","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","613","CU편의점","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","13","20","19","29","010-9317-6999","망포동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","614","새마을식당","serving","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","59","21","13","010-0586-0733","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","615","올리브영","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","29","4","6","010-5485-7648","하동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","616","이디야커피","cooking","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","27","9","45","010-1700-8864","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","617","이디야커피","delivery","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","9","21","46","010-4862-3910","신동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","618","BBQ치킨","cooking","tue","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","45","23","53","010-4016-6655","매탄동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","619","스타벅스","delivery","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","12","13","12","010-1727-4821","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","620","파리바게트","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","17","42","22","49","010-3389-2386","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","621","파리바게트","delivery","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","44","22","21","010-5442-2626","신동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","622","새마을식당","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","38","24","48","010-5736-6937","태장동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","623","이디야커피","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","36","24","23","010-6100-7359","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","624","스타벅스","delivery","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","22","22","42","010-7761-7619","태장동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","625","스타벅스","store","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","14","23","25","010-8267-4351","하동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","626","BBQ치킨","delivery","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","18","15","38","010-8939-8952","신동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","627","GS25","cooking","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","40","11","1","010-2743-5680","하동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","628","GS25","store","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","26","23","55","010-7826-7247","매탄동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","629","GS25","cooking","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","9","24","44","010-1291-0535","원천동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","630","이디야커피","serving","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","10","22","18","010-4689-6210","하동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","631","새마을식당","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","11","13","44","010-9713-5841","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","632","이디야커피","serving","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","11","9","42","010-9854-2461","영통동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","633","GS25","cafe","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","3","40","5","44","010-8176-7912","신동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","634","BBQ치킨","delivery","fri","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","9","52","14","32","010-9774-3417","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","635","스타벅스","delivery","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","15","12","31","010-0298-7419","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","636","올리브영","cafe","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","58","11","30","010-0375-5537","신동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","637","이디야커피","delivery","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","6","11","24","28","010-7990-6799","광교동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","638","새마을식당","cooking","sun","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","27","7","26","010-9682-6334","신동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","639","이디야커피","delivery","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","49","24","52","010-5632-5573","영통동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","640","새마을식당","delivery","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","30","12","28","010-7946-2890","망포동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","641","GS25","serving","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","29","19","34","010-7666-8345","이의동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","642","올리브영","cafe","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","41","20","2","010-7295-6134","원천동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","643","스타벅스","store","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","9","18","47","010-8832-8088","원천동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","644","BBQ치킨","cooking","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","51","19","46","010-9460-0467","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","645","GS25","store","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","2","14","0","010-8592-7163","망포동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","646","올리브영","store","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","49","24","55","010-1854-5230","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","647","이디야커피","cafe","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","23","11","6","010-6734-7497","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","648","올리브영","delivery","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","54","24","31","010-5435-2407","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","649","올리브영","store","mon","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","18","14","2","010-8829-5863","신동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","650","GS25","store","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","15","10","28","010-8209-3480","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","651","CU편의점","serving","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","11","7","46","010-6416-4948","하동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","652","GS25","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","3","43","18","11","010-0120-3594","광교동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","653","스타벅스","serving","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","51","23","46","010-1228-9213","영통동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","654","GS25","cooking","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","43","18","36","010-0233-1436","이의동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","655","파리바게트","store","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","0","17","24","010-1900-3064","영통동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","656","파리바게트","cooking","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","33","17","22","010-6254-7909","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","657","BBQ치킨","cafe","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","35","24","43","010-5017-8982","영통동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","658","BBQ치킨","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","50","23","34","010-7343-4962","원천동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","659","이디야커피","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","19","18","22","010-4529-0701","원천동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","660","파리바게트","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","19","23","54","010-0804-3704","영통동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","661","파리바게트","delivery","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","6","20","20","010-6820-5646","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","662","스타벅스","serving","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","10","18","34","010-8888-6527","원천동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","663","BBQ치킨","store","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","57","23","27","010-4087-6515","매탄동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","664","BBQ치킨","serving","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","53","21","44","010-5459-3303","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","665","이디야커피","cooking","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","43","15","56","010-0415-9110","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","666","새마을식당","store","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","14","32","20","13","010-0735-6486","하동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","667","새마을식당","cooking","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","20","24","21","010-2260-9102","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","668","스타벅스","cooking","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","14","23","32","010-2801-7199","이의동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","669","스타벅스","cooking","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","44","20","0","010-4655-7450","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","670","올리브영","store","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","9","16","24","010-0934-1425","매탄동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","671","올리브영","serving","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","57","21","46","010-6497-4563","이의동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","672","GS25","store","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","17","17","35","010-2485-0644","이의동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","673","새마을식당","cafe","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","13","23","21","010-4300-6048","영통동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","674","올리브영","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","27","19","27","010-7887-3833","광교동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","675","이디야커피","cafe","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","11","14","17","33","010-8211-1938","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","676","올리브영","cooking","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","8","12","23","48","010-4027-6671","원천동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","677","BBQ치킨","store","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","27","23","28","010-0421-7529","원천동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","678","GS25","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","1","39","19","42","010-7521-9174","태장동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","679","이디야커피","cooking","sun","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","13","19","20","010-9519-0771","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","680","GS25","store","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","16","24","2","010-3858-9363","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","681","올리브영","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","9","23","4","010-9134-6131","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","682","CU편의점","cooking","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","47","10","58","010-2903-5784","태장동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","683","CU편의점","cafe","sat","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","48","20","42","010-6494-8158","매탄동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","684","GS25","serving","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","46","24","51","010-2570-3876","이의동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","685","BBQ치킨","cooking","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","1","17","42","010-3973-0741","영통동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","686","올리브영","delivery","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","31","8","16","010-0886-2120","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","687","올리브영","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","29","11","36","010-7895-1483","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","688","올리브영","store","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","4","9","24","9","010-5510-4561","영통동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","689","GS25","delivery","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","36","19","49","010-1943-6124","이의동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","690","새마을식당","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","17","22","4","010-8998-7184","영통동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","691","GS25","cafe","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","21","17","17","010-1958-8224","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","692","CU편의점","cooking","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","48","21","25","010-3172-6426","하동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","693","CU편의점","delivery","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","22","21","4","010-0406-4986","원천동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","694","올리브영","serving","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","11","11","14","55","010-3261-2785","이의동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","695","새마을식당","delivery","tue","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","3","7","9","010-4929-9659","매탄동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","696","BBQ치킨","serving","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","23","22","21","010-9177-4322","광교동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","697","새마을식당","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","19","23","12","010-8817-1503","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","698","스타벅스","delivery","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","14","25","16","10","010-9543-0060","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","699","스타벅스","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","37","23","6","010-9069-2699","매탄동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","700","스타벅스","cafe","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","42","20","42","010-1124-2201","하동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","701","GS25","cafe","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","49","16","46","010-2868-7379","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","702","스타벅스","store","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","18","7","50","010-9587-5198","영통동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","703","스타벅스","store","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","21","24","24","21","010-3902-6232","이의동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","704","올리브영","delivery","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","6","24","11","010-9708-1078","매탄동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","705","CU편의점","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","10","13","24","010-3385-8501","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","706","GS25","store","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","2","7","12","15","010-6328-9659","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","707","올리브영","store","mon","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","45","11","47","010-2926-3688","이의동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","708","파리바게트","store","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","7","20","26","010-1982-4619","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","709","새마을식당","serving","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","5","5","25","010-3099-3327","광교동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","710","GS25","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","27","22","11","010-5962-6105","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","711","스타벅스","cafe","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","28","24","46","010-0244-1306","광교동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","712","CU편의점","store","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","13","15","51","010-8262-4427","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","713","이디야커피","delivery","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","17","13","41","010-9738-0953","망포동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","714","BBQ치킨","cafe","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","41","22","1","010-8670-6073","매탄동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","715","CU편의점","cooking","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","24","18","11","010-3208-2751","광교동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","716","BBQ치킨","serving","fri","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","8","20","16","18","010-1000-7069","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","717","새마을식당","cooking","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","2","39","6","35","010-4062-0697","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","718","BBQ치킨","cafe","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","40","24","59","010-0851-9444","하동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","719","파리바게트","delivery","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","13","24","26","010-4156-9370","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","720","스타벅스","cafe","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","9","24","20","010-3093-6526","태장동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","721","파리바게트","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","22","22","24","39","010-5308-7352","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","722","올리브영","store","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","20","24","42","010-1622-5043","신동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","723","새마을식당","cafe","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","51","22","3","010-4508-9744","신동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","724","새마을식당","cafe","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","11","21","19","14","010-4388-6696","망포동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","725","BBQ치킨","cooking","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","22","35","23","33","010-2932-4487","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","726","GS25","store","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","4","17","18","010-4347-2391","매탄동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","727","GS25","serving","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","16","43","20","15","010-0649-3909","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","728","BBQ치킨","cooking","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","29","23","32","010-2027-5772","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","729","CU편의점","cooking","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","5","9","26","010-8137-3665","신동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","730","스타벅스","delivery","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","16","23","19","010-1552-4380","매탄동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","731","파리바게트","cafe","thu","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","11","13","55","010-1954-4013","매탄동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","732","새마을식당","delivery","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","36","23","18","010-9135-9090","원천동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","733","스타벅스","serving","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","55","23","42","010-6290-6719","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","734","CU편의점","cooking","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","0","26","24","22","010-6383-3834","영통동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","735","올리브영","cooking","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","32","24","32","010-4436-0286","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","736","CU편의점","delivery","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","32","17","20","010-9329-0997","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","737","스타벅스","cafe","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","2","59","17","18","010-6731-8317","광교동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","738","BBQ치킨","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","39","24","52","010-4065-3951","하동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","739","파리바게트","serving","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","33","24","37","010-9393-3653","이의동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","740","파리바게트","store","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","0","18","19","54","010-2659-0498","하동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","741","GS25","cafe","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","28","18","29","010-4333-7970","태장동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","742","스타벅스","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","19","21","17","010-4700-6067","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","743","새마을식당","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","19","6","48","010-9853-4647","영통동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","744","이디야커피","serving","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","26","23","30","010-9746-4661","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","745","BBQ치킨","serving","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","48","15","30","010-9169-2256","이의동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","746","GS25","delivery","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","42","22","14","010-4726-5686","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","747","BBQ치킨","delivery","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","38","22","0","010-2823-2913","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","748","이디야커피","cafe","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","29","19","26","010-6354-8126","신동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","749","이디야커피","store","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","29","16","20","010-0801-3811","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","750","새마을식당","serving","fri","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","9","21","21","010-7188-1225","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","751","이디야커피","cafe","sat","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","20","42","22","13","010-3496-5822","매탄동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","752","GS25","serving","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","27","23","35","010-5086-8750","영통동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","753","CU편의점","serving","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","59","18","4","010-4606-8296","광교동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","754","스타벅스","store","tue","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","40","17","4","010-4555-5153","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","755","CU편의점","delivery","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","41","12","0","010-2513-7788","이의동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","756","올리브영","cafe","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","31","19","55","010-0877-5749","원천동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","757","BBQ치킨","store","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","12","1","20","19","010-2249-6302","태장동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","758","GS25","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","24","17","15","010-3181-8599","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","759","올리브영","store","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","21","7","22","12","010-1706-7735","원천동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","760","스타벅스","serving","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","12","13","13","010-8478-8500","태장동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","761","CU편의점","serving","sat","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","2","34","14","9","010-4761-3196","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","762","BBQ치킨","cooking","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","0","11","4","15","010-2689-3952","이의동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","763","BBQ치킨","store","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","10","19","1","010-1917-0458","신동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","764","파리바게트","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","15","56","23","18","010-1417-9083","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","765","CU편의점","cafe","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","30","14","45","010-2888-6224","태장동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","766","스타벅스","cooking","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","39","17","25","010-2816-6522","광교동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","767","파리바게트","store","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","20","30","22","37","010-8746-0784","태장동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","768","스타벅스","store","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","47","10","53","010-8666-3099","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","769","올리브영","delivery","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","9","9","12","19","010-8919-2094","영통동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","770","CU편의점","delivery","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","30","20","16","010-6444-2252","원천동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","771","GS25","delivery","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","31","5","38","010-1527-9596","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","772","이디야커피","delivery","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","42","20","8","010-6399-2067","신동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","773","CU편의점","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","27","24","10","010-3688-0025","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","774","스타벅스","cooking","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","1","21","23","42","010-2877-0850","신동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","775","BBQ치킨","cooking","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","18","19","33","010-4377-6808","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","776","CU편의점","serving","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","42","18","44","010-1025-8800","망포동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","777","CU편의점","cooking","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","41","8","45","010-7226-9678","광교동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","778","올리브영","store","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","12","22","33","010-1409-7793","영통동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","779","이디야커피","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","15","56","23","40","010-9227-9876","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","780","스타벅스","cooking","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","19","6","32","010-5505-6469","매탄동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","781","올리브영","delivery","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","12","2","13","46","010-5924-2297","영통동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","782","파리바게트","cafe","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","11","8","37","010-0292-0874","태장동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","783","파리바게트","serving","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","49","20","17","010-1186-1565","광교동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","784","새마을식당","cooking","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","31","6","40","010-6601-5819","신동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","785","스타벅스","cafe","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","51","23","8","010-1633-3941","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","786","올리브영","store","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","27","6","43","010-7966-5843","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","787","올리브영","cafe","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","21","24","40","010-4458-9877","신동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","788","올리브영","cafe","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","28","18","27","010-1902-7612","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","789","CU편의점","store","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","16","23","21","50","010-7103-9692","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","790","올리브영","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","5","31","13","30","010-0504-1313","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","791","이디야커피","cafe","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","34","11","9","010-3706-1170","이의동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","792","CU편의점","serving","sun","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","7","41","9","58","010-7862-3273","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","793","BBQ치킨","cooking","sat","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","2","11","0","010-7422-4626","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","794","BBQ치킨","delivery","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","42","11","12","010-5532-3083","원천동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","795","올리브영","delivery","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","37","13","46","010-9727-4838","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","796","GS25","store","tue","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","11","23","23","26","010-9451-3076","영통동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","797","BBQ치킨","store","fri","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","44","7","41","010-2767-7485","광교동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","798","올리브영","serving","tue","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","19","24","14","010-4498-8010","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","799","파리바게트","store","wen","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","21","45","23","15","010-8476-9610","광교동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","800","스타벅스","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","50","14","30","010-6005-7130","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","801","스타벅스","serving","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","22","27","24","9","010-8979-3682","이의동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","802","파리바게트","cafe","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","4","50","12","16","010-6784-4023","태장동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","803","파리바게트","store","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","13","9","22","34","010-0229-0808","하동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","804","파리바게트","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","18","20","24","45","010-9771-4684","이의동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","805","GS25","cooking","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","28","10","28","010-3354-8742","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","806","새마을식당","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","1","23","39","010-7831-3068","이의동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","807","스타벅스","cooking","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","4","31","17","17","010-9317-6127","태장동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","808","CU편의점","serving","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","21","24","21","010-2867-3131","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","809","BBQ치킨","store","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","15","58","24","48","010-7531-3034","영통동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","810","스타벅스","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","54","24","30","010-2454-8404","하동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","811","BBQ치킨","serving","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","1","41","18","27","010-0808-5615","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","812","파리바게트","store","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","14","22","12","010-0412-8980","이의동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","813","이디야커피","store","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","18","24","40","010-1302-5167","영통동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","814","GS25","serving","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","42","21","16","010-0496-6472","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","815","파리바게트","cafe","mon","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","8","30","19","57","010-5795-2955","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","816","새마을식당","delivery","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","17","15","15","010-3969-5892","신동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","817","새마을식당","serving","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","32","10","46","010-6769-9445","망포동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","818","CU편의점","cooking","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","19","30","23","58","010-2498-6843","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","819","새마을식당","cafe","thu","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","20","22","7","010-9821-3166","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","820","BBQ치킨","store","thu","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","6","4","12","54","010-3870-6673","신동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","821","스타벅스","delivery","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","13","49","22","59","010-7363-2404","태장동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","822","새마을식당","store","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","42","21","47","010-3201-8871","영통동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","823","이디야커피","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","31","10","38","010-4872-8824","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","824","GS25","cooking","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","29","24","52","010-4768-1922","원천동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","825","이디야커피","cafe","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","31","21","22","010-3353-1989","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","826","GS25","cooking","thu","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","0","9","3","34","010-1049-3662","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","827","파리바게트","serving","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","11","35","16","59","010-1892-2395","원천동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","828","올리브영","store","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","2","19","32","010-8384-1200","원천동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","829","스타벅스","store","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","1","20","25","010-8509-6377","태장동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","830","GS25","serving","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","32","15","55","010-7533-2705","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","831","스타벅스","cafe","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","40","18","6","010-5827-5308","광교동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","832","GS25","cooking","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","10","6","18","3","010-2830-5369","매탄동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","833","CU편의점","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","12","20","50","010-3390-4282","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","834","파리바게트","cooking","sat","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","43","10","7","010-2427-5534","태장동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","835","CU편의점","cafe","sun","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","11","12","18","21","010-2714-6523","이의동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","836","스타벅스","store","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","23","4","24","47","010-6347-9297","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","837","CU편의점","store","mon","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","23","54","24","50","010-3134-4834","광교동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","838","이디야커피","delivery","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","43","10","50","010-6174-6231","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","839","파리바게트","serving","mon","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","12","4","24","25","010-2172-6734","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","840","올리브영","store","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","10","25","21","0","010-4297-6836","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","841","이디야커피","serving","fri","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","15","6","14","010-3101-6974","매탄동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","842","GS25","cafe","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","31","22","55","010-5976-8205","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","843","BBQ치킨","serving","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","16","20","20","010-9390-3555","원천동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","844","새마을식당","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","53","13","13","010-7230-8405","신동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","845","이디야커피","delivery","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","46","22","14","010-0876-2513","신동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","846","새마을식당","serving","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","4","20","9","41","010-7549-9240","이의동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","847","새마을식당","cafe","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","36","14","34","010-9297-7034","태장동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","848","GS25","store","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","17","21","11","010-4587-5689","망포동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","849","파리바게트","store","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","9","30","17","42","010-2504-7834","망포동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","850","파리바게트","store","thu","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","14","17","23","16","010-9891-0375","태장동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","851","BBQ치킨","cafe","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","19","10","20","13","010-7751-4578","원천동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","852","이디야커피","delivery","sat","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","58","14","26","010-7968-6684","영통동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","853","CU편의점","serving","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","14","44","19","50","010-2142-5932","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","854","CU편의점","serving","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","2","13","38","010-1383-9034","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","855","GS25","store","thu","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","56","21","35","010-8996-4221","매탄동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","856","GS25","serving","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","2","21","2","010-2344-0646","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","857","GS25","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","6","13","22","6","010-0982-2041","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","858","CU편의점","delivery","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","4","40","15","30","010-8214-7637","매탄동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","859","GS25","store","mon","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","50","22","54","010-1412-3221","광교동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","860","파리바게트","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","24","11","11","010-7400-1465","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","861","GS25","cafe","fri","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","12","2","24","2","010-3630-1682","매탄동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","862","이디야커피","cooking","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","18","55","20","25","010-5960-7992","신동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","863","새마을식당","cooking","fri","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","35","24","41","010-3978-9572","원천동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","864","새마을식당","store","thu","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","13","47","14","9","010-0254-0942","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","865","올리브영","cafe","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","44","22","52","010-9526-6258","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","866","스타벅스","delivery","sat","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","9","19","21","41","010-1042-2737","영통동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","867","이디야커피","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","7","29","11","57","010-5539-7461","광교동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","868","CU편의점","serving","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","20","3","23","27","010-7464-0103","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","869","새마을식당","store","sat","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","10","41","17","5","010-1937-5035","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","870","GS25","store","wen","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","39","24","45","010-0158-6958","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","871","이디야커피","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","10","43","22","14","010-5140-1498","이의동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","872","이디야커피","cafe","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","23","29","24","4","010-9310-7335","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","873","GS25","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","14","10","30","010-6234-8420","신동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","874","올리브영","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","8","23","0","010-8672-3963","망포동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","875","GS25","cooking","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","18","12","21","19","010-5989-5884","영통동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","876","이디야커피","delivery","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","4","17","37","010-6380-4564","이의동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","877","CU편의점","cafe","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","26","23","36","010-6843-4505","광교동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","878","BBQ치킨","serving","fri","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","10","21","12","26","010-3094-6154","망포동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","879","새마을식당","store","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","6","33","24","3","010-8834-6910","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","880","새마을식당","serving","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","19","52","22","39","010-9820-0181","광교동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","881","스타벅스","cafe","sun","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","49","18","13","010-9093-8009","광교동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","882","CU편의점","serving","thu","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","47","24","27","010-0036-1189","하동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","883","파리바게트","store","fri","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","5","17","10","28","010-0910-4237","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","884","BBQ치킨","cafe","sat","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","13","39","18","40","010-5869-2945","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","885","CU편의점","serving","tue","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","25","5","23","010-4519-9029","매탄동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","886","CU편의점","store","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","12","12","20","4","010-2074-1124","신동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","887","BBQ치킨","cafe","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","2","29","18","3","010-6019-8343","망포동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","888","GS25","serving","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","47","23","7","010-4065-3270","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","889","이디야커피","store","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","3","7","6","10","010-2857-8117","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","890","새마을식당","delivery","sun","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","22","24","54","010-0998-1242","매탄동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","891","CU편의점","cooking","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","6","24","5","010-3897-3310","망포동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","892","스타벅스","cooking","sat","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","0","37","6","19","010-7590-4026","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","893","올리브영","cafe","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","17","27","21","3","010-5552-3746","신동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","894","스타벅스","store","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","11","24","12","010-2753-6578","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","895","올리브영","cafe","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","41","22","42","010-3353-7975","영통동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","896","CU편의점","store","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","9","42","24","8","010-4780-3194","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","897","CU편의점","cooking","fri","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","22","19","23","6","010-9486-7347","하동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","898","GS25","delivery","tue","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","5","31","24","15","010-6811-6570","이의동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","899","스타벅스","cooking","fri","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","8","32","16","12","010-1223-5308","광교동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","900","BBQ치킨","cooking","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","17","49","23","25","010-5373-1288","하동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","901","이디야커피","cafe","thu","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","55","17","18","010-8623-4410","태장동","15000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","902","스타벅스","cafe","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","38","22","38","010-1736-2711","원천동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","903","GS25","store","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","14","23","14","010-3915-9001","망포동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","904","새마을식당","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","35","17","55","010-9920-7323","원천동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","905","올리브영","cooking","fri","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","6","4","24","0","010-5204-2878","태장동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","906","GS25","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","13","19","18","42","010-4474-0935","하동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","907","올리브영","cooking","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","9","24","3","010-0829-9763","망포동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","908","CU편의점","delivery","fri","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","21","4","22","18","010-3289-0895","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","909","스타벅스","cafe","tue","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","18","49","24","10","010-4743-5993","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","910","CU편의점","cooking","sun","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","17","50","24","55","010-5533-3559","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","911","새마을식당","cafe","thu","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","21","35","23","53","010-0733-6951","광교동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","912","이디야커피","serving","mon","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","17","14","19","7","010-4825-3163","매탄동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","913","올리브영","delivery","wen","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","16","35","20","1","010-0582-6133","영통동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","914","스타벅스","cafe","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","12","34","15","37","010-2815-2556","태장동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","915","CU편의점","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","22","7","6","010-9373-7101","원천동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","916","GS25","serving","wen","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","8","32","21","21","010-6379-3610","매탄동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","917","올리브영","cooking","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","18","55","24","5","010-6411-4336","광교동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","918","올리브영","delivery","tue","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","22","18","24","49","010-0515-1813","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","919","CU편의점","cafe","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","6","21","13","14","010-8350-7969","광교동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","920","스타벅스","store","tue","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","10","22","17","4","010-9033-8589","신동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","921","GS25","store","thu","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","7","31","22","19","010-4764-4304","망포동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","922","스타벅스","cooking","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","35","9","11","010-6501-1298","원천동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","923","새마을식당","serving","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","12","58","20","29","010-2117-0661","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","924","스타벅스","store","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","19","6","23","45","010-2991-7350","하동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","925","이디야커피","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","12","32","19","30","010-8294-6863","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","926","CU편의점","cooking","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","5","12","10","10","010-2019-1921","광교동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","927","스타벅스","delivery","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","16","57","23","52","010-5841-3034","하동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","928","파리바게트","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","23","43","24","45","010-6191-7031","이의동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","929","이디야커피","cooking","mon","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","16","2","23","7","010-3355-7632","이의동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","930","올리브영","cafe","sun","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","38","15","25","010-6984-7219","이의동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","931","새마을식당","serving","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","3","20","39","010-9848-5221","매탄동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","932","새마을식당","delivery","sun","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","18","36","22","13","010-8691-5911","영통동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","933","BBQ치킨","delivery","sat","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","18","0","22","30","010-4426-1033","광교동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","934","이디야커피","serving","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","2","30","14","47","010-5157-3062","태장동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","935","GS25","serving","mon","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","36","23","28","010-0051-0754","원천동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","936","스타벅스","cafe","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","57","11","51","010-2956-8624","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","937","GS25","delivery","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","14","51","23","54","010-9276-4380","원천동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","938","GS25","serving","sun","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","23","54","24","7","010-6236-4153","이의동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","939","새마을식당","cafe","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","4","21","34","010-3024-4581","원천동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","940","새마을식당","cafe","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","9","43","16","18","010-8815-7704","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","941","파리바게트","cooking","sun","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","2","6","16","30","010-2116-3196","매탄동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","942","올리브영","cooking","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","15","18","17","13","010-2776-6327","이의동","17000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","943","이디야커피","serving","thu","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","21","4","22","45","010-3080-8706","태장동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","944","GS25","store","fri","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","4","57","20","10","010-9577-8653","태장동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","945","이디야커피","cafe","wen","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","0","45","18","55","010-2644-2424","태장동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","946","올리브영","cafe","thu","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","20","50","24","12","010-0933-6103","영통동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","947","새마을식당","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","3","59","23","51","010-4863-2388","망포동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","948","올리브영","cafe","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","3","22","23","14","010-2233-5642","태장동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","949","파리바게트","cafe","fri","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","1","14","24","010-4246-5780","광교동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","950","파리바게트","delivery","sat","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","7","7","23","17","010-5925-0675","원천동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","951","BBQ치킨","serving","thu","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","4","53","19","28","010-7967-1737","신동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","952","새마을식당","cooking","tue","고등학교 졸업 이상","1년 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","20","3","21","24","010-2561-8047","태장동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","953","새마을식당","cafe","wen","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","12","23","14","010-0800-9670","이의동","20000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","954","GS25","cafe","mon","학력 무관","1년 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","1","50","16","2","010-6943-4529","광교동","13000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","955","새마을식당","store","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","1","23","2","47","010-2732-7969","망포동","14000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","956","이디야커피","delivery","tue","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","8","8","14","5","010-9201-1293","신동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","957","이디야커피","cafe","sun","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","2","23","22","010-1535-4168","영통동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","958","스타벅스","cafe","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","5","3","12","26","010-7244-5121","망포동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","959","GS25","cooking","sat","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","9","3","22","15","010-5673-5781","원천동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","960","파리바게트","store","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","7","23","43","010-8042-3737","신동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","961","CU편의점","delivery","wen","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","18","14","21","6","010-9774-0474","태장동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","962","이디야커피","serving","sat","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","14","6","19","15","010-1387-7529","매탄동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","963","올리브영","cooking","tue","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","1","54","5","51","010-1561-6666","이의동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","964","BBQ치킨","store","mon","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","0","24","14","010-5148-3187","원천동","15000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","965","스타벅스","cooking","wen","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","3","10","19","43","010-4355-7984","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","966","올리브영","cafe","tue","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","58","23","21","010-1442-6497","망포동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","967","GS25","store","wen","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","19","24","20","58","010-4230-8706","하동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","968","올리브영","cooking","wen","학력 무관","단기","여성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","2","33","13","17","010-4787-2412","광교동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","969","새마을식당","serving","mon","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","17","28","24","13","010-9690-3915","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","970","올리브영","delivery","wen","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","7","52","24","14","010-6336-1684","매탄동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","971","새마을식당","cooking","thu","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","15","0","16","11","010-7653-3678","신동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","972","CU편의점","cooking","mon","학력 무관","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","48","16","59","010-4133-1376","태장동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","973","CU편의점","serving","wen","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","7","10","18","010-5145-2224","이의동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","974","BBQ치킨","cafe","sat","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","5","42","14","10","010-2861-1390","광교동","10000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","975","CU편의점","delivery","tue","학력 무관","단기","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","10","43","18","35","010-7483-6418","원천동","12000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","976","올리브영","cafe","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","23","13","24","36","010-5070-9944","매탄동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","977","스타벅스","serving","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","15","48","23","36","010-3721-2534","이의동","18000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","978","이디야커피","store","tue","고등학교 졸업 이상","단기","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","22","15","24","19","010-2780-8920","태장동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","979","CU편의점","delivery","thu","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","15","2","22","56","010-7677-6047","영통동","14000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","980","BBQ치킨","cooking","sun","고등학교 졸업 이상","1년 이상","남성","gs://albatross-ed1d1.appspot.com/BBQ치킨.png","상시모집","11","7","12","1","010-9956-1531","신동","11000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","981","새마을식당","delivery","wen","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","0","32","9","24","010-7324-3932","태장동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","982","이디야커피","cooking","fri","고등학교 졸업 이상","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","4","32","21","44","010-5549-4095","태장동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","983","GS25","serving","mon","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","20","40","23","37","010-5249-9229","영통동","11000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","984","올리브영","serving","tue","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","13","49","16","46","010-7800-5897","태장동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","985","새마을식당","delivery","wen","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","5","25","11","15","010-0791-0497","하동","9000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","986","스타벅스","delivery","mon","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","6","4","9","47","010-3055-6355","원천동","9000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","987","올리브영","store","sat","고등학교 졸업 이상","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","19","21","22","30","010-5621-1435","광교동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","988","GS25","delivery","mon","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","22","21","23","31","010-1638-9734","하동","16000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","989","GS25","store","sun","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/GS25.png","상시모집","21","52","24","24","010-3098-2260","하동","12000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","990","이디야커피","cooking","mon","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","5","29","6","26","010-9344-3519","영통동","16000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","991","파리바게트","cafe","wen","학력 무관","1년 이상","성별무관","gs://albatross-ed1d1.appspot.com/파리바게트.png","상시모집","6","42","9","50","010-7700-6406","신동","19000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","992","이디야커피","serving","fri","학력 무관","단기","남성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","9","7","11","48","010-0000-6240","영통동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","993","스타벅스","delivery","fri","학력 무관","1년 이상","남성","gs://albatross-ed1d1.appspot.com/스타벅스.png","상시모집","17","42","20","50","010-4933-0967","망포동","10000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","994","이디야커피","cafe","sun","고등학교 졸업 이상","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/이디야커피.png","상시모집","19","54","23","27","010-9781-8458","태장동","19000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","995","새마을식당","serving","sun","고등학교 졸업 이상","6개월 이상","성별무관","gs://albatross-ed1d1.appspot.com/새마을식당.png","상시모집","23","41","24","17","010-3534-0781","원천동","13000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","996","CU편의점","cafe","wen","학력 무관","6개월 이상","여성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","8","52","12","0","010-5030-3505","광교동","17000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","997","CU편의점","cafe","wen","고등학교 졸업 이상","단기","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","3","4","11","21","010-4322-1815","하동","18000","연령무관","100명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","998","올리브영","serving","mon","고등학교 졸업 이상","단기","성별무관","gs://albatross-ed1d1.appspot.com/올리브영.png","상시모집","14","6","19","15","010-0837-5813","하동","20000","연령무관","10명 미만");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","999","CU편의점","serving","thu","학력 무관","6개월 이상","남성","gs://albatross-ed1d1.appspot.com/CU편의점.png","상시모집","1","42","5","44","010-0557-6312","광교동","17000","연령무관","100명 미만");


    }

    void writeDB(String employerIdToken, String id, String name, String job, String day, String education, String eperiod, String gender, String image, String period, String startHour, String startMinute, String endHour, String endMinute, String phoneNumber, String region, String wage, String age, String num) {

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
        mDatabase.child("ID").child(id).child("age").setValue(age);
        mDatabase.child("ID").child(id).child("num").setValue(num);



        //Job
        mDatabase.child("Job").child(job).push().setValue(id);

        //Day
        mDatabase.child("Day").child(day).push().setValue(id);

        //Region
        mDatabase.child("Region").child(region).push().setValue(id);

    }
}