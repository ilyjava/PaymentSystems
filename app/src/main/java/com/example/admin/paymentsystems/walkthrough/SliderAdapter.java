package com.example.admin.paymentsystems.walkthrough;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.paymentsystems.R;

/**
 * Created by Admin on 12.07.2018.
 */

public class SliderAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;
    ImageView slideImageView;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images ={
            R.drawable.wt_push_pad,
            R.drawable.wt_pay_pad,
            R.drawable.wt_discount_pad,
            R.drawable.wt_bill,
            R.drawable.wt_redeemed
    };

    public String[] slide_headings = {
        "Получайте  push-уведомления nо новых штрафах",
        "Оплачивайте штрафы через приложение",
        "Скидка 50%",
        "Получайте квитанцию, как в банке",
        "Гарантия погашения в государственной базе данных",
        "Соблюдайте правила дорожного движения."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams)slideHeading.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);


        if (position == 5){
            ((ViewManager)slideImageView.getParent()).removeView(slideImageView);
            slideHeading.setText(slide_headings[position]);
            slideHeading.setLayoutParams(layoutParams);
        } else {
            slideHeading.setText(slide_headings[position]);
            Glide.with(context).load(slide_images[position])
                    .asBitmap()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(slideImageView);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
