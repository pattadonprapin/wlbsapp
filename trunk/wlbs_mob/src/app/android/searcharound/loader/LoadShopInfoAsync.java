package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class LoadShopInfoAsync extends AsyncTask<Void, Void, JSONObject>
{
	private int shopId;
	private LinearLayout cwaitLayout;
	private IProcessDataAsyncListener listener;
	
	
	public LoadShopInfoAsync(int shopId)
	{
		this.shopId = shopId;
	}

	public LoadShopInfoAsync(int shopId, LinearLayout cwaitLayout)
	{
		this.shopId = shopId;
		this.cwaitLayout = cwaitLayout;
	}
	
	public void setProcessDataAsyncListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.getShopInformation(shopId);
			return response;
		}
		catch (Exception e)
		{
			Log.e("toy", e.toString());
			return null;
		}
	}
	
	protected void onPostExecute(JSONObject response) 
	{
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.GONE);
		listener.onProcessEvent(0, response);
	}
}
