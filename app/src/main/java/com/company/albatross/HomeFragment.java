package com.company.albatross;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String KEY_CURRENT_ITEM = "current_item";
    private static final String KEY_LIST_STATE = "list_state";
    private static final int SLIDESHOW_DELAY_MS = 3000;

    private static final int[] IMAGES = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private int currentItem = 0;
    private ImageView imageView;
    private Thread slideThread = null;
    private Timer timer;
    private FragmentActivity mActivity;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        if (savedInstanceState != null) {
            Parcelable listState = savedInstanceState.getParcelable(KEY_LIST_STATE);
            getListView().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        startSlideshow();
        //setupListView();
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
    private void setupListView() {


        ArrayList<String> items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.items)));
        ListAdapter adapter = new ListAdapter(mActivity, items);
        setListAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_ITEM, currentItem);

        // 리스트 뷰의 스크롤 위치를 저장합니다.
        ListView listView = getListView();
        outState.putParcelable(KEY_LIST_STATE, listView.onSaveInstanceState());
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

        setupListView();


        return rootView;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // 클릭된 아이템의 값을 가져옵니다.
        String item = (String) getListAdapter().getItem(position);

        // Toast로 출력합니다.
        //Toast.makeText(getActivity(), "선택된 알바: " + item, Toast.LENGTH_SHORT).show();
        Intent showDetail = new Intent(mActivity.getApplicationContext(),DetailActivity.class);
        showDetail.putExtra("jobId",item);
        startActivity(showDetail);
    }


}