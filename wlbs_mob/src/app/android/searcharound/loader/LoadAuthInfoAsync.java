package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class LoadAuthInfoAsync extends AsyncTask<Void, Void, JSONObject>
{
	private String email, password;
	private LinearLayout cwaitLayout;
	private ILoaderResultListener listener;
	
	public LoadAuthInfoAsync(String email, String password, LinearLayout cwaitLayout)
	{
		this.email = email;
		this.password = password;
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
	protected JSONObject doInBackground(Void... arg0) 
	{
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.login(email, password);
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
		
		try
		{
			int responseCode = response.getInt("ResponseCode");		
			listener.onProcessEvent(responseCode, response);
			
		}
		catch (Exception e)
		{
			listener.onProcessEvent(-1, null);
		}
	}
	
	
	

}
