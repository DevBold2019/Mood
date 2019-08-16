package com.example.mood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.mood.Chats.FragAdapter;
import com.example.mood.Chats.frag1;
import com.example.mood.Chats.frag2;
import com.example.mood.Chats.frag3;
import com.google.android.material.tabs.TabLayout;

public class MainUi extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager=(ViewPager) findViewById(R.id.vpger);
        tabLayout=(TabLayout) findViewById(R.id.taby);

        FragAdapter fragAdapter=new FragAdapter(getSupportFragmentManager());

        fragAdapter.addFrags(new frag1(),"Welcome");
        fragAdapter.addFrags(new frag2(),"Chats");
        fragAdapter.addFrags(new frag3(),"Buddies");

        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
