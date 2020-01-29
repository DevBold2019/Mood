package com.example.mood.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mood.R;
import com.example.mood.Adapter_Classes.WalkAdapter;
import com.example.mood.Model_Classes.WalkModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button,button1;

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView textView;

    int position=0;

    int READ_CONTACTS_CODE=1;
    int READ_SMS_CODE=2;
    int SEND_SMS_CODE=3;
    int GENERAL_CODE=8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[]seeIf={Manifest.permission_group.SMS,Manifest.permission_group.CONTACTS};

        if (ContextCompat.checkSelfPermission(MainActivity.this, String.valueOf(seeIf))==PackageManager.PERMISSION_GRANTED) {


        }else{

            String[] permision = {Manifest.permission_group.CONTACTS, Manifest.permission_group.SMS};

            ActivityCompat.requestPermissions(MainActivity.this,permision,GENERAL_CODE);

        }




            //getSupportActionBar().hide();

            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


            viewPager = findViewById(R.id.vpg);
            tabLayout = findViewById(R.id.tabs);
            textView = findViewById(R.id.skip);


            button = findViewById(R.id.button3);//Next Button
            button1 = findViewById(R.id.buttonGet);


            // when this activity is about to be launch we need to check if its opened before or not

            if (getData()) {

                Intent mainActivity = new Intent(MainActivity.this, MainUi.class);
                startActivity(mainActivity);
                finish();


            }

            final List<WalkModel> walkModelList = new ArrayList<>();
            walkModelList.add(new WalkModel("Friends", "Talk to them \nfrom anywhere at anytime", R.drawable.chatthree));
            walkModelList.add(new WalkModel("Lost Chats?", "With mood you can backup your chats\nhide for privacy", R.drawable.chattwo));
            walkModelList.add(new WalkModel("Start using Mood", "Have fun with Mood", R.drawable.group));

            WalkAdapter walkAdapter = new WalkAdapter(MainActivity.this, walkModelList);

            viewPager.setAdapter(walkAdapter);
            tabLayout.setupWithViewPager(viewPager);


            //GetStarted Button jumps to M
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(MainActivity.this, MainUi.class);
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
                    position = viewPager.getCurrentItem();
                    //if position is less than maximum of the tabs we go to next
                    if (position < walkModelList.size()) {

                        position++;
                        viewPager.setCurrentItem(position);
                    }
                    //else we skip to the last Page
                    else if (position == walkModelList.size() - 1) {

                        lastPage();
                    }
                }
            });

            //immediately the page reaches the end it skips to the lastPage
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    if (tab.getPosition() == walkModelList.size() - 1) {

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

    public  Boolean getData() {

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loadPage", MODE_PRIVATE);
            Boolean isPageOpen = sharedPreferences.getBoolean("SavePage", false);

            return isPageOpen;



    }



    public void lastPage(){


        button1.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

       // button1.setBackground(getDrawable());

    }

}
