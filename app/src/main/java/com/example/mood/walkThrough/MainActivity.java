package com.example.mood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button,button1;

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView textView;

    int position=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewPager=findViewById(R.id.vpg);
        tabLayout=findViewById(R.id.tabs);
        textView=findViewById(R.id.skip);



        button=findViewById(R.id.button3);//Next Button
        button1=findViewById(R.id.buttonGet);




        // when this activity is about to be launch we need to check if its opened before or not

        if (getData()) {

            Intent mainActivity = new Intent(MainActivity.this,MainUi.class);
            startActivity(mainActivity);
            finish();


        }



        final List<WalkObject>walkObjectList=new ArrayList<>();
        walkObjectList.add(new WalkObject("Friends","Talk to them \nfrom anywhere at anytime",R.drawable.chatthree));
        walkObjectList.add(new WalkObject("Lost Chats?","With mood you can backup your chats\nhide for privacy",R.drawable.chattwo));
        walkObjectList.add(new WalkObject("Start using Mood","Have fun with Mood",R.drawable.group));

        WalkAdapter walkAdapter=new WalkAdapter(MainActivity.this,walkObjectList);

        viewPager.setAdapter(walkAdapter);
        tabLayout.setupWithViewPager(viewPager );



        //GetStarted Button jumps to M
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,MainUi.class);
                savedata();
                startActivity(intent);
                finish();
            }
        });


        //Button to go to the next Swipe
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //getting position of the current Image
               position=viewPager.getCurrentItem();

               //if position is less than maximum of the tabs we go to next
                if (position<walkObjectList.size()){

                    position++;
                    viewPager.setCurrentItem(position);
                }
                //else we skip to the last Page
                else if (position==walkObjectList.size()-1){

                    lastPage();
                }
            }
        });






        //immediately the page reaches the end it skips to the lastPage
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                if(tab.getPosition()==walkObjectList.size()-1){

                    tabLayout.setSelectedTabIndicator(R.drawable.buttonbg);
                    lastPage();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }


    //saving the swipes
    public  void savedata(){

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("loadPage",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("SavePage",true);
        editor.commit();

    }

    public  Boolean getData(){

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("loadPage",MODE_PRIVATE);
        Boolean isPageOpen =sharedPreferences.getBoolean("SavePage",false);

        return isPageOpen;



    }



    public void lastPage(){


        button1.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

       // button1.setBackground(getDrawable());




    }


    public void load(View view) {



    }
}