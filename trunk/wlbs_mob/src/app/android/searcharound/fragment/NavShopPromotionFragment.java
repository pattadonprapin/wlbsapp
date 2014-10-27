package app.android.searcharound.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import app.android.searcharound.R;
import app.android.searcharound.activity.ShopPromotionSaving;
import app.android.searcharound.adapter.ListViewPromotionAdapter;
import app.android.searcharound.common.OPCODE;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopPromotionAsync;
import app.android.searcharound.model.ShopPromotion;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class NavShopPromotionFragment extends Fragment implements InterfaceManager, 
	IProcessDataAsyncListener, ListViewPromotionAdapter.OnClickRemoveCallBack
{
	private LinearLayout waitingLayout;
	private ListView promotionList;
	private ListViewPromotionAdapter promotionListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shop_promotion, container, false);
		
		waitingLayout = (LinearLayout) view.findViewById(R.id.layout_waiting);
		promotionList = (ListView) view.findViewById(R.id.listview_promotion);
		
		promotionListAdapter = new ListViewPromotionAdapter(this.getActivity());
		
		setData();
		
		setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		inflater.inflate(R.menu.menu_with_add, menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (item.getItemId() == R.id.action_add)
		{
			onClickAddPromotion();
		}
		return super.onOptionsItemSelected(item);
	}
	 
	@Override
	public void setData() 
	{

		SecurePreferences pref = new SecurePreferences(this.getActivity(), PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			int shopId = Integer.parseInt(shopId_str);
			LoadShopPromotionAsync loader = new LoadShopPromotionAsync(waitingLayout);
			loader.setShopId(shopId);
			loader.setProcessDataAsyncListener(this);
			
			loader.execute();
		}
	}
	
	public void onClickAddPromotion()
	{
		NavigationService.getInstance().navigate(this.getActivity(), ShopPromotionSaving.class);
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response) 
	{
		try
		{
			JSONArray protmotions = response.getJSONArray("Shop Promotion");
			ArrayList<ShopPromotion> items = new ArrayList<>();
			for (int i = 0; i < protmotions.length(); i++)
			{
				JSONObject promotion = protmotions.getJSONObject(i);
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
			
			promotionListAdapter.setItem(items);
			promotionListAdapter.setOnClickRemoveCallBack(this);
			promotionList.setAdapter(promotionListAdapter);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "Network unavailable");
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
		int promotionId = promotionListAdapter.getItem(position).id;
		
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.removePromotion(promotionId);
			
			int code = response.getInt("ResponseCode");
			
			if (code == OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE)
			{
				promotionListAdapter.removeItem(position);
			}
			else
			{
				AlertBox.showErrorMessage(this.getActivity(), "(Network unavailable)");
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "(Network unavailable)");
		}
	}

}
