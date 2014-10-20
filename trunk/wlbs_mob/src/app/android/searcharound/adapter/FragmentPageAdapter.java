package app.android.searcharound.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import app.android.searcharound.activity.ClientFragment;
import app.android.searcharound.activity.ShopOwnerFragment;

public class FragmentPageAdapter extends FragmentPagerAdapter {

	private Context context;
	
	public FragmentPageAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int key) {
		// TODO Auto-generated method stub
		switch (key) {
		case 0:
			return new ClientFragment();
		case 1:
			return new ShopOwnerFragment(context);
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
