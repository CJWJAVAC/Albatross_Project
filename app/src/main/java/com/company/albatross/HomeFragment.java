package com.company.albatross;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

//import android.support.v4.content.ContextCompat;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String KEY_CURRENT_ITEM = "current_item";
    private static final String KEY_LIST_STATE = "list_state";
    private static final int SLIDESHOW_DELAY_MS = 3000;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private static final int[] IMAGES = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private int currentItem = 0;
    private ImageView imageView;
    private Thread slideThread = null;
    private Timer timer;
    private FragmentActivity mActivity;
    ArrayList<Item> items2 = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private ArrayList<String> employerIdTokens = new ArrayList<>();
    private ArrayList<String> ids=new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update UI here
                        }
                    });
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        startSlideshow();
    }
    @Override
    public void onPause() {
        super.onPause();
        stopSlideshow();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (slideThread != null) {
            slideThread.interrupt();
            slideThread = null;
        }
    }
    private void startSlideshow() {
        slideThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(SLIDESHOW_DELAY_MS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentItem = (currentItem + 1) % IMAGES.length;
                            imageView.setImageResource(IMAGES[currentItem]);
                        }
                    });
                }
            }
        });
        slideThread.start();
    }
    private void stopSlideshow() {
        if (slideThread != null) {
            slideThread.interrupt();
            slideThread = null;
        }
    }
//    private void setupListView() {
//        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
//        DatabaseReference idRef = mDatabase.child("ID");
//        Query query=idRef.limitToFirst(5);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                ArrayList<String> items = new ArrayList<>();
//                for(DataSnapshot childSnapshot : snapshot.getChildren()){
//                    ids.add(childSnapshot.getKey());
//                    HashMap<String, String> idValue =(HashMap<String, String>) childSnapshot.getValue();
//                    employerIdTokens.add(idValue.get("employerIdToken"));
//                    items.add(idValue.get("name")+"\n"+"시급 "+idValue.get("wage")+"원\n"+idValue.get("startHour")+"시 ~ "+idValue.get("endHour")+"시\n"+ "경기도 수원시" + idValue.get("region")+"\n"+idValue.get("phoneNumber"));
//                }
//                ListAdapter adapter = new ListAdapter(mActivity, items);
//                setListAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("Error: " + databaseError.getMessage());
//            }
//        });
//    }

    private void setupRecyclerView() {
        mDatabase= FirebaseDatabase.getInstance("https://albatross-ed1d1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference idRef = mDatabase.child("ID");
        Query query=idRef.limitToFirst(5);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<String> items = new ArrayList<>();
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    ids.add(childSnapshot.getKey());
                    HashMap<String, String> idValue =(HashMap<String, String>) childSnapshot.getValue();
                    employerIdTokens.add(idValue.get("employerIdToken"));
                    items.add(idValue.get("name")+"\n"+"시급 "+idValue.get("wage")+"원\n"+idValue.get("startHour")+"시 ~ "+idValue.get("endHour")+"시\n"+ "경기도 수원시" + idValue.get("region")+"\n"+idValue.get("phoneNumber"));
                }
                List1Adapter adapter = new List1Adapter(items);
                adapter.setOnItemClickListener(new List1Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String item) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                    }
                });

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

                recyclerView1.setLayoutManager(layoutManager);
                recyclerView1.setAdapter(adapter);
                recyclerView1.setHasFixedSize(true);
                recyclerView1.setItemViewCacheSize(2);
                recyclerView1.setDrawingCacheEnabled(true);
                recyclerView1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }
    public class Item {
        public String name;
        public int imageResId;

        public Item(String name, int imageResId) {
            this.name = name;
            this.imageResId = imageResId;
        }

        public String getName() {
            return name;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
    private void setupRecyclerView2() {
        TypedArray itemNames = getResources().obtainTypedArray(R.array.items);
        TypedArray itemImages = getResources().obtainTypedArray(R.array.item_images);

        for (int i = 0; i < itemNames.length(); i++) {
            String name = itemNames.getString(i);
            int imageResId = itemImages.getResourceId(i, 0); // 0은 기본값입니다.
            items2.add(new Item(name, imageResId));
        }

        itemNames.recycle();
        itemImages.recycle();

        List<List2Adapter.Item> listItems2 = new ArrayList<>();
        for (Item item : items2) {
            listItems2.add(new List2Adapter.Item(item.getName(), item.getImageResId()));
        }
        List2Adapter adapter = new List2Adapter(listItems2);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setItemViewCacheSize(2);
        recyclerView2.setDrawingCacheEnabled(true);
        recyclerView2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private void setupRecyclerView3() {
        ArrayList<Item> items3 = new ArrayList<>();
        TypedArray itemNames = getResources().obtainTypedArray(R.array.items);
        TypedArray itemImages = getResources().obtainTypedArray(R.array.item_images);

        for (int i = 0; i < itemNames.length(); i++) {
            String name = itemNames.getString(i);
            int imageResId = itemImages.getResourceId(i, 0); // 0은 기본값입니다.
            items3.add(new Item(name, imageResId));
        }

        itemNames.recycle();
        itemImages.recycle();

        List<List3Adapter.Item> listItems3 = new ArrayList<>();
        for (Item item : items3) {
            listItems3.add(new List3Adapter.Item(item.getName(), item.getImageResId()));
        }

        List3Adapter adapter = new List3Adapter(listItems3);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanCount(2);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        recyclerView3.setLayoutManager(layoutManager);
        recyclerView3.setAdapter(adapter);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setItemViewCacheSize(2);
        recyclerView3.setDrawingCacheEnabled(true);
        recyclerView3.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_ITEM, currentItem);
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = rootView.findViewById(R.id.image1);

        //setupListView();

        recyclerView1 = rootView.findViewById(R.id.recyclerView1);
        setupRecyclerView();

        recyclerView2 = rootView.findViewById(R.id.recyclerView2);
        setupRecyclerView2();

        recyclerView3 = rootView.findViewById(R.id.recyclerView3);
        setupRecyclerView3();

        return rootView;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
}