package com.example.mood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import com.example.mood.Fragments.ConversationFragment;
import com.example.mood.Fragments.contactsFragment;
import com.example.mood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUi extends AppCompatActivity {

    BottomNavigationView bottomNav;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        toolbar=findViewById(R.id.myBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        bottomNav=findViewById(R.id.BottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                  Fragment currentFragment=null;

                  switch (menuItem.getItemId()){

                      case R.id.Message:
                          currentFragment=new ConversationFragment();
                          break;

                      case R.id.Contact:
                          currentFragment=new contactsFragment();
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
