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
import app.android.searcharound.adapter.ListViewDrawerAdapter;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopInfoAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.PREFS_CODE;
import app.android.searcharound.utility.SecurePreferences;

public class MainShopViewActivity extends Activity implements IActivityDataSetter, IProcessDataAsyncListener{

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerListner;
	private ListViewDrawerAdapter listViewDrawerAdapter;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_shop_view);
		
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String auth = pref.getString(PREFS_CODE.AUTH_STATUS);
		String shopId = pref.getString(PREFS_CODE.SHOP_ID);
		
		if (auth != null && auth.equals("true") && shopId != null)
		{
			Toast.makeText(this, "ID = " +shopId, Toast.LENGTH_LONG).show();
			
			
			listView = (ListView) findViewById(R.id.drawerList);
			ListView listView = (ListView) findViewById(R.id.drawerList);
			listView.setOnItemClickListener(new OnMenuClickListener());
			
			drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			
			listViewDrawerAdapter = new ListViewDrawerAdapter(this);
			
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
			NavigationService.getInstance().navigate(this, MainActivity.class);
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
	
	class OnMenuClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			setTitle(listViewDrawerAdapter.getItem(position));
			switch (position) 
			{
				case ListViewDrawerAdapter.HOME:
					onClickHome();
					break;
					
				case ListViewDrawerAdapter.PICTURE:
					onClickPicture();
					break;
					
				case ListViewDrawerAdapter.PROMOTION:
					onClickPromotion();
					break;
					
				case ListViewDrawerAdapter.REQUEST:
					onClickRequest();
					break;
				
				case ListViewDrawerAdapter.CO_PROMOTION:
					onClickCoPromotion();
					break;
					
				case ListViewDrawerAdapter.ACCESS_POINT:
					onClickAccessPoint();
					break;
					
				case ListViewDrawerAdapter.SIGN_OUT:
					onClickSignOut();
					break;
			}
			drawerLayout.closeDrawers();
		}
		
	}
	
	public void onClickHome()
	{
		ShopInformationFragment frag = new ShopInformationFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction trans = fragmentManager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
		
	}
	
	public void onClickPicture()
	{
		ShopPictureFragment frag = new ShopPictureFragment();
		
		FragmentManager manager = getFragmentManager();
		
		FragmentTransaction trans = manager.beginTransaction();
		trans.replace(R.id.frame_container, frag);
		trans.commit();
	}
	
	public void onClickPromotion()
	{
		
	}
	
	public void onClickRequest()
	{
		
	}
	
	public void onClickCoPromotion()
	{
		
	}
	
	public void onClickAccessPoint()
	{
		
	}
	
	public void onClickSignOut()
	{
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		pref.put(PREFS_CODE.AUTH_STATUS, "false");
		pref.put(PREFS_CODE.OWNER_ID, null);
		pref.put(PREFS_CODE.SHOP_ID, null);
		NavigationService.getInstance().navigate(this, MainActivity.class);
	}
		
}





