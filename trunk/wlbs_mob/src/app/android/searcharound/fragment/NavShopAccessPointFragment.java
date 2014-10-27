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
import android.widget.ListView;
import app.android.searcharound.R;
import app.android.searcharound.adapter.ListViewAccessPointAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.dialog.AccessPointAddingDialog;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopAccessPointAsync;
import app.android.searcharound.model.ShopAccessPoint;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.SecurePreferences;

public class NavShopAccessPointFragment extends Fragment 
	implements InterfaceManager, IProcessDataAsyncListener
{

	private View waitingLayout;
	private ListView accessPointList;
	private ListViewAccessPointAdapter accessPointAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_shop_access_point, container, false);
		
		waitingLayout = (View) view.findViewById(R.id.layout_waiting);
		accessPointList = (ListView) view.findViewById(R.id.listview_access_point);
		accessPointAdapter = new ListViewAccessPointAdapter(this.getActivity());
			
		setHasOptionsMenu(true);
		
		setData();
		
		return view;
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		setData();
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
			onClickAddAccessPoint();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickAddAccessPoint()
	{
		AccessPointAddingDialog dialog = new AccessPointAddingDialog(this.getActivity());
		dialog.setInterfaceManager(this);
		dialog.show();
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
			LoadShopAccessPointAsync loader = new LoadShopAccessPointAsync(waitingLayout);
			loader.setShopId(shopId);
			loader.setProcessDataAsyncListener(this);
			
			loader.execute();
		}
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response) 
	{
		try
		{
			JSONArray accessPoints = response.getJSONArray("Access point");
			ArrayList<ShopAccessPoint> items = new ArrayList<>();
			for (int i = 0; i < accessPoints.length(); i++)
			{
				JSONObject accessPoint = accessPoints.getJSONObject(i);
				ShopAccessPoint item = new ShopAccessPoint();
				
				item.id = accessPoint.getInt("AccessPointId");
				item.name = accessPoint.getString("Name");
				item.bssid = accessPoint.getString("BSSID");
				items.add(item);
			}
			
			accessPointAdapter.setItems(items);
			accessPointList.setAdapter(accessPointAdapter);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "(Network unavailable)");
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
}
