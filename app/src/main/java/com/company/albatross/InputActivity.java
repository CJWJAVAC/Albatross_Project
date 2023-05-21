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
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","000","CU편의점","store","fri","19","39","20","40","010-6314-7741","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","001","스타벅스","cafe","fri","19","9","20","7","010-9947-2505","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","002","이디야커피","store","thu","3","17","23","17","010-0518-6675","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","003","새마을식당","delivery","wen","3","11","8","40","010-7203-6976","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","004","BBQ치킨","cafe","fri","1","34","19","6","010-7649-6928","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","005","새마을식당","serving","fri","1","44","2","20","010-6332-3560","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","006","파리바게트","delivery","thu","19","49","20","30","010-2764-4599","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","007","BBQ치킨","delivery","mon","8","33","23","17","010-7059-5802","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","008","GS25","serving","mon","9","48","18","9","010-3283-3401","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","009","GS25","delivery","sat","13","43","16","28","010-8555-0465","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","010","스타벅스","store","thu","23","39","24","14","010-3638-1083","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","011","이디야커피","delivery","mon","3","52","15","10","010-2564-8046","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","012","이디야커피","cooking","mon","14","28","23","32","010-7598-2066","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","013","파리바게트","store","sun","7","31","8","48","010-1055-8116","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","014","스타벅스","cooking","mon","0","31","15","52","010-8010-1312","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","015","CU편의점","cafe","fri","1","59","5","43","010-6765-2827","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","016","새마을식당","cooking","fri","4","9","21","45","010-9751-9810","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","017","파리바게트","store","tue","4","22","15","12","010-6418-7108","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","018","새마을식당","cafe","sat","17","54","22","59","010-4603-5749","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","019","GS25","cafe","sat","0","55","2","13","010-0854-2500","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","020","GS25","cafe","mon","2","34","9","39","010-4784-1517","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","021","BBQ치킨","cafe","mon","18","58","23","57","010-3205-1904","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","022","CU편의점","cafe","thu","9","25","16","3","010-7790-6193","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","023","BBQ치킨","serving","mon","0","18","4","12","010-6882-5807","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","024","GS25","cafe","thu","19","46","20","19","010-8687-9971","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","025","이디야커피","cooking","wen","7","59","24","25","010-5144-0178","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","026","새마을식당","store","thu","22","18","23","35","010-0947-9371","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","027","이디야커피","cafe","wen","2","24","21","58","010-3378-1736","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","028","CU편의점","serving","wen","11","28","13","39","010-8211-0922","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","029","파리바게트","cooking","wen","19","18","24","7","010-4666-6099","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","030","스타벅스","cafe","wen","2","49","6","2","010-1331-9815","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","031","스타벅스","serving","sat","15","55","21","38","010-9182-8676","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","032","올리브영","store","sun","19","5","23","36","010-5675-4376","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","033","올리브영","serving","wen","7","44","11","39","010-2909-3005","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","034","올리브영","serving","fri","7","57","24","8","010-3078-3941","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","035","새마을식당","cooking","mon","12","0","13","54","010-5538-0395","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","036","새마을식당","cafe","thu","20","0","23","27","010-9156-8446","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","037","새마을식당","store","wen","14","15","22","59","010-5981-7237","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","038","GS25","serving","wen","18","55","22","18","010-9078-6443","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","039","GS25","store","sun","2","54","24","38","010-1090-0961","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","040","스타벅스","delivery","sun","0","6","7","55","010-4189-6857","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","041","파리바게트","serving","wen","16","58","18","7","010-9422-4679","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","042","올리브영","store","tue","9","17","19","37","010-6537-6500","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","043","CU편의점","cooking","fri","15","30","22","53","010-0140-0807","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","044","BBQ치킨","delivery","mon","10","47","11","31","010-7130-8882","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","045","스타벅스","store","tue","14","32","19","0","010-3581-7315","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","046","새마을식당","cooking","mon","6","26","22","37","010-4410-2252","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","047","GS25","serving","sat","1","36","22","7","010-4357-9175","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","048","새마을식당","serving","sun","16","23","18","58","010-7703-4199","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","049","BBQ치킨","cafe","fri","20","33","22","58","010-8471-7331","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","050","GS25","delivery","wen","4","2","20","3","010-0273-3973","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","051","GS25","cafe","mon","15","52","22","8","010-1615-5743","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","052","올리브영","store","tue","19","48","22","8","010-1708-4209","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","053","CU편의점","store","wen","11","12","18","45","010-7938-4500","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","054","스타벅스","delivery","wen","14","47","20","37","010-6410-9721","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","055","CU편의점","cooking","sat","1","48","4","31","010-5068-8078","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","056","BBQ치킨","serving","thu","0","20","20","44","010-2769-5157","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","057","스타벅스","store","mon","2","46","10","11","010-2035-2806","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","058","CU편의점","store","mon","0","50","8","20","010-7711-6802","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","059","CU편의점","store","tue","10","39","15","47","010-5863-1877","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","060","파리바게트","serving","wen","22","20","24","24","010-3503-2093","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","061","스타벅스","serving","thu","8","17","9","6","010-6458-3249","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","062","이디야커피","delivery","fri","14","0","20","7","010-5504-7674","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","063","CU편의점","serving","mon","9","13","14","46","010-5860-5128","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","064","CU편의점","serving","mon","6","41","21","58","010-8695-9890","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","065","파리바게트","serving","fri","20","2","21","9","010-8400-4350","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","066","CU편의점","cafe","wen","22","53","23","38","010-5672-6591","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","067","GS25","delivery","thu","23","17","24","26","010-3952-5014","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","068","BBQ치킨","store","thu","21","37","22","49","010-5197-1295","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","069","이디야커피","cooking","wen","7","45","12","50","010-4988-0972","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","070","스타벅스","delivery","tue","19","41","21","40","010-6751-7545","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","071","GS25","serving","thu","11","35","16","30","010-5537-7325","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","072","CU편의점","delivery","sun","12","38","15","51","010-0179-4367","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","073","이디야커피","cafe","sat","20","15","21","25","010-2425-1790","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","074","새마을식당","cooking","thu","21","44","24","53","010-2781-7490","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","075","올리브영","delivery","sat","15","40","17","28","010-1519-2796","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","076","BBQ치킨","store","wen","14","6","20","41","010-4850-8339","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","077","이디야커피","delivery","wen","12","6","21","18","010-7141-0067","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","078","올리브영","cooking","sun","21","11","24","21","010-1064-8729","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","079","BBQ치킨","store","fri","0","51","6","12","010-9119-8662","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","080","BBQ치킨","serving","sat","21","17","23","18","010-3249-4060","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","081","새마을식당","cooking","sun","5","26","17","46","010-3290-0107","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","082","새마을식당","cafe","tue","14","43","16","22","010-2289-0140","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","083","BBQ치킨","serving","wen","8","4","16","23","010-3880-0368","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","084","파리바게트","serving","fri","8","26","9","10","010-4534-0029","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","085","새마을식당","cafe","sat","11","28","17","49","010-4072-7443","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","086","파리바게트","cooking","sat","11","9","21","57","010-4563-5285","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","087","올리브영","delivery","fri","21","58","22","49","010-9788-0817","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","088","CU편의점","store","wen","15","13","21","26","010-9070-6575","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","089","BBQ치킨","delivery","thu","4","7","8","47","010-2481-9188","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","090","새마을식당","cafe","thu","17","49","20","41","010-9859-7625","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","091","스타벅스","serving","tue","15","8","20","54","010-3586-8709","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","092","BBQ치킨","serving","fri","3","6","15","53","010-7049-4338","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","093","파리바게트","delivery","wen","12","0","20","34","010-7305-7091","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","094","스타벅스","serving","fri","16","54","17","36","010-8661-4951","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","095","올리브영","cafe","tue","12","48","20","32","010-3625-9036","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","096","올리브영","delivery","tue","22","30","24","34","010-1564-4679","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","097","올리브영","cafe","sun","17","31","21","32","010-4376-7456","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","098","파리바게트","store","fri","11","37","17","21","010-3256-0692","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","099","GS25","cooking","thu","23","36","24","29","010-8987-8537","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","100","스타벅스","delivery","wen","15","46","24","5","010-6718-1972","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","101","새마을식당","store","sat","2","33","10","12","010-3254-0292","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","102","이디야커피","serving","thu","19","37","21","52","010-5613-7895","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","103","올리브영","cafe","tue","9","37","22","25","010-4375-1421","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","104","스타벅스","cooking","sat","17","39","24","44","010-3815-4813","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","105","CU편의점","serving","wen","14","53","21","32","010-8256-8556","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","106","스타벅스","delivery","fri","18","11","19","25","010-2622-7438","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","107","새마을식당","serving","tue","13","53","22","6","010-5992-0543","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","108","스타벅스","cooking","wen","23","47","24","8","010-9045-1346","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","109","올리브영","delivery","wen","19","8","23","10","010-8447-4422","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","110","올리브영","cooking","thu","14","50","22","27","010-5999-8128","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","111","CU편의점","cafe","thu","3","5","24","46","010-0224-2363","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","112","GS25","delivery","sun","21","52","24","40","010-9579-4731","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","113","GS25","cafe","mon","23","32","24","5","010-3215-9491","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","114","스타벅스","cooking","tue","7","42","21","27","010-5712-4131","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","115","BBQ치킨","delivery","sat","8","8","18","14","010-6868-7526","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","116","이디야커피","cafe","mon","3","32","16","52","010-5488-0285","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","117","스타벅스","cooking","fri","1","59","4","45","010-4305-3992","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","118","이디야커피","cooking","fri","0","3","1","25","010-2667-8872","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","119","스타벅스","serving","mon","23","27","24","32","010-6193-8680","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","120","CU편의점","cafe","sun","5","43","9","31","010-8010-4305","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","121","파리바게트","store","mon","3","26","18","15","010-4847-5778","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","122","스타벅스","serving","fri","17","11","23","24","010-9225-3675","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","123","파리바게트","store","mon","0","32","24","0","010-4871-2764","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","124","CU편의점","cafe","tue","6","35","21","48","010-4054-4386","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","125","파리바게트","serving","tue","19","3","23","17","010-8223-8969","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","126","올리브영","store","thu","22","44","23","8","010-8783-2680","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","127","GS25","cafe","mon","8","38","22","47","010-4164-6263","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","128","GS25","cafe","mon","2","32","23","59","010-6831-8694","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","129","새마을식당","cafe","thu","9","35","19","48","010-4514-1847","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","130","이디야커피","delivery","thu","16","3","23","59","010-6445-2983","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","131","GS25","store","thu","9","21","15","11","010-9900-7883","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","132","이디야커피","store","sat","15","35","24","24","010-8190-8983","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","133","올리브영","cooking","mon","13","40","15","39","010-0076-7480","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","134","GS25","store","mon","23","18","24","7","010-2608-5142","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","135","파리바게트","serving","tue","20","23","21","59","010-3748-6705","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","136","CU편의점","store","sat","10","3","16","51","010-5815-7654","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","137","CU편의점","cafe","tue","8","16","21","35","010-8843-0725","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","138","파리바게트","store","thu","20","23","23","34","010-1518-4613","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","139","파리바게트","cooking","wen","22","25","23","12","010-3304-9918","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","140","이디야커피","store","tue","20","18","21","19","010-8113-5993","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","141","이디야커피","serving","sun","13","58","20","7","010-7331-0780","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","142","CU편의점","delivery","thu","13","56","14","16","010-9536-8839","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","143","올리브영","serving","fri","0","9","6","32","010-4827-3662","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","144","새마을식당","cafe","thu","1","6","18","25","010-7824-8979","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","145","CU편의점","serving","thu","5","17","23","2","010-5531-4491","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","146","BBQ치킨","delivery","sat","21","49","24","7","010-5049-0933","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","147","올리브영","cooking","sat","20","0","22","32","010-9141-4802","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","148","이디야커피","cafe","tue","12","14","24","46","010-2443-3941","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","149","CU편의점","serving","wen","11","57","21","21","010-5899-9188","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","150","스타벅스","serving","tue","2","35","21","43","010-5559-0704","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","151","파리바게트","delivery","thu","12","3","24","47","010-5071-3711","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","152","스타벅스","cafe","thu","20","40","24","39","010-0622-8834","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","153","GS25","serving","fri","20","26","22","45","010-1589-0689","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","154","CU편의점","cooking","tue","17","23","22","55","010-3170-8725","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","155","이디야커피","store","mon","10","19","21","39","010-9998-7468","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","156","CU편의점","serving","thu","20","43","23","35","010-5870-2189","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","157","올리브영","cafe","thu","0","24","20","19","010-7795-4300","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","158","올리브영","delivery","wen","8","59","17","21","010-4277-8231","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","159","GS25","delivery","mon","16","39","19","35","010-6376-1497","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","160","새마을식당","serving","thu","14","24","21","51","010-8900-8036","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","161","파리바게트","store","wen","17","14","23","9","010-6240-0081","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","162","BBQ치킨","cafe","wen","22","48","23","32","010-6644-6072","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","163","CU편의점","delivery","wen","9","1","18","57","010-1939-9205","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","164","올리브영","serving","sat","15","7","23","35","010-3765-2183","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","165","이디야커피","delivery","tue","2","40","10","42","010-9529-8562","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","166","올리브영","cafe","sat","23","55","24","35","010-0768-5286","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","167","파리바게트","cooking","wen","19","33","21","36","010-0824-7634","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","168","CU편의점","cafe","sat","15","23","21","44","010-6137-8294","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","169","BBQ치킨","store","wen","3","16","20","13","010-8292-3878","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","170","GS25","delivery","mon","9","0","18","3","010-9396-3464","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","171","스타벅스","store","fri","10","13","22","47","010-0686-2515","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","172","새마을식당","store","sun","13","9","22","27","010-4279-0896","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","173","CU편의점","serving","sun","4","9","6","16","010-9326-3598","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","174","올리브영","cafe","thu","16","34","20","43","010-7809-7452","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","175","파리바게트","serving","wen","23","13","24","53","010-5617-2057","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","176","파리바게트","cooking","thu","12","46","14","17","010-3390-4194","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","177","올리브영","serving","wen","15","55","19","27","010-6244-2459","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","178","BBQ치킨","store","thu","13","24","15","48","010-9825-0790","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","179","올리브영","delivery","mon","12","48","20","8","010-5025-6139","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","180","GS25","store","thu","8","9","13","11","010-9626-0128","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","181","새마을식당","serving","fri","5","35","9","22","010-4398-7604","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","182","스타벅스","delivery","wen","3","20","20","2","010-8894-9378","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","183","CU편의점","serving","mon","0","36","19","37","010-0283-8190","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","184","스타벅스","cafe","fri","16","29","18","3","010-3720-4561","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","185","새마을식당","delivery","wen","1","48","24","40","010-5247-7418","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","186","GS25","delivery","sun","21","26","22","20","010-7328-2947","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","187","CU편의점","cafe","sat","6","56","18","26","010-5554-3260","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","188","파리바게트","delivery","fri","15","37","20","22","010-2308-4382","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","189","스타벅스","delivery","sun","1","41","3","17","010-5187-3215","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","190","올리브영","delivery","wen","5","57","10","21","010-6237-4806","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","191","BBQ치킨","serving","thu","13","51","21","14","010-2181-0462","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","192","GS25","serving","mon","19","0","21","9","010-4130-4378","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","193","BBQ치킨","delivery","sat","22","14","23","3","010-2238-6850","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","194","BBQ치킨","delivery","fri","12","19","16","4","010-1357-1695","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","195","파리바게트","serving","thu","11","50","24","16","010-5168-3356","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","196","새마을식당","cafe","fri","11","37","17","41","010-2699-3530","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","197","새마을식당","cafe","fri","19","33","22","44","010-5413-7130","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","198","CU편의점","store","sat","19","55","21","2","010-7576-2596","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","199","스타벅스","cafe","sun","16","25","23","52","010-4413-5332","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","200","GS25","cooking","sun","7","27","16","18","010-7433-2596","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","201","스타벅스","cooking","mon","15","11","19","34","010-4503-7150","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","202","BBQ치킨","delivery","thu","21","22","22","8","010-7420-3447","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","203","올리브영","cooking","sat","12","14","24","20","010-4926-9000","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","204","파리바게트","store","fri","11","44","17","28","010-1593-2963","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","205","새마을식당","serving","wen","17","15","19","48","010-8817-2259","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","206","BBQ치킨","delivery","wen","16","56","23","4","010-2101-2066","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","207","올리브영","delivery","mon","17","12","20","22","010-0599-4838","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","208","스타벅스","store","tue","17","59","21","54","010-4890-2833","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","209","BBQ치킨","serving","sat","18","9","20","32","010-6911-9664","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","210","BBQ치킨","delivery","tue","15","2","20","1","010-3500-5771","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","211","이디야커피","cafe","wen","10","52","19","9","010-2614-2848","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","212","이디야커피","delivery","mon","16","30","22","34","010-2995-2016","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","213","CU편의점","store","tue","11","37","18","23","010-3296-2890","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","214","파리바게트","delivery","sat","13","41","22","27","010-9467-2837","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","215","GS25","delivery","sun","8","31","22","59","010-5949-7960","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","216","GS25","serving","wen","21","36","22","56","010-5365-3899","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","217","올리브영","cooking","wen","5","23","21","15","010-9089-7037","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","218","이디야커피","cafe","mon","1","14","9","17","010-0943-5820","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","219","이디야커피","serving","thu","3","34","11","5","010-6611-3260","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","220","GS25","cooking","fri","5","46","15","39","010-4262-1083","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","221","올리브영","cooking","tue","19","50","22","45","010-3397-1442","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","222","이디야커피","delivery","mon","15","42","17","43","010-2429-3371","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","223","이디야커피","cafe","fri","6","27","13","8","010-3220-2124","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","224","파리바게트","serving","sat","16","36","17","1","010-3781-6993","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","225","GS25","delivery","thu","16","42","23","49","010-9088-6187","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","226","GS25","store","fri","14","51","19","43","010-2595-4151","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","227","CU편의점","serving","wen","14","39","18","9","010-8700-8246","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","228","파리바게트","store","sun","21","10","22","47","010-1086-3771","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","229","스타벅스","cafe","mon","23","28","24","57","010-7106-4570","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","230","올리브영","cooking","tue","3","41","11","4","010-7555-7536","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","231","올리브영","cooking","fri","0","21","11","12","010-4385-0457","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","232","GS25","serving","wen","22","56","24","13","010-2383-9935","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","233","새마을식당","cafe","sun","20","15","21","51","010-6874-1365","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","234","올리브영","cafe","fri","15","48","22","19","010-4143-0383","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","235","파리바게트","delivery","thu","5","16","15","17","010-8788-8989","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","236","새마을식당","delivery","wen","4","57","10","27","010-0899-6152","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","237","파리바게트","delivery","thu","14","3","22","51","010-9487-6962","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","238","파리바게트","serving","wen","8","16","12","23","010-5927-6818","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","239","이디야커피","cafe","mon","11","29","22","23","010-1390-4807","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","240","BBQ치킨","delivery","sun","20","4","21","34","010-6345-1150","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","241","BBQ치킨","cafe","sat","19","44","21","9","010-3738-3712","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","242","파리바게트","serving","thu","10","13","21","13","010-1998-3032","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","243","새마을식당","cafe","mon","23","0","24","50","010-9500-4501","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","244","새마을식당","cooking","fri","17","32","21","13","010-2051-3804","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","245","GS25","store","sun","4","20","8","5","010-8780-8747","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","246","이디야커피","delivery","thu","1","24","2","5","010-9660-7481","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","247","GS25","delivery","sat","14","40","21","2","010-1428-1509","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","248","파리바게트","cooking","fri","19","47","21","24","010-6645-6796","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","249","올리브영","serving","sat","17","31","23","10","010-7084-3367","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","250","GS25","delivery","mon","0","57","11","53","010-2471-5249","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","251","파리바게트","serving","fri","12","1","24","52","010-4115-3270","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","252","BBQ치킨","serving","sat","21","18","22","25","010-2390-7230","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","253","새마을식당","delivery","thu","19","50","21","33","010-4444-0163","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","254","올리브영","delivery","sun","7","17","9","59","010-2594-3639","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","255","CU편의점","delivery","wen","22","0","23","34","010-6298-3764","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","256","BBQ치킨","delivery","wen","1","37","9","19","010-7900-3690","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","257","CU편의점","cafe","tue","5","49","16","58","010-5299-7855","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","258","BBQ치킨","serving","mon","10","5","21","21","010-1654-1262","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","259","파리바게트","delivery","wen","6","2","12","4","010-8424-8130","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","260","파리바게트","serving","sun","2","39","15","23","010-5628-1391","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","261","이디야커피","delivery","mon","3","3","22","36","010-5628-4915","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","262","올리브영","cafe","sun","14","51","22","0","010-9311-0155","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","263","CU편의점","serving","mon","6","47","16","35","010-8835-4533","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","264","올리브영","serving","wen","23","33","24","58","010-8766-4392","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","265","스타벅스","delivery","fri","13","11","19","11","010-1314-4820","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","266","스타벅스","cafe","tue","12","45","22","41","010-5063-3861","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","267","파리바게트","serving","sun","13","26","14","31","010-6116-4232","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","268","BBQ치킨","delivery","sat","20","31","23","38","010-9904-1749","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","269","새마을식당","serving","tue","3","28","12","29","010-8957-0799","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","270","스타벅스","cooking","sat","1","18","14","24","010-6901-4357","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","271","CU편의점","serving","wen","1","34","5","2","010-8510-1311","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","272","BBQ치킨","store","sat","15","1","17","57","010-3433-0720","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","273","GS25","store","sun","7","29","9","5","010-5581-0673","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","274","올리브영","cooking","wen","7","59","24","15","010-1365-0856","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","275","파리바게트","store","sun","5","19","10","14","010-0036-3530","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","276","새마을식당","serving","wen","3","29","5","57","010-4918-2697","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","277","CU편의점","store","fri","0","11","10","53","010-2670-8369","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","278","새마을식당","store","sat","16","1","22","34","010-1044-8765","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","279","GS25","delivery","fri","6","4","14","9","010-3498-7814","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","280","새마을식당","delivery","sun","10","59","13","50","010-6208-0529","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","281","새마을식당","delivery","thu","11","19","24","29","010-2633-5901","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","282","올리브영","cooking","fri","19","53","24","49","010-1927-1737","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","283","새마을식당","delivery","tue","14","33","19","1","010-1790-4737","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","284","이디야커피","delivery","thu","5","13","13","47","010-0369-3371","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","285","이디야커피","cafe","fri","3","38","21","40","010-9346-9372","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","286","새마을식당","store","fri","15","5","16","24","010-7947-1429","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","287","파리바게트","delivery","wen","3","3","15","34","010-9529-2859","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","288","CU편의점","serving","wen","22","33","23","21","010-4753-3203","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","289","파리바게트","cooking","fri","18","5","24","0","010-7251-7168","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","290","스타벅스","cafe","thu","12","5","17","45","010-8478-0896","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","291","이디야커피","store","sun","4","58","13","43","010-9829-8712","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","292","스타벅스","store","sun","18","3","20","18","010-1659-9841","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","293","BBQ치킨","cooking","thu","7","5","15","28","010-7177-5659","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","294","올리브영","cafe","wen","6","27","12","57","010-9108-3353","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","295","GS25","cooking","thu","6","9","9","0","010-3670-5020","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","296","이디야커피","cooking","thu","12","16","18","50","010-7699-7567","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","297","파리바게트","serving","sun","21","42","22","34","010-8371-0116","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","298","BBQ치킨","cooking","thu","9","18","14","41","010-8682-6695","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","299","BBQ치킨","delivery","fri","20","19","22","0","010-3394-5233","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","300","BBQ치킨","serving","fri","6","17","9","19","010-7109-8471","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","301","BBQ치킨","delivery","fri","22","43","24","50","010-3105-8826","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","302","올리브영","cooking","tue","8","26","13","23","010-4606-4736","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","303","파리바게트","serving","thu","22","23","23","7","010-1181-1714","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","304","BBQ치킨","cafe","fri","19","36","21","38","010-4189-8754","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","305","CU편의점","cafe","sun","18","45","23","17","010-6855-3964","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","306","올리브영","store","fri","17","0","20","51","010-0312-4572","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","307","스타벅스","delivery","mon","1","56","22","8","010-4288-3208","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","308","BBQ치킨","cooking","sun","14","29","16","59","010-9044-3542","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","309","스타벅스","cooking","mon","6","19","12","56","010-0881-2642","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","310","이디야커피","cafe","sun","11","55","16","56","010-4214-7931","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","311","BBQ치킨","cafe","mon","11","25","14","8","010-9208-6493","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","312","GS25","cooking","mon","13","7","21","18","010-0516-7468","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","313","파리바게트","store","tue","13","23","20","36","010-4015-2792","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","314","파리바게트","cafe","mon","11","54","20","4","010-4317-9646","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","315","이디야커피","cooking","sat","2","57","14","7","010-1694-5321","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","316","BBQ치킨","store","wen","4","57","7","4","010-0480-0889","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","317","BBQ치킨","cafe","wen","19","40","21","57","010-0408-7585","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","318","파리바게트","store","tue","8","43","10","49","010-2195-7404","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","319","이디야커피","delivery","thu","12","25","17","20","010-9269-1137","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","320","CU편의점","serving","wen","23","17","24","6","010-5464-8223","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","321","이디야커피","store","thu","5","6","9","15","010-3005-5223","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","322","파리바게트","store","sat","2","1","17","48","010-9154-5618","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","323","스타벅스","delivery","sat","7","14","9","29","010-7994-4825","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","324","새마을식당","delivery","sat","10","18","18","36","010-5811-2384","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","325","스타벅스","store","sat","2","13","23","38","010-8144-1570","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","326","새마을식당","store","tue","3","42","16","24","010-4944-1280","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","327","GS25","delivery","fri","0","44","18","5","010-1429-0973","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","328","파리바게트","serving","wen","7","9","19","26","010-2778-5593","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","329","올리브영","store","sat","18","1","19","55","010-9586-9744","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","330","CU편의점","cooking","tue","17","22","20","40","010-6308-4968","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","331","스타벅스","serving","wen","11","31","12","10","010-7629-1026","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","332","스타벅스","store","wen","16","16","19","56","010-8959-4581","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","333","GS25","delivery","wen","22","8","23","53","010-6682-8450","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","334","올리브영","cooking","wen","13","2","24","46","010-7465-6385","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","335","스타벅스","delivery","thu","18","46","19","37","010-2663-9165","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","336","파리바게트","cafe","sat","19","43","24","35","010-6744-9078","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","337","올리브영","delivery","sun","7","19","10","10","010-1755-3884","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","338","이디야커피","cooking","sat","11","5","23","11","010-3090-1700","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","339","올리브영","delivery","sat","12","50","16","10","010-3381-3575","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","340","BBQ치킨","serving","tue","6","12","11","14","010-7176-8826","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","341","파리바게트","serving","mon","13","56","21","19","010-5805-5171","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","342","스타벅스","serving","thu","19","40","23","52","010-4670-4017","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","343","CU편의점","delivery","sun","10","5","13","57","010-8710-2697","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","344","GS25","store","mon","4","11","23","52","010-0374-0894","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","345","GS25","serving","thu","16","36","18","41","010-3306-1996","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","346","BBQ치킨","store","sun","7","43","21","13","010-7535-3239","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","347","새마을식당","cafe","fri","2","58","13","21","010-1392-5004","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","348","BBQ치킨","store","mon","2","0","9","44","010-7601-6910","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","349","BBQ치킨","store","tue","7","30","11","44","010-9262-4598","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","350","CU편의점","serving","mon","5","13","24","8","010-4607-4016","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","351","이디야커피","store","mon","5","8","19","18","010-7815-0222","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","352","올리브영","serving","tue","4","15","10","16","010-2109-7464","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","353","이디야커피","cooking","fri","17","53","18","1","010-1076-0543","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","354","새마을식당","cooking","sun","21","53","23","32","010-0543-8408","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","355","GS25","serving","mon","0","46","13","57","010-7833-5436","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","356","이디야커피","serving","mon","0","45","14","40","010-9622-9196","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","357","파리바게트","store","tue","10","16","22","25","010-4251-1877","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","358","CU편의점","serving","wen","2","24","20","44","010-0436-7235","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","359","스타벅스","cafe","tue","18","22","23","4","010-8538-6747","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","360","이디야커피","store","fri","12","36","13","24","010-9421-4767","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","361","CU편의점","cooking","tue","20","51","24","17","010-0539-3516","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","362","새마을식당","serving","fri","14","35","22","38","010-8222-1251","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","363","CU편의점","delivery","sun","19","31","23","26","010-6042-2272","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","364","GS25","cafe","tue","22","29","23","52","010-4169-5081","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","365","이디야커피","delivery","sun","8","40","20","7","010-0351-2124","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","366","GS25","cafe","sat","23","14","24","56","010-4613-7057","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","367","CU편의점","delivery","wen","9","4","24","16","010-2799-2357","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","368","새마을식당","store","tue","9","38","19","20","010-6640-8628","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","369","CU편의점","store","tue","0","45","9","32","010-0441-6532","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","370","CU편의점","store","sun","4","57","23","17","010-5011-1691","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","371","새마을식당","delivery","sun","4","28","11","13","010-3051-7250","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","372","CU편의점","delivery","thu","1","54","5","32","010-8031-0679","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","373","새마을식당","store","mon","5","27","12","12","010-2317-5669","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","374","올리브영","cafe","sat","16","57","19","18","010-4942-1319","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","375","GS25","cooking","wen","4","31","10","8","010-6603-9721","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","376","BBQ치킨","cafe","tue","3","38","24","10","010-4904-8990","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","377","CU편의점","cafe","wen","2","55","10","8","010-1184-2697","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","378","파리바게트","cafe","sun","11","7","17","27","010-3863-2047","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","379","파리바게트","cooking","thu","14","23","21","47","010-2777-6989","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","380","스타벅스","delivery","sun","3","36","11","54","010-2289-0210","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","381","이디야커피","cafe","fri","4","47","11","51","010-4718-6289","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","382","파리바게트","cooking","wen","5","0","8","17","010-1686-1578","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","383","올리브영","serving","thu","13","52","19","9","010-7845-7075","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","384","스타벅스","store","tue","17","58","20","15","010-8462-9316","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","385","새마을식당","cafe","fri","11","45","20","3","010-2410-8233","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","386","스타벅스","cooking","thu","21","20","23","42","010-4375-8666","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","387","이디야커피","cafe","wen","3","6","6","17","010-8048-9953","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","388","스타벅스","delivery","thu","17","44","19","39","010-5299-4370","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","389","스타벅스","serving","tue","23","44","24","49","010-0086-5993","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","390","올리브영","serving","tue","4","15","20","35","010-9127-4819","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","391","파리바게트","store","wen","19","6","21","15","010-9406-0182","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","392","스타벅스","serving","thu","16","13","19","11","010-3031-3701","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","393","올리브영","serving","mon","7","20","17","59","010-6925-6339","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","394","스타벅스","cafe","fri","13","19","24","27","010-7185-6930","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","395","스타벅스","cooking","thu","9","33","17","36","010-5769-3353","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","396","BBQ치킨","cooking","wen","20","8","23","9","010-5573-1148","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","397","GS25","cooking","wen","10","18","16","35","010-5396-9954","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","398","새마을식당","delivery","mon","0","21","17","15","010-7281-2849","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","399","BBQ치킨","serving","wen","10","0","11","27","010-2788-5619","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","400","파리바게트","cafe","sat","22","3","24","33","010-1467-4642","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","401","GS25","delivery","sat","17","27","18","26","010-5406-2331","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","402","스타벅스","cooking","sat","2","22","9","17","010-3899-2760","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","403","새마을식당","cooking","sun","21","13","22","50","010-3790-1490","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","404","스타벅스","cooking","thu","23","49","24","56","010-7892-9712","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","405","이디야커피","delivery","tue","13","17","15","57","010-8812-3495","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","406","CU편의점","cooking","mon","9","35","12","3","010-6916-9379","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","407","GS25","store","wen","22","41","23","6","010-2511-4555","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","408","이디야커피","serving","tue","6","24","8","11","010-8255-2595","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","409","스타벅스","cafe","wen","14","37","15","40","010-9083-1752","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","410","올리브영","cafe","tue","15","51","21","20","010-5409-9627","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","411","올리브영","store","mon","17","38","19","24","010-8078-8258","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","412","CU편의점","cooking","sat","6","46","7","26","010-6028-6729","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","413","새마을식당","delivery","sun","11","43","23","27","010-9433-6857","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","414","새마을식당","cooking","sun","9","19","15","23","010-3550-1057","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","415","스타벅스","store","sun","13","55","24","45","010-8677-0691","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","416","CU편의점","store","fri","8","12","17","4","010-5779-6057","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","417","새마을식당","delivery","thu","8","58","19","8","010-8233-4471","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","418","파리바게트","delivery","sun","13","39","22","15","010-3672-8652","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","419","파리바게트","delivery","fri","17","12","21","42","010-0800-4612","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","420","스타벅스","cafe","fri","0","58","24","48","010-8548-6832","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","421","GS25","cafe","mon","14","27","16","45","010-0955-7213","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","422","이디야커피","store","fri","16","24","18","48","010-4372-1980","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","423","파리바게트","cafe","sun","4","22","15","55","010-7994-4323","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","424","BBQ치킨","delivery","thu","17","37","19","8","010-6205-0926","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","425","GS25","cafe","thu","12","10","15","23","010-8985-2252","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","426","파리바게트","store","sat","20","54","24","55","010-5417-1980","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","427","새마을식당","cooking","thu","13","57","14","31","010-7318-0675","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","428","스타벅스","cafe","fri","17","6","23","26","010-0000-2234","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","429","GS25","cooking","wen","21","31","24","28","010-9155-7329","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","430","파리바게트","serving","sun","4","46","12","17","010-9169-5865","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","431","스타벅스","cafe","fri","23","32","24","13","010-0543-6443","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","432","이디야커피","cafe","tue","0","14","9","7","010-2831-4829","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","433","새마을식당","serving","fri","20","17","23","33","010-1469-6717","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","434","GS25","store","mon","16","31","20","41","010-4015-4947","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","435","스타벅스","store","fri","11","37","16","58","010-8092-0808","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","436","파리바게트","store","fri","9","57","20","4","010-3948-3369","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","437","CU편의점","serving","tue","13","33","16","0","010-5800-6061","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","438","파리바게트","delivery","tue","22","0","24","2","010-8105-3131","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","439","BBQ치킨","cooking","thu","10","21","18","0","010-8948-3524","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","440","스타벅스","cooking","tue","16","9","18","7","010-2201-2186","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","441","파리바게트","cafe","sun","10","22","15","2","010-4462-9672","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","442","CU편의점","serving","tue","13","41","24","49","010-6460-2655","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","443","이디야커피","cafe","thu","21","17","24","5","010-6053-1185","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","444","GS25","cafe","tue","14","23","18","27","010-5217-2667","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","445","GS25","serving","tue","17","38","23","12","010-9918-9153","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","446","파리바게트","cafe","wen","15","5","21","14","010-2353-6268","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","447","CU편의점","store","sat","6","28","10","11","010-2503-1468","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","448","파리바게트","serving","thu","4","6","20","56","010-8784-9880","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","449","이디야커피","serving","sat","6","32","8","6","010-4447-5297","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","450","GS25","cooking","sat","11","26","14","41","010-9899-8418","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","451","이디야커피","cooking","sat","13","54","14","25","010-9385-4583","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","452","파리바게트","cafe","thu","9","41","14","54","010-3964-2724","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","453","BBQ치킨","cafe","mon","12","29","16","29","010-1222-7043","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","454","CU편의점","delivery","wen","10","13","15","10","010-8235-6312","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","455","GS25","cooking","thu","19","33","23","23","010-1828-9701","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","456","스타벅스","cooking","tue","23","19","24","41","010-1977-3941","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","457","GS25","cooking","thu","17","32","20","14","010-0872-7340","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","458","새마을식당","cooking","sat","13","43","24","10","010-3270-4237","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","459","새마을식당","cooking","tue","18","7","19","40","010-0848-0123","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","460","올리브영","cooking","fri","9","46","13","50","010-7712-1554","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","461","새마을식당","serving","mon","8","1","12","23","010-5111-8733","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","462","파리바게트","cooking","thu","21","26","22","28","010-3049-3878","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","463","올리브영","cafe","wen","3","50","10","57","010-6765-1806","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","464","올리브영","cafe","thu","17","4","23","28","010-0247-1035","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","465","파리바게트","store","tue","21","42","23","50","010-4178-6439","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","466","BBQ치킨","serving","fri","8","59","13","14","010-0199-0648","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","467","올리브영","store","sat","17","55","18","7","010-1611-7357","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","468","CU편의점","cafe","thu","17","34","21","6","010-5566-2726","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","469","이디야커피","cooking","thu","15","44","21","57","010-3848-2604","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","470","CU편의점","delivery","sun","7","0","19","20","010-3155-3654","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","471","올리브영","cooking","wen","6","30","23","58","010-8984-4971","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","472","이디야커피","delivery","tue","12","16","23","38","010-5664-6964","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","473","스타벅스","cooking","fri","5","50","22","49","010-0732-0207","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","474","스타벅스","cooking","sat","17","19","23","11","010-9885-9880","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","475","새마을식당","delivery","tue","11","18","17","32","010-9447-1701","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","476","올리브영","cooking","thu","12","25","14","45","010-0918-4595","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","477","새마을식당","store","tue","14","7","24","54","010-8746-0903","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","478","이디야커피","serving","wen","7","15","19","56","010-0574-7901","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","479","올리브영","serving","fri","23","21","24","37","010-1482-6757","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","480","올리브영","delivery","sat","6","44","18","40","010-4686-1837","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","481","스타벅스","serving","sat","19","43","22","12","010-0568-8431","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","482","올리브영","cafe","wen","15","2","24","22","010-1335-2474","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","483","CU편의점","delivery","tue","3","38","19","0","010-2135-7675","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","484","이디야커피","cafe","thu","10","19","20","54","010-4082-1062","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","485","올리브영","serving","tue","21","59","22","58","010-3586-5723","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","486","BBQ치킨","serving","tue","8","27","15","44","010-0595-4887","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","487","BBQ치킨","delivery","thu","23","19","24","15","010-5380-4060","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","488","이디야커피","store","sun","16","8","23","34","010-9900-2233","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","489","이디야커피","delivery","thu","11","40","20","56","010-5288-1592","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","490","스타벅스","store","sun","16","27","21","27","010-8111-3730","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","491","스타벅스","cooking","tue","23","23","24","56","010-8602-2059","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","492","GS25","cooking","thu","14","7","24","3","010-0455-1855","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","493","스타벅스","serving","sun","7","3","20","33","010-6865-1117","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","494","새마을식당","cafe","fri","10","44","19","6","010-5643-6030","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","495","파리바게트","delivery","mon","15","52","21","57","010-0272-2264","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","496","스타벅스","serving","sun","6","0","16","17","010-9547-7143","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","497","BBQ치킨","serving","fri","21","8","24","51","010-8433-1143","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","498","올리브영","serving","thu","0","32","22","24","010-2125-5625","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","499","파리바게트","delivery","sat","20","22","23","41","010-4634-9129","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","500","올리브영","serving","sat","12","48","20","51","010-3061-4521","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","501","올리브영","store","tue","21","33","23","28","010-7396-9814","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","502","GS25","delivery","thu","23","10","24","59","010-9809-9275","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","503","새마을식당","cooking","thu","15","32","24","10","010-7366-5392","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","504","이디야커피","cooking","thu","5","21","24","11","010-4122-2314","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","505","새마을식당","delivery","wen","17","53","24","10","010-5879-9977","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","506","BBQ치킨","cooking","sat","3","6","19","24","010-1274-3041","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","507","스타벅스","cafe","wen","21","35","22","34","010-9196-3415","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","508","CU편의점","delivery","tue","14","17","24","9","010-9741-1516","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","509","GS25","cafe","wen","4","43","8","53","010-2763-3374","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","510","새마을식당","delivery","mon","12","24","23","35","010-5668-6084","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","511","새마을식당","delivery","fri","7","33","21","42","010-6220-0772","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","512","CU편의점","cafe","mon","11","50","15","44","010-1395-9408","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","513","이디야커피","store","wen","9","25","23","31","010-7644-9678","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","514","파리바게트","serving","sat","17","4","23","1","010-8089-5694","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","515","이디야커피","serving","thu","19","42","21","13","010-5855-1134","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","516","올리브영","cafe","mon","19","50","24","20","010-9588-1296","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","517","올리브영","cafe","sun","20","49","21","51","010-1038-8250","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","518","이디야커피","cafe","sun","21","42","24","25","010-0671-3471","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","519","GS25","delivery","sun","23","26","24","5","010-6052-3419","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","520","CU편의점","delivery","mon","11","44","12","25","010-1614-8829","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","521","BBQ치킨","store","tue","23","52","24","11","010-4076-3282","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","522","올리브영","store","fri","20","40","23","48","010-8711-9637","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","523","이디야커피","cafe","sun","14","20","15","52","010-4278-3771","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","524","올리브영","cooking","sat","22","19","23","30","010-8086-0567","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","525","새마을식당","cafe","fri","20","9","24","56","010-9722-6726","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","526","이디야커피","serving","wen","8","23","22","8","010-1962-3864","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","527","이디야커피","cafe","sun","12","51","16","22","010-0157-9414","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","528","올리브영","store","mon","7","27","18","42","010-4568-0102","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","529","새마을식당","store","mon","16","40","24","12","010-4856-2224","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","530","GS25","delivery","sun","4","6","20","5","010-6338-1201","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","531","스타벅스","cafe","tue","0","46","12","46","010-0519-1025","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","532","GS25","serving","sat","6","51","7","30","010-9495-5008","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","533","스타벅스","serving","wen","16","31","21","53","010-0339-5280","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","534","새마을식당","store","wen","21","21","23","24","010-3524-7402","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","535","CU편의점","serving","mon","11","50","16","59","010-3500-3605","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","536","스타벅스","cooking","tue","14","14","24","40","010-4047-3789","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","537","올리브영","delivery","thu","22","54","24","12","010-3944-6357","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","538","새마을식당","cooking","fri","21","17","23","14","010-2416-6537","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","539","새마을식당","store","mon","14","11","24","36","010-5009-8403","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","540","GS25","delivery","mon","12","17","23","31","010-4803-7369","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","541","GS25","cafe","tue","4","45","21","32","010-0302-5378","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","542","GS25","delivery","sun","4","46","13","36","010-4343-2173","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","543","CU편의점","serving","mon","5","51","8","30","010-4031-2244","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","544","CU편의점","store","sat","3","10","19","3","010-8299-8732","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","545","BBQ치킨","cooking","sun","1","59","6","18","010-4926-1031","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","546","GS25","cafe","mon","13","38","16","40","010-4381-6878","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","547","스타벅스","cafe","mon","20","57","23","53","010-7255-5437","하동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","548","GS25","cooking","sat","1","10","9","26","010-2442-8969","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","549","이디야커피","cafe","mon","18","20","19","53","010-1206-3375","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","550","이디야커피","cooking","wen","23","44","24","31","010-6308-0409","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","551","이디야커피","serving","thu","5","37","7","59","010-6827-6454","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","552","GS25","cooking","mon","6","8","14","34","010-8598-1656","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","553","GS25","serving","sun","0","24","23","32","010-2734-2781","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","554","파리바게트","cafe","sun","2","57","6","19","010-5909-8294","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","555","스타벅스","store","wen","23","25","24","46","010-8265-0775","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","556","이디야커피","cooking","sat","3","44","15","21","010-4699-2200","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","557","새마을식당","serving","tue","15","21","23","56","010-2262-9368","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","558","파리바게트","cafe","sun","0","52","11","51","010-3522-0956","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","559","스타벅스","serving","sat","8","32","9","17","010-1092-8379","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","560","올리브영","cafe","sat","11","49","20","0","010-5427-7205","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","561","올리브영","serving","sat","11","4","16","43","010-5876-8259","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","562","CU편의점","delivery","thu","13","30","15","17","010-1973-2862","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","563","올리브영","serving","mon","0","5","17","11","010-1520-0538","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","564","BBQ치킨","delivery","sat","11","47","16","7","010-8133-1314","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","565","GS25","cafe","mon","0","13","24","43","010-7538-3793","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","566","이디야커피","serving","fri","3","26","12","45","010-5032-3642","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","567","스타벅스","cafe","mon","20","4","24","4","010-2027-6664","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","568","새마을식당","cooking","mon","6","19","18","25","010-3300-0461","원천동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","569","스타벅스","cooking","fri","23","16","24","23","010-3869-4343","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","570","올리브영","serving","fri","22","40","24","3","010-3637-3653","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","571","스타벅스","store","mon","17","21","24","59","010-8715-1116","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","572","스타벅스","serving","wen","6","10","10","46","010-4585-8063","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","573","GS25","serving","mon","21","7","23","34","010-7320-9203","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","574","GS25","cooking","tue","22","55","24","51","010-0207-3694","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","575","새마을식당","cafe","wen","13","16","24","35","010-0476-5022","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","576","스타벅스","delivery","sat","23","40","24","3","010-7666-4563","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","577","CU편의점","cafe","sat","14","45","17","41","010-5107-5101","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","578","BBQ치킨","store","thu","23","29","24","7","010-4619-6481","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","579","CU편의점","delivery","mon","3","14","9","3","010-7091-4220","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","580","파리바게트","delivery","fri","10","6","11","12","010-5250-9179","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","581","CU편의점","delivery","sun","8","57","16","59","010-1153-8144","원천동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","582","BBQ치킨","serving","mon","19","8","21","33","010-5264-2302","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","583","올리브영","cafe","sun","11","8","20","52","010-8691-5334","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","584","CU편의점","serving","sat","3","2","5","42","010-4903-2410","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","585","이디야커피","serving","sun","12","2","17","14","010-4643-0922","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","586","새마을식당","cooking","wen","5","39","22","42","010-0291-3077","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","587","CU편의점","cooking","sun","4","23","5","54","010-2821-2634","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","588","올리브영","delivery","wen","12","0","13","36","010-8660-0741","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","589","파리바게트","cooking","wen","20","59","24","7","010-7193-2681","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","590","BBQ치킨","store","sat","10","21","22","11","010-1780-5848","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","591","스타벅스","store","tue","9","56","22","46","010-5040-5159","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","592","올리브영","store","mon","2","19","4","50","010-2126-3758","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","593","새마을식당","delivery","thu","17","36","21","36","010-5321-5988","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","594","스타벅스","cafe","mon","8","34","19","34","010-8170-9719","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","595","이디야커피","serving","tue","20","18","24","28","010-0263-5241","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","596","CU편의점","serving","tue","23","19","24","38","010-0430-1175","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","597","스타벅스","cafe","tue","0","39","19","41","010-4942-9973","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","598","BBQ치킨","serving","tue","17","25","19","34","010-2117-3509","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","599","BBQ치킨","store","sun","2","12","13","14","010-3545-7327","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","600","새마을식당","delivery","tue","21","56","22","55","010-0417-3582","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","601","올리브영","cooking","tue","2","58","9","43","010-7124-3778","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","602","스타벅스","cooking","fri","20","57","21","32","010-2071-0435","광교동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","603","CU편의점","store","mon","1","7","15","6","010-1887-9321","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","604","GS25","cooking","sat","23","39","24","17","010-6777-7173","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","605","BBQ치킨","delivery","tue","23","2","24","12","010-6824-6036","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","606","스타벅스","store","tue","4","39","23","23","010-9330-3968","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","607","BBQ치킨","delivery","tue","4","39","22","38","010-2416-6133","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","608","CU편의점","cooking","fri","14","57","21","1","010-3141-8691","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","609","새마을식당","cooking","thu","16","56","17","9","010-5871-0323","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","610","스타벅스","store","wen","6","29","19","29","010-2193-3362","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","611","파리바게트","cooking","fri","14","12","23","10","010-7745-7661","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","612","올리브영","cooking","sat","11","56","23","2","010-8788-1089","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","613","CU편의점","delivery","mon","19","20","23","2","010-6912-0830","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","614","GS25","delivery","tue","12","11","22","4","010-1223-7908","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","615","새마을식당","cafe","fri","16","54","21","24","010-4834-8639","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","616","이디야커피","delivery","wen","17","17","18","28","010-8778-6692","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","617","스타벅스","cooking","mon","2","56","18","51","010-2168-2430","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","618","CU편의점","delivery","fri","12","35","21","17","010-0458-6384","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","619","BBQ치킨","delivery","thu","20","20","21","34","010-9097-0739","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","620","올리브영","cooking","tue","17","1","22","45","010-5719-6667","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","621","GS25","delivery","tue","4","10","19","19","010-5746-3878","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","622","GS25","cooking","fri","1","3","18","53","010-8004-4269","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","623","파리바게트","store","fri","4","59","5","17","010-2289-2997","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","624","새마을식당","delivery","tue","9","6","24","39","010-1455-4193","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","625","새마을식당","serving","thu","12","53","21","9","010-0400-6808","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","626","스타벅스","serving","fri","3","15","19","37","010-6023-1991","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","627","파리바게트","serving","tue","14","27","20","2","010-6343-3632","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","628","CU편의점","serving","mon","19","23","24","37","010-9513-1058","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","629","CU편의점","serving","mon","7","38","19","18","010-6513-9746","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","630","BBQ치킨","cafe","thu","17","3","19","46","010-3078-9557","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","631","새마을식당","cooking","wen","16","16","19","12","010-1835-9056","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","632","새마을식당","cooking","tue","12","47","24","27","010-4529-8380","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","633","파리바게트","cooking","sun","0","32","1","56","010-0690-4666","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","634","스타벅스","cafe","wen","4","23","11","28","010-5449-1496","영통동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","635","올리브영","delivery","thu","4","2","5","16","010-1025-2027","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","636","새마을식당","delivery","tue","13","33","18","40","010-7094-8503","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","637","파리바게트","delivery","fri","10","34","22","25","010-5801-1882","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","638","새마을식당","store","wen","11","13","23","57","010-7754-2185","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","639","이디야커피","cooking","thu","15","33","16","59","010-2984-5413","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","640","새마을식당","cooking","mon","17","15","21","46","010-3815-4272","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","641","새마을식당","cooking","sun","0","42","14","42","010-6574-4015","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","642","새마을식당","cafe","thu","21","7","23","25","010-3305-7755","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","643","GS25","cafe","fri","8","45","16","29","010-6280-7840","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","644","새마을식당","cafe","fri","12","44","16","20","010-0054-2403","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","645","BBQ치킨","delivery","wen","22","51","24","14","010-9802-1706","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","646","올리브영","store","sat","20","30","24","36","010-9439-1731","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","647","새마을식당","store","sun","21","11","22","17","010-3283-9695","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","648","파리바게트","delivery","tue","4","14","20","30","010-5637-8738","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","649","파리바게트","cooking","wen","20","58","22","25","010-1652-2732","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","650","BBQ치킨","serving","mon","15","7","24","38","010-7548-6876","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","651","BBQ치킨","store","tue","13","57","15","41","010-3212-7637","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","652","BBQ치킨","cafe","tue","5","22","9","32","010-9338-6606","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","653","CU편의점","cooking","fri","3","52","6","29","010-1625-1384","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","654","GS25","store","sun","2","0","11","56","010-6175-3886","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","655","스타벅스","cooking","sun","17","16","22","2","010-7943-5216","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","656","BBQ치킨","cafe","tue","23","48","24","0","010-3599-6444","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","657","스타벅스","cooking","wen","17","56","18","25","010-8831-9583","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","658","스타벅스","cafe","wen","6","34","9","37","010-7276-4979","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","659","새마을식당","delivery","mon","13","20","21","20","010-5951-0603","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","660","이디야커피","store","wen","17","35","22","50","010-5647-5871","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","661","파리바게트","cooking","sat","1","36","11","39","010-1912-8730","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","662","올리브영","store","fri","23","40","24","16","010-9246-5118","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","663","이디야커피","serving","thu","20","50","22","28","010-4223-5589","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","664","GS25","cooking","tue","18","0","20","14","010-9504-2318","태장동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","665","파리바게트","serving","sat","10","4","17","53","010-6368-3193","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","666","새마을식당","cafe","wen","3","56","19","52","010-3036-6947","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","667","GS25","cooking","wen","4","13","12","1","010-0174-3489","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","668","새마을식당","store","fri","21","59","23","14","010-3755-1503","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","669","BBQ치킨","cooking","thu","17","12","23","33","010-9486-0572","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","670","스타벅스","delivery","fri","1","39","8","36","010-0692-1771","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","671","파리바게트","cafe","sun","22","40","23","4","010-8336-3351","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","672","BBQ치킨","delivery","fri","15","41","22","29","010-0147-7373","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","673","스타벅스","serving","sun","18","34","23","24","010-2904-3594","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","674","이디야커피","cafe","sat","0","2","22","25","010-7988-4826","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","675","파리바게트","cooking","fri","21","46","23","30","010-7028-9422","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","676","올리브영","store","sat","5","9","23","25","010-9996-1074","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","677","CU편의점","delivery","tue","18","24","21","8","010-1437-4470","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","678","GS25","store","thu","8","26","19","0","010-8766-2281","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","679","스타벅스","store","sat","7","25","12","12","010-7844-3394","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","680","GS25","cooking","mon","19","32","21","11","010-4951-5624","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","681","CU편의점","store","wen","22","30","23","8","010-5585-9508","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","682","BBQ치킨","store","fri","11","15","19","47","010-6794-4622","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","683","CU편의점","serving","fri","1","24","16","9","010-8256-3199","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","684","GS25","serving","tue","13","1","14","54","010-5395-5503","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","685","이디야커피","store","tue","9","46","24","24","010-3712-3257","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","686","BBQ치킨","store","thu","7","43","10","16","010-3948-2504","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","687","BBQ치킨","serving","thu","10","46","13","9","010-0986-3527","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","688","GS25","delivery","fri","9","33","20","53","010-1760-4551","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","689","올리브영","serving","tue","18","27","19","48","010-9038-2973","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","690","BBQ치킨","cooking","mon","16","13","21","47","010-9967-9249","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","691","BBQ치킨","delivery","tue","1","35","17","1","010-9537-4201","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","692","파리바게트","delivery","tue","5","29","16","35","010-6858-9363","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","693","파리바게트","cooking","mon","17","0","21","30","010-1882-6182","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","694","CU편의점","cafe","mon","11","22","20","54","010-4884-9249","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","695","이디야커피","delivery","tue","13","25","15","46","010-7811-5315","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","696","올리브영","store","tue","11","11","14","43","010-2307-9614","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","697","스타벅스","delivery","sat","7","0","15","45","010-2713-2954","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","698","파리바게트","cafe","thu","14","9","21","35","010-1876-0213","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","699","GS25","delivery","sat","18","46","23","40","010-1129-5512","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","700","새마을식당","cooking","thu","8","59","20","25","010-0043-8806","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","701","CU편의점","serving","sun","18","14","22","8","010-2775-2986","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","702","올리브영","store","fri","12","18","21","3","010-6206-6678","이의동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","703","올리브영","delivery","mon","22","1","24","41","010-8860-0726","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","704","CU편의점","store","tue","16","22","24","29","010-6477-6234","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","705","CU편의점","store","sat","23","43","24","23","010-5824-8616","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","706","올리브영","cafe","wen","20","3","22","50","010-2747-5407","매탄동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","707","새마을식당","delivery","wen","6","52","23","3","010-8557-3868","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","708","CU편의점","delivery","tue","12","9","16","43","010-4117-3611","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","709","스타벅스","serving","thu","17","59","24","54","010-7758-9262","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","710","GS25","cafe","fri","19","57","24","34","010-4618-2996","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","711","새마을식당","cafe","fri","12","44","17","42","010-9583-1157","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","712","파리바게트","serving","wen","4","46","6","31","010-4049-2468","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","713","스타벅스","serving","wen","15","6","21","58","010-4965-8188","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","714","새마을식당","serving","fri","3","53","15","14","010-7717-6899","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","715","이디야커피","cooking","tue","5","5","6","1","010-7851-1406","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","716","CU편의점","serving","mon","18","8","19","9","010-1794-8055","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","717","파리바게트","serving","sat","9","56","22","8","010-9783-4070","영통동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","718","BBQ치킨","cooking","sun","6","22","13","57","010-7869-6661","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","719","GS25","serving","sat","15","52","19","33","010-4430-8260","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","720","새마을식당","serving","fri","16","15","20","59","010-2871-9040","매탄동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","721","GS25","serving","sat","10","5","14","49","010-7352-8752","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","722","CU편의점","store","thu","18","55","24","35","010-3090-5868","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","723","GS25","serving","sun","16","57","18","4","010-4591-2632","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","724","이디야커피","cafe","fri","11","10","22","24","010-9569-1398","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","725","GS25","serving","fri","22","54","24","8","010-2077-6578","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","726","이디야커피","delivery","tue","4","43","7","39","010-5086-9839","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","727","GS25","cooking","mon","4","26","7","0","010-5322-2831","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","728","스타벅스","cafe","sat","12","19","24","12","010-2039-6550","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","729","이디야커피","cooking","wen","8","58","23","23","010-8944-1000","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","730","스타벅스","cooking","sun","9","29","10","45","010-6005-7275","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","731","올리브영","store","mon","13","42","15","43","010-8590-5423","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","732","BBQ치킨","store","mon","19","44","20","25","010-8521-1195","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","733","GS25","cafe","thu","18","13","22","21","010-6962-3249","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","734","BBQ치킨","store","sat","11","25","19","39","010-0227-0298","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","735","GS25","store","mon","14","55","17","58","010-4516-6643","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","736","이디야커피","delivery","tue","17","59","24","38","010-7192-7815","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","737","GS25","delivery","sat","17","10","21","24","010-9655-1837","하동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","738","GS25","cooking","tue","5","6","22","8","010-3207-0501","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","739","새마을식당","serving","sat","20","4","24","40","010-5024-0990","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","740","이디야커피","store","wen","12","28","14","48","010-8339-0664","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","741","GS25","store","sat","22","0","23","0","010-3440-7294","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","742","이디야커피","serving","thu","13","4","22","17","010-6877-1155","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","743","CU편의점","delivery","tue","1","18","13","35","010-9914-7674","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","744","이디야커피","delivery","sun","22","39","24","15","010-5122-8757","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","745","이디야커피","delivery","thu","20","11","21","3","010-5486-0627","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","746","파리바게트","serving","fri","12","0","24","24","010-3071-4739","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","747","CU편의점","cooking","wen","5","31","21","11","010-1681-3709","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","748","스타벅스","serving","sun","21","14","23","36","010-1827-4744","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","749","GS25","store","thu","15","53","17","36","010-3977-0975","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","750","이디야커피","cafe","fri","13","57","24","53","010-7341-2071","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","751","이디야커피","store","mon","14","42","16","30","010-6254-4161","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","752","스타벅스","cafe","mon","2","25","10","49","010-7329-7256","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","753","CU편의점","store","thu","17","26","18","10","010-0407-7371","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","754","새마을식당","delivery","sat","6","24","21","51","010-9893-0734","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","755","CU편의점","cooking","sat","12","57","14","48","010-3859-4516","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","756","CU편의점","cooking","mon","5","39","23","38","010-3327-2050","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","757","올리브영","cafe","mon","21","18","24","35","010-8893-3274","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","758","새마을식당","cafe","fri","1","26","5","0","010-2525-2551","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","759","새마을식당","serving","tue","19","59","22","45","010-1541-6320","신동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","760","올리브영","cooking","sat","8","7","13","40","010-9848-1114","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","761","올리브영","serving","thu","22","18","24","38","010-3495-4831","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","762","파리바게트","cooking","sat","12","0","22","8","010-2210-4422","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","763","올리브영","cooking","sun","2","34","18","10","010-9181-9769","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","764","이디야커피","cafe","mon","14","51","24","34","010-7252-0274","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","765","새마을식당","cafe","wen","18","31","19","46","010-4602-9588","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","766","BBQ치킨","serving","fri","6","51","17","38","010-4600-7125","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","767","CU편의점","serving","mon","11","19","22","46","010-7620-5123","하동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","768","파리바게트","cafe","fri","7","3","12","38","010-1484-3761","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","769","GS25","cafe","fri","12","23","13","43","010-9652-1093","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","770","GS25","cooking","wen","3","12","5","41","010-8810-0823","매탄동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","771","스타벅스","store","tue","17","13","22","37","010-2859-7933","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","772","이디야커피","serving","sun","13","26","21","1","010-3742-8865","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","773","BBQ치킨","cafe","sat","1","3","10","29","010-6514-3319","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","774","새마을식당","serving","wen","12","44","21","57","010-8115-7808","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","775","파리바게트","delivery","fri","6","4","7","35","010-6887-0636","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","776","스타벅스","cooking","tue","9","23","18","37","010-7952-7260","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","777","BBQ치킨","store","fri","10","26","19","43","010-0747-9127","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","778","새마을식당","store","thu","20","2","23","3","010-0080-4296","신동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","779","CU편의점","cooking","sat","19","8","21","23","010-1606-9861","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","780","새마을식당","store","wen","8","27","19","53","010-4721-1871","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","781","CU편의점","cooking","mon","15","34","21","12","010-4248-6383","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","782","BBQ치킨","serving","wen","14","20","17","33","010-8989-2043","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","783","이디야커피","cooking","fri","7","27","19","18","010-9515-2174","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","784","이디야커피","delivery","sat","22","25","23","51","010-2682-9276","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","785","CU편의점","cooking","tue","10","19","12","16","010-2974-2031","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","786","스타벅스","cooking","sat","20","1","21","30","010-6705-3024","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","787","CU편의점","cooking","thu","8","5","11","9","010-8350-0386","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","788","BBQ치킨","serving","tue","2","35","24","24","010-8654-1629","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","789","GS25","cafe","thu","12","8","21","59","010-4408-7831","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","790","파리바게트","cooking","sat","2","48","6","11","010-5251-3390","하동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","791","BBQ치킨","delivery","fri","2","54","12","10","010-6331-5236","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","792","파리바게트","cafe","sun","17","10","21","33","010-1884-9426","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","793","BBQ치킨","delivery","tue","5","49","10","45","010-3216-5712","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","794","GS25","store","sat","6","40","21","32","010-5466-7997","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","795","스타벅스","store","wen","12","12","20","11","010-6766-5708","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","796","새마을식당","cooking","tue","8","6","10","32","010-2113-1078","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","797","새마을식당","store","thu","9","18","17","50","010-2607-8703","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","798","이디야커피","delivery","wen","19","38","20","14","010-2672-3771","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","799","파리바게트","cafe","fri","15","49","18","1","010-8611-8464","영통동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","800","올리브영","cafe","tue","3","41","8","44","010-7346-7145","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","801","올리브영","store","thu","0","22","3","22","010-5380-0491","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","802","파리바게트","cooking","tue","14","5","20","34","010-6267-0783","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","803","새마을식당","cooking","sun","10","22","21","1","010-4675-6386","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","804","GS25","cooking","fri","7","49","14","44","010-2320-2733","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","805","BBQ치킨","cafe","mon","9","23","11","55","010-9570-0205","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","806","BBQ치킨","serving","mon","4","49","8","49","010-0622-3772","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","807","GS25","cafe","fri","3","15","5","26","010-9318-9727","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","808","파리바게트","cafe","sun","1","30","10","40","010-8051-1885","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","809","스타벅스","store","fri","14","21","17","47","010-8112-1111","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","810","스타벅스","cooking","thu","0","49","12","6","010-1949-8218","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","811","올리브영","cooking","wen","16","18","17","3","010-1487-2277","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","812","새마을식당","delivery","fri","0","38","22","58","010-4408-4168","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","813","올리브영","cooking","mon","13","32","23","35","010-3817-2495","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","814","GS25","store","wen","15","21","24","49","010-5171-2967","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","815","새마을식당","cooking","sun","18","48","19","38","010-4543-3162","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","816","CU편의점","cafe","sun","23","56","24","1","010-0939-7432","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","817","파리바게트","delivery","tue","13","15","24","31","010-1549-7449","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","818","BBQ치킨","delivery","fri","18","45","24","21","010-7665-6198","원천동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","819","올리브영","cooking","tue","12","54","13","39","010-3596-7593","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","820","올리브영","cooking","mon","2","29","23","53","010-7740-6452","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","821","이디야커피","cafe","sun","3","12","24","55","010-2092-9467","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","822","GS25","delivery","thu","0","59","10","16","010-5212-6328","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","823","올리브영","store","tue","21","39","24","33","010-4771-8626","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","824","CU편의점","serving","fri","11","27","15","47","010-5700-4284","하동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","825","스타벅스","store","sat","7","59","12","56","010-4767-6803","망포동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","826","새마을식당","delivery","fri","22","58","24","15","010-6806-1392","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","827","BBQ치킨","serving","sun","6","6","22","16","010-3853-8294","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","828","BBQ치킨","cooking","sun","1","51","16","47","010-6964-1741","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","829","이디야커피","cooking","thu","12","25","20","33","010-3028-3000","신동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","830","파리바게트","serving","thu","2","47","10","36","010-7340-6969","태장동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","831","파리바게트","cafe","mon","14","37","17","24","010-3876-4344","태장동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","832","새마을식당","cooking","thu","9","24","14","9","010-3207-7465","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","833","CU편의점","cooking","mon","3","7","14","57","010-1015-9341","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","834","GS25","serving","sun","4","50","8","50","010-0377-5864","망포동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","835","올리브영","cafe","mon","3","34","21","4","010-2750-9602","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","836","CU편의점","serving","thu","13","4","18","36","010-5464-0745","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","837","BBQ치킨","cafe","tue","4","15","16","6","010-5928-7282","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","838","BBQ치킨","delivery","sat","3","51","13","6","010-0829-3727","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","839","새마을식당","delivery","fri","7","13","14","31","010-2000-3843","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","840","이디야커피","store","mon","21","42","24","9","010-8259-3553","망포동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","841","이디야커피","delivery","fri","9","12","24","50","010-2669-8290","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","842","올리브영","cafe","mon","23","13","24","54","010-1846-8871","원천동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","843","파리바게트","cooking","sun","21","56","24","59","010-3869-7361","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","844","이디야커피","delivery","mon","10","50","15","32","010-1135-1578","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","845","파리바게트","cooking","tue","14","0","20","11","010-7226-5881","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","846","GS25","serving","wen","19","15","20","10","010-2918-3515","광교동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","847","BBQ치킨","cafe","wen","17","53","18","59","010-1775-2366","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","848","파리바게트","cafe","sun","3","36","5","55","010-8047-5966","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","849","GS25","cafe","fri","23","30","24","7","010-7951-5081","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","850","새마을식당","serving","tue","21","44","24","5","010-8355-6926","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","851","올리브영","cooking","mon","19","34","24","30","010-8031-1620","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","852","올리브영","cooking","sun","22","24","24","8","010-3208-2548","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","853","BBQ치킨","store","mon","8","36","9","5","010-2663-9513","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","854","스타벅스","cafe","sun","13","3","24","54","010-0569-2680","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","855","새마을식당","cooking","thu","22","37","23","13","010-9173-2591","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","856","스타벅스","serving","tue","1","4","18","14","010-1822-9404","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","857","CU편의점","store","wen","6","39","22","33","010-9195-4555","이의동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","858","GS25","cafe","mon","5","56","22","3","010-1339-4013","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","859","올리브영","cooking","thu","22","33","24","25","010-0003-1765","망포동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","860","새마을식당","serving","tue","21","24","24","50","010-3817-7705","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","861","BBQ치킨","cooking","mon","0","43","10","43","010-7853-5239","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","862","스타벅스","cafe","sun","1","12","11","47","010-1748-9467","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","863","스타벅스","serving","mon","13","33","23","34","010-1252-5149","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","864","GS25","serving","mon","17","41","22","59","010-6990-4691","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","865","파리바게트","serving","fri","10","59","22","40","010-7018-4613","이의동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","866","올리브영","delivery","wen","11","13","15","43","010-2243-5451","신동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","867","BBQ치킨","cafe","tue","15","28","18","41","010-0539-0554","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","868","새마을식당","cafe","fri","7","17","9","11","010-4600-2285","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","869","이디야커피","cafe","wen","4","44","18","1","010-0703-9122","신동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","870","파리바게트","store","mon","12","16","23","31","010-2141-9409","매탄동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","871","CU편의점","serving","sun","3","5","22","39","010-9073-7900","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","872","파리바게트","cooking","sat","9","21","19","10","010-8516-6203","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","873","스타벅스","cooking","wen","8","39","15","28","010-0543-2963","원천동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","874","새마을식당","serving","thu","6","0","22","46","010-0514-7643","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","875","파리바게트","serving","fri","23","21","24","33","010-0886-7035","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","876","올리브영","delivery","wen","1","26","11","56","010-2332-1039","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","877","BBQ치킨","cooking","fri","12","21","16","15","010-1582-8217","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","878","이디야커피","serving","wen","6","35","8","26","010-8751-9626","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","879","새마을식당","store","wen","15","54","21","37","010-1813-8203","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","880","새마을식당","cafe","sun","23","57","24","50","010-5097-8375","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","881","GS25","delivery","thu","8","6","14","39","010-6863-5053","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","882","새마을식당","cooking","sun","22","49","24","25","010-2335-9100","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","883","CU편의점","delivery","fri","3","10","10","25","010-0630-3035","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","884","스타벅스","store","wen","1","34","20","20","010-8037-7917","망포동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","885","스타벅스","delivery","sat","3","52","4","30","010-9909-4980","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","886","파리바게트","serving","mon","13","46","17","36","010-3285-8270","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","887","이디야커피","cooking","mon","11","42","13","7","010-0595-3862","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","888","BBQ치킨","store","tue","2","11","17","0","010-2700-2026","망포동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","889","스타벅스","cooking","wen","16","25","22","8","010-7719-3230","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","890","이디야커피","delivery","sat","5","35","14","52","010-1742-9228","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","891","새마을식당","delivery","tue","19","37","21","41","010-6878-6734","영통동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","892","이디야커피","store","mon","1","22","11","11","010-6259-5062","원천동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","893","스타벅스","serving","sat","19","13","22","18","010-5145-5626","광교동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","894","파리바게트","delivery","fri","12","7","20","20","010-7580-2009","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","895","GS25","delivery","tue","15","41","21","39","010-3704-2163","망포동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","896","BBQ치킨","serving","thu","10","6","24","29","010-7395-6509","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","897","이디야커피","serving","mon","22","13","24","40","010-6548-3022","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","898","CU편의점","cafe","fri","17","55","18","46","010-7147-6701","이의동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","899","CU편의점","delivery","fri","10","28","13","52","010-3505-0497","원천동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","900","파리바게트","cooking","sat","1","21","5","7","010-1840-1178","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","901","스타벅스","cooking","wen","19","1","22","4","010-6488-4653","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","902","GS25","store","thu","13","8","23","15","010-2356-8673","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","903","스타벅스","cafe","wen","10","36","20","52","010-6866-4772","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","904","CU편의점","store","thu","4","57","11","6","010-2691-2385","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","905","이디야커피","store","mon","3","52","10","59","010-2174-7291","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","906","새마을식당","cooking","wen","1","48","14","52","010-1535-2288","하동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","907","CU편의점","cafe","sun","10","42","12","41","010-3736-3552","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","908","파리바게트","cooking","sat","6","34","16","8","010-8504-6094","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","909","파리바게트","serving","wen","3","57","18","5","010-4719-3526","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","910","BBQ치킨","serving","sun","0","52","23","6","010-5745-3668","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","911","BBQ치킨","cooking","sat","8","27","20","30","010-3355-5148","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","912","새마을식당","cafe","tue","5","33","19","6","010-4922-0881","신동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","913","BBQ치킨","serving","wen","5","22","21","11","010-0244-8196","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","914","이디야커피","cafe","fri","17","59","21","22","010-0847-5957","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","915","스타벅스","cooking","fri","2","52","12","15","010-3288-9002","이의동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","916","새마을식당","serving","tue","7","38","16","53","010-2407-9964","광교동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","917","GS25","store","sat","10","7","21","50","010-8980-7900","하동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","918","올리브영","cafe","mon","8","21","17","49","010-9714-2897","이의동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","919","이디야커피","cafe","thu","6","30","9","36","010-7066-1748","하동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","920","GS25","cafe","tue","14","57","17","23","010-9534-1487","이의동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","921","스타벅스","cooking","thu","21","54","23","13","010-7679-3051","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","922","GS25","cooking","fri","0","54","12","36","010-7621-6584","태장동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","923","올리브영","delivery","thu","23","29","24","7","010-9585-1233","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","924","이디야커피","store","sat","9","54","22","2","010-2194-0841","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","925","파리바게트","store","sat","19","21","21","43","010-6281-5502","신동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","926","스타벅스","serving","mon","10","9","21","23","010-9306-3486","이의동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","927","새마을식당","store","sun","2","31","3","39","010-1565-1443","영통동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","928","새마을식당","cooking","thu","10","56","11","34","010-6519-8011","영통동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","929","GS25","cafe","thu","14","27","21","57","010-6894-3099","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","930","CU편의점","delivery","thu","5","13","14","31","010-6331-3643","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","931","GS25","store","wen","7","30","15","42","010-6433-1761","망포동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","932","파리바게트","cafe","sun","8","47","11","12","010-5489-0353","광교동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","933","GS25","serving","tue","11","56","23","44","010-9346-2083","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","934","BBQ치킨","delivery","thu","14","54","23","49","010-3441-7576","매탄동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","935","스타벅스","store","tue","23","28","24","4","010-6346-8652","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","936","파리바게트","store","mon","2","26","7","31","010-0741-0317","원천동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","937","이디야커피","store","tue","19","33","23","45","010-0429-6864","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","938","파리바게트","cooking","wen","0","25","10","36","010-3257-1273","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","939","BBQ치킨","cooking","thu","0","58","5","33","010-6384-7039","매탄동","10000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","940","새마을식당","cooking","sun","11","56","18","13","010-7295-8930","매탄동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","941","스타벅스","cafe","fri","23","14","24","48","010-1249-9254","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","942","BBQ치킨","cooking","tue","5","20","9","26","010-3425-0312","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","943","GS25","serving","wen","12","11","21","45","010-6539-3599","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","944","이디야커피","cafe","tue","12","10","17","49","010-2305-9660","신동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","945","CU편의점","cafe","thu","2","55","14","29","010-8650-7404","태장동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","946","이디야커피","delivery","tue","12","22","13","48","010-2677-7651","태장동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","947","GS25","serving","fri","22","28","23","31","010-9783-3643","영통동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","948","새마을식당","delivery","tue","11","19","15","2","010-6076-0180","이의동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","949","올리브영","serving","thu","18","55","19","11","010-5393-1451","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","950","파리바게트","delivery","fri","2","11","15","24","010-8812-5055","매탄동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","951","올리브영","cooking","mon","16","46","18","6","010-7821-2972","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","952","올리브영","cafe","wen","5","30","24","58","010-8584-8812","영통동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","953","이디야커피","delivery","sun","14","10","24","54","010-5974-0907","광교동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","954","새마을식당","cafe","mon","13","15","24","16","010-5725-8347","광교동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","955","GS25","cafe","mon","19","15","21","56","010-1645-4684","영통동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","956","파리바게트","serving","thu","12","45","14","2","010-9821-9171","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","957","새마을식당","store","mon","4","46","24","3","010-6941-3267","망포동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","958","CU편의점","cooking","tue","22","36","24","14","010-0107-9997","태장동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","959","GS25","cafe","tue","12","50","22","43","010-0379-3045","하동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","960","새마을식당","serving","fri","18","24","24","10","010-5685-5251","이의동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","961","CU편의점","cooking","tue","19","25","20","16","010-9295-2571","원천동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","962","BBQ치킨","cooking","sun","18","4","22","11","010-1751-0467","원천동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","963","CU편의점","store","sun","3","0","6","29","010-5924-4184","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","964","BBQ치킨","store","fri","12","52","22","22","010-4221-2575","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","965","CU편의점","delivery","thu","20","26","23","20","010-8640-9929","매탄동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","966","새마을식당","serving","thu","6","41","20","28","010-5272-9630","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","967","이디야커피","cooking","tue","0","40","24","55","010-1963-3431","망포동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","968","BBQ치킨","serving","thu","4","26","6","45","010-3259-6178","광교동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","969","GS25","store","fri","15","17","16","15","010-0967-3403","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","970","스타벅스","store","wen","20","31","24","58","010-6393-8226","영통동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","971","BBQ치킨","serving","mon","16","8","23","40","010-6896-0493","원천동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","972","올리브영","cafe","sun","1","12","18","53","010-4683-0138","신동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","973","스타벅스","delivery","sat","12","7","21","25","010-0134-3828","매탄동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","974","새마을식당","delivery","fri","5","5","21","28","010-4076-5653","태장동","20000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","975","올리브영","cooking","sat","1","47","4","10","010-8472-4926","태장동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","976","새마을식당","cooking","sat","5","28","18","50","010-8231-7151","광교동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","977","올리브영","delivery","tue","13","11","19","44","010-6681-2009","하동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","978","스타벅스","serving","sat","18","33","19","54","010-1666-0989","매탄동","12000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","979","BBQ치킨","cooking","mon","16","34","21","14","010-1016-0138","망포동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","980","BBQ치킨","serving","sat","23","12","24","27","010-4378-6578","신동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","981","올리브영","store","wen","14","17","21","14","010-5886-6152","영통동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","982","이디야커피","delivery","wen","22","34","24","32","010-5002-9038","신동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","983","BBQ치킨","delivery","sat","15","8","18","59","010-7683-7247","매탄동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","984","BBQ치킨","cafe","fri","0","4","13","21","010-5707-0838","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","985","이디야커피","store","fri","21","48","23","46","010-0081-2189","광교동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","986","스타벅스","delivery","sat","8","42","10","40","010-5665-4929","이의동","9000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","987","GS25","store","wen","4","24","14","30","010-7801-1081","하동","16000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","988","CU편의점","store","fri","6","48","13","9","010-1555-4811","태장동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","989","새마을식당","store","sat","12","25","23","18","010-4417-9006","광교동","15000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","990","BBQ치킨","cooking","mon","22","14","24","7","010-5402-1084","원천동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","991","GS25","cafe","tue","9","25","18","23","010-8343-5194","태장동","11000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","992","올리브영","cafe","sun","17","58","24","54","010-5186-3977","이의동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","993","BBQ치킨","delivery","fri","18","3","22","0","010-0705-9684","광교동","19000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","994","올리브영","cafe","thu","15","4","20","3","010-7849-7260","망포동","13000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","995","BBQ치킨","serving","thu","11","38","17","19","010-8222-9466","태장동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","996","올리브영","serving","sat","4","49","15","47","010-9495-5051","신동","14000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","997","이디야커피","serving","thu","2","53","20","3","010-5876-6937","영통동","18000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","998","새마을식당","delivery","sat","18","31","23","34","010-7172-7128","하동","17000");
        writeDB("ZYhezTgDCCagmvH2BUqVjHALZUk1","999","새마을식당","delivery","wen","1","15","18","23","010-7942-8761","광교동","13000");


    }

    void writeDB(String employerIdToken, String id, String name, String job, String day, String startHour, String startMinute, String endHour, String endMinute, String phoneNumber, String region, String wage) {

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
        mDatabase.child("ID").child(id).child("wage").setValue(wage);


        //Job
        mDatabase.child("Job").child(job).push().setValue(id);

        //Day
        mDatabase.child("Day").child(day).push().setValue(id);

        //Region
        mDatabase.child("Region").child(region).push().setValue(id);

    }
}