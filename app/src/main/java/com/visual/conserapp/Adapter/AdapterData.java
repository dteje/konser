package com.visual.conserapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visual.conserapp.R;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData> {

    ArrayList<String> listData;

    public AdapterData(ArrayList<String> listData) {
        this.listData = listData;
    }

    public AdapterData(){}

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderData holder, int position) {
        holder.assignData(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView data;

        public ViewHolderData(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.idData);
        }

        public void assignData(String aData) {
            data.setText(aData);
        }
    }
}


