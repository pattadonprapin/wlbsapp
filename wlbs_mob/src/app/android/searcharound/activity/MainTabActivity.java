package app.android.searcharound.activity;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import app.android.searcharound.R;
import app.android.searcharound.adapter.MainTapAdapter;

public class MainTabActivity extends FragmentActivity{

	private ActionBar actionBar;
	private ViewPager viewPager;
	private MainTapAdapter fragment;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);
			
		viewPager = (ViewPager)findViewById(R.id.pager);
		fragment = new MainTapAdapter(getSupportFragmentManager(), this);
		actionBar = getActionBar();
		
		actionBar.setHomeButtonEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
		viewPager.setAdapter(fragment);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("Client").setTabListener(new TabChangeAction()));
		actionBar.addTab(actionBar.newTab().setText("Shop Owner").setTabListener(new TabChangeAction()));
	
		viewPager.setOnPageChangeListener(new PageChangeAction());
	}

	
	public class TabChangeAction implements TabListener
	{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
			viewPager.setCurrentItem(tab.getPosition());
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class PageChangeAction implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
			
		}
		
	}

}
