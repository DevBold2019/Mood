package com.example.mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.mood.Chats.frag1;
import com.example.mood.Fragments.TestingFrag;
import com.example.mood.Fragments.contactsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainUi extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    BottomNavigationView bottomNav;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        toolbar=findViewById(R.id.myBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mood");


      //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

      //  FragAdapter fragAdapter=new FragAdapter(getSupportFragmentManager());

       // fragAdapter.addFrags(new frag1(),"Chats");
        //fragAdapter.addFrags(new frag2(),"Contact");
      // fragAdapter.addFrags(new frag3(),"Buddies");

      //  viewPager.setAdapter(fragAdapter);
       // tabLayout.setupWithViewPager(viewPager);

        bottomNav=findViewById(R.id.BottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                  Fragment currentFragment=null;

                  switch (menuItem.getItemId()){

                      case R.id.Message:
                          currentFragment=new frag1();
                          break;

                      case R.id.Contact:
                          currentFragment=new contactsFragment();
                          break;

                      case R.id.camera:
                          currentFragment=new TestingFrag();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainui,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.refresh:

                Intent intent=new Intent(getApplicationContext(),MainUi.class);
                startActivity(intent);

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
