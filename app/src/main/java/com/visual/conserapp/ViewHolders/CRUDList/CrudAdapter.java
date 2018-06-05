package com.visual.conserapp.ViewHolders.CRUDList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.visual.conserapp.R;
import com.visual.conserapp.Views.CRUD.Retrieve.Crud;

import java.util.List;

/**
 * Created by daniel on 05/06/2018.
 */

class CrudViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txt_id;
    public TextView txt_name;
    public TextView txt_price;

    public void setTxt_id(TextView txt_id) {
        this.txt_id = txt_id;
    }
    public void setTxt_name(EditText txt_name) {
        this.txt_name = txt_name;
    }
    public void setTxt_price(EditText txt_price) {
        this.txt_price = txt_price;
    }

    public CrudViewHolder(View itemView) {
        super(itemView);
        txt_id = (TextView) itemView.findViewById(R.id.crud_list_txt_id);
        txt_name = (TextView) itemView.findViewById(R.id.crud_list_txt_name);
        txt_price = (TextView) itemView.findViewById(R.id.crud_list_txt_price);
    }

    @Override
    public void onClick(View view) {
    }
}

public abstract class CrudAdapter extends RecyclerView.Adapter<CrudViewHolder>  {

    protected Crud crud;
    protected List<Object> listDataObjects;


    public CrudAdapter(List<Object> listData, Crud crud){
        this.listDataObjects = listData;
        this.crud = crud;

    }

    @Override
    public CrudViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.crud);
        View itemView = layoutInflater.inflate(R.layout.crud_list_item, parent, false);
        return new CrudViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listDataObjects.size();
    }

    public Object getItem(int position) {
        return listDataObjects.get(position);
    }

    @Override
    public abstract void onBindViewHolder(CrudViewHolder holder, final int position);

    public void clear(){
        this.listDataObjects.clear();
    };
}
