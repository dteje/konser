package com.visual.conserapp.ViewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.visual.conserapp.R.drawable.editable;

/**
 * Created by daniel on 18/05/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images = new int[]{};
    private boolean[] done;
    private Food food;
    private List<Food> foods = new ArrayList<>();

    public ViewPagerAdapter(Context context, Food f) {
        this.context = context;
        done = new boolean[getCount()];
        foods.add(f);
        //food = (new Gson()).fromJson(gson_food, Food.class);
    }

    public ViewPagerAdapter(Context context, List<Food> foods) {
        this.context = context;
        done = new boolean[getCount()];
        this.foods = foods;
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

    int contador;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.adapter_slider, null);
        final Button btn = (Button) view.findViewById(R.id.adapter_slider_btn_done);
        //ImageView iv = (ImageView) findViewById(R.drawable.editable);
        //System.out.println(//iv.toString());
        //String url = foods.get(position).getImage();
        //System.out.println(url);
        //Picasso.with(context).load(url).into(iv);
        btn.setText(foods.get(position).getName());
        //String url = foods.get(position).getImage();
        //Picasso.with(context).load(url).into(editable);
        btn.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //btn.setBackgroundResource(editable);
        //System.out.println(iv.toString());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done[position] = !done[position];
                if (done[position]) {
                    btn.setAlpha((float) 0.5);
                    //btn.setText("HECHO");
                } else {
                    btn.setAlpha((float) 1);
                    //btn.setText(foods.get(position).getName());
                }

            }
        });
        //contador++;
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
