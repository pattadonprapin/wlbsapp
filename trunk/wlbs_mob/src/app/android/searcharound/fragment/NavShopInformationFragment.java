package app.android.searcharound.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.LinearLayout;
//import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import app.android.searcharound.R;
import app.android.searcharound.activity.ShopSavingActivity;
import app.android.searcharound.adapter.ListViewCommentAdapter;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.loader.IProcessDataAsyncListener;
import app.android.searcharound.loader.LoadShopCommentAsync;
import app.android.searcharound.loader.LoadShopInfoAsync;
import app.android.searcharound.model.ShopComment;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.NavigationService;
import app.android.searcharound.utility.SecurePreferences;

public class NavShopInformationFragment extends Fragment implements InterfaceManager, IProcessDataAsyncListener
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
	private ListView listviewComment;
	private ListViewCommentAdapter listviewCommentAdapter;
	
	private String imgURL = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View view = inflater.inflate(R.layout.fragment_shop_information, container, false);
		
		spinner = (ProgressBar) view.findViewById(R.id.progressbar_img);
		cwaitLayout = (LinearLayout) view.findViewById(R.id.layout_waiting);
		imgViewShop = (ImageView) view.findViewById(R.id.imgview_shop);
		txtViewShopName = (TextView) view.findViewById(R.id.txtview_shop_name);
		txtViewAddress = (TextView) view.findViewById(R.id.txtview_address);
		txtViewLocation = (TextView) view.findViewById(R.id.txtview_location);
		txtViewContact = (TextView) view.findViewById(R.id.txtview_contact);
		txtViewEdit = (TextView) view.findViewById(R.id.txtview_edit);
		imgViewEdit = (ImageView) view.findViewById(R.id.imgview_edit);
		
		listviewComment = (ListView) view.findViewById(R.id.listview_comment);
		listviewCommentAdapter = new ListViewCommentAdapter(this.getActivity());
		
		OnClickEditInfoListener listener = new OnClickEditInfoListener();
		txtViewEdit.setOnClickListener(listener);
		imgViewEdit.setOnClickListener(listener);
		imgViewShop.setOnClickListener(listener);
		setData();
		
		Toast.makeText(this.getActivity(), "OPEN", Toast.LENGTH_LONG).show();
		return view;
	}

	@Override
	public void setData() 
	{
		onLock();
		SecurePreferences pref = new SecurePreferences(this.getActivity(), PREFS_CODE.PREFS_CODE_NAME,
				PREFS_CODE.PRIVATE_KEY, true);
		
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			int shopId = Integer.parseInt(shopId_str);
			LoadShopInfoAsync loader = new LoadShopInfoAsync(shopId, null);
			loader.setProcessDataAsyncListener(this);
			loader.execute();
			
			LoadShopCommentAsync loader2 = new LoadShopCommentAsync(cwaitLayout);
			loader2.setShopId(shopId);
			loader2.setProcessDataAsyncListener(new OnProcessDataFromComment());
			loader2.execute();
			
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
			
			//String img_url = "http://"+SERVER_ADDRESS.IP+"/"+picPath;
			this.imgURL = picPath;
			ImgLoader imgLoader = new ImgLoader(picPath, imgViewShop, this.getActivity(), spinner);
			imgLoader.showImageOnFail(R.drawable.insert_image);
			imgLoader.load();
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(this.getActivity(), "(Network unavailable)");
		}
		onUnlock();
	}
	
	class OnProcessDataFromComment implements IProcessDataAsyncListener
	{

		@Override
		public void onProcessEvent(int responseCode, JSONObject response) 
		{
			try
			{
				JSONArray comments = response.getJSONArray("Comment");
				
				ArrayList<ShopComment> items = new ArrayList<ShopComment>();
				
				for (int i = 0; i < comments.length(); i++)
				{
					JSONObject comment = comments.getJSONObject(i);
					
					ShopComment item = new ShopComment();
					item.commentId = comment.getInt("CommentId");
					item.shopId = comment.getInt("ShopId");
					item.text = comment.getString("Text");
					item.dateTime = comment.getString("RecordDate");
					
					items.add(item);
				}
				
				listviewCommentAdapter.setItems(items);
				listviewComment.setAdapter(listviewCommentAdapter);
			}
			catch (Exception e)
			{
				AlertBox.showErrorMessage(NavShopInformationFragment.this.getActivity(), 
						"(Network unavailable)");
			}
			
		}
		
	}
	
	@Override
	public void onLock() {
		imgViewEdit.setClickable(false);
		txtViewEdit.setClickable(false);
		imgViewShop.setClickable(false);
	}

	@Override
	public void onUnlock() {
		imgViewEdit.setClickable(true);
		txtViewEdit.setClickable(true);
		imgViewShop.setClickable(true);
	}

	@Override
	public void onClear() {
		// TODO Auto-generated method stub
		
	}
	
	class OnClickEditInfoListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			onClickEditInfo();
		}

		
	}
	
	public void onClickEditInfo()
	{
		SecurePreferences pref = new SecurePreferences(this.getActivity(), PREFS_CODE.PREFS_CODE_NAME,
				PREFS_CODE.PRIVATE_KEY, true);
		
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		
		Bundle param = new Bundle();
		param.putInt("ShopId", Integer.parseInt(shopId_str));
		param.putString("ShopName", txtViewShopName.getText().toString());
		param.putString("PhoneNumber", txtViewContact.getText().toString());
		param.putString("Address", txtViewAddress.getText().toString());
		String spt[] = txtViewLocation.getText().toString().split(", ");
		param.putString("Latitude", spt[0]);
		param.putString("Longitude", spt[1]);
		param.putString("ImageURL", imgURL);
		
		NavigationService.getInstance().navigate(this.getActivity(), 
				ShopSavingActivity.class, param);
	}

	
}
