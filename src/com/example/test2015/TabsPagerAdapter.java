package com.example.test2015;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.test2015.ChartFragment;
import com.example.test2015.PlaylistFragment;
import com.example.test2015.SearchFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter  {
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {		
		switch (arg0) {
        case 0:
            return new ChartFragment();
        case 1:         	
            return new SearchFragment();
        case 2:
            return new PlaylistFragment();
        }
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}	
}
