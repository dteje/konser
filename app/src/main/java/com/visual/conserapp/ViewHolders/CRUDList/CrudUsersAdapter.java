package com.visual.conserapp.ViewHolders.CRUDList;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.Views.CRUD.Retrieve.Users;
import com.visual.conserapp.Views.CRUD.Update.CrudEditUsers;

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
    public void onBindViewHolder(CrudViewHolder holder, final int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getEmail());
        holder.txt_id.setText(" ");
        if (listData.get(position).getAdmin()) holder.txt_id.setText("A");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listData.get(position).getEmail().equals(Common.currentUser.getEmail())){
                    Toast.makeText(crud,"No puedes editar el usuario en uso", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(crud, CrudEditUsers.class);
                    intent.putExtra("id",listData.get(position).getEmailAsId());
                    crud.startActivity(intent);
                }
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

