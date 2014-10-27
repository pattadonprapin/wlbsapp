package app.android.searcharound.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import app.android.searcharound.R;
import app.android.searcharound.adapter.FullScreenPromotionAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopPromotionAsync;
import app.android.searcharound.model.ShopPromotion;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.SecurePreferences;

public class PromotionFullScreenActivity extends Activity implements InterfaceManager,
	IProcessDataAsyncListener, OnPageChangeListener
{
	private ViewPager pager;
	private FullScreenPromotionAdapter promotionAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promotion_full_screen);
		
		pager = (ViewPager)findViewById(R.id.pager);
		pager.setOnPageChangeListener(this);
		
		promotionAdapter = new FullScreenPromotionAdapter(this, pager);
		
		setData();
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		setTitle(promotionAdapter.getItem(position).name);
		
	}

	@Override
	public void onProcessEvent(int responseCode, JSONObject response) {
		try
		{
			Bundle param = getIntent().getExtras();
			if (param != null)
			{
				int position = param.getInt("position");
				
				JSONArray promotions = response.getJSONArray("Shop Promotion");
				ArrayList<ShopPromotion> items = new ArrayList<>();
				for (int i = 0; i < promotions.length(); i++)
				{
					JSONObject promotion = promotions.getJSONObject(i);
					ShopPromotion item = new ShopPromotion();
					
					item.id = promotion.getInt("ShopPromotionId");
					item.name = promotion.getString("PromotionName");
					item.detail = promotion.getString("PromotionDetail");
					
					String recordDate = promotion.getString("RecordDate");
					String spt[] = recordDate.split(" ");
					
					item.date = spt[0];
					item.time = spt[1];
					
					item.picPath = promotion.getString("PicturePath");
					items.add(item);
				}
				
				promotionAdapter.setItems(items);
				pager.setAdapter(promotionAdapter);
				pager.setCurrentItem(position);
				
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "(Network unavailable "+e.toString()+") ");
		}
		
	}

	@Override
	public void setData() {
		SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			int shopId = Integer.parseInt(shopId_str);
			
			LoadShopPromotionAsync loader = new LoadShopPromotionAsync(null);
			loader.setShopId(shopId);
			loader.setProcessDataAsyncListener(this);
			loader.execute();
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


}
