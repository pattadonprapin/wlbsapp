package app.android.searcharound.loader;

import java.io.File;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class SaveShopAsync extends AsyncTask<Void, Void, Void>
{
	private LinearLayout cwaitLayout;
	private int responseCode;
	
	private String shopName, phoneNo, latitude, longitude, address;
	private int shopType, ownerId;
	private File picture;
	
	public SaveShopAsync(LinearLayout cwaitLayout)
	{
		this.cwaitLayout = cwaitLayout;
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
	
	
	public int getResponseCode() throws Exception
	{
		this.get();
		return this.responseCode;
	}
	
	@Override
	protected void onPreExecute() {
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			JSONObject response = service.addNewShop(shopName, 
					phoneNo, latitude, longitude, address, shopType, ownerId, picture);
			
			this.responseCode = response.getInt("ResponseCode");
		}
		catch (Exception e)
		{
			this.responseCode = -1;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (cwaitLayout != null)
			cwaitLayout.setVisibility(View.GONE);
	}
	
}
