package com.company.albatross;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userIdToken";
    private static final String ARG_PARAM2 = "userData";

    // TODO: Rename and change types of parameters
    private String userIdToken;
    private DatabaseReference mDatabase;
    private HashMap<String, String> userData;
    private TextView nameText;

    public MypageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userIdToken Parameter 1.
     * @param userData Parameter 2.
     * @return A new instance of fragment MypageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageFragment newInstance(String userIdToken, HashMap<String, String> userData) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userIdToken);
        args.putSerializable(ARG_PARAM2, userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userIdToken = getArguments().getString(ARG_PARAM1);
            userData= (HashMap<String, String>) getArguments().getSerializable(ARG_PARAM2);
            Log.i("userData", String.valueOf(userData));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mypage, container, false);
        nameText=rootView.findViewById(R.id.username);
        nameText.setText(userData.get("name")+"님");

        ImageButton applicationStatus = (ImageButton) rootView.findViewById(R.id.myscheduleimage);
        applicationStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), ApplicationStatusActivity.class);
                intent.putExtra("userIdToken", userData.get("idToken") );
                startActivity(intent);
            }
        });

        ImageButton updateMyData = (ImageButton) rootView.findViewById(R.id.mydataimage);
        updateMyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), UpdateMyDataActivity.class);
                intent.putExtra("userData", userData );
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}