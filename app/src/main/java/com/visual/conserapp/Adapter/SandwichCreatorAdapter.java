package com.visual.conserapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.IngredientesFerran.EditIngredient;
import com.visual.conserapp.IngredientesFerran.ManageIngredients;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.SandwitchCreator;

import java.util.ArrayList;
import java.util.Hashtable;

class SandwichCreatorViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

    public TextView txt_ingredient_name;
    Button btn_add;

    public void setTxt_ingredient_name(TextView txt_ingredient_name) {
        this.txt_ingredient_name = txt_ingredient_name;
    }

    public SandwichCreatorViewHolder(View itemView){
        super(itemView);
        txt_ingredient_name = (TextView) itemView.findViewById(R.id.sc_adapter_ing_name);
        btn_add = (Button) itemView.findViewById(R.id.sc_adapter_ing_name);
    }

    @Override
    public void onClick(View view){}

}

public class SandwichCreatorAdapter extends RecyclerView.Adapter<SandwichCreatorViewHolder> {

    private ArrayList<String> listIngredients = new ArrayList<String>();
    private SandwitchCreator sandwitchCreator;
    private Context context;

    public SandwichCreatorAdapter(){}

    public SandwichCreatorAdapter(ArrayList<String> listIngredients, Context context, SandwitchCreator sandwitchCreator) {
        this.sandwitchCreator = sandwitchCreator;
        this.listIngredients = listIngredients;
        this.context = context;
    }

    @Override
    public SandwichCreatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.sandwich_creator_view, parent, false);
        return new SandwichCreatorViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final SandwichCreatorViewHolder holder, final int position) {
        String name = listIngredients.get(position);

        holder.txt_ingredient_name.setText(name);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sandwitchCreator.addToSandwitch(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listIngredients.size();
    }
}
