package com.visual.conserapp.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Cart;
import com.visual.conserapp.Views.ManageIngredients;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_ingredient_type;
    public  TextView txt_ingredient_name;
    Button btn_eliminar;
    Button btn_editar;

    public void setTxt_ingredient_name(TextView txt_ingredient_name) {
        this.txt_ingredient_name = txt_ingredient_name;
    }

    public void setTxt_ingredient_type(TextView txt_ingredient_type) {
        this.txt_ingredient_type = txt_ingredient_type;
    }

    public IngredientViewHolder(View itemView) {
        super(itemView);
        txt_ingredient_name = (TextView) itemView.findViewById(R.id.ingredient_adapter_item_name);
        txt_ingredient_type = (TextView) itemView.findViewById(R.id.ingredient_adapter_item_type);
        btn_eliminar = (Button) itemView.findViewById(R.id.ingredient_adapter_btn_eliminar);
        btn_editar = (Button) itemView.findViewById(R.id.ingredient_adapter_btn_edit);
    }

    @Override
    public void onClick(View view) {
    }

}

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private ArrayList<Ingredient> listIngredients = new ArrayList<Ingredient>();
    private ManageIngredients mIngredient;
    private  FirebaseDatabase database;
    private Hashtable<Ingredient, String> hashtable;

    public IngredientAdapter(ArrayList<Ingredient> listIngredients, Hashtable<Ingredient, String> hashtable, FirebaseDatabase database, ManageIngredients mIngredient) {
        this.mIngredient = mIngredient;
        this.hashtable = hashtable;
        this.database = database;
        this.listIngredients = listIngredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.mIngredient);
        View itemView = layoutInflater.inflate(R.layout.manage_ingredients_layout, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, final int position) {
        String name = listIngredients.get(position).getName();
        String type = listIngredients.get(position).getType();

        holder.txt_ingredient_name.setText(name);
        holder.txt_ingredient_type.setText(type);

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient ingredient = listIngredients.get(position);
                String key = hashtable.get(ingredient);
                database.getReference().child(key).removeValue();
                mIngredient.loadIngredients();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listIngredients.size();
    }

    public Ingredient getIngredient(int position) {
        return listIngredients.get(position);
    }
}
