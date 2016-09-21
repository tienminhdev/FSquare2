package dev.tienminh.fsquare.TabManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TIEN on 20/08/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String>arrayTitle = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public void AddFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
        arrayTitle.add(title);
    }
    public CharSequence getPageTitle(int positiion){
//        return arrayTitle.get(positiion);
        return null;
    }
}
