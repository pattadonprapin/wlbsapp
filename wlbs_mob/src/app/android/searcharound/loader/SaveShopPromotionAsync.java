package app.android.searcharound.loader;

import java.io.File;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.transport.ProgressFileTransferListener;

public class SaveShopPromotionAsync extends AsyncTask<Void, Void, JSONObject>
	implements ProgressFileTransferListener 
{
	public static final int ADD = 1;
	public static final int EDIT = 2;
	
	private String name, detail;
	private int shopId, promotionId;
	private File picture;
	private int type;
	
	private LinearLayout waitingLayout;
	
	private IProcessDataAsyncListener listener;
	private ProgressBar progressBar;
	
	
	public SaveShopPromotionAsync(LinearLayout waitingLayout)
	{
		this.waitingLayout = waitingLayout;
	}
	
	public void setProgressBar(ProgressBar progressBar)
	{
		this.progressBar = progressBar;
	}
	
	public void setProcessDataAsynListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	public void setPicture(File picture)
	{
		this.picture = picture;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}
	
	public void setDetail(String detail)
	{
		this.detail = detail;
	}
	
	@Override
	protected void onPreExecute() 
	{
		if (waitingLayout != null)
			waitingLayout.setVisibility(View.VISIBLE);
	}

	
	@Override
	protected JSONObject doInBackground(Void... arg0) 
	{
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = null;
			
			switch (type) 
			{
				case EDIT:
					response = service.editPromotion(promotionId, name, detail);
					break;
	
				case ADD:
					if (progressBar != null)
					{
						response = service.addPromotion(name, detail, shopId, picture, this);
					}
					else
					{
						response = service.addPromotion(name, detail, shopId, picture);
					}
					break;
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
		if (waitingLayout != null)
			waitingLayout.setVisibility(View.GONE);
		
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
