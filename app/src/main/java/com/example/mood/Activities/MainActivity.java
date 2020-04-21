package com.example.mood.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mood.Fragments.ConversationFragment;
import com.example.mood.R;
import com.example.mood.Adapter_Classes.WalkAdapter;
import com.example.mood.Model_Classes.WalkModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    Button button, button1;
    Button buttonPermission;

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView textView;
    ConstraintLayout constraintLayout;

    int position = 0;
    String[] perms;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] checkPermission = {Manifest.permission_group.SMS, Manifest.permission_group.CONTACTS};


        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewPager = findViewById(R.id.vpg);
        tabLayout = findViewById(R.id.tabs);
        textView = findViewById(R.id.skip);

        constraintLayout = findViewById(R.id.permissionLayout);
        buttonPermission = findViewById(R.id.acceptPermissionButton);

        constraintLayout.setVisibility(View.GONE);

        button = findViewById(R.id.button3);//Next Button
        button1 = findViewById(R.id.buttonGet);


        buttonPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckPermissions();

            }
        });


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


                constraintLayout.setVisibility(View.VISIBLE);
                button1.setVisibility(View.GONE);



                  /*  Intent intent = new Intent(MainActivity.this, MainUi.class);
                    savedata();
                    startActivity(intent);
                    finish();*/


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
    public void savedata() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loadPage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("SavePage", true);
        editor.commit();

    }

    public Boolean getData() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loadPage", MODE_PRIVATE);
        Boolean isPageOpen = sharedPreferences.getBoolean("SavePage", false);

        return isPageOpen;


    }


    public void lastPage() {


        button1.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        // button1.setBackground(getDrawable());

    }


    @AfterPermissionGranted(123)
    public void CheckPermissions() {


        perms = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS};

        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {


                   Intent intent = new Intent(MainActivity.this, MainUi.class);
                    savedata();
                    startActivity(intent);
                    finish();

        } else {



            EasyPermissions.requestPermissions(MainActivity.this, "We need permissions to Read Sms", 123, perms);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }









}
