package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import app.android.searcharound.service.ShopOwnerService;

public class LoadShopPromotionAsync extends AsyncTask<Void, Void, JSONObject>
{
	private int shopId;
	private View waitingLayout;
	private IProcessDataAsyncListener listener;
	
	public LoadShopPromotionAsync(View waitingLayout)
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
	protected void onPreExecute() 
	{
		if (waitingLayout != null)
			waitingLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.getPromotion(shopId);
			return response;
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
		listener.onProcessEvent(0, response);
	}
}
