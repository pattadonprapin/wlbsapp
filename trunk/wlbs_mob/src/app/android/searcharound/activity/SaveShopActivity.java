package app.android.searcharound.activity;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.SaveShopAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.OPCODE;
import app.android.searcharound.utility.PREFS_CODE;
import app.android.searcharound.utility.SecurePreferences;

public class SaveShopActivity extends Activity implements IActivityDataSetter, IProcessDataAsyncListener
{
	private static final int SELECTED_PICURE = 1;
	
	private String picFilePath;
	private EditText txtboxShopName;
	private EditText txtboxAddress;
	private EditText txtboxPhoneNo;
	private EditText txtboxLatitude;
	private EditText txtboxLongitude;
	private ImageView imgViewShop;
	private ProgressBar spinner;
	
	private LinearLayout cwaitLayout;
	
	private TextView linkGenLocation;
	
	private Button btnSave;
	
	private boolean EDIT_STATE = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_shop);
		
		txtboxShopName = (EditText) findViewById(R.id.txtboxShopName);
		txtboxAddress = (EditText) findViewById(R.id.txtboxAddress);
		txtboxPhoneNo = (EditText) findViewById(R.id.txtboxPhoneNo);
		txtboxLatitude = (EditText) findViewById(R.id.txtboxLatitude);
		txtboxLongitude = (EditText) findViewById(R.id.txtBoxLongitude);
		
		spinner = (ProgressBar) findViewById(R.id.imgProgress);
		linkGenLocation = (TextView) findViewById(R.id.linkGenLocation);
		linkGenLocation.setOnClickListener(new OnClickGenLocationListner());
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickSaveListener());
		
		imgViewShop = (ImageView) findViewById(R.id.imgViewAddShop);
		imgViewShop.setOnClickListener(new OnClickAddPictureListener());
		
		cwaitLayout = (LinearLayout) findViewById(R.id.cwait_layout);
		
		Bundle param = this.getIntent().getExtras();
		if (param != null)
		{
			EDIT_STATE = true;
			setData();
		}
		
	}
	
	class OnClickGenLocationListner implements View.OnClickListener
	{
		private LocationManager manager;
		private LocationListener listener;
		@Override
		public void onClick(View v) {
		    manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			
		    listener = new LocationListener() {
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLocationChanged(Location location) {
					processLocation(location.getLatitude(), location.getLongitude());
					
				}
			};
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 1, 0, listener);
		}
		
		public void processLocation(double lat, double lng)
		{
			txtboxLatitude.setText(lat+"");
			txtboxLongitude.setText(lng+"");
			manager.removeUpdates(listener);
		}
	}
	
	class OnClickSaveListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			onClickSave();		
		}
		
	}
	
	public void onClickSave()
	{
		onLock();
		try
		{
			SecurePreferences pref = new SecurePreferences(SaveShopActivity.this, PREFS_CODE.PREFS_CODE_NAME, 
					PREFS_CODE.PRIVATE_KEY, true);
			
			String ownerId = pref.getString(PREFS_CODE.OWNER_ID);
			
			if (ownerId == null)
			{
				NavigationService.getInstance().navigate(SaveShopActivity.this, MainActivity.class);
			}
			
			String shopName = txtboxShopName.getText().toString();
			String phoneNumber = txtboxPhoneNo.getText().toString();
			String latitude = txtboxLatitude.getText().toString();
			String longitude = txtboxLongitude.getText().toString();
			String address = txtboxAddress.getText().toString();
			
			File file = null;
			
			if (picFilePath != null)
				file = new File(picFilePath);
			
			SaveShopAsync handler = new SaveShopAsync(cwaitLayout);
			
			if (EDIT_STATE)
			{
				Bundle param = this.getIntent().getExtras();
				int shopId = param.getInt("ShopId");
				handler.setShopId(shopId);
			}
			handler.setShopName(shopName);
			handler.setPhoneNo(phoneNumber);
			handler.setLatitude(latitude);
			handler.setLongitude(longitude);
			handler.setAddress(address);
			handler.setPictureFile(file);
			handler.setShopType(1);
			handler.setOwnerId(Integer.parseInt(ownerId));	
			handler.setProcessDataAsyncListener(this);
			handler.execute();
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(SaveShopActivity.this, "(Network unavailable)");
			onUnlock();
		}
		
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response) 
	{
		switch (responseCode) 
		{
			case -1:
				AlertBox.showErrorMessage(SaveShopActivity.this, "(Network unavailable)");
				break;
			case OPCODE.SERVER_SAVE_SUCCESS_RESPONSE:
				if (EDIT_STATE)
					NavigationService.getInstance().navigate(SaveShopActivity.this, MainShopViewActivity.class);
				else
					NavigationService.getInstance().navigate(SaveShopActivity.this, SelectShopActivity.class);
					
				onClear();
				break;

			case OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE:
				AlertBox.showMessageBox(SaveShopActivity.this, "Unsuccesfully",
						"Some input data has wrong format !");
				break;
		}	
		onUnlock();
	}
	
	class OnClickAddPictureListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			Intent i = new Intent(Intent.ACTION_PICK, 
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, SELECTED_PICURE);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case SELECTED_PICURE:
				if (resultCode == RESULT_OK)
				{
					Uri uri = data.getData();
					String projection [] = {MediaStore.Images.Media.DATA};
					
					Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
					cursor.moveToFirst();
					
					int columnIndex = cursor.getColumnIndex(projection[0]);
					String filePath = cursor.getString(columnIndex);
					this.picFilePath = filePath;
					cursor.close();
					
					Bitmap bitmap = BitmapFactory.decodeFile(filePath);
					//Bitmap bitmapResize = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
				
					Drawable d = new BitmapDrawable(getResources(), bitmap);
				
					imgViewShop.setImageDrawable(d);
				}
			break;

		default:
			break;
		}
	}

	@Override
	public void onLock() {
		btnSave.setClickable(false);
		imgViewShop.setClickable(false);
	}

	@Override
	public void onUnlock() {
		btnSave.setClickable(true);
		imgViewShop.setClickable(true);	
	}

	@Override
	public void onClear() {
		txtboxShopName.setText("");
		txtboxAddress.setText("");
		txtboxPhoneNo.setText("");
		txtboxLatitude.setText("");
		txtboxLongitude.setText("");
		
		imgViewShop.setImageResource(R.drawable.insert_image);
		
	}

	@Override
	public void setData() 
	{
		Bundle param = this.getIntent().getExtras();
		
		String shopName = param.getString("ShopName");
		String phoneNo = param.getString("PhoneNumber");
		String address = param.getString("Address");
		String latitude = param.getString("Latitude");
		String longitude = param.getString("Longitude");
		String imgURL = param.getString("ImageURL");
		
		txtboxShopName.setText(shopName);
		txtboxPhoneNo.setText(phoneNo);
		txtboxAddress.setText(address);
		txtboxLatitude.setText(latitude);
		txtboxLongitude.setText(longitude);
		
		ImgLoader imgLoader = new ImgLoader(imgURL, imgViewShop, this, spinner);
		imgLoader.showImageOnFail(R.drawable.insert_image);
		imgLoader.load();
		
	}
	
	
}
