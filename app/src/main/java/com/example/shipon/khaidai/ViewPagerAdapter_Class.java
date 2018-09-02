package com.example.shipon.khaidai;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.shipon.khaidai.FirstPage_Class.slideImage;

/**
 * Created by Shipon on 2/26/2018.
 */

public class ViewPagerAdapter_Class extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;


    public ViewPagerAdapter_Class(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slideImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_image_layout, null);
        ImageView images = (ImageView) view.findViewById(R.id.idSlideImage);
        images.setImageResource(slideImage.get(position));
        images.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
