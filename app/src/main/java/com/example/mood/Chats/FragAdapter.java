package com.example.mood.Chats;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragAdapter extends FragmentPagerAdapter {

   private List<Fragment>fragmentList=new ArrayList<>();
   private List<String>fragTitle=new ArrayList<>();


    public FragAdapter(FragmentManager fm) {
        super(fm);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return fragTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return fragTitle.size();
    }

    public void addFrags(Fragment fg,String title){

        fragmentList.add(fg);
        fragTitle.add(title);
    }
}
