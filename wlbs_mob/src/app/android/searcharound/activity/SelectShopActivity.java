package app.android.searcharound.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import app.android.searcharound.R;
import app.android.searcharound.adapter.ListViewShopAdapter;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadAllShopInfoAsync;
import app.android.searcharound.model.ListViewShop;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.OPCODE;
import app.android.searcharound.utility.PREFS_CODE;
import app.android.searcharound.utility.SecurePreferences;

public class SelectShopActivity extends Activity implements IActivityDataSetter, 
IProcessDataAsyncListener, ListViewShopAdapter.OnClickRemoveCallBack{

	private ListView listViewShop;
	private ListViewShopAdapter listViewShopAdapter;
	private LinearLayout cwaitLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_shop);
		
		
		listViewShop = (ListView) findViewById(R.id.listViewShop);
		
		cwaitLayout = (LinearLayout) findViewById(R.id.cwait_layout);
		
		listViewShopAdapter = new ListViewShopAdapter(this);
		listViewShopAdapter.setOnClickRemoveCallBack(this);		
		listViewShop.setOnItemClickListener(new OnClickItemAction());
		setData();
	}
	
	
	class OnClickItemAction implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			if (id == ListViewShopAdapter.ADD_POSITION)
			{
				NavigationService.getInstance().navigate(SelectShopActivity.this, SaveShopActivity.class);
				
			}
			else
			{
				SecurePreferences pref = new SecurePreferences(SelectShopActivity.this, PREFS_CODE.PREFS_CODE_NAME, 
						PREFS_CODE.PRIVATE_KEY, true);
				pref.put(PREFS_CODE.SHOP_ID, listViewShopAdapter.getItem(position).id+"");
				NavigationService.getInstance().navigate(SelectShopActivity.this, 
						MainShopViewActivity.class);
			}			
		}
		
	}


	@Override
	public void setData() 
	{
		try
		{
			SecurePreferences pref = new SecurePreferences(SelectShopActivity.this, PREFS_CODE.PREFS_CODE_NAME, 
					PREFS_CODE.PRIVATE_KEY, true);
			
			String m_ownerId = pref.getString(PREFS_CODE.OWNER_ID);
			if (m_ownerId != null)
			{
				int ownerId = Integer.parseInt(m_ownerId);
				
				LoadAllShopInfoAsync loader = new LoadAllShopInfoAsync(ownerId, cwaitLayout);				
				loader.execute();
				loader.setProcessDataAsyncListener(this);
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "(Network unavailable)");
		}
		
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response) {
		try
		{
			JSONArray shops = response.getJSONArray("Shops");
			ArrayList<ListViewShop> items = new ArrayList<>();
			
			for (int i = 0; i < shops.length(); i++)
			{
				ListViewShop item = new ListViewShop();
				item.name = shops.getJSONObject(i).getString("Name");
				item.id = shops.getJSONObject(i).getInt("ShopId");
				item.picturePath = shops.getJSONObject(i).getString("PicturePath");
				items.add(item);
			}
			
			ListViewShop item = new ListViewShop();
			item.name = "Add new shop";
			item.id = ListViewShopAdapter.ADD_POSITION;
			item.picturePath = "";
			items.add(item);
			
			listViewShopAdapter.setData(items);
			listViewShop.setAdapter(listViewShopAdapter);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "");
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


	@Override
	public void processOnRemoveCallback(int position) 
	{
		 final int p = position;
		 
		 
		 AlertBox.showConfirmMessage(this, "Confirmation", 
				 "Are you sure you want to delete this shop?", 
				 new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onRemoveShop(p);					
					}
				}, null);	
	}
	
	public void onRemoveShop(int position)
	{
		long lShopId = listViewShopAdapter.getItem(position).id;
		int shopId = Integer.parseInt(lShopId+"");
			
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.removeShop(shopId);
			
			int code = response.getInt("ResponseCode");
			if (code == OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE)
			{
				listViewShopAdapter.removeItem(position);
			}
			else
			{
				AlertBox.showErrorMessage(this, "(Network unavailable)");
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "(Network unavailable)");
		}
	}

	
}
