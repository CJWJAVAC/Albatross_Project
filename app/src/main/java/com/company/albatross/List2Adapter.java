package com.company.albatross;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class List2Adapter extends RecyclerView.Adapter<List2Adapter.ViewHolder> {

    private List<Item> mItems;

    public List2Adapter(List<Item> items2) {
        mItems = items2;
    }

    public static class Item {
        private String name;
        private int imageResId;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.mTextView.setText(item.getName());
        holder.mImageView.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_textview2);
            mImageView = itemView.findViewById(R.id.item_imageview2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 아이템 클릭 이벤트 처리
                    String itemName = mTextView.getText().toString();
                    Toast.makeText(itemView.getContext(), itemName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
