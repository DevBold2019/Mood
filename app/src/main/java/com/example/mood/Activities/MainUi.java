package com.example.mood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mood.Fragments.ConversationFragment;
import com.example.mood.Fragments.contactsFragment;
import com.example.mood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUi extends AppCompatActivity {

    BottomNavigationView bottomNav;

    Toolbar toolbar;

    BroadcastReceiver receiver;
    IntentFilter intentFilter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(),""+intent.getExtras().getString("message")+"\nFrom:\t"+intent.getExtras().getString("number"),Toast.LENGTH_LONG).show();

            }
        };


        intentFilter=new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        toolbar=findViewById(R.id.myBar);

        setSupportActionBar(toolbar);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        bottomNav=findViewById(R.id.BottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                  Fragment currentFragment=null;

                  switch (menuItem.getItemId()){

                      case R.id.Message:
                          currentFragment=new ConversationFragment();
                          getSupportActionBar().setTitle("Messages");
                          break;

                      case R.id.Contact:
                          currentFragment=new contactsFragment();
                          getSupportActionBar().setTitle("Contacts");
                          break;


                  }

                  getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,currentFragment).commit();

              return true;
              }
            }


        );

        //BY DEFAULT This App will open this fragment being the first one
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.Message);
        }

    }


}
