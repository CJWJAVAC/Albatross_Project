package com.company.albatross;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> filteredItems;

    public ListAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
        mContext = context;
    }

    public void setFilteredList(ArrayList<String> filteredItems) {
        this.filteredItems = filteredItems;
        notifyDataSetChanged(); // 데이터 변경을 알려 어댑터를 갱신
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        }

        String item = getItem(position);
        TextView textView = view.findViewById(R.id.item_textview);
        textView.setText(item);

        view.setBackgroundColor(Color.parseColor("#FFE3EE"));

        return view;
    }


}