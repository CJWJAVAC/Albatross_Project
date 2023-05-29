package com.company.albatross.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.company.albatross.R;
import com.company.albatross.models.Post;

import java.util.ArrayList;
import java.util.List;

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
            convertView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // 불러오는 view 의 객체로 id를 주입함
        TextView tv_idtoken = (TextView) convertView.findViewById(R.id.post_contents_employerIdToken);
        TextView tv_id = (TextView) convertView.findViewById(R.id.post_contents_id);
        TextView tv_name = (TextView) convertView.findViewById(R.id.post_contents_name);
        TextView tv_job = (TextView) convertView.findViewById(R.id.post_contents_job);
        TextView tv_day = (TextView) convertView.findViewById(R.id.post_contents_day);
        TextView tv_education = (TextView) convertView.findViewById(R.id.post_contents_education);
        TextView tv_eperiod = (TextView) convertView.findViewById(R.id.post_contents_eperiod);
        TextView tv_gender = (TextView) convertView.findViewById(R.id.post_contents_gender);
        TextView tv_image = (TextView) convertView.findViewById(R.id.post_contents_image);
        TextView tv_period = (TextView) convertView.findViewById(R.id.post_contents_period);
        TextView tv_starthour = (TextView) convertView.findViewById(R.id.post_contents_starthour);
        TextView tv_startminute = (TextView) convertView.findViewById(R.id.post_contents_startminute);
        TextView tv_endhour = (TextView) convertView.findViewById(R.id.post_contents_endhour);
        TextView tv_endminute = (TextView) convertView.findViewById(R.id.post_contents_endminute);
        TextView tv_phoneNumber = (TextView) convertView.findViewById(R.id.post_contents_phonenumber);
        TextView tv_region = (TextView) convertView.findViewById(R.id.post_contents_region);
        TextView tv_wage = (TextView) convertView.findViewById(R.id.post_contents_wage);


        Post post = postArrayList.get(position); // 아래 addItem 에서 넣은 값을 다시 board에 넣는 방식을 취함

        // 그 값을 세팅을 함
        tv_idtoken.setText(post.getEmployerIdToken());
        tv_id.setText(post.getId());
        tv_name.setText(post.getName());
        tv_job.setText(post.getJob());
        tv_day.setText(post.getDay());
        tv_education.setText(post.getEducation());
        tv_eperiod.setText(post.getEperiod());
        tv_gender.setText(post.getGender());
        tv_image.setText(post.getImage());
        tv_period.setText(post.getPeriod());
        tv_starthour.setText(post.getStartHour());
        tv_startminute.setText(post.getStartMinute());
        tv_endhour.setText(post.getEndHour());
        tv_endminute.setText(post.getEndMinute());
        tv_phoneNumber.setText(post.getPhoneNumber());
        tv_region.setText(post.getRegion());
        tv_wage.setText(post.getWage());


        return convertView; //view를 return 함
    }

    public void setItems(List<Post> posts) {
        postArrayList.clear();
        postArrayList.addAll(posts);
        notifyDataSetChanged();
    }

    //아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능.
    public void addItem(String employerIdToken, String id, String name,
                        String job, String day, String education, String eperiod,
                        String gender, String image, String period, String startHour,
                        String startMinute, String endHour, String endMinute, String phoneNumber, String region, String wage) {
        Post item = new Post();
        item.setEmployerIdToken(employerIdToken);
        item.setId(id);
        item.setName(name);
        item.setJob(job);
        item.setDay(day);
        item.setEducation(education);
        item.setEperiod(eperiod);
        item.setGender(gender);
        item.setImage(image);
        item.setPeriod(period);
        item.setStartHour(startHour);
        item.setStartMinute(startMinute);
        item.setEndHour(endHour);
        item.setEndMinute(endMinute);
        item.setPhoneNumber(phoneNumber);
        item.setRegion(region);
        item.setWage(wage);


        postArrayList.add(item);
    }
}

