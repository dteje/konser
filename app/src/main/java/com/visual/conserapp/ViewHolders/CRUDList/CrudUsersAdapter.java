package com.visual.conserapp.ViewHolders.CRUDList;

import android.view.View;

import com.visual.conserapp.Model.User;
import com.visual.conserapp.Views.CRUD.Retrieve.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 03/06/2018.
 */


public class CrudUsersAdapter extends CrudAdapter {

    List<User> listData;

    public CrudUsersAdapter(List<Object> objects, Users users) {
        super(objects, users);
        this.listData = convertObjectsToUsers(listDataObjects);
    }

    @Override
    public void onBindViewHolder(CrudViewHolder holder, int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getEmail());
        holder.txt_id.setText(" ");
        if (listData.get(position).getAdmin()) holder.txt_id.setText("A");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("A");

            }
        });
    }

    private List<User> convertObjectsToUsers(List<Object> listobjects) {
        List<User> users = new ArrayList<>();
        for (Object o : listobjects) {
            users.add(((User) o));
        }
        return users;
    }


}

