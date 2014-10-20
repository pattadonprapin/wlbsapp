package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class RegisterOwnerAsync extends AsyncTask<Void, Void, JSONObject>
{
	private LinearLayout cwaitLayout;
	private String email, password, phoneNo, firstname, lastname, citizenId;
	private IProcessDataAsyncListener listener;
	
	public RegisterOwnerAsync(LinearLayout cwaitLayout)
	{
		this.cwaitLayout = cwaitLayout;
	}

	public void setProcessDataAsyncListener(IProcessDataAsyncListener listener)
	{
		this.listener = listener;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}
	
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	public void setCitizenId(String citizenId)
	{
		this.citizenId = citizenId;
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
			JSONObject response = service.registerShopOwner(email, 
															password, 
															phoneNo,
															firstname, 
															lastname, 
															citizenId);
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
			int responseCode = response.getInt("ResponseCode");
			listener.onProcessEvent(responseCode, response);
		}
		catch (Exception e)
		{
			listener.onProcessEvent(-1, null);
		}
	}

}
