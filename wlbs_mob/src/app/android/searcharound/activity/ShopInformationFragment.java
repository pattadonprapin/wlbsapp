package app.android.searcharound.activity;

import org.json.JSONObject;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import app.android.searcharound.R;
import app.android.searcharound.loader.ILoaderResultListener;
import app.android.searcharound.loader.LoadShopInfoAsync;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.PREFS_CODE;
import app.android.searcharound.utility.SERVER_ADDRESS;
import app.android.searcharound.utility.SecurePreferences;

public class ShopInformationFragment extends Fragment implements IActivityDataSetter, ILoaderResultListener
{
	private ProgressBar spinner;
	private LinearLayout cwaitLayout;
	private ImageView imgViewShop;
	private TextView txtViewShopName;
	private TextView txtViewAddress;
	private TextView txtViewLocation;
	private TextView txtViewContact;
	private TextView txtViewEdit;
	private ImageView imgViewEdit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View view = inflater.inflate(R.layout.shop_info_layout, container, false);
		
		spinner = (ProgressBar) view.findViewById(R.id.imgProgress);
		cwaitLayout = (LinearLayout) view.findViewById(R.id.cwait_layout);
		imgViewShop = (ImageView) view.findViewById(R.id.imgViewShop);
		txtViewShopName = (TextView) view.findViewById(R.id.txtViewShopName);
		txtViewAddress = (TextView) view.findViewById(R.id.txtViewAddress);
		txtViewLocation = (TextView) view.findViewById(R.id.txtViewLocation);
		txtViewContact = (TextView) view.findViewById(R.id.txtViewContact);
		txtViewEdit = (TextView) view.findViewById(R.id.txtViewEdit);
		imgViewEdit = (ImageView) view.findViewById(R.id.imgViewEdit);
		
		txtViewEdit.setOnClickListener(new onClickEditInfoListener());
		
		setData();
		
		Toast.makeText(this.getActivity(), "OPEN", Toast.LENGTH_LONG).show();
		return view;
	}

	@Override
	public void setData() 
	{
		SecurePreferences pref = new SecurePreferences(this.getActivity(), PREFS_CODE.PREFS_CODE_NAME,
				PREFS_CODE.PRIVATE_KEY, true);
		
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			int shopId = Integer.parseInt(shopId_str);
			LoadShopInfoAsync loader = new LoadShopInfoAsync(shopId, cwaitLayout);
			loader.setLoaderResultListener(this);
			loader.execute();
		}
		
	}

	@Override
	public void onProcessEvent(int responseCode, JSONObject response) 
	{
		try
		{
			JSONObject shop = response.getJSONObject("Shop");
			String shopName = shop.getString("Name");
			String address = shop.getString("Address");
			String contact = shop.getString("PhoneNumber");
			String location = shop.getString("Latitude") + ", " + shop.getString("Longitude");
			String picPath = shop.getString("PicturePath");
			
			txtViewShopName.setText(shopName);
			txtViewAddress.setText(address);
			txtViewContact.setText(contact);
			txtViewLocation.setText(location);
			
			String img_url = "http://"+SERVER_ADDRESS.IP+"/"+picPath;
			ImgLoader imgLoader = new ImgLoader(img_url, imgViewShop, this.getActivity(), spinner);
			imgLoader.showImageOnFail(R.drawable.insert_image);
			imgLoader.load();
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "(Network unavailable)");
		}
	}
	
	@Override
	public void onLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClear() {
		// TODO Auto-generated method stub
		
	}
	
	class onClickEditInfoListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			onClickEditInfo();
			
		}

		
	}
	
	public void onClickEditInfo()
	{
		
	}

	
}
