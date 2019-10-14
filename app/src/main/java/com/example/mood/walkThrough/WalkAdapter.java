package com.example.mood.walkThrough;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mood.R;

import java.util.List;

import static android.content.Context.*;

public class WalkAdapter extends PagerAdapter {

    private Context context;
    private List<WalkObject> walkObjectList;

    //constructor
    protected WalkAdapter(Context context, List<WalkObject> walkObjectList) {
        this.context = context;
        this.walkObjectList = walkObjectList;
    }

    //getting size of our objects
    @Override
    public int getCount() {
        return walkObjectList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

       // @SuppressLint("InflateParams")
        View view=layoutInflater.inflate(R.layout.walklay,null);

        TextView textView=view.findViewById(R.id.Title);
        TextView textView1=view.findViewById(R.id.desc);
        ImageView imageView=view.findViewById(R.id.pic);

        textView.setText(walkObjectList.get(position).getTitle());
        textView1.setText(walkObjectList.get(position).getDescription());
        imageView.setImageResource(walkObjectList.get(position).getImage());



        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
