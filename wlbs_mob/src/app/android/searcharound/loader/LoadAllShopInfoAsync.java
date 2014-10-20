package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class LoadAllShopInfoAsync extends AsyncTask<Void, Void, JSONObject>
{
	private int ownerId;
	private LinearLayout cwaitLayout;
	private ILoaderResultListener listener;
	
	public LoadAllShopInfoAsync(int ownerId)
	{
		this.ownerId = ownerId;
	}
	
	public LoadAllShopInfoAsync(int ownerId, LinearLayout cwaitLayout)
	{
		this.ownerId = ownerId;
		this.cwaitLayout = cwaitLayout;
	}
	
	public void setLoaderResultListener(ILoaderResultListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() 
	{
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) 
	{
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.getAllShopInfo(ownerId);
			return response;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject response)
	{
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.GONE);
		
		listener.onProcessEvent(0, response);
	}
	

}
