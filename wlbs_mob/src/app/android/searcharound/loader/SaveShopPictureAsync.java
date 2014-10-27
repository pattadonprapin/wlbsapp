package app.android.searcharound.loader;

import java.io.File;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.transport.ProgressFileTransferListener;

public class SaveShopPictureAsync extends AsyncTask<Bundle, Void, JSONObject>
	implements ProgressFileTransferListener 
{
	private LinearLayout cwaitLayout;
	private ProgressBar progressBar;
	private int shopId;
	private String name;
	private File picture;
	
	private IProcessDataAsyncListener listener;
	
	public SaveShopPictureAsync(LinearLayout cwaitLayout)
	{
		this.cwaitLayout = cwaitLayout;
	}
	
	public void setShopId(int id)
	{
		this.shopId = id;
	}
	
	public void setPictureName(String name)
	{
		this.name = name;
	}
	
	public void setProgressBar(ProgressBar progressBar)
	{
		this.progressBar = progressBar;
	}
	
	public void setPicture(File picture)
	{
		this.picture = picture;
	}
	
	public void setProcessDataAsynListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Bundle... params) {
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = null;
			if (progressBar != null)
			{
				response = service.uploadPicture(picture, name, shopId, this);
			}
			else
			{		
				response = service.uploadPicture(picture, name, shopId);
			}
			
			return response;
			
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.GONE);
		
		try
		{
			int reponseCode = response.getInt("ResponseCode");
			listener.onProcessEvent(reponseCode, response);
		}
		catch (Exception e)
		{
			listener.onProcessEvent(-1, response);
		}
	}

	@Override
	public void progress(float percent) 
	{
		if (progressBar != null)
		{
			progressBar.setMax(100);
			progressBar.setProgress((int)percent);
		}
		
	}
	
}
