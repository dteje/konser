package com.visual.conserapp.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.UserFavs;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;

class FavsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_favourite_name;
    public TextView txt_favourite_price;
    public TextView txt_favourite_ingredients;
    Button btn_eliminar;
    public FloatingActionButton btn_add;

    public void setTxt_favourite_name(TextView txt_favourite_name) {
        this.txt_favourite_name = txt_favourite_name;
    }


    public FavsViewHolder(View itemView) {
        super(itemView);
        txt_favourite_name = (TextView) itemView.findViewById(R.id.fav_adapter_item_name);
        txt_favourite_price = (TextView) itemView.findViewById(R.id.fav_adapter_price);
        txt_favourite_ingredients = (TextView) itemView.findViewById(R.id.fav_adapter_ingredients);
        btn_eliminar = (Button) itemView.findViewById(R.id.fav_adapter_btn_eliminar);
        btn_add = (FloatingActionButton) itemView.findViewById(R.id.favs_addtoCart);
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
        String name = listFav.get(position).getNameSandwichUser();
        String ingredients = listFav.get(position).getIngredientes();
        String price = String.valueOf(listFav.get(position).getPrice()) + "€";

        holder.txt_favourite_name.setText(name);
        holder.txt_favourite_ingredients.setText(ingredients);
        holder.txt_favourite_price.setText(price);

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFav.remove(listFav.get(position));
                userFavs.setListFavs(listFav);

                database.getReference("UserFavs").child(id).setValue(userFavs);

                home.setFavAdapter();
            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favs favs = listFav.get(position);

                String orderId = "Order " + String.valueOf(System.currentTimeMillis());
                String quantity = "1";
                String price = String.valueOf(favs.getPrice());
                String discount = "0";

                Order orderRes = new Order(orderId, favs.getNameSandwichOfficial(), quantity, price, discount);

                new Database(context).addToCart(orderRes);
                Toast.makeText(context, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
            }
        });


    }




    @Override
    public int getItemCount() {
        return listFav.size();
    }

}

