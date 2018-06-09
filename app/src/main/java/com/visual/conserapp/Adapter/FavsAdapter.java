package com.visual.conserapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.IngredientesFerran.EditIngredient;
import com.visual.conserapp.IngredientesFerran.ManageIngredients;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.Model.UserFavs;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.FavDetails;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;
import java.util.Hashtable;

class FavsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_favourite_name;
    Button btn_eliminar;
    Button btn_detalles;

    public void setTxt_favourite_name(TextView txt_favourite_name) {
        this.txt_favourite_name = txt_favourite_name;
    }


    public FavsViewHolder(View itemView) {
        super(itemView);
        txt_favourite_name = (TextView) itemView.findViewById(R.id.fav_adapter_item_name);
        btn_eliminar = (Button) itemView.findViewById(R.id.fav_adapter_btn_eliminar);
        btn_detalles = (Button) itemView.findViewById(R.id.fav_adapter_btn_details);
    }

    @Override
    public void onClick(View view) {
    }

}

public class FavsAdapter extends RecyclerView.Adapter<FavsViewHolder> {

    private ArrayList<Favs> listFav = new ArrayList<Favs>();
    private Home home;
    private FirebaseDatabase database;
    private DatabaseReference userfavs_table;
    private Context context;
    private String id = Common.currentUser.getEmailAsId();
    private UserFavs userFavs;

    public FavsAdapter(UserFavs userFavs, Context context, ArrayList<Favs> listFav, FirebaseDatabase database, Home home) {
        this.userFavs = userFavs;
        this.listFav = listFav;
        this.database = database;
        this.context = context;
        this.home = home;
    }

    @Override
    public FavsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.favs_card_view, parent, false);
        return new FavsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavsViewHolder holder, final int position) {
        String name = listFav.get(position).getNameSandwichOfficial();

        holder.txt_favourite_name.setText(name);

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFav.remove(listFav.get(position));
                userFavs.setListFavs(listFav);

                database.getReference("UserFavs").child(id).setValue(userFavs);

                home.initializeFavs();
            }
        });

        holder.btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favs favs = listFav.get(position);

                String nameSandwichOfficial = favs.getNameSandwichOfficial();
                String nameSandwichUser = favs.getNameSandwichUser();
                String ingredients = favs.getIngredientes();
                Double price = favs.getPrice();

                Intent i = new Intent(context, FavDetails.class);
                i.putExtra("nameSandwichOfficial", nameSandwichOfficial);
                i.putExtra("nameSandwichUser", nameSandwichUser);
                i.putExtra("Ingredients", ingredients);
                i.putExtra("price", price);

                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listFav.size();
    }

}

