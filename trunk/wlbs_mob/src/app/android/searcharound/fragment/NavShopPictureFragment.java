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
import android.widget.AdapterView;
import android.widget.GridView;
import app.android.searcharound.R;
import app.android.searcharound.activity.ShopPictureSavingActivity;
import app.android.searcharound.adapter.GridViewShopPictureAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadAllShopPictureAsync;
import app.android.searcharound.model.ShopPicture;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class NavShopPictureFragment extends Fragment implements InterfaceManager, IProcessDataAsyncListener
{
	private GridViewShopPictureAdapter gridAdapter;
	private GridView gridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_shop_picture, container, false);
		
		gridView = (GridView) view.findViewById(R.id.gridview_shop_pic);
		
		gridAdapter = new GridViewShopPictureAdapter(this.getActivity());
		setData();
		setHasOptionsMenu(true);
		return view;
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
			
			gridAdapter.setData(items);
			gridView.setAdapter(gridAdapter);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "Network unavailable");
		}
	}

	@Override
	public void onLock() 
	{
		
		
	}

	@Override
	public void onUnlock()
	{
		
	}

	@Override
	public void onClear() 
	{
		
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
			onClickAddPicture();
		}
		return super.onOptionsItemSelected(item);
	}
	 
	public void onClickAddPicture()
	{
		NavigationService.getInstance().navigate(this.getActivity(), ShopPictureSavingActivity.class);	 
	}
	
	class OnItemClickListener implements android.widget.AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) 
		{
			
		}
		
	}
}
