package com.company.albatross.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.company.albatross.R;
import com.company.albatross.models.Post;

import java.util.ArrayList;

public class EmpListAdapter extends BaseAdapter {

    public ArrayList<Post> postArrayList = null;

    public EmpListAdapter() {
        postArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return postArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return postArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) { // 이때 다른 xml 에서불러오는 view 의 객체값이 존재하지 않는다면 .. 최초 한번 실행
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.employer_item_post, parent, false); // 다른 xml 에 있는 view객체를 가지고 올것임
        }

        // 불러오는 view 의 객체로 id를 주입함
        TextView tv_title = (TextView) convertView.findViewById(R.id.item_post_title);
        TextView tv_contents = (TextView) convertView.findViewById(R.id.item_post_contents);
        TextView tv_date = (TextView) convertView.findViewById(R.id.item_post_date);

        Post post = postArrayList.get(position); // 아래 addItem 에서 넣은 값을 다시 board에 넣는 방식을 취함

        // 그 값을 세팅을 함
        tv_title.setText(post.getTitle());
        tv_contents.setText(post.getContents());
        tv_date.setText(post.getDate());




        return convertView; //view를 return 함
    }

    //아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능.
    public void addItem(String title,String contents,String date) {
        Post item = new Post();

        item.setTitle(title);
        item.setContents(contents);
        item.setDate(date);

        postArrayList.add(item);
    }
}

