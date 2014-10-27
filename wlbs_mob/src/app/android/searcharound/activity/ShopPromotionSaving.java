package app.android.searcharound.activity;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import app.android.searcharound.R;
import app.android.searcharound.common.OPCODE;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.SaveShopPromotionAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.SecurePreferences;

public class ShopPromotionSaving extends Activity implements InterfaceManager,
	IProcessDataAsyncListener
{
	private static final int SELECTED_PICURE = 1;
	private String selectedPicPath;
	
	private LinearLayout waitingLayout;
	private ProgressBar progressStatus;
	private ImageView imgPromotion;
	private EditText txtboxName;
	private EditText txtboxDetail;
	
	private Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setTitle("Add new promotion");
		setContentView(R.layout.activity_shop_promotion_saving);
		
		waitingLayout = (LinearLayout) findViewById(R.id.layout_waiting);
		progressStatus = (ProgressBar) findViewById(R.id.progressbar_status);
		
		txtboxName = (EditText) findViewById(R.id.txtbox_name);
		txtboxDetail = (EditText) findViewById(R.id.txtbox_detail);
		
		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickSavePromotion());
		
		imgPromotion = (ImageView) findViewById(R.id.imgview_promotion);
		imgPromotion.setOnClickListener(new OnClickImageListener());
	}

	
	@Override
	public void setData() 
	{
		
		
	}

	@Override
	public void onLock() 
	{
		btnSave.setClickable(false);
	}

	@Override
	public void onUnlock() 
	{
		btnSave.setClickable(true);	
	}

	@Override
	public void onClear() 
	{
		txtboxDetail.setText("");
		txtboxName.setText("");	
		imgPromotion.setImageResource(R.drawable.insert_image);
	}
	
	class OnClickImageListener implements OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			Intent i = new Intent(Intent.ACTION_PICK, 
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, SELECTED_PICURE);
			
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == SELECTED_PICURE && resultCode == RESULT_OK)
		{
			if (resultCode == RESULT_OK)
			{
				Uri uri = data.getData();
				String projection [] = {MediaStore.Images.Media.DATA};
				
				Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
				cursor.moveToFirst();
				
				int columnIndex = cursor.getColumnIndex(projection[0]);
				String filePath = cursor.getString(columnIndex);
				this.selectedPicPath = filePath;
				cursor.close();
				
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				
				//Bitmap bitmapResize = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
				
				Drawable d = new BitmapDrawable(getResources(), bitmap);
				
				imgPromotion.setImageDrawable(d);			
			
			}
		}
	}
	
	class OnClickSavePromotion implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			onClickSavePromotion();
		}
		
	}
	
	public void onClickSavePromotion()
	{
		onLock();
		try
		{
			SecurePreferences pref = new SecurePreferences(this, PREFS_CODE.PREFS_CODE_NAME, 
					PREFS_CODE.PRIVATE_KEY, true);
			
			String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
	
			
			if (shopId_str != null)
			{
				int shopId = Integer.parseInt(shopId_str);
				String name = txtboxName.getText().toString();
				String detail = txtboxDetail.getText().toString();
				
				SaveShopPromotionAsync handler = new SaveShopPromotionAsync(waitingLayout);
				handler.setName(name);
				handler.setShopId(shopId);
				handler.setDetail(detail);
				if (selectedPicPath != null)
					handler.setPicture(new File(selectedPicPath));
				else
					handler.setPicture(null);
				handler.setType(SaveShopPromotionAsync.ADD);
				handler.setProgressBar(progressStatus);
				handler.setProcessDataAsynListener(this);
				handler.execute();
			}
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this, "");
		}
	}
	
	@Override
	public void onProcessEvent(int responseCode, JSONObject response)
	{
		switch (responseCode) 
		{
			case -1:
				AlertBox.showErrorMessage(this, "(Network unavailable)");
				break;
	
			case OPCODE.SERVER_SAVE_SUCCESS_RESPONSE:
				AlertBox.showErrorMessage(this, "YES");
				break;
				
			case OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE:
				AlertBox.showErrorMessage(this, "(Save unsuccessfully)");
				break;
		}
		onClear();
		onUnlock();
	}

}
