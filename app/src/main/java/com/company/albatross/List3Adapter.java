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

public class List3Adapter extends RecyclerView.Adapter<List3Adapter.ViewHolder> {

    private List<Item> mItems;

    public List3Adapter(List<List3Adapter.Item> items2) {
        mItems = items2;
    }

    public static class Item {
        private String mName;
        private int mImageResId;

        public Item(String name, int imageResId) {
            mName = name;
            mImageResId = imageResId;
        }

        public String getName() {
            return mName;
        }

        public int getImageResId() {
            return mImageResId;
        }
    }

    @NonNull
    @Override
    public List3Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout3, parent, false);
        return new List3Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List3Adapter.ViewHolder holder, int position) {
        List3Adapter.Item item = mItems.get(position);
        holder.mTextView.setText(item.getName());
        holder.mImageView.setImageResource(item.getImageResId());
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (position % 2 == 0) {
            layoutParams.width = (int) (180 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
        } else {
            layoutParams.width = (int) (120 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
        }
        holder.itemView.setLayoutParams(layoutParams);
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
            mTextView = itemView.findViewById(R.id.item_textview3);
            mImageView = itemView.findViewById(R.id.item_imageview3);
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
