package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import app.android.searcharound.service.ShopOwnerService;

public class LoadShopAccessPointAsync extends AsyncTask<Void, Void, JSONObject>
{
	private View waitingLayout;
	private int shopId;
	private IProcessDataAsyncListener listener;
	
	public LoadShopAccessPointAsync(View waitingLayout)
	{
		this.waitingLayout = waitingLayout;
	}
	
	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}
	
	public void setProcessDataAsyncListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if (waitingLayout != null)
			waitingLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Void... arg0) {
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			return service.getAccessPoint(shopId);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		if (waitingLayout != null)
			waitingLayout.setVisibility(View.GONE);
		
		if (listener != null)
			listener.onProcessEvent(0, response);
	}

}
