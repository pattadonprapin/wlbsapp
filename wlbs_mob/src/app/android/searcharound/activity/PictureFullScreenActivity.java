package app.android.searcharound.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import app.android.searcharound.R;
import app.android.searcharound.adapter.FullScreenPictureAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadAllShopPictureAsync;
import app.android.searcharound.model.ShopPicture;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.SecurePreferences;

public class PictureFullScreenActivity extends Activity implements InterfaceManager,
	IProcessDataAsyncListener, OnPageChangeListener
{

	private ViewPager pager;
	private FullScreenPictureAdapter pictureAdatper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_full_screen);
		
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOnPageChangeListener(this);
		
		
		pictureAdatper = new FullScreenPictureAdapter(this, pager);

		setData();
	}
	
	
	@Override
	public void setData() 
	{
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			int shopId = Integer.parseInt(shopId_str);
			LoadAllShopPictureAsync loader = new LoadAllShopPictureAsync(shopId, null);
			loader.setProcessDataAsyncListener(this);
			loader.execute();
		}		
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response)
	{
		try
		{
			Bundle param = getIntent().getExtras();
			if (param != null)
			{
				int position = param.getInt("position");
				
				JSONArray pictures = response.getJSONArray("Picture");
				ArrayList<ShopPicture> items = new ArrayList<>();
				for (int i = 0; i < pictures.length(); i++)
				{
					JSONObject picture = pictures.getJSONObject(i);
					ShopPicture item = new ShopPicture();
					item.name = picture.getString("Name");
					item.pictureId = picture.getInt("PictureId");
					item.picturePath = picture.getString("SavePath");
					items.add(item);
				}
				
				pictureAdatper.setItems(items);
				pager.setAdapter(pictureAdatper);
				pager.setCurrentItem(position);
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "(Network unavailable "+e.toString()+") ");
		}
	}
	
	@Override
	public void onPageSelected(int position) {
		setTitle(pictureAdatper.getItem(position).name);
		
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


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	


}
