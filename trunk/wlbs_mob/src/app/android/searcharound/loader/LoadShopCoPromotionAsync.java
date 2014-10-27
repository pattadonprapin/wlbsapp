package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;

public class LoadShopCoPromotionAsync extends AsyncTask<Void, Void, JSONObject> 
{
	private int shopId;
	private View waitingLayout;
	private IProcessDataAsyncListener listener;
	
	public LoadShopCoPromotionAsync(View waitingLayout)
	{
		this.waitingLayout = waitingLayout;
	}
	
	@Override
	protected void onPreExecute() {
	
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		return null;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {

	}
}
