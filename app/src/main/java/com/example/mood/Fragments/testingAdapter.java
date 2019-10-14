package com.example.mood.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.R;

import java.util.List;

public class testingAdapter extends RecyclerView.Adapter<testingAdapter.viewholder> {

    Context context;
    List<testingModel>testingModels;

    public testingAdapter(Context context, List<testingModel> testingModels) {

        this.context = context;
        this.testingModels = testingModels;
    }

    @NonNull
    @Override
    public testingAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =(LayoutInflater.from(context).inflate(R.layout.testing_layout,parent,false));

        return new viewholder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull testingAdapter.viewholder holder, int position) {

        testingModel tst=testingModels.get(position);

        holder.tvw.setText(tst.getMessages());



    }

    @Override
    public int getItemCount() {

        return testingModels.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView tvw;

        public viewholder(@NonNull View itemView) {
            super(itemView);

             tvw=itemView.findViewById(R.id.testingMessage);

        }
    }
}
