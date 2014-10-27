package app.android.searcharound.activity;


import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import app.android.searcharound.R;
import app.android.searcharound.adapter.ListViewDrawerNavAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.fragment.NavShopAccessPointFragment;
import app.android.searcharound.fragment.NavShopCoPromotionFragment;
import app.android.searcharound.fragment.NavShopInformationFragment;
import app.android.searcharound.fragment.NavShopPictureFragment;
import app.android.searcharound.fragment.NavShopPromotionFragment;
import app.android.searcharound.fragment.NavShopRequestFragment;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopInfoAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class ShopNavigatingActivity extends Activity 
	implements InterfaceManager, IProcessDataAsyncListener
{

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerListner;
	private ListViewDrawerNavAdapter listViewDrawerAdapter;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_nav);
		
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String auth = pref.getString(PREFS_CODE.AUTH_STATUS);
		String shopId = pref.getString(PREFS_CODE.SHOP_ID);
		
		if (auth != null && auth.equals("true") && shopId != null)
		{
			Toast.makeText(this, "ID = " +shopId, Toast.LENGTH_LONG).show();
			
			
			listView = (ListView) findViewById(R.id.listview_nav_drawer);
			ListView listView = (ListView) findViewById(R.id.listview_nav_drawer);
			listView.setOnItemClickListener(new OnNavClickListener());
			
			drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
			
			listViewDrawerAdapter = new ListViewDrawerNavAdapter(this);
			
			drawerListner = new ActionBarDrawerToggle(this, drawerLayout, 
					R.drawable.nav_ic, R.string.app_name, R.string.app_name)
			
			{
				
				@Override
					public void onDrawerClosed(View drawerView) {
						setData();	
				}
			};
			
			drawerLayout.setDrawerListener(drawerListner);		
			
			setData();
			
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		else
		{
			NavigationService.getInstance().navigate(this, MainTabActivity.class);
		}
		
		onClickHome();
		setTitle("Home");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListner.syncState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerListner.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListner.onConfigurationChanged(newConfig);
	}
	
	

	@Override
	public void setData() {
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String shopId = pref.getString(PREFS_CODE.SHOP_ID);
		
		if (shopId != null)
		{
			
			LoadShopInfoAsync loader = new LoadShopInfoAsync(Integer.parseInt(shopId));
			loader.setProcessDataAsyncListener(this);
			loader.execute();
		}
		
	}

	@Override
	public void onProcessEvent(int responseCode, JSONObject response) 
	{
		try
		{
			JSONObject shop = response.getJSONObject("Shop");
			
			String shopName = shop.getString("Name");
			
			SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME,
					PREFS_CODE.PRIVATE_KEY, true);
			
			String email = pref.getString(PREFS_CODE.EMAIL);
			
			String shopPicPath = shop.getString("PicturePath");
			
			listViewDrawerAdapter.setData(shopName, email, shopPicPath);
			
			listView.setAdapter(listViewDrawerAdapter);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "(Network unavailable)");
		}
	}	
	
	@Override
	public void onLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClear() {
		// TODO Auto-generated method stub
		
	}
	
	class OnNavClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			setTitle(listViewDrawerAdapter.getItem(position));
			switch (position) 
			{
				case ListViewDrawerNavAdapter.HOME:
					onClickHome();
					break;
					
				case ListViewDrawerNavAdapter.PICTURE:
					onClickPicture();
					break;
					
				case ListViewDrawerNavAdapter.PROMOTION:
					onClickPromotion();
					break;
					
				case ListViewDrawerNavAdapter.REQUEST:
					onClickRequest();
					break;
				
				case ListViewDrawerNavAdapter.CO_PROMOTION:
					onClickCoPromotion();
					break;
					
				case ListViewDrawerNavAdapter.ACCESS_POINT:
					onClickAccessPoint();
					break;
					
				case ListViewDrawerNavAdapter.SIGN_OUT:
					onClickSignOut();
					break;
			}
			drawerLayout.closeDrawers();
		}
		
	}
	
	public void onClickHome()
	{
		NavShopInformationFragment frag = new NavShopInformationFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction trans = fragmentManager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
		
	}
	
	public void onClickPicture()
	{
		NavShopPictureFragment frag = new NavShopPictureFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickPromotion()
	{
		NavShopPromotionFragment frag = new NavShopPromotionFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickRequest()
	{
		NavShopRequestFragment frag = new NavShopRequestFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickCoPromotion()
	{
		NavShopCoPromotionFragment frag = new NavShopCoPromotionFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickAccessPoint()
	{
		NavShopAccessPointFragment frag = new NavShopAccessPointFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickSignOut()
	{
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		pref.put(PREFS_CODE.AUTH_STATUS, "false");
		pref.put(PREFS_CODE.OWNER_ID, null);
		pref.put(PREFS_CODE.SHOP_ID, null);
		NavigationService.getInstance().navigate(this, MainTabActivity.class);
	}
		
}





