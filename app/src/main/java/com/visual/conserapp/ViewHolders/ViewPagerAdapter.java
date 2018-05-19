package com.visual.conserapp.ViewHolders;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18/05/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public List<Integer> getImages() {
        return images;
    }

    public ViewPagerAdapter(Context context) {
        this.context = context;
        this.images = new ArrayList<>();
    }

    //private List<Integer> images;
    private List<Integer> images;
    private List<ImageView> pictures;

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public void addImage(String url) {
        ImageView image = new ImageView(this.context);
        Picasso.with(this.context).load(url).into(image);
        pictures.add(image);
        aux(this.container);


    }

    View view;
    ImageView iv;
    ViewGroup container;

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        this.container = container;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_slider, null);
        iv = (ImageView) view.findViewById(R.id.adapter_slider_iv);
        iv.setImageResource(images.get(position));
        return aux(container);

    }

    private View aux(ViewGroup container) {
        view = layoutInflater.inflate(R.layout.adapter_slider, null);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}
