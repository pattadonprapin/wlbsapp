package app.android.searcharound.fragment;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.activity.ShopOwnerRegActivity;
import app.android.searcharound.activity.ShopSelectingActivity;
import app.android.searcharound.common.OPCODE;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadAuthInfoAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class TabShopOwnerFragment extends Fragment implements InterfaceManager, IProcessDataAsyncListener{

	private Button btnSignIn;
	private TextView linkReg;
	private LinearLayout cwaitLayout;
	private EditText txtboxEmail;
	private EditText txtboxPassword;

	private Context context;

	public TabShopOwnerFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tab_shop_owner, container,
				false);

		linkReg = (TextView) view.findViewById(R.id.txtview_reg);
		linkReg.setOnClickListener(new OnClickRegisterListener());

		btnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
		btnSignIn.setOnClickListener(new OnClickSignInListener());

		cwaitLayout = (LinearLayout) view.findViewById(R.id.layout_waiting);
		
		txtboxEmail = (EditText) view.findViewById(R.id.txtbox_email);
		txtboxPassword = (EditText) view.findViewById(R.id.txtbox_password);

		setData();
	
		return view;
	}

	class OnClickRegisterListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			onClickRegister();
		}

	}
	
	public void onClickRegister()
	{
		NavigationService.getInstance().navigate(context, ShopOwnerRegActivity.class);
	}

	class OnClickSignInListener implements View.OnClickListener {

		@Override
		public void onClick(View view)
		{	
			onClickSignIn();
		}
	}
	
	public void onClickSignIn()
	{
		onLock();
		try
		{
			String email = txtboxEmail.getText().toString();
			String password = txtboxPassword.getText().toString();
			
			LoadAuthInfoAsync loader = new LoadAuthInfoAsync(email, password, cwaitLayout);
			loader.setProcessDataAsyncListener(this);
			loader.execute();
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(context, "(Network unavailable)");
			onUnlock();
		}
	}

	@Override
	public void onProcessEvent(int responseCode, JSONObject response) {
		try
		{
			switch (responseCode) 
			{
				case -1:
					AlertBox.showErrorMessage(context, "(Network unavailable)");
					break;
				case OPCODE.SERVER_AUTHENTICATION_INACTIVED_RESPONSE:
					AlertBox.showMessageBox(context, "Warning",
							"User is not activated. Please wait for checking");
					break;
		
				case OPCODE.SERVER_AUTHENTICATION_INVALID_RESPONSE:
					AlertBox.showMessageBox(context, "Invalid",
							"Email or Password is not valid");
					break;
		
				case OPCODE.SERVER_AUTHENTICATION_VALID_RESPONSE:
						String email = txtboxEmail.getText().toString();
						int ownerId = Integer.parseInt(response.getString("OwnerId"));
						doValidAuth(email, ownerId);
					break;		
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(context, "");		
		}
		onUnlock();
	}
	
	public void doValidAuth(String email, int ownerId)
	{
		SecurePreferences pref = new SecurePreferences(context, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		
		pref.put(PREFS_CODE.AUTH_STATUS, "true");
		pref.put(PREFS_CODE.EMAIL, email);
		pref.put(PREFS_CODE.OWNER_ID, ownerId+"");
		
		NavigationService.getInstance().navigate(context, ShopSelectingActivity.class);
	}

	@Override
	public void setData() {
		SecurePreferences pref = new SecurePreferences(context, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		
		if (pref.getString(PREFS_CODE.EMAIL) != null)
		{
			txtboxEmail.setText(pref.getString(PREFS_CODE.EMAIL));
		}
	}

	@Override
	public void onLock() {
		btnSignIn.setClickable(false);	
	}

	@Override
	public void onUnlock() {
		btnSignIn.setClickable(true);
	}

	@Override
	public void onClear() {
		txtboxPassword.setText("");
		
	}

	

}
