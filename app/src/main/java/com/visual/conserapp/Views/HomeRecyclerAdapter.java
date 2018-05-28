package com.visual.conserapp.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.List;


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{


    public Button btn1;

    private HomeRecyclerItemListener itemListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        //btn1 = (Button) itemView.findViewById(R.id.btn1);
        btn1 = (Button) itemView.findViewById(R.id.btn1);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(HomeRecyclerItemListener itemListener){
        this.itemListener = itemListener;
    }

    @Override
    public void onClick(View v) {
        itemListener.onClick(v, getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemListener.onClick(v, getAdapterPosition(), false);
        return false;
    }



}

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Button> listData = new ArrayList<>();
    private Context context;


    public HomeRecyclerAdapter(List<Button> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.home_recycler_item, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.btn1.setText((CharSequence) listData.get(position).getText());

        holder.setItemClickListener(new HomeRecyclerItemListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Has clickado el botón", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(context, "El otro coment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
