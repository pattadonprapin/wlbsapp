package app.android.searcharound.loader;

import java.io.File;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class SaveShopAsync extends AsyncTask<Void, Void, JSONObject>
{	
	private LinearLayout cwaitLayout;
	
	private String shopName, phoneNo, latitude, longitude, address;
	private int shopType, ownerId, shopId;
	private File picture;
	private IProcessDataAsyncListener listener;
	
	
	
	public SaveShopAsync(LinearLayout cwaitLayout)
	{
		this.cwaitLayout = cwaitLayout;
	}
	
	public void setProcessDataAsyncListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}
	
	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}
	
	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	
	public void setShopType(int shopType)
	{
		this.shopType = shopType;
	}
	
	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}
	
	public void setPictureFile(File picture)
	{
		this.picture = picture;
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
			JSONObject response = null;
			ShopOwnerService service = new ShopOwnerService();
			
			if (shopId == 0)
			{
				response = service.addNewShop(shopName, 
						phoneNo, latitude, longitude, address, shopType, ownerId, picture);
			}
			else
			{
				response = service.editShopInformation(shopId, shopName,
						phoneNo, latitude, longitude, address, picture);			
			}
			return response;
			//this.responseCode = response.getInt("ResponseCode");
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
			int responseCode = response.getInt("ResponseCode");
			listener.onProcessEvent(responseCode, response);
		}
		catch (Exception e)
		{
			listener.onProcessEvent(-1, response);
		}
	}
	
}
