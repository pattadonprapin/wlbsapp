package app.android.searcharound.fragment;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.android.searcharound.R;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.utility.InterfaceManager;

public class NavShopAccessPointFragment extends Fragment 
	implements InterfaceManager, IProcessDataAsyncListener
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_shop_access_point, container, false);
		
		return view;
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		
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
