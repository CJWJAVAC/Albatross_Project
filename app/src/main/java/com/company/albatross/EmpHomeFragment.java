package com.company.albatross;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.company.albatross.adapters.EmpListAdapter;
import com.company.albatross.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpHomeFragment extends Fragment implements View.OnClickListener, ValueEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final DatabaseReference mDatabaseRef =
            FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference()
                    .child("ID");

    private ListView mListView;
    EmpListAdapter listAdapter = null;
    String uid;
    private FragmentActivity mActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmpHomeFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpHomeFragment newInstance(String param1, String param2) {
        EmpHomeFragment fragment = new EmpHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_employer_home, container, false);
        mListView = (ListView) rootview.findViewById(R.id.main_listview);

        listAdapter = new EmpListAdapter();
        mListView.setAdapter(listAdapter);

        getBoard();
        rootview.findViewById(R.id.main_post_edit).setOnClickListener(this);
        return rootview;
    }

    public void onClick(View v) {
        Intent intent = new Intent(mActivity, PostActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        mDatabaseRef.removeEventListener(this);
        super.onDestroy();
    }

    public void getBoard() {
        mDatabaseRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<Post> posts = new ArrayList<>();

        for (DataSnapshot child : snapshot.getChildren()) {
            Post post = child.getValue(Post.class);
            posts.add(post);
        }

        Collections.reverse(posts);

        listAdapter.setItems(posts);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}