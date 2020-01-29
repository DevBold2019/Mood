package com.example.mood.Adapter_Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mood.Model_Classes.WalkModel;
import com.example.mood.R;

import java.util.List;

import static android.content.Context.*;

public class WalkAdapter extends PagerAdapter {

    private Context context;
    private List<WalkModel> walkModelList;

    //constructor
    public WalkAdapter(Context context, List<WalkModel> walkModelList) {
        this.context = context;
        this.walkModelList = walkModelList;
    }

    //getting size of our objects
    @Override
    public int getCount() {
        return walkModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.walklay,null);

        TextView textView=view.findViewById(R.id.Title);
        TextView textView1=view.findViewById(R.id.desc);
        ImageView imageView=view.findViewById(R.id.pic);

        textView.setText(walkModelList.get(position).getTitle());
        textView1.setText(walkModelList.get(position).getDescription());
        imageView.setImageResource(walkModelList.get(position).getImage());



        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
