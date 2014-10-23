package app.android.searcharound.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import app.android.searcharound.fragment.TabClientFragment;
import app.android.searcharound.fragment.TabShopOwnerFragment;

public class MainTapAdapter extends FragmentPagerAdapter {

	private Context context;
	
	public MainTapAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int key) {
		// TODO Auto-generated method stub
		switch (key) {
		case 0:
			return new TabClientFragment();
		case 1:
			return new TabShopOwnerFragment(context);
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
