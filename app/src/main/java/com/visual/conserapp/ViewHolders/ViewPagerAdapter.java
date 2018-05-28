package com.visual.conserapp.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18/05/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images = new int[]{};
    private boolean[] done;
    private List<Food> foods = new ArrayList<>();

    public ViewPagerAdapter(Context context, Food f) {
        this.context = context;
        done = new boolean[getCount()];
        foods.add(f);
    }

    public ViewPagerAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
        done = new boolean[getCount()];
        for(Boolean b : done){
            b = false;
        }

    }

    public void addFood(Food f) {
        foods.add(f);
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

   @Override
   public Object instantiateItem(ViewGroup container, final int position){
       layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = layoutInflater.inflate(R.layout.adapter_slider, null);
       final ImageView imagen = (ImageView) view.findViewById(R.id.adapter_slider_iv_foodimage);
       final TextView name = (TextView) view.findViewById(R.id.adapter_slider_tv_foodname);
       final String url = foods.get(position).getImage();
       if(url != ""){
           Picasso.with(context).load(url).into(imagen);
       }
       else{
           imagen.setAlpha((float)0);
       }
       name.setText(foods.get(position).getName());
       imagen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!done[position]) {
                   done[position] = true;
                   if(url!="")imagen.setAlpha((float) 0.5);
                   name.setText("HECHO");
               } else {
                   done[position] = false;
                   if(url!="")imagen.setAlpha((float) 1);
                   name.setText(foods.get(position).getName());

               }

           }
       });

       ViewPager viewPager = (ViewPager) container;
       viewPager.addView(view, 0);
       return view;
   }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
