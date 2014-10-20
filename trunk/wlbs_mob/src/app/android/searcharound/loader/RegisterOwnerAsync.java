package app.android.searcharound.loader;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import app.android.searcharound.service.ShopOwnerService;

public class RegisterOwnerAsync extends AsyncTask<Void, Void, Void>
{
	private LinearLayout cwaitLayout;
	private String email, password, phoneNo, firstname, lastname, citizenId;
	private int responseCode;
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
	protected Void doInBackground(Void... arg0) 
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
	
	public int getReponseCode() throws Exception
	{
		this.wait();
		return this.responseCode;
	}
}
