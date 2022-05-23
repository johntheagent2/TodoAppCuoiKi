package com.example.todoappcuoiki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;
    private final List<Item> lstItem;


    public ItemAdapter(Context mContext, List<Item> lstItem) {
        this.mContext = mContext;
        this.lstItem = lstItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item p = lstItem.get(position);
        if(p == null){
            return;
        }
        holder.btnTodoName.setText(p.getTodo());
        holder.linearLayout.setOnClickListener(view -> OnClickGoToDetail(p));
    }

    private void OnClickGoToDetail(Item item) {
        Intent intent = new Intent(mContext, ShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Todo Info", item);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(lstItem != null){
            return lstItem.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView btnTodoName;
        LinearLayout linearLayout;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layout_item);
            btnTodoName = itemView.findViewById(R.id.todo_name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}

