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
//        initDB();
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


//    void initDB() {
//        //모두삭제
//        mDatabase.removeValue();
//        //쓰기
//        writeDB("001", "스타벅스", "delivery", "sun", "7", "27", "8", "4", "010-9488-5150", "망포동", "20000");
//        writeDB("002", "새마을식당", "cafe", "sat", "1", "51", "13", "34", "010-8314-5313", "광교동", "20000");
//        writeDB("003", "이디야커피", "cooking", "fri", "15", "15", "24", "5", "010-5981-0709", "망포동", "11000");
//        writeDB("004", "새마을식당", "serving", "sun", "15", "12", "17", "33", "010-2380-4759", "광교동", "20000");
//        writeDB("005", "GS25", "delivery", "wen", "13", "5", "17", "54", "010-2518-7388", "매탄동", "20000");
//        writeDB("006", "CU편의점", "store", "sun", "22", "52", "23", "20", "010-1891-7228", "이의동", "18000");
//        writeDB("007", "파리바게트", "delivery", "thu", "12", "14", "13", "33", "010-1975-6077", "원천동", "12000");
//        writeDB("008", "이디야커피", "store", "thu", "22", "29", "24", "17", "010-2525-5741", "원천동", "9000");
//        writeDB("009", "새마을식당", "cooking", "sat", "5", "58", "20", "16", "010-5518-7278", "신동", "17000");
//        writeDB("010", "스타벅스", "cafe", "fri", "6", "54", "19", "6", "010-8778-9870", "신동", "15000");
//        writeDB("011", "이디야커피", "delivery", "fri", "15", "40", "16", "38", "010-6005-4617", "매탄동", "16000");
//        writeDB("012", "올리브영", "cafe", "tue", "5", "37", "24", "2", "010-4411-3760", "이의동", "15000");
//        writeDB("013", "GS25", "serving", "wen", "6", "46", "24", "42", "010-3789-3392", "영통동", "13000");
//        writeDB("014", "스타벅스", "cafe", "tue", "0", "1", "16", "1", "010-5163-3840", "광교동", "10000");
//        writeDB("015", "새마을식당", "cooking", "tue", "21", "26", "23", "4", "010-7583-2571", "태장동", "13000");
//        writeDB("016", "CU편의점", "cooking", "tue", "7", "30", "15", "26", "010-4136-9132", "망포동", "14000");
//        writeDB("017", "CU편의점", "cooking", "sun", "10", "30", "23", "30", "010-7792-2623", "태장동", "14000");
//        writeDB("018", "이디야커피", "cooking", "wen", "6", "33", "15", "31", "010-5225-9185", "원천동", "10000");
//        writeDB("019", "이디야커피", "cafe", "sat", "2", "6", "21", "38", "010-9933-5378", "신동", "16000");
//        writeDB("020", "BBQ치킨", "cooking", "wen", "0", "21", "4", "10", "010-7918-1823", "원천동", "13000");
//        writeDB("021", "이디야커피", "cafe", "mon", "4", "27", "6", "45", "010-4340-1916", "하동", "14000");
//        writeDB("022", "새마을식당", "serving", "fri", "23", "53", "24", "47", "010-5138-8007", "영통동", "13000");
//        writeDB("023", "BBQ치킨", "serving", "wen", "6", "18", "14", "0", "010-9246-6059", "이의동", "11000");
//        writeDB("024", "BBQ치킨", "cafe", "thu", "4", "9", "6", "57", "010-2575-7431", "원천동", "10000");
//        writeDB("025", "GS25", "store", "thu", "12", "52", "13", "43", "010-7785-3676", "원천동", "15000");
//        writeDB("026", "CU편의점", "cafe", "wen", "8", "53", "20", "52", "010-5013-7151", "신동", "10000");
//        writeDB("027", "BBQ치킨", "delivery", "thu", "8", "29", "17", "52", "010-0493-0637", "원천동", "10000");
//        writeDB("028", "올리브영", "delivery", "wen", "4", "7", "24", "7", "010-1136-8326", "원천동", "13000");
//        writeDB("029", "이디야커피", "delivery", "sat", "15", "58", "20", "29", "010-8708-2673", "광교동", "12000");
//        writeDB("030", "올리브영", "serving", "sat", "0", "30", "19", "26", "010-5648-0597", "매탄동", "19000");
//        writeDB("031", "새마을식당", "serving", "sat", "10", "22", "22", "43", "010-9136-1906", "광교동", "14000");
//        writeDB("032", "새마을식당", "cooking", "fri", "5", "11", "15", "9", "010-8775-7621", "광교동", "14000");
//        writeDB("033", "CU편의점", "serving", "fri", "16", "48", "22", "28", "010-3102-6388", "영통동", "20000");
//        writeDB("034", "올리브영", "serving", "tue", "19", "5", "24", "35", "010-0058-9413", "매탄동", "18000");
//        writeDB("035", "이디야커피", "serving", "tue", "13", "0", "16", "27", "010-4724-1281", "망포동", "9000");
//        writeDB("036", "GS25", "serving", "sat", "6", "35", "20", "50", "010-1004-1225", "매탄동", "20000");
//        writeDB("037", "올리브영", "cooking", "fri", "7", "35", "17", "40", "010-7601-5223", "원천동", "17000");
//        writeDB("038", "BBQ치킨", "serving", "fri", "12", "32", "18", "24", "010-8341-5815", "이의동", "14000");
//        writeDB("039", "이디야커피", "serving", "wen", "5", "4", "23", "15", "010-8878-6365", "원천동", "15000");
//        writeDB("040", "BBQ치킨", "store", "fri", "9", "5", "21", "13", "010-7186-4650", "이의동", "18000");
//        writeDB("041", "파리바게트", "cooking", "mon", "23", "12", "24", "58", "010-2240-0499", "매탄동", "13000");
//        writeDB("042", "스타벅스", "delivery", "fri", "11", "44", "21", "49", "010-6371-8461", "매탄동", "16000");
//        writeDB("043", "GS25", "serving", "wen", "12", "0", "21", "42", "010-5396-9822", "원천동", "19000");
//        writeDB("044", "새마을식당", "cafe", "mon", "13", "14", "16", "56", "010-9667-5015", "원천동", "16000");
//        writeDB("045", "BBQ치킨", "store", "wen", "8", "49", "14", "31", "010-4660-4890", "원천동", "17000");
//        writeDB("046", "CU편의점", "store", "sun", "7", "4", "22", "6", "010-0818-4979", "영통동", "15000");
//        writeDB("047", "스타벅스", "delivery", "fri", "23", "16", "24", "33", "010-5755-6740", "하동", "18000");
//        writeDB("048", "BBQ치킨", "cooking", "mon", "8", "30", "11", "56", "010-2032-6792", "신동", "13000");
//        writeDB("049", "파리바게트", "cafe", "thu", "3", "50", "20", "38", "010-4858-5015", "원천동", "16000");
//        writeDB("050", "BBQ치킨", "cooking", "fri", "3", "50", "4", "47", "010-8565-3995", "하동", "10000");
//        writeDB("051", "CU편의점", "cooking", "fri", "10", "51", "11", "33", "010-4941-6068", "원천동", "14000");
//        writeDB("052", "이디야커피", "cooking", "fri", "10", "9", "17", "38", "010-2578-0341", "영통동", "17000");
//        writeDB("053", "파리바게트", "serving", "mon", "16", "47", "19", "8", "010-9003-9781", "태장동", "15000");
//        writeDB("054", "CU편의점", "cafe", "fri", "23", "53", "24", "48", "010-7245-0379", "신동", "9000");
//        writeDB("055", "BBQ치킨", "store", "wen", "17", "17", "18", "11", "010-5453-0252", "원천동", "20000");
//        writeDB("056", "BBQ치킨", "store", "tue", "1", "40", "6", "29", "010-6894-2454", "원천동", "18000");
//        writeDB("057", "파리바게트", "cooking", "fri", "22", "11", "23", "0", "010-9923-6731", "원천동", "13000");
//        writeDB("058", "BBQ치킨", "store", "tue", "8", "30", "17", "52", "010-6919-5197", "망포동", "10000");
//        writeDB("059", "파리바게트", "serving", "tue", "6", "33", "11", "35", "010-4667-4730", "하동", "10000");
//        writeDB("060", "CU편의점", "cooking", "mon", "23", "32", "24", "44", "010-5198-4013", "영통동", "11000");
//        writeDB("061", "스타벅스", "cafe", "tue", "3", "5", "4", "40", "010-1522-8980", "신동", "19000");
//        writeDB("062", "올리브영", "cooking", "tue", "10", "18", "24", "28", "010-3003-1227", "매탄동", "14000");
//        writeDB("063", "새마을식당", "serving", "sun", "8", "58", "13", "16", "010-5081-8460", "하동", "9000");
//        writeDB("064", "CU편의점", "cafe", "fri", "23", "35", "24", "36", "010-5709-2320", "매탄동", "17000");
//        writeDB("065", "새마을식당", "cooking", "wen", "7", "36", "14", "33", "010-9715-6313", "이의동", "12000");
//        writeDB("066", "올리브영", "store", "mon", "16", "5", "21", "44", "010-3682-4990", "영통동", "20000");
//        writeDB("067", "BBQ치킨", "store", "wen", "21", "29", "24", "11", "010-1930-2910", "광교동", "20000");
//        writeDB("068", "CU편의점", "cooking", "fri", "22", "52", "23", "40", "010-6861-4566", "태장동", "20000");
//        writeDB("069", "GS25", "cafe", "wen", "0", "39", "13", "11", "010-9805-4574", "이의동", "16000");
//        writeDB("070", "BBQ치킨", "store", "tue", "19", "43", "23", "47", "010-1212-2823", "매탄동", "18000");
//        writeDB("071", "CU편의점", "delivery", "sat", "4", "21", "20", "53", "010-3355-5170", "영통동", "12000");
//        writeDB("072", "올리브영", "cooking", "mon", "22", "51", "23", "58", "010-1519-6788", "영통동", "11000");
//        writeDB("073", "GS25", "store", "thu", "23", "34", "24", "2", "010-1396-8622", "태장동", "9000");
//        writeDB("074", "스타벅스", "cafe", "sun", "19", "47", "23", "57", "010-5718-6316", "원천동", "19000");
//        writeDB("075", "CU편의점", "store", "mon", "5", "33", "6", "59", "010-9506-2546", "매탄동", "11000");
//        writeDB("076", "CU편의점", "cafe", "tue", "7", "56", "20", "42", "010-4739-7197", "하동", "16000");
//        writeDB("077", "GS25", "cafe", "thu", "3", "51", "17", "17", "010-1546-8045", "태장동", "10000");
//        writeDB("078", "GS25", "delivery", "wen", "20", "11", "21", "19", "010-1135-7510", "원천동", "11000");
//        writeDB("079", "CU편의점", "cafe", "fri", "20", "29", "24", "13", "010-2847-7253", "신동", "15000");
//        writeDB("080", "GS25", "store", "mon", "11", "54", "24", "14", "010-7049-7175", "광교동", "19000");
//        writeDB("081", "새마을식당", "cooking", "fri", "1", "59", "14", "12", "010-1043-2827", "이의동", "15000");
//        writeDB("082", "GS25", "delivery", "sun", "21", "2", "23", "8", "010-0312-9040", "광교동", "20000");
//        writeDB("083", "새마을식당", "serving", "thu", "16", "26", "19", "8", "010-8075-1085", "태장동", "19000");
//        writeDB("084", "새마을식당", "cafe", "thu", "19", "56", "20", "1", "010-0646-8012", "하동", "19000");
//        writeDB("085", "BBQ치킨", "delivery", "sun", "0", "6", "3", "6", "010-5754-3289", "망포동", "16000");
//        writeDB("086", "새마을식당", "delivery", "sun", "19", "20", "24", "59", "010-3463-4686", "하동", "13000");
//        writeDB("087", "올리브영", "delivery", "sat", "22", "29", "23", "16", "010-7346-6633", "하동", "11000");
//        writeDB("088", "BBQ치킨", "cooking", "mon", "4", "58", "23", "35", "010-7551-1772", "원천동", "10000");
//        writeDB("089", "GS25", "delivery", "mon", "13", "24", "17", "4", "010-4589-5912", "매탄동", "17000");
//        writeDB("090", "파리바게트", "delivery", "mon", "7", "40", "12", "5", "010-7172-7668", "광교동", "11000");
//        writeDB("091", "CU편의점", "cooking", "sun", "13", "57", "22", "52", "010-5059-8144", "광교동", "10000");
//        writeDB("092", "파리바게트", "delivery", "thu", "5", "57", "24", "47", "010-9356-7155", "원천동", "18000");
//        writeDB("093", "BBQ치킨", "delivery", "mon", "18", "33", "22", "25", "010-7449-2273", "영통동", "20000");
//        writeDB("094", "CU편의점", "serving", "thu", "11", "6", "18", "10", "010-0201-7967", "광교동", "14000");
//        writeDB("095", "스타벅스", "store", "wen", "20", "4", "21", "3", "010-5729-6191", "망포동", "13000");
//        writeDB("096", "이디야커피", "cooking", "fri", "19", "7", "20", "7", "010-9151-1733", "망포동", "15000");
//        writeDB("097", "스타벅스", "delivery", "sun", "0", "10", "6", "28", "010-3496-5969", "영통동", "17000");
//        writeDB("098", "GS25", "cafe", "fri", "0", "53", "19", "15", "010-6199-6390", "신동", "10000");
//        writeDB("099", "BBQ치킨", "store", "wen", "21", "9", "24", "3", "010-2267-6219", "광교동", "18000");
//        writeDB("100", "올리브영", "cooking", "tue", "14", "25", "22", "26", "010-1241-7102", "영통동", "14000");
//        writeDB("101", "이디야커피", "cooking", "fri", "10", "26", "22", "46", "010-6530-8554", "망포동", "14000");
//        writeDB("102", "BBQ치킨", "cooking", "wen", "10", "38", "17", "36", "010-9549-0642", "하동", "11000");
//        writeDB("103", "새마을식당", "delivery", "wen", "8", "34", "22", "33", "010-0756-0250", "망포동", "14000");
//        writeDB("104", "새마을식당", "store", "sat", "7", "44", "24", "47", "010-4114-8574", "광교동", "11000");
//        writeDB("105", "BBQ치킨", "delivery", "mon", "8", "9", "20", "36", "010-3663-1516", "태장동", "14000");
//        writeDB("106", "올리브영", "store", "sat", "7", "20", "15", "54", "010-2330-0894", "태장동", "11000");
//        writeDB("107", "이디야커피", "store", "wen", "0", "8", "11", "55", "010-1086-0074", "하동", "12000");
//        writeDB("108", "GS25", "delivery", "thu", "9", "44", "15", "45", "010-8703-7837", "망포동", "10000");
//        writeDB("109", "이디야커피", "cooking", "sun", "20", "23", "21", "13", "010-4794-5357", "원천동", "12000");
//        writeDB("110", "GS25", "delivery", "sat", "20", "10", "23", "40", "010-9840-6227", "이의동", "16000");
//        writeDB("111", "파리바게트", "store", "tue", "12", "25", "17", "4", "010-9196-0794", "이의동", "13000");
//        writeDB("112", "스타벅스", "serving", "mon", "8", "44", "22", "27", "010-9212-1844", "망포동", "13000");
//        writeDB("113", "BBQ치킨", "delivery", "mon", "23", "18", "24", "3", "010-7388-6858", "이의동", "11000");
//        writeDB("114", "BBQ치킨", "serving", "thu", "4", "48", "16", "39", "010-9367-5014", "신동", "19000");
//        writeDB("115", "파리바게트", "delivery", "sun", "22", "56", "23", "19", "010-7653-1405", "원천동", "18000");
//        writeDB("116", "스타벅스", "cafe", "mon", "18", "36", "21", "58", "010-4400-0274", "망포동", "13000");
//        writeDB("117", "이디야커피", "serving", "wen", "5", "46", "24", "44", "010-4346-1362", "광교동", "10000");
//        writeDB("118", "올리브영", "delivery", "thu", "6", "40", "15", "56", "010-5365-3749", "망포동", "17000");
//        writeDB("119", "스타벅스", "delivery", "sat", "1", "36", "20", "7", "010-4648-1753", "망포동", "20000");
//        writeDB("120", "CU편의점", "store", "tue", "16", "8", "19", "3", "010-3907-3411", "망포동", "13000");
//        writeDB("121", "CU편의점", "delivery", "wen", "0", "38", "20", "43", "010-6784-1834", "신동", "12000");
//        writeDB("122", "BBQ치킨", "cafe", "sun", "17", "49", "23", "58", "010-7043-6598", "매탄동", "11000");
//        writeDB("123", "파리바게트", "cooking", "wen", "0", "29", "2", "24", "010-4715-8234", "광교동", "11000");
//        writeDB("124", "올리브영", "serving", "sun", "9", "19", "20", "49", "010-4532-8103", "신동", "13000");
//        writeDB("125", "스타벅스", "cooking", "wen", "11", "58", "17", "29", "010-6124-2532", "광교동", "9000");
//        writeDB("126", "새마을식당", "cooking", "mon", "4", "56", "5", "58", "010-6070-3791", "태장동", "19000");
//        writeDB("127", "파리바게트", "cafe", "fri", "16", "13", "18", "46", "010-3389-6916", "태장동", "13000");
//        writeDB("128", "새마을식당", "store", "fri", "18", "49", "20", "17", "010-7011-7809", "하동", "15000");
//        writeDB("129", "올리브영", "delivery", "tue", "12", "47", "24", "37", "010-1140-5695", "원천동", "17000");
//        writeDB("130", "스타벅스", "delivery", "mon", "12", "41", "16", "54", "010-9844-2931", "매탄동", "15000");
//        writeDB("131", "GS25", "cooking", "sun", "11", "6", "14", "41", "010-3444-0789", "태장동", "14000");
//        writeDB("132", "스타벅스", "serving", "wen", "0", "2", "6", "59", "010-2474-8270", "영통동", "12000");
//        writeDB("133", "새마을식당", "store", "thu", "5", "57", "23", "4", "010-8359-7880", "신동", "13000");
//        writeDB("134", "GS25", "store", "tue", "12", "47", "19", "52", "010-7849-5827", "영통동", "14000");
//        writeDB("135", "이디야커피", "store", "fri", "5", "43", "18", "53", "010-4883-2979", "원천동", "12000");
//        writeDB("136", "스타벅스", "store", "wen", "18", "38", "19", "3", "010-2902-7635", "영통동", "9000");
//        writeDB("137", "올리브영", "cafe", "fri", "5", "15", "16", "28", "010-7851-2204", "태장동", "16000");
//        writeDB("138", "새마을식당", "cafe", "wen", "4", "3", "20", "39", "010-3617-9959", "하동", "10000");
//        writeDB("139", "올리브영", "cooking", "sat", "14", "43", "16", "31", "010-0676-2802", "태장동", "11000");
//        writeDB("140", "CU편의점", "store", "tue", "10", "18", "15", "52", "010-7994-9578", "이의동", "18000");
//        writeDB("141", "CU편의점", "delivery", "sun", "21", "37", "22", "20", "010-4308-6286", "하동", "14000");
//        writeDB("142", "올리브영", "delivery", "tue", "1", "43", "15", "24", "010-5896-5741", "매탄동", "9000");
//        writeDB("143", "CU편의점", "serving", "sun", "15", "5", "16", "15", "010-3173-6902", "하동", "13000");
//        writeDB("144", "파리바게트", "serving", "fri", "16", "17", "20", "16", "010-1600-5686", "매탄동", "20000");
//        writeDB("145", "새마을식당", "serving", "wen", "20", "23", "24", "39", "010-6028-9246", "영통동", "11000");
//        writeDB("146", "GS25", "cooking", "wen", "16", "21", "17", "16", "010-1129-1563", "망포동", "11000");
//        writeDB("147", "GS25", "cafe", "thu", "15", "15", "16", "34", "010-0734-0438", "하동", "16000");
//        writeDB("148", "GS25", "store", "wen", "11", "58", "14", "41", "010-9006-3881", "태장동", "10000");
//        writeDB("149", "GS25", "serving", "sat", "22", "39", "24", "21", "010-8536-1287", "원천동", "10000");
//        writeDB("150", "CU편의점", "delivery", "tue", "12", "28", "19", "21", "010-0997-5892", "신동", "14000");
//        writeDB("151", "파리바게트", "serving", "fri", "15", "49", "16", "29", "010-3505-3503", "망포동", "10000");
//        writeDB("152", "GS25", "cafe", "mon", "8", "11", "13", "37", "010-0827-1948", "태장동", "17000");
//        writeDB("153", "BBQ치킨", "serving", "fri", "20", "6", "23", "5", "010-1219-2279", "망포동", "16000");
//        writeDB("154", "파리바게트", "store", "thu", "10", "53", "17", "41", "010-5228-5554", "매탄동", "13000");
//        writeDB("155", "BBQ치킨", "cooking", "fri", "9", "11", "11", "22", "010-1402-8314", "매탄동", "11000");
//        writeDB("156", "CU편의점", "delivery", "thu", "12", "58", "14", "33", "010-4365-1506", "망포동", "15000");
//        writeDB("157", "올리브영", "delivery", "sun", "12", "0", "18", "44", "010-6488-4129", "광교동", "16000");
//        writeDB("158", "GS25", "serving", "sun", "13", "58", "22", "14", "010-3076-0792", "망포동", "12000");
//        writeDB("159", "올리브영", "delivery", "thu", "21", "25", "23", "26", "010-3839-1043", "하동", "18000");
//        writeDB("160", "파리바게트", "cooking", "wen", "21", "49", "24", "45", "010-4187-1514", "영통동", "13000");
//        writeDB("161", "파리바게트", "serving", "fri", "18", "59", "22", "33", "010-2863-4455", "태장동", "10000");
//        writeDB("162", "CU편의점", "cooking", "fri", "23", "40", "24", "29", "010-0422-5984", "광교동", "16000");
//        writeDB("163", "올리브영", "cafe", "wen", "13", "47", "15", "16", "010-3279-4669", "이의동", "13000");
//        writeDB("164", "파리바게트", "delivery", "sat", "6", "5", "17", "34", "010-0370-6217", "신동", "20000");
//        writeDB("165", "새마을식당", "serving", "tue", "11", "16", "15", "35", "010-7394-3562", "하동", "15000");
//        writeDB("166", "올리브영", "serving", "sat", "21", "10", "23", "4", "010-5834-0716", "원천동", "14000");
//        writeDB("167", "올리브영", "serving", "thu", "14", "23", "16", "33", "010-5085-6596", "매탄동", "11000");
//        writeDB("168", "스타벅스", "cooking", "mon", "8", "36", "14", "41", "010-2650-7236", "광교동", "12000");
//        writeDB("169", "파리바게트", "cafe", "fri", "14", "11", "18", "32", "010-4341-2511", "신동", "19000");
//        writeDB("170", "스타벅스", "cafe", "sun", "17", "39", "22", "34", "010-9110-5322", "하동", "19000");
//        writeDB("171", "BBQ치킨", "delivery", "tue", "1", "40", "14", "52", "010-1986-3366", "원천동", "20000");
//        writeDB("172", "CU편의점", "cafe", "sun", "12", "37", "23", "12", "010-8788-2997", "태장동", "15000");
//        writeDB("173", "스타벅스", "cooking", "wen", "13", "40", "19", "22", "010-8379-9521", "이의동", "14000");
//        writeDB("174", "GS25", "cooking", "thu", "23", "52", "24", "57", "010-9849-9893", "망포동", "18000");
//        writeDB("175", "GS25", "store", "sat", "22", "49", "23", "47", "010-4544-9707", "매탄동", "15000");
//        writeDB("176", "BBQ치킨", "store", "fri", "11", "6", "18", "16", "010-4138-2878", "이의동", "10000");
//        writeDB("177", "BBQ치킨", "delivery", "wen", "10", "37", "24", "11", "010-4542-5375", "영통동", "9000");
//        writeDB("178", "CU편의점", "delivery", "sat", "14", "16", "20", "41", "010-5312-9001", "영통동", "16000");
//        writeDB("179", "CU편의점", "cooking", "wen", "20", "33", "22", "5", "010-4186-3294", "원천동", "9000");
//        writeDB("180", "CU편의점", "store", "thu", "18", "44", "24", "52", "010-6625-4084", "영통동", "17000");
//        writeDB("181", "이디야커피", "delivery", "wen", "11", "32", "18", "13", "010-1528-8869", "하동", "17000");
//        writeDB("182", "BBQ치킨", "cooking", "sat", "18", "10", "20", "5", "010-1226-6827", "영통동", "14000");
//        writeDB("183", "새마을식당", "delivery", "sat", "17", "59", "23", "37", "010-1390-3409", "매탄동", "19000");
//        writeDB("184", "파리바게트", "serving", "tue", "10", "31", "14", "49", "010-5172-5232", "이의동", "11000");
//        writeDB("185", "스타벅스", "delivery", "sat", "10", "41", "16", "53", "010-4796-0276", "이의동", "10000");
//        writeDB("186", "올리브영", "serving", "tue", "3", "43", "24", "35", "010-2125-6800", "망포동", "9000");
//        writeDB("187", "BBQ치킨", "cooking", "tue", "4", "23", "19", "15", "010-7222-6409", "태장동", "18000");
//        writeDB("188", "GS25", "cooking", "fri", "3", "42", "10", "39", "010-8593-3315", "이의동", "18000");
//        writeDB("189", "새마을식당", "delivery", "sat", "16", "16", "24", "10", "010-8709-1513", "하동", "9000");
//        writeDB("190", "CU편의점", "delivery", "sat", "8", "57", "15", "42", "010-7273-7565", "신동", "11000");
//        writeDB("191", "GS25", "store", "sat", "0", "41", "12", "22", "010-7409-4784", "망포동", "11000");
//        writeDB("192", "올리브영", "store", "tue", "8", "55", "14", "38", "010-7562-8896", "영통동", "14000");
//        writeDB("193", "파리바게트", "cafe", "wen", "8", "3", "12", "16", "010-4272-8928", "이의동", "9000");
//        writeDB("194", "GS25", "cooking", "sat", "5", "26", "24", "8", "010-3021-2030", "신동", "15000");
//        writeDB("195", "GS25", "delivery", "sun", "11", "25", "19", "13", "010-9812-0310", "원천동", "14000");
//        writeDB("196", "올리브영", "store", "wen", "16", "42", "21", "53", "010-3100-1368", "신동", "17000");
//        writeDB("197", "올리브영", "cafe", "wen", "1", "10", "11", "12", "010-7883-7680", "광교동", "10000");
//        writeDB("198", "새마을식당", "serving", "mon", "8", "48", "9", "54", "010-7209-8413", "망포동", "17000");
//        writeDB("199", "이디야커피", "serving", "sun", "3", "51", "16", "32", "010-1573-1168", "광교동", "15000");
//        writeDB("200", "새마을식당", "serving", "wen", "21", "50", "24", "27", "010-8543-6423", "광교동", "20000");
//        writeDB("201", "BBQ치킨", "serving", "fri", "12", "46", "16", "46", "010-6282-7694", "매탄동", "12000");
//        writeDB("202", "이디야커피", "delivery", "tue", "17", "9", "24", "52", "010-8221-6606", "광교동", "14000");
//        writeDB("203", "CU편의점", "cafe", "mon", "23", "54", "24", "49", "010-3611-3940", "하동", "15000");
//        writeDB("204", "스타벅스", "cooking", "sat", "18", "35", "20", "55", "010-0119-0570", "매탄동", "15000");
//        writeDB("205", "올리브영", "serving", "mon", "16", "2", "21", "48", "010-6095-3673", "망포동", "20000");
//        writeDB("206", "이디야커피", "cafe", "mon", "5", "20", "24", "54", "010-5953-6072", "태장동", "19000");
//        writeDB("207", "파리바게트", "serving", "tue", "13", "22", "17", "42", "010-0831-6733", "원천동", "16000");
//        writeDB("208", "이디야커피", "cooking", "tue", "23", "11", "24", "48", "010-7346-6604", "영통동", "19000");
//        writeDB("209", "BBQ치킨", "store", "wen", "21", "56", "23", "54", "010-0296-3556", "원천동", "16000");
//        writeDB("210", "BBQ치킨", "cafe", "fri", "13", "1", "21", "16", "010-0572-2598", "매탄동", "19000");
//        writeDB("211", "GS25", "store", "sat", "19", "17", "22", "50", "010-7819-3896", "신동", "12000");
//        writeDB("212", "새마을식당", "cafe", "wen", "13", "53", "24", "18", "010-4958-0897", "신동", "19000");
//        writeDB("213", "CU편의점", "serving", "thu", "23", "13", "24", "34", "010-7063-7593", "신동", "13000");
//        writeDB("214", "이디야커피", "store", "tue", "14", "25", "18", "13", "010-9916-2138", "원천동", "16000");
//        writeDB("215", "스타벅스", "cooking", "thu", "4", "5", "8", "32", "010-2606-7860", "신동", "9000");
//        writeDB("216", "올리브영", "delivery", "wen", "11", "53", "12", "29", "010-3271-4904", "영통동", "10000");
//        writeDB("217", "GS25", "serving", "wen", "14", "33", "23", "34", "010-8974-2841", "매탄동", "17000");
//        writeDB("218", "CU편의점", "serving", "sun", "19", "43", "23", "54", "010-4586-1577", "매탄동", "18000");
//        writeDB("219", "올리브영", "serving", "wen", "4", "17", "17", "41", "010-1214-8110", "망포동", "18000");
//        writeDB("220", "BBQ치킨", "cooking", "tue", "19", "49", "22", "56", "010-7023-3324", "태장동", "19000");
//        writeDB("221", "스타벅스", "serving", "fri", "3", "11", "22", "28", "010-1356-8353", "매탄동", "11000");
//        writeDB("222", "BBQ치킨", "cafe", "thu", "7", "18", "9", "6", "010-1905-8947", "매탄동", "17000");
//        writeDB("223", "GS25", "serving", "mon", "4", "47", "7", "24", "010-1768-7373", "태장동", "19000");
//        writeDB("224", "이디야커피", "cafe", "wen", "21", "29", "23", "58", "010-3848-8983", "원천동", "13000");
//        writeDB("225", "CU편의점", "delivery", "fri", "9", "44", "17", "0", "010-1691-8022", "매탄동", "11000");
//        writeDB("226", "이디야커피", "store", "sat", "12", "49", "15", "7", "010-8880-4695", "광교동", "9000");
//        writeDB("227", "새마을식당", "store", "sat", "7", "58", "18", "26", "010-5538-0803", "태장동", "18000");
//        writeDB("228", "파리바게트", "delivery", "sat", "3", "13", "13", "26", "010-9226-1183", "영통동", "19000");
//        writeDB("229", "CU편의점", "delivery", "mon", "3", "50", "23", "39", "010-0742-5082", "하동", "13000");
//        writeDB("230", "BBQ치킨", "store", "sun", "5", "49", "14", "54", "010-2875-1802", "영통동", "9000");
//        writeDB("231", "올리브영", "cooking", "thu", "15", "35", "21", "21", "010-6680-6956", "광교동", "19000");
//        writeDB("232", "GS25", "delivery", "tue", "0", "37", "7", "54", "010-8753-9665", "태장동", "12000");
//        writeDB("233", "이디야커피", "delivery", "sun", "15", "5", "20", "1", "010-3215-1614", "하동", "11000");
//        writeDB("234", "GS25", "delivery", "sat", "9", "21", "24", "5", "010-2556-1054", "이의동", "18000");
//        writeDB("235", "이디야커피", "store", "sun", "8", "11", "14", "40", "010-6532-6727", "신동", "14000");
//        writeDB("236", "CU편의점", "serving", "wen", "1", "40", "18", "56", "010-5843-2657", "하동", "9000");
//        writeDB("237", "올리브영", "store", "sun", "8", "20", "23", "26", "010-9054-4576", "원천동", "16000");
//        writeDB("238", "GS25", "serving", "tue", "14", "43", "18", "57", "010-9733-3679", "원천동", "14000");
//        writeDB("239", "이디야커피", "cafe", "sun", "9", "38", "14", "29", "010-1500-8775", "하동", "16000");
//        writeDB("240", "GS25", "cooking", "sun", "7", "40", "17", "17", "010-4106-5384", "신동", "15000");
//        writeDB("241", "파리바게트", "delivery", "sun", "11", "58", "16", "38", "010-4227-3125", "망포동", "9000");
//        writeDB("242", "GS25", "store", "fri", "7", "35", "19", "53", "010-6046-2378", "망포동", "12000");
//        writeDB("243", "새마을식당", "serving", "sun", "8", "44", "19", "30", "010-9384-6710", "태장동", "20000");
//        writeDB("244", "올리브영", "serving", "fri", "5", "28", "14", "35", "010-5934-0974", "광교동", "16000");
//        writeDB("245", "올리브영", "store", "tue", "17", "1", "22", "53", "010-5562-5885", "이의동", "11000");
//        writeDB("246", "CU편의점", "cooking", "sun", "22", "48", "24", "12", "010-3769-2294", "태장동", "19000");
//        writeDB("247", "스타벅스", "store", "mon", "17", "18", "18", "41", "010-3941-9445", "광교동", "18000");
//        writeDB("248", "CU편의점", "serving", "wen", "8", "30", "15", "50", "010-2209-2553", "광교동", "19000");
//        writeDB("249", "GS25", "cafe", "tue", "10", "30", "14", "9", "010-9962-9495", "이의동", "17000");
//        writeDB("250", "GS25", "delivery", "fri", "11", "7", "22", "43", "010-6743-6602", "광교동", "13000");
//        writeDB("251", "BBQ치킨", "serving", "mon", "17", "40", "18", "41", "010-2409-5530", "하동", "13000");
//        writeDB("252", "GS25", "cooking", "mon", "15", "27", "19", "56", "010-7476-4577", "태장동", "13000");
//        writeDB("253", "스타벅스", "serving", "thu", "10", "23", "23", "49", "010-6868-2448", "광교동", "14000");
//        writeDB("254", "CU편의점", "cafe", "tue", "18", "3", "20", "44", "010-9951-2314", "매탄동", "19000");
//        writeDB("255", "GS25", "delivery", "thu", "12", "13", "16", "7", "010-9895-3412", "태장동", "11000");
//        writeDB("256", "스타벅스", "cooking", "tue", "3", "0", "8", "53", "010-7155-5159", "영통동", "13000");
//        writeDB("257", "새마을식당", "cafe", "wen", "12", "28", "16", "4", "010-0074-3290", "신동", "12000");
//        writeDB("258", "이디야커피", "cafe", "sun", "21", "58", "22", "29", "010-1164-6089", "신동", "13000");
//        writeDB("259", "이디야커피", "cooking", "sat", "8", "3", "17", "47", "010-0262-4295", "매탄동", "18000");
//        writeDB("260", "스타벅스", "delivery", "mon", "11", "4", "18", "7", "010-3294-0713", "광교동", "13000");
//        writeDB("261", "BBQ치킨", "cafe", "mon", "13", "45", "20", "51", "010-0210-1248", "이의동", "18000");
//        writeDB("262", "CU편의점", "delivery", "thu", "2", "35", "22", "56", "010-0548-8876", "원천동", "9000");
//        writeDB("263", "새마을식당", "serving", "tue", "18", "41", "21", "42", "010-4661-1118", "매탄동", "10000");
//        writeDB("264", "BBQ치킨", "serving", "fri", "16", "21", "22", "8", "010-4873-9559", "원천동", "19000");
//        writeDB("265", "BBQ치킨", "cafe", "tue", "16", "1", "23", "23", "010-4031-9313", "영통동", "14000");
//        writeDB("266", "BBQ치킨", "cooking", "wen", "14", "8", "21", "21", "010-4179-9063", "하동", "15000");
//        writeDB("267", "GS25", "cooking", "mon", "12", "6", "15", "41", "010-6457-4584", "광교동", "18000");
//        writeDB("268", "BBQ치킨", "cafe", "wen", "11", "32", "17", "54", "010-1664-0057", "매탄동", "14000");
//        writeDB("269", "올리브영", "store", "sun", "0", "53", "8", "50", "010-5802-3497", "원천동", "16000");
//        writeDB("270", "파리바게트", "cafe", "mon", "9", "10", "24", "38", "010-2538-3863", "영통동", "17000");
//        writeDB("271", "이디야커피", "serving", "tue", "23", "29", "24", "38", "010-8253-4545", "태장동", "15000");
//        writeDB("272", "새마을식당", "serving", "wen", "23", "24", "24", "49", "010-9496-3790", "하동", "17000");
//        writeDB("273", "CU편의점", "delivery", "sat", "20", "41", "24", "56", "010-6591-2301", "매탄동", "15000");
//        writeDB("274", "이디야커피", "cooking", "fri", "5", "45", "16", "48", "010-7685-8537", "광교동", "10000");
//        writeDB("275", "CU편의점", "cafe", "sun", "14", "26", "16", "7", "010-2677-8534", "태장동", "14000");
//        writeDB("276", "GS25", "cafe", "sun", "16", "18", "22", "32", "010-8835-5376", "신동", "11000");
//        writeDB("277", "BBQ치킨", "cooking", "mon", "16", "38", "17", "55", "010-8009-2239", "망포동", "10000");
//        writeDB("278", "새마을식당", "cafe", "tue", "5", "43", "20", "50", "010-2492-2851", "태장동", "14000");
//        writeDB("279", "새마을식당", "cooking", "tue", "9", "16", "19", "13", "010-7813-2977", "망포동", "12000");
//        writeDB("280", "BBQ치킨", "serving", "fri", "2", "8", "18", "4", "010-3190-8241", "이의동", "18000");
//        writeDB("281", "CU편의점", "delivery", "thu", "18", "43", "22", "33", "010-6585-6100", "하동", "12000");
//        writeDB("282", "CU편의점", "delivery", "thu", "12", "38", "22", "48", "010-6100-4249", "영통동", "9000");
//        writeDB("283", "CU편의점", "cafe", "wen", "20", "31", "23", "48", "010-9915-7831", "하동", "15000");
//        writeDB("284", "BBQ치킨", "store", "tue", "18", "18", "20", "44", "010-4466-9718", "영통동", "17000");
//        writeDB("285", "파리바게트", "cafe", "sat", "19", "23", "21", "2", "010-8037-1048", "하동", "9000");
//        writeDB("286", "이디야커피", "store", "fri", "11", "41", "20", "9", "010-1002-1314", "이의동", "10000");
//        writeDB("287", "파리바게트", "cafe", "wen", "4", "10", "16", "22", "010-7426-5631", "신동", "17000");
//        writeDB("288", "CU편의점", "serving", "fri", "10", "8", "15", "27", "010-3788-4769", "하동", "17000");
//        writeDB("289", "CU편의점", "cooking", "fri", "16", "1", "19", "27", "010-7453-2867", "영통동", "11000");
//        writeDB("290", "올리브영", "cooking", "wen", "20", "34", "21", "15", "010-9720-1713", "신동", "10000");
//        writeDB("291", "이디야커피", "cafe", "tue", "1", "54", "2", "17", "010-3444-6037", "하동", "20000");
//        writeDB("292", "CU편의점", "store", "thu", "12", "44", "17", "9", "010-7109-3695", "매탄동", "16000");
//        writeDB("293", "GS25", "store", "tue", "21", "22", "24", "4", "010-7991-5487", "매탄동", "17000");
//        writeDB("294", "올리브영", "cooking", "wen", "0", "58", "15", "9", "010-7793-0045", "태장동", "11000");
//        writeDB("295", "스타벅스", "cafe", "sat", "20", "48", "22", "4", "010-5100-4306", "하동", "9000");
//        writeDB("296", "CU편의점", "delivery", "wen", "22", "39", "23", "0", "010-0960-8425", "태장동", "11000");
//        writeDB("297", "BBQ치킨", "serving", "mon", "20", "51", "21", "21", "010-8775-8002", "원천동", "13000");
//        writeDB("298", "이디야커피", "cooking", "sat", "9", "8", "22", "1", "010-6714-8540", "매탄동", "19000");
//        writeDB("299", "BBQ치킨", "cooking", "thu", "8", "10", "21", "40", "010-0827-4256", "신동", "13000");
//        writeDB("300", "CU편의점", "delivery", "mon", "13", "14", "24", "49", "010-8939-4598", "신동", "15000");
//        writeDB("301", "GS25", "serving", "mon", "2", "24", "15", "3", "010-6704-6109", "하동", "12000");
//        writeDB("302", "GS25", "cafe", "wen", "23", "39", "24", "39", "010-3222-8651", "광교동", "9000");
//        writeDB("303", "새마을식당", "cooking", "sat", "5", "39", "18", "50", "010-5536-9925", "태장동", "20000");
//        writeDB("304", "올리브영", "delivery", "sun", "12", "22", "16", "7", "010-8690-8043", "하동", "19000");
//        writeDB("305", "이디야커피", "cafe", "tue", "23", "4", "24", "11", "010-2728-3246", "광교동", "14000");
//        writeDB("306", "이디야커피", "serving", "sun", "1", "27", "23", "20", "010-5977-4164", "광교동", "13000");
//        writeDB("307", "GS25", "cafe", "wen", "4", "53", "5", "57", "010-0043-3192", "매탄동", "17000");
//        writeDB("308", "GS25", "delivery", "sat", "12", "48", "16", "2", "010-6697-7236", "신동", "13000");
//        writeDB("309", "파리바게트", "delivery", "wen", "9", "27", "12", "3", "010-1727-6739", "망포동", "10000");
//        writeDB("310", "파리바게트", "serving", "tue", "2", "2", "22", "32", "010-2710-7374", "망포동", "16000");
//        writeDB("311", "이디야커피", "cafe", "sat", "12", "47", "18", "37", "010-1590-9423", "이의동", "18000");
//        writeDB("312", "올리브영", "cafe", "sat", "11", "21", "14", "6", "010-3808-7477", "광교동", "10000");
//        writeDB("313", "CU편의점", "delivery", "sat", "23", "11", "24", "11", "010-5262-4524", "원천동", "19000");
//        writeDB("314", "스타벅스", "cafe", "sun", "22", "11", "24", "34", "010-5108-3993", "망포동", "15000");
//        writeDB("315", "올리브영", "serving", "mon", "5", "9", "24", "2", "010-3216-4106", "태장동", "20000");
//        writeDB("316", "이디야커피", "cafe", "sat", "3", "28", "11", "35", "010-0130-9436", "신동", "10000");
//        writeDB("317", "파리바게트", "cafe", "sun", "8", "35", "22", "8", "010-2932-3770", "영통동", "16000");
//        writeDB("318", "올리브영", "store", "mon", "0", "43", "8", "31", "010-6470-3943", "원천동", "20000");
//        writeDB("319", "CU편의점", "serving", "sat", "17", "40", "18", "52", "010-4004-0279", "망포동", "19000");
//        writeDB("320", "CU편의점", "delivery", "sat", "21", "25", "22", "59", "010-6431-4381", "광교동", "13000");
//        writeDB("321", "BBQ치킨", "cafe", "wen", "5", "54", "16", "17", "010-1757-6865", "하동", "10000");
//        writeDB("322", "새마을식당", "serving", "fri", "6", "29", "20", "57", "010-3705-7435", "망포동", "9000");
//        writeDB("323", "스타벅스", "store", "tue", "21", "11", "22", "29", "010-2440-6211", "광교동", "15000");
//        writeDB("324", "BBQ치킨", "cooking", "fri", "8", "23", "12", "33", "010-9417-9567", "신동", "15000");
//        writeDB("325", "CU편의점", "serving", "tue", "9", "56", "24", "16", "010-8381-8381", "영통동", "13000");
//        writeDB("326", "CU편의점", "store", "fri", "4", "21", "19", "1", "010-4225-4077", "이의동", "15000");
//        writeDB("327", "올리브영", "cooking", "tue", "17", "30", "19", "12", "010-7096-2245", "하동", "14000");
//        writeDB("328", "스타벅스", "serving", "sat", "6", "47", "24", "3", "010-9544-9706", "매탄동", "20000");
//        writeDB("329", "이디야커피", "store", "sat", "1", "49", "13", "0", "010-0631-7950", "망포동", "10000");
//        writeDB("330", "새마을식당", "cafe", "tue", "4", "48", "5", "37", "010-9279-9847", "신동", "11000");
//        writeDB("331", "올리브영", "delivery", "wen", "1", "28", "23", "50", "010-6374-6145", "원천동", "13000");
//        writeDB("332", "새마을식당", "serving", "sun", "20", "59", "23", "31", "010-9078-4435", "태장동", "15000");
//        writeDB("333", "스타벅스", "cooking", "tue", "6", "3", "22", "8", "010-3735-8047", "태장동", "17000");
//        writeDB("334", "새마을식당", "store", "fri", "20", "44", "21", "58", "010-4466-7932", "이의동", "14000");
//        writeDB("335", "새마을식당", "serving", "sun", "9", "18", "15", "8", "010-7783-0250", "영통동", "13000");
//        writeDB("336", "GS25", "cooking", "fri", "7", "39", "20", "12", "010-1562-0507", "신동", "15000");
//        writeDB("337", "새마을식당", "delivery", "sun", "19", "36", "21", "50", "010-0992-8574", "영통동", "9000");
//        writeDB("338", "이디야커피", "cafe", "tue", "2", "0", "20", "8", "010-5482-1895", "태장동", "17000");
//        writeDB("339", "이디야커피", "store", "fri", "23", "57", "24", "55", "010-8511-7245", "원천동", "17000");
//        writeDB("340", "이디야커피", "serving", "tue", "13", "38", "20", "8", "010-2895-0001", "태장동", "11000");
//        writeDB("341", "BBQ치킨", "delivery", "tue", "10", "5", "21", "24", "010-9069-4257", "이의동", "18000");
//        writeDB("342", "CU편의점", "serving", "sat", "12", "49", "20", "29", "010-2688-2215", "하동", "11000");
//        writeDB("343", "GS25", "serving", "sun", "10", "10", "12", "45", "010-6785-6467", "매탄동", "15000");
//        writeDB("344", "새마을식당", "serving", "sun", "0", "55", "20", "27", "010-2154-2007", "원천동", "16000");
//        writeDB("345", "스타벅스", "cafe", "fri", "14", "33", "16", "58", "010-1904-5754", "신동", "16000");
//        writeDB("346", "스타벅스", "delivery", "sun", "14", "3", "19", "29", "010-6552-8923", "망포동", "11000");
//        writeDB("347", "GS25", "cafe", "sat", "11", "17", "23", "24", "010-1937-9961", "망포동", "19000");
//        writeDB("348", "BBQ치킨", "cooking", "wen", "4", "27", "24", "31", "010-3441-1093", "광교동", "14000");
//        writeDB("349", "파리바게트", "delivery", "fri", "23", "50", "24", "48", "010-0941-1528", "하동", "10000");
//        writeDB("350", "CU편의점", "delivery", "mon", "10", "44", "11", "54", "010-2266-3980", "이의동", "20000");
//        writeDB("351", "BBQ치킨", "serving", "wen", "11", "36", "22", "2", "010-0482-0847", "매탄동", "10000");
//        writeDB("352", "CU편의점", "store", "tue", "8", "4", "18", "14", "010-0126-7549", "신동", "10000");
//        writeDB("353", "새마을식당", "serving", "fri", "0", "29", "20", "7", "010-0847-6512", "망포동", "18000");
//        writeDB("354", "올리브영", "store", "wen", "3", "15", "22", "35", "010-5526-1808", "망포동", "11000");
//        writeDB("355", "올리브영", "cafe", "sun", "1", "53", "23", "49", "010-2724-5336", "하동", "12000");
//        writeDB("356", "이디야커피", "cafe", "thu", "15", "9", "17", "58", "010-3568-2182", "망포동", "12000");
//        writeDB("357", "이디야커피", "delivery", "fri", "16", "42", "21", "9", "010-7728-1496", "광교동", "15000");
//        writeDB("358", "새마을식당", "store", "mon", "8", "6", "16", "55", "010-1128-7836", "태장동", "10000");
//        writeDB("359", "CU편의점", "store", "sun", "18", "13", "24", "11", "010-4331-5227", "광교동", "15000");
//        writeDB("360", "CU편의점", "delivery", "wen", "10", "37", "18", "34", "010-2585-6074", "영통동", "19000");
//        writeDB("361", "파리바게트", "cooking", "mon", "20", "52", "21", "32", "010-7105-5546", "하동", "16000");
//        writeDB("362", "올리브영", "store", "sun", "5", "55", "18", "20", "010-2571-7764", "이의동", "19000");
//        writeDB("363", "CU편의점", "delivery", "wen", "23", "32", "24", "0", "010-7702-6637", "원천동", "11000");
//        writeDB("364", "BBQ치킨", "cafe", "thu", "9", "15", "11", "37", "010-7343-2156", "신동", "13000");
//        writeDB("365", "새마을식당", "cafe", "mon", "22", "0", "24", "24", "010-2518-2891", "하동", "18000");
//        writeDB("366", "올리브영", "store", "wen", "16", "46", "19", "54", "010-5939-5774", "원천동", "20000");
//        writeDB("367", "CU편의점", "serving", "tue", "7", "10", "19", "27", "010-2749-2142", "매탄동", "19000");
//        writeDB("368", "스타벅스", "delivery", "thu", "0", "19", "5", "58", "010-3994-5322", "영통동", "14000");
//        writeDB("369", "스타벅스", "cooking", "thu", "12", "42", "19", "13", "010-5424-6142", "영통동", "9000");
//        writeDB("370", "새마을식당", "delivery", "tue", "18", "42", "19", "46", "010-2767-7333", "이의동", "14000");
//        writeDB("371", "올리브영", "serving", "sat", "3", "3", "15", "11", "010-9166-7966", "신동", "9000");
//        writeDB("372", "새마을식당", "cooking", "wen", "23", "56", "24", "13", "010-1837-6731", "매탄동", "16000");
//        writeDB("373", "스타벅스", "cooking", "sun", "3", "20", "14", "17", "010-6671-0302", "매탄동", "13000");
//        writeDB("374", "파리바게트", "serving", "sun", "6", "5", "8", "39", "010-7465-9978", "망포동", "16000");
//        writeDB("375", "GS25", "serving", "mon", "12", "10", "18", "15", "010-1091-9340", "원천동", "19000");
//        writeDB("376", "새마을식당", "cafe", "mon", "15", "36", "22", "58", "010-8721-2964", "광교동", "20000");
//        writeDB("377", "올리브영", "delivery", "sat", "7", "51", "11", "19", "010-7866-5710", "광교동", "12000");
//        writeDB("378", "GS25", "store", "sat", "1", "40", "21", "41", "010-9614-5844", "영통동", "13000");
//        writeDB("379", "BBQ치킨", "cooking", "wen", "11", "51", "13", "4", "010-8428-8755", "망포동", "9000");
//        writeDB("380", "파리바게트", "delivery", "sat", "6", "16", "15", "29", "010-7358-3333", "하동", "12000");
//        writeDB("381", "GS25", "serving", "sun", "7", "42", "24", "40", "010-3950-4415", "이의동", "15000");
//        writeDB("382", "스타벅스", "serving", "mon", "12", "47", "17", "46", "010-1880-1573", "영통동", "17000");
//        writeDB("383", "파리바게트", "serving", "sun", "10", "7", "11", "18", "010-7405-5409", "영통동", "9000");
//        writeDB("384", "GS25", "store", "thu", "0", "52", "10", "36", "010-9639-2786", "망포동", "13000");
//        writeDB("385", "GS25", "cooking", "thu", "14", "43", "17", "19", "010-8000-4681", "이의동", "19000");
//        writeDB("386", "파리바게트", "store", "sun", "10", "21", "12", "38", "010-8761-1263", "신동", "9000");
//        writeDB("387", "파리바게트", "cooking", "wen", "2", "58", "12", "42", "010-9016-9921", "하동", "13000");
//        writeDB("388", "올리브영", "cooking", "mon", "20", "59", "21", "57", "010-1683-8586", "하동", "18000");
//        writeDB("389", "스타벅스", "cafe", "thu", "21", "14", "22", "46", "010-4796-3728", "신동", "15000");
//        writeDB("390", "스타벅스", "cooking", "tue", "8", "51", "23", "32", "010-7410-4617", "원천동", "12000");
//        writeDB("391", "GS25", "cafe", "mon", "3", "57", "7", "56", "010-4234-0538", "신동", "19000");
//        writeDB("392", "이디야커피", "serving", "sun", "18", "1", "19", "1", "010-6540-9761", "하동", "16000");
//        writeDB("393", "GS25", "serving", "thu", "7", "6", "11", "16", "010-0372-7167", "원천동", "11000");
//        writeDB("394", "올리브영", "store", "mon", "6", "52", "12", "50", "010-1976-4786", "매탄동", "15000");
//        writeDB("395", "올리브영", "store", "wen", "9", "36", "12", "16", "010-9781-8078", "망포동", "19000");
//        writeDB("396", "새마을식당", "serving", "thu", "19", "0", "24", "34", "010-0139-6303", "원천동", "18000");
//        writeDB("397", "파리바게트", "cafe", "sun", "9", "17", "18", "3", "010-7251-3655", "이의동", "16000");
//        writeDB("398", "올리브영", "cafe", "thu", "22", "45", "23", "17", "010-3254-0546", "망포동", "19000");
//        writeDB("399", "스타벅스", "store", "thu", "13", "38", "14", "46", "010-4508-7165", "광교동", "15000");
//        writeDB("400", "GS25", "cooking", "tue", "23", "36", "24", "40", "010-1891-5323", "하동", "15000");
//        writeDB("401", "파리바게트", "cooking", "tue", "8", "33", "11", "41", "010-1113-7940", "영통동", "17000");
//        writeDB("402", "스타벅스", "cooking", "sun", "23", "20", "24", "18", "010-4403-4115", "매탄동", "9000");
//        writeDB("403", "새마을식당", "cooking", "tue", "11", "19", "13", "31", "010-8893-6634", "하동", "11000");
//        writeDB("404", "이디야커피", "delivery", "tue", "19", "31", "20", "40", "010-2033-3384", "영통동", "17000");
//        writeDB("405", "올리브영", "store", "sun", "10", "38", "20", "6", "010-9071-5399", "망포동", "14000");
//        writeDB("406", "올리브영", "serving", "wen", "1", "31", "6", "12", "010-5282-9879", "매탄동", "17000");
//        writeDB("407", "이디야커피", "store", "fri", "9", "23", "22", "14", "010-2846-8636", "신동", "9000");
//        writeDB("408", "GS25", "serving", "thu", "20", "16", "22", "11", "010-4747-6066", "이의동", "13000");
//        writeDB("409", "파리바게트", "store", "thu", "22", "29", "23", "3", "010-9893-8679", "이의동", "12000");
//        writeDB("410", "파리바게트", "cooking", "sun", "20", "2", "23", "29", "010-1482-6654", "신동", "13000");
//        writeDB("411", "CU편의점", "delivery", "wen", "0", "36", "4", "3", "010-3158-3058", "영통동", "13000");
//        writeDB("412", "파리바게트", "cooking", "sat", "11", "6", "18", "37", "010-8523-6022", "영통동", "15000");
//        writeDB("413", "CU편의점", "store", "mon", "2", "22", "4", "15", "010-2772-3009", "광교동", "10000");
//        writeDB("414", "올리브영", "cafe", "sun", "15", "10", "23", "18", "010-2618-9123", "이의동", "13000");
//        writeDB("415", "스타벅스", "cafe", "fri", "3", "33", "18", "24", "010-7052-9032", "신동", "18000");
//        writeDB("416", "스타벅스", "cafe", "fri", "18", "21", "21", "29", "010-6223-4102", "이의동", "9000");
//        writeDB("417", "BBQ치킨", "serving", "thu", "7", "50", "22", "4", "010-9948-0026", "하동", "11000");
//        writeDB("418", "파리바게트", "serving", "fri", "11", "45", "22", "29", "010-4154-1218", "매탄동", "17000");
//        writeDB("419", "CU편의점", "serving", "fri", "12", "40", "15", "23", "010-7896-0053", "매탄동", "13000");
//        writeDB("420", "CU편의점", "store", "sat", "9", "26", "24", "59", "010-9715-5215", "영통동", "15000");
//        writeDB("421", "이디야커피", "delivery", "fri", "23", "45", "24", "41", "010-6181-9152", "태장동", "9000");
//        writeDB("422", "새마을식당", "cafe", "tue", "18", "6", "20", "44", "010-4907-3751", "원천동", "15000");
//        writeDB("423", "새마을식당", "serving", "fri", "7", "27", "8", "14", "010-8465-5405", "영통동", "11000");
//        writeDB("424", "CU편의점", "cafe", "mon", "17", "55", "22", "26", "010-3943-6917", "매탄동", "15000");
//        writeDB("425", "스타벅스", "serving", "sat", "5", "28", "15", "9", "010-6702-4793", "망포동", "20000");
//        writeDB("426", "GS25", "cooking", "wen", "14", "12", "23", "1", "010-0041-2400", "영통동", "18000");
//        writeDB("427", "스타벅스", "cooking", "sat", "15", "13", "23", "0", "010-3109-9273", "하동", "11000");
//        writeDB("428", "올리브영", "store", "tue", "6", "37", "13", "55", "010-3048-2723", "하동", "16000");
//        writeDB("429", "BBQ치킨", "store", "sat", "7", "25", "17", "20", "010-6784-9352", "망포동", "20000");
//        writeDB("430", "새마을식당", "delivery", "thu", "7", "53", "20", "10", "010-7526-2091", "이의동", "17000");
//        writeDB("431", "새마을식당", "cooking", "mon", "15", "26", "24", "26", "010-3888-9611", "원천동", "20000");
//        writeDB("432", "파리바게트", "cafe", "fri", "14", "45", "24", "34", "010-0601-0243", "원천동", "14000");
//        writeDB("433", "스타벅스", "store", "fri", "14", "51", "24", "59", "010-5699-6984", "신동", "14000");
//        writeDB("434", "새마을식당", "store", "sat", "16", "43", "19", "38", "010-2706-9223", "이의동", "9000");
//        writeDB("435", "GS25", "delivery", "sun", "10", "33", "15", "1", "010-4040-2551", "하동", "16000");
//        writeDB("436", "이디야커피", "delivery", "wen", "20", "26", "23", "22", "010-3499-7662", "영통동", "16000");
//        writeDB("437", "CU편의점", "cooking", "thu", "23", "34", "24", "5", "010-5051-1557", "광교동", "15000");
//        writeDB("438", "파리바게트", "cafe", "sun", "7", "6", "12", "44", "010-4082-6716", "영통동", "10000");
//        writeDB("439", "스타벅스", "cafe", "thu", "5", "41", "17", "8", "010-4239-3104", "원천동", "14000");
//        writeDB("440", "GS25", "cafe", "fri", "13", "36", "14", "21", "010-4515-0123", "영통동", "9000");
//        writeDB("441", "새마을식당", "cooking", "wen", "3", "9", "16", "38", "010-2732-4615", "광교동", "15000");
//        writeDB("442", "이디야커피", "cooking", "fri", "5", "36", "15", "48", "010-1846-7302", "신동", "19000");
//        writeDB("443", "올리브영", "serving", "thu", "13", "46", "16", "9", "010-1464-5048", "매탄동", "17000");
//        writeDB("444", "스타벅스", "serving", "sat", "2", "8", "13", "3", "010-0928-3391", "신동", "18000");
//        writeDB("445", "GS25", "delivery", "thu", "0", "7", "16", "12", "010-9315-8186", "태장동", "13000");
//        writeDB("446", "새마을식당", "cafe", "wen", "0", "44", "15", "36", "010-4804-3747", "영통동", "10000");
//        writeDB("447", "이디야커피", "cooking", "thu", "8", "50", "14", "2", "010-8785-6703", "원천동", "11000");
//        writeDB("448", "이디야커피", "delivery", "sun", "9", "11", "15", "17", "010-5066-3390", "영통동", "12000");
//        writeDB("449", "새마을식당", "delivery", "wen", "12", "47", "18", "40", "010-2792-3964", "원천동", "18000");
//        writeDB("450", "스타벅스", "cooking", "mon", "5", "41", "16", "46", "010-1756-3974", "신동", "9000");
//        writeDB("451", "BBQ치킨", "store", "thu", "10", "25", "14", "13", "010-6515-2866", "하동", "13000");
//        writeDB("452", "GS25", "serving", "thu", "20", "37", "21", "50", "010-9174-2068", "원천동", "19000");
//        writeDB("453", "새마을식당", "cooking", "wen", "20", "29", "23", "5", "010-4751-8732", "이의동", "19000");
//        writeDB("454", "파리바게트", "cafe", "fri", "1", "14", "20", "26", "010-4280-3133", "신동", "13000");
//        writeDB("455", "올리브영", "cooking", "fri", "15", "49", "22", "11", "010-3915-1589", "하동", "18000");
//        writeDB("456", "파리바게트", "delivery", "sat", "4", "32", "18", "4", "010-3363-1496", "태장동", "17000");
//        writeDB("457", "파리바게트", "cafe", "wen", "20", "50", "22", "34", "010-4851-9418", "신동", "12000");
//        writeDB("458", "파리바게트", "cooking", "sat", "19", "56", "23", "20", "010-4638-0845", "망포동", "12000");
//        writeDB("459", "이디야커피", "cooking", "fri", "14", "15", "18", "32", "010-7471-9163", "광교동", "18000");
//        writeDB("460", "GS25", "cooking", "mon", "3", "24", "20", "18", "010-6038-8714", "태장동", "17000");
//        writeDB("461", "CU편의점", "delivery", "tue", "23", "10", "24", "38", "010-2982-3806", "신동", "14000");
//        writeDB("462", "새마을식당", "cooking", "sun", "2", "11", "22", "50", "010-8555-9372", "이의동", "14000");
//        writeDB("463", "파리바게트", "store", "thu", "20", "25", "22", "36", "010-2915-9506", "신동", "18000");
//        writeDB("464", "스타벅스", "cafe", "thu", "11", "10", "24", "56", "010-8813-9340", "매탄동", "10000");
//        writeDB("465", "스타벅스", "cafe", "fri", "22", "50", "23", "31", "010-1200-5759", "태장동", "20000");
//        writeDB("466", "이디야커피", "delivery", "sat", "15", "53", "19", "46", "010-1844-1867", "원천동", "17000");
//        writeDB("467", "스타벅스", "delivery", "wen", "21", "52", "24", "56", "010-0378-1618", "이의동", "9000");
//        writeDB("468", "새마을식당", "cafe", "sun", "11", "33", "16", "44", "010-4527-9385", "이의동", "13000");
//        writeDB("469", "BBQ치킨", "cafe", "sun", "5", "29", "18", "57", "010-6885-1891", "원천동", "19000");
//        writeDB("470", "이디야커피", "delivery", "mon", "16", "21", "23", "3", "010-7664-4993", "매탄동", "15000");
//        writeDB("471", "스타벅스", "serving", "thu", "7", "22", "17", "56", "010-5878-7563", "이의동", "13000");
//        writeDB("472", "이디야커피", "delivery", "wen", "14", "50", "16", "2", "010-8202-0198", "망포동", "9000");
//        writeDB("473", "올리브영", "cooking", "sun", "23", "34", "24", "51", "010-2363-2765", "태장동", "12000");
//        writeDB("474", "올리브영", "delivery", "sun", "14", "48", "24", "19", "010-6827-4753", "하동", "10000");
//        writeDB("475", "GS25", "serving", "sun", "22", "33", "24", "9", "010-7161-3312", "매탄동", "11000");
//        writeDB("476", "새마을식당", "cooking", "tue", "17", "19", "21", "44", "010-6468-6613", "하동", "15000");
//        writeDB("477", "CU편의점", "store", "mon", "16", "6", "17", "19", "010-6264-6563", "원천동", "12000");
//        writeDB("478", "CU편의점", "store", "fri", "3", "23", "22", "16", "010-9011-8867", "신동", "19000");
//        writeDB("479", "파리바게트", "delivery", "sat", "18", "10", "20", "52", "010-0125-8479", "이의동", "11000");
//        writeDB("480", "BBQ치킨", "store", "mon", "6", "8", "12", "9", "010-4453-1961", "망포동", "16000");
//        writeDB("481", "BBQ치킨", "serving", "sat", "22", "37", "23", "5", "010-8364-5676", "태장동", "17000");
//        writeDB("482", "파리바게트", "delivery", "mon", "6", "20", "12", "27", "010-7736-0419", "하동", "13000");
//        writeDB("483", "이디야커피", "cafe", "mon", "10", "43", "19", "2", "010-2878-1798", "신동", "16000");
//        writeDB("484", "CU편의점", "serving", "wen", "10", "54", "11", "56", "010-3755-1352", "광교동", "20000");
//        writeDB("485", "CU편의점", "serving", "sun", "22", "20", "23", "20", "010-9094-2822", "하동", "18000");
//        writeDB("486", "새마을식당", "delivery", "tue", "10", "24", "22", "31", "010-6450-8798", "망포동", "15000");
//        writeDB("487", "GS25", "cooking", "sat", "1", "1", "22", "21", "010-4224-2346", "이의동", "14000");
//        writeDB("488", "파리바게트", "serving", "mon", "10", "26", "14", "25", "010-4886-2979", "망포동", "10000");
//        writeDB("489", "이디야커피", "delivery", "thu", "1", "49", "19", "3", "010-0583-1079", "망포동", "10000");
//        writeDB("490", "파리바게트", "cooking", "mon", "13", "28", "16", "47", "010-4181-6344", "하동", "13000");
//        writeDB("491", "스타벅스", "cafe", "wen", "14", "25", "15", "33", "010-2909-1617", "영통동", "18000");
//        writeDB("492", "새마을식당", "cafe", "sat", "23", "9", "24", "35", "010-4284-8489", "이의동", "20000");
//        writeDB("493", "GS25", "serving", "wen", "10", "38", "23", "53", "010-8622-7411", "신동", "11000");
//        writeDB("494", "BBQ치킨", "serving", "fri", "16", "55", "17", "33", "010-0527-4424", "광교동", "18000");
//        writeDB("495", "올리브영", "delivery", "fri", "11", "30", "22", "45", "010-7282-6850", "광교동", "18000");
//        writeDB("496", "BBQ치킨", "cafe", "thu", "18", "46", "23", "58", "010-2107-4806", "원천동", "20000");
//        writeDB("497", "CU편의점", "cooking", "mon", "21", "33", "23", "32", "010-3864-0091", "원천동", "15000");
//        writeDB("498", "CU편의점", "delivery", "fri", "20", "25", "22", "14", "010-8214-0848", "매탄동", "9000");
//        writeDB("499", "올리브영", "cooking", "fri", "1", "44", "20", "13", "010-4422-8447", "광교동", "10000");
//        writeDB("500", "올리브영", "store", "tue", "4", "13", "22", "17", "010-5055-1045", "망포동", "14000");
//        writeDB("501", "GS25", "delivery", "sat", "5", "27", "18", "48", "010-1235-2388", "원천동", "10000");
//        writeDB("502", "스타벅스", "delivery", "thu", "9", "36", "22", "18", "010-9659-4702", "원천동", "9000");
//        writeDB("503", "올리브영", "delivery", "wen", "14", "25", "16", "18", "010-3666-6837", "매탄동", "18000");
//        writeDB("504", "올리브영", "store", "sat", "14", "35", "23", "23", "010-0483-5143", "이의동", "16000");
//        writeDB("505", "GS25", "store", "tue", "15", "26", "18", "26", "010-4302-4142", "매탄동", "15000");
//        writeDB("506", "파리바게트", "cooking", "sun", "4", "42", "15", "22", "010-7085-3985", "매탄동", "14000");
//        writeDB("507", "올리브영", "cafe", "sun", "5", "57", "6", "57", "010-5482-0256", "원천동", "16000");
//        writeDB("508", "이디야커피", "store", "wen", "16", "8", "23", "44", "010-6605-8227", "태장동", "9000");
//        writeDB("509", "이디야커피", "store", "thu", "18", "42", "22", "32", "010-3773-2325", "신동", "16000");
//        writeDB("510", "BBQ치킨", "cafe", "tue", "1", "30", "11", "3", "010-8451-7063", "태장동", "19000");
//        writeDB("511", "올리브영", "cafe", "thu", "3", "43", "16", "48", "010-5464-2825", "하동", "20000");
//        writeDB("512", "GS25", "delivery", "wen", "1", "41", "11", "18", "010-0437-8458", "영통동", "14000");
//        writeDB("513", "올리브영", "cafe", "sun", "19", "13", "23", "6", "010-6103-2257", "망포동", "15000");
//        writeDB("514", "GS25", "serving", "fri", "1", "29", "10", "23", "010-0163-8631", "영통동", "12000");
//        writeDB("515", "새마을식당", "cooking", "sun", "23", "5", "24", "53", "010-9658-0249", "영통동", "15000");
//        writeDB("516", "파리바게트", "serving", "sun", "15", "36", "19", "20", "010-9822-9051", "이의동", "9000");
//        writeDB("517", "스타벅스", "delivery", "sat", "7", "2", "8", "3", "010-3184-2373", "신동", "14000");
//        writeDB("518", "GS25", "delivery", "mon", "15", "10", "22", "59", "010-1219-9951", "매탄동", "15000");
//        writeDB("519", "새마을식당", "delivery", "tue", "19", "37", "22", "24", "010-2986-9235", "이의동", "18000");
//        writeDB("520", "새마을식당", "delivery", "wen", "7", "20", "20", "54", "010-7884-3787", "광교동", "13000");
//        writeDB("521", "파리바게트", "cafe", "sat", "18", "52", "24", "38", "010-2450-4556", "하동", "17000");
//        writeDB("522", "BBQ치킨", "cooking", "sun", "21", "30", "24", "10", "010-9879-6082", "태장동", "20000");
//        writeDB("523", "BBQ치킨", "delivery", "wen", "14", "5", "22", "34", "010-9154-2530", "광교동", "16000");
//        writeDB("524", "BBQ치킨", "delivery", "fri", "10", "48", "13", "38", "010-0426-4919", "이의동", "17000");
//        writeDB("525", "이디야커피", "serving", "fri", "2", "33", "12", "23", "010-7117-0461", "광교동", "15000");
//        writeDB("526", "이디야커피", "cooking", "sun", "5", "40", "6", "22", "010-6583-8863", "신동", "14000");
//        writeDB("527", "파리바게트", "serving", "thu", "19", "45", "22", "57", "010-4872-8521", "원천동", "14000");
//        writeDB("528", "스타벅스", "cafe", "sat", "18", "21", "19", "28", "010-1848-0074", "광교동", "19000");
//        writeDB("529", "새마을식당", "cafe", "thu", "21", "24", "22", "6", "010-0989-4068", "하동", "19000");
//        writeDB("530", "이디야커피", "store", "sun", "10", "21", "20", "46", "010-4701-5680", "이의동", "10000");
//        writeDB("531", "새마을식당", "cafe", "wen", "19", "14", "23", "41", "010-6302-8365", "망포동", "16000");
//        writeDB("532", "파리바게트", "serving", "mon", "6", "22", "17", "30", "010-6281-0506", "이의동", "14000");
//        writeDB("533", "BBQ치킨", "store", "sun", "3", "38", "19", "11", "010-8031-3795", "태장동", "10000");
//        writeDB("534", "CU편의점", "store", "sun", "9", "23", "11", "14", "010-6122-1591", "매탄동", "12000");
//        writeDB("535", "파리바게트", "cooking", "thu", "0", "40", "6", "2", "010-7204-8849", "망포동", "12000");
//        writeDB("536", "올리브영", "delivery", "sat", "4", "37", "23", "40", "010-4021-8409", "망포동", "14000");
//        writeDB("537", "CU편의점", "cafe", "mon", "4", "24", "18", "26", "010-4741-4214", "망포동", "10000");
//        writeDB("538", "파리바게트", "store", "tue", "5", "38", "10", "4", "010-8338-5520", "원천동", "16000");
//        writeDB("539", "새마을식당", "delivery", "fri", "15", "22", "16", "44", "010-2044-6684", "매탄동", "15000");
//        writeDB("540", "파리바게트", "cooking", "mon", "16", "32", "18", "16", "010-7595-2825", "태장동", "11000");
//        writeDB("541", "스타벅스", "delivery", "sat", "6", "36", "21", "52", "010-9319-1028", "하동", "10000");
//        writeDB("542", "새마을식당", "store", "mon", "14", "9", "22", "26", "010-7677-6804", "광교동", "15000");
//        writeDB("543", "이디야커피", "cafe", "wen", "13", "28", "21", "26", "010-3083-8189", "매탄동", "15000");
//        writeDB("544", "GS25", "cooking", "fri", "4", "55", "23", "5", "010-9231-8008", "원천동", "19000");
//        writeDB("545", "올리브영", "delivery", "sun", "18", "49", "19", "5", "010-6670-1379", "태장동", "10000");
//        writeDB("546", "파리바게트", "serving", "thu", "23", "43", "24", "31", "010-2780-0514", "태장동", "17000");
//        writeDB("547", "BBQ치킨", "delivery", "fri", "15", "10", "19", "22", "010-6302-4025", "이의동", "20000");
//        writeDB("548", "CU편의점", "cafe", "mon", "20", "23", "22", "25", "010-3685-9022", "원천동", "9000");
//        writeDB("549", "파리바게트", "delivery", "sat", "13", "52", "18", "30", "010-9372-5306", "이의동", "12000");
//        writeDB("550", "새마을식당", "cafe", "thu", "2", "37", "9", "16", "010-2472-7156", "신동", "9000");
//        writeDB("551", "올리브영", "cafe", "sat", "13", "11", "16", "40", "010-8357-5552", "영통동", "15000");
//        writeDB("552", "새마을식당", "cooking", "thu", "21", "34", "22", "44", "010-2815-8063", "원천동", "9000");
//        writeDB("553", "올리브영", "cafe", "mon", "15", "19", "21", "3", "010-1248-8780", "하동", "15000");
//        writeDB("554", "새마을식당", "serving", "tue", "14", "2", "23", "53", "010-3167-3100", "하동", "17000");
//        writeDB("555", "스타벅스", "serving", "wen", "9", "48", "20", "59", "010-1642-6724", "광교동", "14000");
//        writeDB("556", "새마을식당", "serving", "wen", "15", "36", "23", "48", "010-2196-9559", "신동", "18000");
//        writeDB("557", "BBQ치킨", "cafe", "sun", "11", "11", "21", "51", "010-4541-6971", "영통동", "14000");
//        writeDB("558", "파리바게트", "store", "mon", "17", "7", "24", "42", "010-5974-5066", "신동", "12000");
//        writeDB("559", "파리바게트", "cooking", "tue", "15", "16", "21", "16", "010-0884-8354", "하동", "17000");
//        writeDB("560", "파리바게트", "store", "thu", "15", "55", "22", "2", "010-8306-7733", "이의동", "15000");
//        writeDB("561", "올리브영", "cafe", "sun", "2", "46", "15", "35", "010-7007-7471", "태장동", "18000");
//        writeDB("562", "이디야커피", "serving", "wen", "13", "52", "23", "6", "010-3345-4787", "영통동", "16000");
//        writeDB("563", "스타벅스", "cooking", "sat", "9", "24", "15", "50", "010-2052-6324", "원천동", "18000");
//        writeDB("564", "이디야커피", "store", "wen", "12", "45", "24", "22", "010-7004-5627", "태장동", "16000");
//        writeDB("565", "이디야커피", "serving", "sun", "4", "20", "15", "56", "010-1567-6667", "원천동", "18000");
//        writeDB("566", "이디야커피", "delivery", "mon", "17", "2", "19", "14", "010-8190-4510", "태장동", "16000");
//        writeDB("567", "BBQ치킨", "store", "thu", "23", "7", "24", "18", "010-6800-9356", "하동", "9000");
//        writeDB("568", "올리브영", "delivery", "thu", "8", "7", "23", "56", "010-4648-0087", "매탄동", "14000");
//        writeDB("569", "새마을식당", "cooking", "sun", "10", "53", "24", "42", "010-9269-6552", "광교동", "13000");
//        writeDB("570", "BBQ치킨", "cafe", "mon", "22", "11", "23", "17", "010-7128-8708", "이의동", "20000");
//        writeDB("571", "GS25", "serving", "tue", "10", "35", "17", "19", "010-8979-9319", "이의동", "14000");
//        writeDB("572", "CU편의점", "delivery", "fri", "6", "32", "15", "28", "010-8975-7881", "신동", "11000");
//        writeDB("573", "올리브영", "serving", "fri", "4", "49", "8", "58", "010-1678-3432", "하동", "13000");
//        writeDB("574", "파리바게트", "cooking", "thu", "23", "57", "24", "0", "010-9838-4536", "신동", "14000");
//        writeDB("575", "올리브영", "cafe", "sun", "1", "18", "4", "50", "010-3664-4422", "매탄동", "12000");
//        writeDB("576", "새마을식당", "cafe", "tue", "16", "49", "23", "56", "010-5486-7444", "광교동", "11000");
//        writeDB("577", "새마을식당", "serving", "tue", "3", "41", "14", "38", "010-1378-8525", "망포동", "20000");
//        writeDB("578", "BBQ치킨", "store", "tue", "13", "9", "22", "6", "010-1387-6172", "광교동", "15000");
//        writeDB("579", "이디야커피", "store", "sun", "0", "50", "10", "42", "010-3108-6071", "매탄동", "20000");
//        writeDB("580", "CU편의점", "store", "thu", "9", "37", "22", "49", "010-7685-4327", "망포동", "13000");
//        writeDB("581", "새마을식당", "delivery", "mon", "11", "13", "23", "32", "010-3190-5071", "하동", "18000");
//        writeDB("582", "스타벅스", "cooking", "wen", "17", "6", "24", "21", "010-3007-5157", "매탄동", "16000");
//        writeDB("583", "BBQ치킨", "serving", "sun", "18", "17", "21", "19", "010-4921-6162", "태장동", "12000");
//        writeDB("584", "스타벅스", "delivery", "fri", "4", "46", "8", "13", "010-4322-5686", "태장동", "12000");
//        writeDB("585", "CU편의점", "cooking", "sat", "1", "1", "24", "33", "010-1274-8528", "매탄동", "15000");
//        writeDB("586", "BBQ치킨", "cafe", "sat", "19", "39", "23", "35", "010-0371-9980", "신동", "9000");
//        writeDB("587", "스타벅스", "delivery", "sat", "6", "9", "21", "40", "010-4714-5016", "영통동", "18000");
//        writeDB("588", "파리바게트", "delivery", "thu", "18", "5", "23", "27", "010-5511-3763", "이의동", "12000");
//        writeDB("589", "이디야커피", "store", "wen", "4", "42", "14", "5", "010-8579-4250", "신동", "12000");
//        writeDB("590", "GS25", "serving", "mon", "10", "36", "12", "36", "010-1024-9161", "광교동", "19000");
//        writeDB("591", "파리바게트", "store", "thu", "11", "45", "20", "3", "010-0534-8212", "망포동", "13000");
//        writeDB("592", "BBQ치킨", "cafe", "sat", "11", "13", "15", "57", "010-5901-0667", "망포동", "10000");
//        writeDB("593", "CU편의점", "serving", "tue", "10", "5", "12", "34", "010-4843-1940", "영통동", "13000");
//        writeDB("594", "올리브영", "store", "sat", "20", "5", "21", "47", "010-0430-8842", "태장동", "14000");
//        writeDB("595", "스타벅스", "cooking", "thu", "0", "54", "12", "19", "010-0591-1319", "신동", "15000");
//        writeDB("596", "BBQ치킨", "serving", "fri", "3", "27", "16", "25", "010-4314-5013", "원천동", "14000");
//        writeDB("597", "GS25", "cafe", "wen", "5", "0", "24", "14", "010-6023-9492", "영통동", "12000");
//        writeDB("598", "이디야커피", "delivery", "wen", "1", "50", "12", "0", "010-9881-4995", "태장동", "12000");
//        writeDB("599", "GS25", "cooking", "mon", "3", "58", "12", "53", "010-7422-3351", "하동", "20000");
//        writeDB("600", "이디야커피", "serving", "wen", "1", "2", "6", "24", "010-0140-6616", "태장동", "10000");
//        writeDB("601", "CU편의점", "store", "mon", "8", "17", "21", "12", "010-9406-2139", "매탄동", "18000");
//        writeDB("602", "새마을식당", "serving", "wen", "15", "52", "19", "34", "010-2727-4438", "이의동", "10000");
//        writeDB("603", "스타벅스", "cooking", "mon", "23", "25", "24", "17", "010-6488-3004", "매탄동", "18000");
//        writeDB("604", "파리바게트", "cooking", "thu", "21", "38", "24", "50", "010-0202-2061", "매탄동", "10000");
//        writeDB("605", "올리브영", "store", "sat", "4", "24", "15", "21", "010-2685-9425", "영통동", "17000");
//        writeDB("606", "이디야커피", "serving", "sun", "8", "3", "21", "51", "010-9423-6887", "매탄동", "18000");
//        writeDB("607", "CU편의점", "cafe", "thu", "0", "22", "9", "10", "010-3079-5322", "광교동", "20000");
//        writeDB("608", "BBQ치킨", "delivery", "fri", "1", "56", "2", "45", "010-9293-7704", "광교동", "11000");
//        writeDB("609", "파리바게트", "delivery", "tue", "15", "40", "16", "23", "010-8460-6406", "영통동", "10000");
//        writeDB("610", "새마을식당", "delivery", "fri", "22", "43", "23", "54", "010-6490-9679", "광교동", "12000");
//        writeDB("611", "BBQ치킨", "serving", "sun", "6", "22", "17", "16", "010-8396-1400", "태장동", "18000");
//        writeDB("612", "이디야커피", "cooking", "fri", "11", "22", "22", "8", "010-5581-3257", "망포동", "14000");
//        writeDB("613", "이디야커피", "cafe", "sat", "11", "41", "23", "39", "010-4924-0945", "원천동", "17000");
//        writeDB("614", "이디야커피", "store", "sun", "9", "17", "23", "57", "010-2951-8495", "신동", "20000");
//        writeDB("615", "CU편의점", "store", "wen", "22", "41", "24", "40", "010-3987-2060", "이의동", "9000");
//        writeDB("616", "GS25", "cafe", "sun", "22", "3", "23", "32", "010-4098-2395", "이의동", "11000");
//        writeDB("617", "파리바게트", "cafe", "sat", "18", "32", "22", "26", "010-7381-4414", "원천동", "12000");
//        writeDB("618", "BBQ치킨", "store", "wen", "5", "33", "8", "9", "010-2880-5112", "하동", "12000");
//        writeDB("619", "GS25", "store", "thu", "5", "44", "16", "15", "010-7653-5106", "태장동", "11000");
//        writeDB("620", "새마을식당", "cafe", "fri", "19", "2", "22", "12", "010-2245-1779", "신동", "20000");
//        writeDB("621", "새마을식당", "cooking", "thu", "2", "49", "15", "46", "010-0382-8517", "영통동", "15000");
//        writeDB("622", "이디야커피", "cafe", "sat", "6", "24", "14", "46", "010-3132-5632", "원천동", "17000");
//        writeDB("623", "스타벅스", "delivery", "wen", "18", "48", "23", "19", "010-2007-5826", "태장동", "14000");
//        writeDB("624", "BBQ치킨", "serving", "sun", "4", "7", "23", "11", "010-1445-9160", "하동", "16000");
//        writeDB("625", "스타벅스", "serving", "tue", "0", "1", "7", "46", "010-6007-0627", "망포동", "14000");
//        writeDB("626", "이디야커피", "serving", "tue", "20", "0", "22", "25", "010-6088-5929", "원천동", "19000");
//        writeDB("627", "새마을식당", "serving", "tue", "6", "47", "9", "51", "010-1304-7003", "광교동", "10000");
//        writeDB("628", "새마을식당", "delivery", "mon", "20", "35", "22", "33", "010-0188-9044", "하동", "13000");
//        writeDB("629", "파리바게트", "store", "tue", "0", "50", "23", "45", "010-6421-6086", "영통동", "12000");
//        writeDB("630", "BBQ치킨", "cafe", "thu", "20", "11", "22", "52", "010-1990-5914", "매탄동", "17000");
//        writeDB("631", "스타벅스", "store", "wen", "5", "11", "17", "2", "010-3625-3991", "망포동", "19000");
//        writeDB("632", "파리바게트", "cooking", "sun", "16", "32", "22", "14", "010-6534-1911", "광교동", "18000");
//        writeDB("633", "파리바게트", "cooking", "sat", "20", "28", "23", "18", "010-1257-5922", "광교동", "19000");
//        writeDB("634", "BBQ치킨", "cooking", "tue", "11", "49", "15", "15", "010-9856-6056", "하동", "14000");
//        writeDB("635", "올리브영", "cafe", "wen", "20", "51", "24", "2", "010-3874-1836", "망포동", "11000");
//        writeDB("636", "파리바게트", "cafe", "sun", "14", "1", "16", "2", "010-8993-2693", "신동", "20000");
//        writeDB("637", "CU편의점", "store", "fri", "1", "32", "13", "18", "010-6154-3020", "하동", "17000");
//        writeDB("638", "BBQ치킨", "delivery", "tue", "6", "17", "22", "48", "010-8877-8986", "매탄동", "13000");
//        writeDB("639", "GS25", "cooking", "sat", "9", "4", "16", "31", "010-5151-9255", "하동", "20000");
//        writeDB("640", "파리바게트", "cooking", "tue", "15", "47", "18", "7", "010-8100-5810", "하동", "14000");
//        writeDB("641", "올리브영", "store", "sat", "23", "54", "24", "58", "010-8421-2566", "태장동", "20000");
//        writeDB("642", "올리브영", "store", "wen", "10", "48", "23", "30", "010-0591-9591", "망포동", "20000");
//        writeDB("643", "BBQ치킨", "store", "thu", "22", "59", "24", "48", "010-4906-4947", "이의동", "13000");
//        writeDB("644", "이디야커피", "store", "tue", "3", "22", "24", "25", "010-3467-3635", "영통동", "11000");
//        writeDB("645", "올리브영", "cafe", "sat", "3", "45", "14", "29", "010-5516-3335", "태장동", "18000");
//        writeDB("646", "GS25", "cooking", "thu", "22", "23", "24", "41", "010-9960-8894", "망포동", "14000");
//        writeDB("647", "새마을식당", "serving", "fri", "22", "54", "23", "21", "010-4594-5430", "망포동", "19000");
//        writeDB("648", "스타벅스", "cooking", "sat", "7", "13", "11", "49", "010-3714-2130", "망포동", "13000");
//        writeDB("649", "새마을식당", "cooking", "mon", "17", "41", "21", "23", "010-3798-4169", "태장동", "15000");
//        writeDB("650", "GS25", "store", "sat", "16", "12", "21", "35", "010-6446-0601", "영통동", "12000");
//        writeDB("651", "새마을식당", "delivery", "thu", "4", "36", "15", "5", "010-7739-2399", "하동", "18000");
//        writeDB("652", "파리바게트", "delivery", "wen", "12", "58", "20", "30", "010-5596-3165", "이의동", "12000");
//        writeDB("653", "CU편의점", "cooking", "sat", "22", "4", "24", "54", "010-4176-3352", "광교동", "13000");
//        writeDB("654", "GS25", "serving", "mon", "19", "41", "23", "43", "010-2969-0855", "영통동", "18000");
//        writeDB("655", "GS25", "store", "thu", "11", "8", "17", "42", "010-2581-8385", "하동", "13000");
//        writeDB("656", "GS25", "cooking", "mon", "6", "0", "13", "42", "010-6012-4303", "광교동", "16000");
//        writeDB("657", "이디야커피", "serving", "wen", "0", "55", "12", "3", "010-1050-5850", "하동", "12000");
//        writeDB("658", "BBQ치킨", "cooking", "fri", "21", "58", "22", "43", "010-8405-2132", "망포동", "14000");
//        writeDB("659", "파리바게트", "store", "tue", "21", "49", "23", "58", "010-7110-7249", "망포동", "12000");
//        writeDB("660", "이디야커피", "serving", "thu", "19", "54", "22", "53", "010-2464-6075", "광교동", "15000");
//        writeDB("661", "스타벅스", "serving", "thu", "23", "28", "24", "40", "010-2500-2528", "원천동", "20000");
//        writeDB("662", "스타벅스", "store", "wen", "21", "33", "23", "5", "010-7046-0393", "태장동", "20000");
//        writeDB("663", "BBQ치킨", "store", "sun", "19", "40", "21", "57", "010-9877-7269", "하동", "17000");
//        writeDB("664", "스타벅스", "serving", "sun", "22", "35", "23", "52", "010-7409-7821", "광교동", "14000");
//        writeDB("665", "파리바게트", "serving", "thu", "17", "53", "19", "47", "010-1863-5626", "신동", "11000");
//        writeDB("666", "새마을식당", "cooking", "wen", "16", "54", "17", "44", "010-9347-3498", "태장동", "16000");
//        writeDB("667", "올리브영", "store", "sat", "7", "42", "23", "53", "010-5905-9525", "하동", "11000");
//        writeDB("668", "BBQ치킨", "serving", "thu", "11", "16", "15", "58", "010-1858-7749", "매탄동", "20000");
//        writeDB("669", "올리브영", "cooking", "tue", "17", "22", "22", "16", "010-2436-1341", "원천동", "20000");
//        writeDB("670", "올리브영", "cafe", "mon", "22", "59", "24", "38", "010-0956-0690", "이의동", "9000");
//        writeDB("671", "CU편의점", "serving", "wen", "21", "26", "24", "24", "010-9464-1273", "태장동", "13000");
//        writeDB("672", "BBQ치킨", "cooking", "sat", "10", "16", "15", "41", "010-4567-4958", "이의동", "17000");
//        writeDB("673", "GS25", "cafe", "fri", "22", "16", "24", "40", "010-5349-7383", "영통동", "13000");
//        writeDB("674", "올리브영", "cafe", "sun", "21", "18", "23", "48", "010-6324-8216", "신동", "20000");
//        writeDB("675", "올리브영", "cooking", "tue", "1", "27", "20", "39", "010-5282-2772", "태장동", "14000");
//        writeDB("676", "이디야커피", "serving", "sat", "22", "43", "23", "8", "010-4377-4362", "원천동", "19000");
//        writeDB("677", "GS25", "store", "mon", "7", "51", "11", "34", "010-6504-3332", "광교동", "18000");
//        writeDB("678", "파리바게트", "delivery", "fri", "22", "2", "23", "12", "010-9225-1770", "이의동", "14000");
//        writeDB("679", "GS25", "store", "sat", "16", "28", "22", "30", "010-3964-4557", "광교동", "15000");
//        writeDB("680", "BBQ치킨", "cafe", "sat", "13", "29", "21", "34", "010-2559-2050", "원천동", "13000");
//        writeDB("681", "이디야커피", "cafe", "tue", "12", "54", "15", "14", "010-6597-2479", "망포동", "13000");
//        writeDB("682", "새마을식당", "store", "wen", "20", "43", "24", "54", "010-5570-6968", "하동", "15000");
//        writeDB("683", "이디야커피", "cafe", "sun", "12", "39", "17", "29", "010-1431-8012", "매탄동", "12000");
//        writeDB("684", "새마을식당", "serving", "sat", "16", "59", "19", "49", "010-9471-6482", "신동", "9000");
//        writeDB("685", "GS25", "delivery", "wen", "16", "17", "23", "6", "010-8496-7578", "이의동", "19000");
//        writeDB("686", "새마을식당", "cafe", "tue", "8", "16", "12", "28", "010-4978-0858", "하동", "9000");
//        writeDB("687", "CU편의점", "cafe", "thu", "10", "6", "20", "55", "010-1047-1704", "매탄동", "19000");
//        writeDB("688", "GS25", "cafe", "fri", "17", "31", "24", "21", "010-6958-0339", "원천동", "17000");
//        writeDB("689", "스타벅스", "cooking", "mon", "4", "46", "22", "59", "010-6306-0699", "매탄동", "19000");
//        writeDB("690", "스타벅스", "cooking", "tue", "21", "25", "23", "6", "010-6665-3004", "신동", "9000");
//        writeDB("691", "스타벅스", "store", "mon", "5", "1", "21", "5", "010-0710-5271", "망포동", "18000");
//        writeDB("692", "BBQ치킨", "store", "fri", "17", "42", "23", "51", "010-7471-0420", "태장동", "19000");
//        writeDB("693", "올리브영", "cooking", "wen", "7", "43", "11", "31", "010-2453-9469", "태장동", "19000");
//        writeDB("694", "스타벅스", "serving", "mon", "14", "38", "22", "55", "010-0608-2057", "이의동", "9000");
//        writeDB("695", "파리바게트", "cooking", "fri", "18", "47", "21", "25", "010-3470-1093", "하동", "20000");
//        writeDB("696", "새마을식당", "cafe", "wen", "21", "50", "23", "49", "010-1511-7967", "망포동", "11000");
//        writeDB("697", "GS25", "delivery", "sat", "17", "41", "20", "51", "010-1562-1837", "신동", "11000");
//        writeDB("698", "CU편의점", "serving", "fri", "20", "20", "21", "38", "010-4140-2761", "태장동", "20000");
//        writeDB("699", "BBQ치킨", "delivery", "sat", "9", "39", "15", "51", "010-9924-7423", "태장동", "9000");
//        writeDB("700", "CU편의점", "delivery", "tue", "16", "30", "24", "34", "010-3868-1695", "매탄동", "11000");
//        writeDB("701", "파리바게트", "delivery", "tue", "18", "47", "22", "6", "010-4159-6693", "광교동", "19000");
//        writeDB("702", "CU편의점", "delivery", "thu", "4", "33", "18", "0", "010-6108-2907", "매탄동", "19000");
//        writeDB("703", "GS25", "cooking", "sun", "21", "20", "23", "2", "010-6624-7838", "태장동", "18000");
//        writeDB("704", "파리바게트", "serving", "mon", "16", "13", "18", "25", "010-2744-4895", "광교동", "15000");
//        writeDB("705", "이디야커피", "store", "sun", "20", "4", "22", "22", "010-6278-2041", "매탄동", "10000");
//        writeDB("706", "새마을식당", "delivery", "sun", "21", "33", "22", "21", "010-1273-3247", "하동", "20000");
//        writeDB("707", "BBQ치킨", "store", "thu", "14", "45", "19", "37", "010-8797-8960", "원천동", "18000");
//        writeDB("708", "BBQ치킨", "delivery", "sun", "4", "18", "15", "50", "010-9144-7694", "광교동", "12000");
//        writeDB("709", "CU편의점", "serving", "wen", "7", "38", "20", "44", "010-0759-0065", "망포동", "13000");
//        writeDB("710", "올리브영", "delivery", "sun", "16", "43", "18", "12", "010-2444-9399", "망포동", "19000");
//        writeDB("711", "BBQ치킨", "store", "fri", "10", "40", "12", "37", "010-1706-8239", "광교동", "19000");
//        writeDB("712", "GS25", "cafe", "sun", "22", "59", "24", "44", "010-7056-8508", "하동", "14000");
//        writeDB("713", "BBQ치킨", "cafe", "mon", "17", "47", "19", "17", "010-0244-7965", "태장동", "14000");
//        writeDB("714", "올리브영", "serving", "sun", "13", "9", "17", "24", "010-1560-6305", "망포동", "9000");
//        writeDB("715", "올리브영", "delivery", "tue", "16", "57", "19", "0", "010-2705-6816", "이의동", "14000");
//        writeDB("716", "스타벅스", "store", "thu", "7", "48", "14", "45", "010-9740-5550", "광교동", "9000");
//        writeDB("717", "이디야커피", "store", "sat", "13", "35", "19", "40", "010-8669-1788", "신동", "12000");
//        writeDB("718", "BBQ치킨", "cooking", "thu", "16", "15", "17", "45", "010-2082-3987", "신동", "17000");
//        writeDB("719", "스타벅스", "delivery", "tue", "22", "9", "23", "10", "010-8447-9158", "신동", "13000");
//        writeDB("720", "스타벅스", "delivery", "wen", "20", "13", "23", "54", "010-1804-1605", "하동", "16000");
//        writeDB("721", "GS25", "delivery", "thu", "20", "8", "21", "22", "010-7440-4247", "하동", "10000");
//        writeDB("722", "새마을식당", "store", "sun", "22", "34", "24", "12", "010-8670-7020", "광교동", "11000");
//        writeDB("723", "GS25", "store", "thu", "15", "9", "24", "8", "010-3841-0042", "이의동", "19000");
//        writeDB("724", "BBQ치킨", "cafe", "thu", "14", "45", "19", "31", "010-5405-3706", "신동", "20000");
//        writeDB("725", "GS25", "store", "sun", "5", "4", "16", "2", "010-4641-7529", "이의동", "16000");
//        writeDB("726", "파리바게트", "delivery", "thu", "21", "51", "23", "16", "010-3728-8411", "영통동", "12000");
//        writeDB("727", "스타벅스", "cooking", "wen", "19", "43", "23", "46", "010-8195-9850", "영통동", "13000");
//        writeDB("728", "BBQ치킨", "store", "sun", "11", "18", "13", "43", "010-7372-9783", "이의동", "12000");
//        writeDB("729", "BBQ치킨", "cafe", "thu", "4", "7", "11", "8", "010-6833-3564", "광교동", "18000");
//        writeDB("730", "올리브영", "cooking", "thu", "12", "19", "20", "29", "010-7627-0914", "망포동", "12000");
//        writeDB("731", "이디야커피", "cooking", "wen", "14", "43", "18", "7", "010-9353-1195", "이의동", "20000");
//        writeDB("732", "이디야커피", "serving", "mon", "2", "37", "24", "27", "010-9567-4522", "하동", "17000");
//        writeDB("733", "이디야커피", "cafe", "thu", "8", "20", "21", "48", "010-0573-5959", "영통동", "11000");
//        writeDB("734", "BBQ치킨", "serving", "sat", "15", "42", "19", "23", "010-6116-0214", "신동", "11000");
//        writeDB("735", "CU편의점", "cooking", "sun", "5", "21", "7", "39", "010-9076-4730", "매탄동", "9000");
//        writeDB("736", "GS25", "delivery", "thu", "0", "50", "23", "6", "010-6434-2715", "광교동", "19000");
//        writeDB("737", "GS25", "cooking", "sun", "9", "25", "13", "34", "010-1246-8214", "태장동", "13000");
//        writeDB("738", "BBQ치킨", "cafe", "wen", "14", "52", "21", "2", "010-0496-5367", "매탄동", "20000");
//        writeDB("739", "새마을식당", "delivery", "tue", "5", "4", "23", "34", "010-9387-9020", "이의동", "17000");
//        writeDB("740", "새마을식당", "cooking", "wen", "13", "1", "15", "5", "010-0914-1341", "매탄동", "12000");
//        writeDB("741", "스타벅스", "cooking", "sun", "20", "51", "22", "22", "010-2761-0015", "광교동", "12000");
//        writeDB("742", "올리브영", "serving", "tue", "4", "39", "8", "33", "010-8937-6978", "하동", "17000");
//        writeDB("743", "올리브영", "delivery", "tue", "1", "50", "19", "4", "010-0779-1409", "망포동", "14000");
//        writeDB("744", "파리바게트", "delivery", "sat", "7", "15", "19", "33", "010-9014-9200", "태장동", "19000");
//        writeDB("745", "올리브영", "delivery", "tue", "23", "6", "24", "51", "010-4293-9579", "하동", "20000");
//        writeDB("746", "CU편의점", "store", "fri", "4", "21", "11", "0", "010-6213-0996", "하동", "14000");
//        writeDB("747", "BBQ치킨", "store", "sun", "5", "33", "9", "45", "010-8356-7687", "영통동", "9000");
//        writeDB("748", "파리바게트", "serving", "thu", "1", "19", "20", "31", "010-6147-8025", "영통동", "11000");
//        writeDB("749", "새마을식당", "cafe", "sun", "14", "22", "18", "35", "010-8399-8206", "태장동", "19000");
//        writeDB("750", "스타벅스", "delivery", "tue", "6", "38", "24", "36", "010-4296-0694", "신동", "18000");
//        writeDB("751", "BBQ치킨", "delivery", "tue", "19", "47", "24", "23", "010-9986-9336", "원천동", "14000");
//        writeDB("752", "올리브영", "cooking", "mon", "11", "51", "21", "6", "010-4690-7498", "이의동", "20000");
//        writeDB("753", "올리브영", "delivery", "mon", "0", "41", "15", "47", "010-0091-9349", "하동", "20000");
//        writeDB("754", "CU편의점", "store", "tue", "20", "15", "24", "10", "010-2806-3185", "이의동", "20000");
//        writeDB("755", "이디야커피", "cooking", "fri", "0", "3", "22", "9", "010-4187-9997", "태장동", "11000");
//        writeDB("756", "BBQ치킨", "store", "mon", "16", "11", "18", "52", "010-0550-3514", "신동", "20000");
//        writeDB("757", "새마을식당", "serving", "sun", "21", "21", "23", "23", "010-2636-6064", "망포동", "13000");
//        writeDB("758", "파리바게트", "cafe", "mon", "19", "4", "24", "41", "010-9355-4095", "망포동", "12000");
//        writeDB("759", "새마을식당", "cafe", "mon", "11", "16", "20", "40", "010-7080-2381", "망포동", "19000");
//        writeDB("760", "새마을식당", "store", "sat", "11", "36", "14", "54", "010-3025-3994", "원천동", "17000");
//        writeDB("761", "GS25", "cooking", "fri", "10", "1", "20", "14", "010-1045-6510", "태장동", "11000");
//        writeDB("762", "파리바게트", "delivery", "fri", "20", "8", "23", "5", "010-1659-9345", "망포동", "12000");
//        writeDB("763", "이디야커피", "cafe", "sat", "7", "16", "15", "16", "010-7653-6664", "이의동", "18000");
//        writeDB("764", "파리바게트", "delivery", "tue", "11", "46", "21", "49", "010-2601-9797", "신동", "9000");
//        writeDB("765", "CU편의점", "delivery", "fri", "18", "59", "24", "43", "010-7625-2120", "영통동", "15000");
//        writeDB("766", "CU편의점", "store", "sat", "0", "13", "24", "23", "010-1931-4029", "신동", "9000");
//        writeDB("767", "이디야커피", "cafe", "tue", "8", "24", "16", "11", "010-5404-6648", "원천동", "19000");
//        writeDB("768", "새마을식당", "cafe", "thu", "19", "48", "22", "16", "010-0029-9486", "매탄동", "10000");
//        writeDB("769", "이디야커피", "store", "mon", "23", "15", "24", "2", "010-8609-8347", "하동", "17000");
//        writeDB("770", "파리바게트", "serving", "sat", "6", "34", "19", "27", "010-7417-1936", "망포동", "19000");
//        writeDB("771", "새마을식당", "cafe", "tue", "11", "5", "14", "23", "010-0591-5688", "하동", "16000");
//        writeDB("772", "스타벅스", "cooking", "wen", "21", "33", "22", "56", "010-0713-3748", "하동", "13000");
//        writeDB("773", "이디야커피", "cafe", "fri", "6", "21", "11", "26", "010-8692-6611", "하동", "11000");
//        writeDB("774", "스타벅스", "cafe", "tue", "21", "43", "22", "59", "010-7521-0466", "하동", "10000");
//        writeDB("775", "GS25", "store", "mon", "14", "49", "15", "51", "010-9352-4717", "하동", "15000");
//        writeDB("776", "BBQ치킨", "cooking", "thu", "16", "10", "22", "45", "010-9639-0643", "망포동", "17000");
//        writeDB("777", "파리바게트", "delivery", "wen", "6", "18", "14", "41", "010-0985-4923", "하동", "11000");
//        writeDB("778", "CU편의점", "store", "mon", "16", "33", "21", "53", "010-7043-9496", "영통동", "14000");
//        writeDB("779", "CU편의점", "delivery", "sun", "6", "53", "20", "49", "010-6535-4469", "하동", "15000");
//        writeDB("780", "GS25", "cafe", "sun", "20", "31", "24", "8", "010-3719-7000", "태장동", "17000");
//        writeDB("781", "GS25", "cooking", "sat", "14", "48", "15", "44", "010-3832-8055", "망포동", "14000");
//        writeDB("782", "새마을식당", "delivery", "sun", "11", "12", "20", "31", "010-7940-2007", "이의동", "19000");
//        writeDB("783", "스타벅스", "store", "thu", "22", "39", "23", "38", "010-4617-5985", "매탄동", "9000");
//        writeDB("784", "올리브영", "serving", "mon", "20", "29", "23", "11", "010-7125-5925", "신동", "14000");
//        writeDB("785", "올리브영", "delivery", "tue", "18", "43", "24", "42", "010-5997-2337", "원천동", "9000");
//        writeDB("786", "스타벅스", "cooking", "fri", "12", "57", "23", "18", "010-1704-3576", "원천동", "14000");
//        writeDB("787", "새마을식당", "delivery", "fri", "18", "4", "24", "31", "010-2701-8875", "신동", "16000");
//        writeDB("788", "새마을식당", "cooking", "sun", "18", "9", "21", "23", "010-8272-4900", "영통동", "12000");
//        writeDB("789", "이디야커피", "serving", "sat", "2", "55", "9", "50", "010-3023-6003", "신동", "18000");
//        writeDB("790", "BBQ치킨", "store", "sat", "21", "7", "24", "53", "010-2992-9743", "매탄동", "14000");
//        writeDB("791", "CU편의점", "store", "wen", "21", "49", "23", "0", "010-9005-2835", "태장동", "13000");
//        writeDB("792", "GS25", "store", "mon", "7", "35", "17", "37", "010-0016-8887", "이의동", "10000");
//        writeDB("793", "이디야커피", "delivery", "tue", "22", "34", "23", "3", "010-6177-9481", "매탄동", "9000");
//        writeDB("794", "새마을식당", "cafe", "wen", "21", "25", "23", "31", "010-3834-3549", "영통동", "10000");
//        writeDB("795", "새마을식당", "serving", "mon", "9", "22", "11", "54", "010-0889-9194", "이의동", "13000");
//        writeDB("796", "새마을식당", "store", "thu", "12", "54", "23", "17", "010-6306-3347", "망포동", "10000");
//        writeDB("797", "이디야커피", "cafe", "sat", "17", "20", "21", "47", "010-0945-3068", "광교동", "20000");
//        writeDB("798", "이디야커피", "cooking", "sat", "2", "23", "5", "2", "010-6052-9732", "태장동", "17000");
//        writeDB("799", "이디야커피", "serving", "tue", "2", "14", "9", "39", "010-2111-5006", "신동", "10000");
//        writeDB("800", "새마을식당", "delivery", "fri", "10", "8", "16", "52", "010-6207-2878", "이의동", "19000");
//        writeDB("801", "올리브영", "cooking", "mon", "14", "23", "20", "10", "010-4135-0942", "신동", "13000");
//        writeDB("802", "BBQ치킨", "cafe", "sun", "21", "15", "23", "39", "010-8246-7108", "원천동", "15000");
//        writeDB("803", "새마을식당", "cooking", "thu", "20", "44", "22", "8", "010-5590-7559", "이의동", "16000");
//        writeDB("804", "GS25", "cafe", "wen", "13", "50", "23", "1", "010-2217-9918", "망포동", "14000");
//        writeDB("805", "GS25", "cafe", "thu", "0", "32", "18", "15", "010-5654-2577", "하동", "11000");
//        writeDB("806", "올리브영", "serving", "thu", "10", "39", "14", "38", "010-2951-7672", "이의동", "19000");
//        writeDB("807", "CU편의점", "cooking", "thu", "21", "12", "22", "25", "010-4124-6782", "광교동", "10000");
//        writeDB("808", "이디야커피", "cafe", "sun", "13", "34", "22", "44", "010-6183-4013", "광교동", "11000");
//        writeDB("809", "스타벅스", "store", "fri", "20", "20", "24", "2", "010-8052-5941", "매탄동", "9000");
//        writeDB("810", "CU편의점", "cafe", "sat", "21", "8", "24", "53", "010-8934-6335", "신동", "19000");
//        writeDB("811", "스타벅스", "store", "fri", "10", "41", "13", "12", "010-8457-4863", "이의동", "11000");
//        writeDB("812", "스타벅스", "cafe", "sun", "16", "45", "19", "17", "010-6548-8280", "원천동", "20000");
//        writeDB("813", "이디야커피", "delivery", "wen", "22", "11", "23", "51", "010-4310-1744", "원천동", "15000");
//        writeDB("814", "이디야커피", "cooking", "fri", "4", "0", "16", "5", "010-8192-0839", "매탄동", "9000");
//        writeDB("815", "올리브영", "cooking", "thu", "11", "1", "18", "24", "010-9864-6646", "광교동", "9000");
//        writeDB("816", "BBQ치킨", "cooking", "wen", "2", "36", "10", "40", "010-4546-9261", "원천동", "14000");
//        writeDB("817", "CU편의점", "serving", "sun", "20", "22", "21", "14", "010-3879-8425", "하동", "10000");
//        writeDB("818", "GS25", "cafe", "thu", "13", "36", "16", "14", "010-5247-9271", "광교동", "13000");
//        writeDB("819", "이디야커피", "store", "sun", "0", "9", "9", "17", "010-3926-9789", "태장동", "19000");
//        writeDB("820", "스타벅스", "store", "tue", "4", "16", "23", "36", "010-1590-4950", "광교동", "13000");
//        writeDB("821", "BBQ치킨", "delivery", "mon", "6", "1", "15", "3", "010-4362-5320", "광교동", "14000");
//        writeDB("822", "이디야커피", "serving", "mon", "0", "19", "19", "35", "010-9794-1340", "광교동", "12000");
//        writeDB("823", "올리브영", "cooking", "fri", "21", "18", "24", "46", "010-8445-2312", "하동", "12000");
//        writeDB("824", "CU편의점", "delivery", "sun", "23", "1", "24", "18", "010-6510-2292", "영통동", "16000");
//        writeDB("825", "이디야커피", "delivery", "tue", "23", "32", "24", "32", "010-3291-4510", "원천동", "16000");
//        writeDB("826", "이디야커피", "store", "fri", "14", "46", "19", "48", "010-8903-7262", "이의동", "20000");
//        writeDB("827", "GS25", "cooking", "fri", "16", "32", "18", "42", "010-7592-5566", "이의동", "15000");
//        writeDB("828", "새마을식당", "cooking", "sun", "5", "4", "12", "39", "010-0317-2827", "하동", "11000");
//        writeDB("829", "파리바게트", "cooking", "sun", "1", "14", "12", "41", "010-4604-6271", "망포동", "12000");
//        writeDB("830", "GS25", "serving", "thu", "14", "23", "18", "15", "010-3265-2780", "하동", "11000");
//        writeDB("831", "파리바게트", "cooking", "wen", "23", "42", "24", "45", "010-2158-1565", "원천동", "17000");
//        writeDB("832", "스타벅스", "cooking", "tue", "8", "17", "9", "7", "010-4627-9101", "신동", "16000");
//        writeDB("833", "GS25", "delivery", "thu", "0", "10", "2", "14", "010-0374-8567", "광교동", "19000");
//        writeDB("834", "올리브영", "delivery", "thu", "11", "48", "22", "15", "010-2049-0737", "원천동", "11000");
//        writeDB("835", "이디야커피", "cooking", "sat", "19", "9", "23", "32", "010-9258-0610", "태장동", "16000");
//        writeDB("836", "올리브영", "delivery", "sat", "22", "23", "23", "55", "010-7314-6925", "망포동", "17000");
//        writeDB("837", "스타벅스", "cooking", "sat", "11", "7", "14", "47", "010-6960-4107", "광교동", "18000");
//        writeDB("838", "이디야커피", "cafe", "fri", "2", "26", "17", "5", "010-8728-2660", "원천동", "11000");
//        writeDB("839", "BBQ치킨", "delivery", "sat", "12", "50", "13", "46", "010-9168-7372", "망포동", "13000");
//        writeDB("840", "스타벅스", "delivery", "mon", "17", "37", "22", "14", "010-9587-7079", "원천동", "16000");
//        writeDB("841", "CU편의점", "delivery", "fri", "8", "43", "23", "37", "010-5887-9228", "이의동", "12000");
//        writeDB("842", "CU편의점", "cafe", "thu", "3", "38", "21", "56", "010-5756-6079", "이의동", "17000");
//        writeDB("843", "BBQ치킨", "cooking", "thu", "19", "28", "23", "33", "010-3702-7875", "원천동", "13000");
//        writeDB("844", "스타벅스", "cafe", "tue", "0", "30", "15", "7", "010-6011-5660", "원천동", "9000");
//        writeDB("845", "올리브영", "serving", "fri", "3", "11", "24", "16", "010-1028-5613", "원천동", "11000");
//        writeDB("846", "올리브영", "store", "mon", "9", "24", "22", "22", "010-2767-3756", "망포동", "17000");
//        writeDB("847", "BBQ치킨", "serving", "wen", "20", "27", "22", "7", "010-2977-9127", "영통동", "11000");
//        writeDB("848", "CU편의점", "store", "tue", "14", "2", "20", "18", "010-7504-2946", "광교동", "17000");
//        writeDB("849", "스타벅스", "store", "tue", "9", "1", "21", "47", "010-4030-1105", "광교동", "12000");
//        writeDB("850", "올리브영", "store", "wen", "6", "18", "12", "17", "010-3701-0454", "원천동", "14000");
//        writeDB("851", "GS25", "cooking", "sat", "2", "1", "7", "35", "010-3520-2426", "하동", "11000");
//        writeDB("852", "스타벅스", "cafe", "fri", "10", "35", "18", "59", "010-8632-6918", "하동", "10000");
//        writeDB("853", "이디야커피", "serving", "wen", "17", "17", "18", "53", "010-9200-6366", "이의동", "19000");
//        writeDB("854", "새마을식당", "cooking", "sat", "4", "49", "21", "2", "010-9554-5959", "신동", "16000");
//        writeDB("855", "스타벅스", "delivery", "fri", "9", "31", "20", "14", "010-7729-2766", "망포동", "14000");
//        writeDB("856", "이디야커피", "store", "sat", "4", "21", "17", "41", "010-7121-4675", "이의동", "18000");
//        writeDB("857", "새마을식당", "delivery", "fri", "12", "25", "22", "10", "010-2635-5196", "원천동", "12000");
//        writeDB("858", "파리바게트", "cafe", "sun", "11", "10", "22", "42", "010-3739-3376", "태장동", "10000");
//        writeDB("859", "이디야커피", "store", "wen", "19", "52", "21", "1", "010-0276-9349", "영통동", "20000");
//        writeDB("860", "새마을식당", "cafe", "mon", "0", "16", "24", "30", "010-9884-7732", "이의동", "15000");
//        writeDB("861", "파리바게트", "cooking", "mon", "4", "21", "19", "51", "010-2092-7767", "이의동", "11000");
//        writeDB("862", "BBQ치킨", "delivery", "sun", "17", "53", "22", "4", "010-2058-4661", "하동", "10000");
//        writeDB("863", "이디야커피", "cooking", "sat", "12", "48", "13", "35", "010-0443-1304", "망포동", "10000");
//        writeDB("864", "파리바게트", "cafe", "tue", "17", "49", "24", "25", "010-0469-7791", "영통동", "14000");
//        writeDB("865", "새마을식당", "cafe", "sat", "14", "9", "16", "31", "010-9387-3620", "영통동", "11000");
//        writeDB("866", "BBQ치킨", "cafe", "wen", "3", "12", "12", "11", "010-7403-9209", "태장동", "17000");
//        writeDB("867", "이디야커피", "delivery", "tue", "14", "41", "17", "13", "010-2529-2004", "망포동", "10000");
//        writeDB("868", "이디야커피", "store", "mon", "5", "33", "17", "51", "010-3628-9810", "원천동", "16000");
//        writeDB("869", "올리브영", "delivery", "sat", "17", "35", "22", "22", "010-0858-6248", "하동", "12000");
//        writeDB("870", "스타벅스", "serving", "mon", "4", "39", "15", "33", "010-0599-6507", "신동", "19000");
//        writeDB("871", "파리바게트", "serving", "wen", "14", "6", "19", "3", "010-8901-7126", "하동", "12000");
//        writeDB("872", "파리바게트", "cooking", "sat", "10", "18", "23", "12", "010-8875-5718", "매탄동", "17000");
//        writeDB("873", "CU편의점", "serving", "mon", "1", "54", "16", "26", "010-7530-2658", "광교동", "11000");
//        writeDB("874", "스타벅스", "store", "tue", "14", "17", "16", "16", "010-3808-6966", "매탄동", "13000");
//        writeDB("875", "파리바게트", "store", "wen", "15", "3", "17", "5", "010-3113-4902", "태장동", "12000");
//        writeDB("876", "올리브영", "serving", "sun", "17", "59", "23", "34", "010-4720-1041", "망포동", "9000");
//        writeDB("877", "파리바게트", "delivery", "sun", "6", "55", "19", "57", "010-2509-0389", "원천동", "13000");
//        writeDB("878", "스타벅스", "cafe", "sat", "15", "49", "20", "17", "010-2169-9152", "매탄동", "15000");
//        writeDB("879", "파리바게트", "cooking", "sun", "1", "6", "19", "51", "010-7968-1946", "하동", "16000");
//        writeDB("880", "올리브영", "cooking", "wen", "12", "26", "14", "5", "010-2626-5263", "하동", "17000");
//        writeDB("881", "파리바게트", "store", "thu", "1", "20", "8", "26", "010-3634-9084", "하동", "11000");
//        writeDB("882", "이디야커피", "cafe", "wen", "10", "14", "17", "10", "010-8947-7687", "매탄동", "13000");
//        writeDB("883", "BBQ치킨", "serving", "sat", "6", "30", "12", "32", "010-1075-6791", "광교동", "19000");
//        writeDB("884", "새마을식당", "delivery", "mon", "13", "25", "22", "48", "010-4445-5127", "원천동", "18000");
//        writeDB("885", "GS25", "cafe", "tue", "0", "44", "9", "41", "010-6748-4366", "신동", "9000");
//        writeDB("886", "올리브영", "store", "sat", "7", "7", "10", "17", "010-0504-8002", "신동", "14000");
//        writeDB("887", "BBQ치킨", "serving", "mon", "22", "24", "24", "47", "010-2436-7810", "신동", "15000");
//        writeDB("888", "올리브영", "store", "fri", "5", "46", "24", "14", "010-6875-5417", "하동", "17000");
//        writeDB("889", "BBQ치킨", "delivery", "sat", "4", "35", "20", "6", "010-2572-8984", "광교동", "13000");
//        writeDB("890", "GS25", "store", "tue", "2", "53", "4", "47", "010-4537-5962", "영통동", "17000");
//        writeDB("891", "BBQ치킨", "delivery", "thu", "15", "57", "18", "43", "010-4227-4878", "하동", "16000");
//        writeDB("892", "파리바게트", "serving", "wen", "19", "7", "24", "20", "010-3921-7827", "이의동", "10000");
//        writeDB("893", "BBQ치킨", "delivery", "tue", "17", "46", "18", "6", "010-5310-2631", "태장동", "12000");
//        writeDB("894", "CU편의점", "serving", "sun", "9", "8", "13", "47", "010-1764-8833", "원천동", "10000");
//        writeDB("895", "BBQ치킨", "cooking", "wen", "6", "38", "12", "33", "010-5757-6397", "영통동", "13000");
//        writeDB("896", "파리바게트", "serving", "thu", "20", "32", "21", "38", "010-2064-7838", "이의동", "19000");
//        writeDB("897", "새마을식당", "cooking", "fri", "22", "30", "23", "7", "010-6722-5810", "광교동", "14000");
//        writeDB("898", "BBQ치킨", "cooking", "wen", "16", "33", "24", "51", "010-8491-2274", "신동", "9000");
//        writeDB("899", "올리브영", "serving", "thu", "15", "38", "22", "26", "010-2506-6846", "광교동", "15000");
//        writeDB("900", "올리브영", "cafe", "mon", "0", "56", "2", "23", "010-3629-1368", "태장동", "12000");
//
//
//    }
//
//    void writeDB(String id, String name, String job, String day, String startHour, String startMinute, String endHour, String endMinute, String phoneNumber, String region, String wage) {
//
//        // ID
//        mDatabase.child("ID").child(id).child("name").setValue(name);
//        mDatabase.child("ID").child(id).child("job").setValue(job);
//        mDatabase.child("ID").child(id).child("day").setValue(day);
//        mDatabase.child("ID").child(id).child("startHour").setValue(startHour);
//        mDatabase.child("ID").child(id).child("startMinute").setValue(startMinute);
//        mDatabase.child("ID").child(id).child("endHour").setValue(endHour);
//        mDatabase.child("ID").child(id).child("endMinute").setValue(endMinute);
//        mDatabase.child("ID").child(id).child("phoneNumber").setValue(phoneNumber);
//        mDatabase.child("ID").child(id).child("region").setValue(region);
//        mDatabase.child("ID").child(id).child("wage").setValue(wage);
//
//
//        //Job
//        mDatabase.child("Job").child(job).push().setValue(id);
//
//        //Day
//        mDatabase.child("Day").child(day).push().setValue(id);
//
//        //Region
//        mDatabase.child("Region").child(region).push().setValue(id);
//
//    }
}