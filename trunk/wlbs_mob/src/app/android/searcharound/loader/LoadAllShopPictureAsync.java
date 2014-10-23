package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class LoadAllShopPictureAsync extends AsyncTask<Void, Void, JSONObject>
{
	private LinearLayout cwaitLayout;
	private int shopId;
	private IProcessDataAsyncListener listener;
	
	public LoadAllShopPictureAsync(int shopId, LinearLayout cwaitLayout)
	{
		this.shopId = shopId;
		this.cwaitLayout = cwaitLayout;
	}
	
	public void setProcessDataAsyncListener(IProcessDataAsyncListener listener)
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
			JSONObject response = service.getPicture(shopId);
			
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
