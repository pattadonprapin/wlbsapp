package app.android.searcharound.activity;


import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.common.OPCODE;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.RegisterOwnerAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class ShopOwnerRegActivity extends Activity implements InterfaceManager, IProcessDataAsyncListener{

	private TextView txtboxEmail;
	private TextView txtboxPassword;
	private TextView txtboxFirstname;
	private TextView txtboxLastname;
	private TextView txtboxPhoneNo;
	private TextView txtboxCitizenId;
	private TextView txtboxReTypePass;
	private LinearLayout cwaitLayout;
	
	private Button btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_owner_reg);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		txtboxEmail = (TextView)findViewById(R.id.txtbox_email);
		txtboxPassword = (TextView)findViewById(R.id.txtbox_password);
		txtboxReTypePass = (TextView)findViewById(R.id.txtbox_re_type_password);
		txtboxFirstname = (TextView)findViewById(R.id.txtbox_first_name);
		txtboxLastname = (TextView)findViewById(R.id.txtbox_last_name);
		txtboxPhoneNo = (TextView)findViewById(R.id.txtbox_phone_no);
		txtboxCitizenId = (TextView)findViewById(R.id.txtbox_citizen_id);
		
		cwaitLayout = (LinearLayout) findViewById(R.id.layout_waiting);
		
		btnRegister = (Button) findViewById(R.id.btn_register);
	}
	
	public void onClick(View view) 
	{
		onClear();
		onLock();
		try
		{
			String email = txtboxEmail.getText().toString();
			String pass = txtboxPassword.getText().toString();
			String re_pass = txtboxReTypePass.getText().toString();
			String firstname = txtboxFirstname.getText().toString();
			String lastname = txtboxLastname.getText().toString();
			String phoneNo = txtboxPhoneNo.getText().toString();
			String citizenId = txtboxCitizenId.getText().toString();
			
			if (!re_pass.equals(pass))
			{
				txtboxReTypePass.setError("This password doesn't match.");
				return ;
			}
			
			RegisterOwnerAsync handler = new RegisterOwnerAsync(cwaitLayout);
			handler.setEmail(email);
			handler.setPassword(pass);
			handler.setFirstname(firstname);
			handler.setLastname(lastname);
			handler.setPhoneNo(phoneNo);
			handler.setCitizenId(citizenId);
			handler.setProcessDataAsyncListener(this);
			handler.execute();			
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(ShopOwnerRegActivity.this, "(Network unavailable)");
			onUnlock();
		}
		
	}
	

	@Override
	public void onProcessEvent(int responseCode, JSONObject response) {
		switch (responseCode) 
		{
			case -1:
				AlertBox.showErrorMessage(ShopOwnerRegActivity.this, "(Network unavailable )");
				break;
				
			case OPCODE.SERVER_SAVE_SUCCESS_RESPONSE:
				onSuccessful();
				break;
	
			case OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE:
				AlertBox.showErrorMessage(ShopOwnerRegActivity.this, "");
				break;
				
			case OPCODE.SERVER_ERROR_DUPLICATE_EMAIL_RESPONSE:
				txtboxEmail.setError("This email is already registered.");
				break;
		}
		onUnlock();
	}
	
	public void onSuccessful()
	{
		AlertBox.showMessageBox(ShopOwnerRegActivity.this, "Successfully","Register Successfully");
		
		SecurePreferences pref = new SecurePreferences(ShopOwnerRegActivity.this, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		
		pref.put(PREFS_CODE.EMAIL, txtboxEmail.getText().toString());
		NavigationService.getInstance().navigate(ShopOwnerRegActivity.this, 
				MainTabActivity.class);
	}
	
	@Override
	public void setData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLock() {
		btnRegister.setClickable(false);
		
	}

	@Override
	public void onUnlock() 
	{
		btnRegister.setClickable(true);
	}

	@Override
	public void onClear() 
	{
		txtboxCitizenId.setError(null);
		txtboxEmail.setError(null);
		txtboxFirstname.setError(null);
		txtboxLastname.setError(null);
		txtboxPassword.setError(null);
		txtboxPhoneNo.setError(null);
		txtboxReTypePass.setError(null);
	}


}
