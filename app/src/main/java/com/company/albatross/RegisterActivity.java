package com.company.albatross;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference(); // region이 싱가포르여서 uri 넣어줘야함.;

    private EditText mEtEmail,mEtPwd,mEtName,mEtGen,mEtAge,mEtPhone,mEtMil,mEtRes;
    private Button mBtnRegister;
    private RadioGroup rg;
    private RadioButton rb_employee, rb_employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("capLogin");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtName = findViewById(R.id.et_name);
        mEtGen = findViewById(R.id.et_gen);
        mEtAge = findViewById(R.id.et_age);
        mEtPhone = findViewById(R.id.et_phone);
        mEtMil = findViewById(R.id.et_military);
        mEtRes = findViewById(R.id.et_live);
        mBtnRegister = findViewById(R.id.btn_register);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rb_employee = findViewById(R.id.rb_employee);
        rb_employer = findViewById(R.id.rb_employer);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_employee){
                    mEtAge.setVisibility(View.VISIBLE);
                    mEtName.setVisibility(View.VISIBLE);
                    mEtGen.setVisibility(View.VISIBLE);
                    mEtPhone.setVisibility(View.VISIBLE);
                    mEtMil.setVisibility(View.VISIBLE);
                    mEtRes.setVisibility(View.VISIBLE);
                    mBtnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String strEmail = mEtEmail.getText().toString();
                            String strPwd = mEtPwd.getText().toString();
                            String strName = mEtName.getText().toString();
                            String strGen = mEtGen.getText().toString();
                            String strAge = mEtAge.getText().toString();
                            String strPhone = mEtPhone.getText().toString();
                            String strMil = mEtMil.getText().toString();
                            String strRes = mEtRes.getText().toString();

                            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        UserAccount account = new UserAccount();
                                        account.setIdToken(firebaseUser.getUid());
                                        account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(strPwd);
                                        account.setName(strName);
                                        account.setGender(strGen);
                                        account.setAge(strAge);
                                        account.setPhoneNum(strPhone);
                                        account.setMilitary(strMil);
                                        account.setResidence(strRes);

                                        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
                else if(checkedId==R.id.rb_employer){
                    mEtAge.setVisibility(View.INVISIBLE);
                    mEtName.setVisibility(View.INVISIBLE);
                    mEtGen.setVisibility(View.INVISIBLE);
                    mEtPhone.setVisibility(View.INVISIBLE);
                    mEtMil.setVisibility(View.INVISIBLE);
                    mEtRes.setVisibility(View.INVISIBLE);
                    mBtnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String strEmail = mEtEmail.getText().toString();
                            String strPwd = mEtPwd.getText().toString();

                            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        EmployerAccount account = new EmployerAccount();
                                        account.setIdToken(firebaseUser.getUid());
                                        account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(strPwd);
                                        Log.i("firebaseUser", firebaseUser.getUid());
                                        mDatabaseRef.child("capLogin").child("EmployerAccount").child(firebaseUser.getUid()).setValue(account);

                                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        // 내 정보 : 이름, 성별, 나이, 전화번호, 군필여부, 거주지(구직자 기준)
        // 점주 기준 :
    }
}