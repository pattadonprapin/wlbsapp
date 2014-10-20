package app.android.searcharound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.SERVER_ADDRESS;

public class ListViewDrawerAdapter extends BaseAdapter
{
	public final static int HOME = 0;
	public final static int PICTURE = 1;
	public final static int REQUEST = 2;
	public final static int PROMOTION = 3;
	public final static int CO_PROMOTION = 4;
	public final static int ACCESS_POINT = 5;
	public final static int SIGN_OUT = 6;
	
	private String shopName;
	private String email;
	private String shopPicPath;
	
	private Context context;
	
	private String [] menu_txt;
	
	private int [] images = {0, R.drawable.pic_ic, R.drawable.request_ic, R.drawable.pro_ic,
			R.drawable.co_pro_ic, R.drawable.ap_ic, R.drawable.signout_ic};
	
	public ListViewDrawerAdapter(Context context)
	{
		this.context = context;
		menu_txt = context.getResources().getStringArray(R.array.nav_drawer_items);
	}
	
	@Override
	public int getCount() 
	{
		return menu_txt.length;
	}
	
	public void setData(String shopName, String email, String shopPicPath)
	{
		this.shopName = shopName;
		this.email = email;
		this.shopPicPath = shopPicPath;
	}

	@Override
	public String getItem(int position) {
		return menu_txt[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null; 
		
		if (position == HOME)
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.shop_info_listview_custom, parent, false);
			
			ImageView imgShop = (ImageView) row.findViewById(R.id.imgViewShop);
			TextView txtViewName = (TextView) row.findViewById(R.id.txtViewShopName);
			TextView txtViewEmail = (TextView) row.findViewById(R.id.txtViewEmail);
			ProgressBar spinner = (ProgressBar) row.findViewById(R.id.imgProgress);
			
			txtViewEmail.setText(email);
			txtViewName.setText(shopName);
			
			String image_url = "http://"+SERVER_ADDRESS.IP+"/"+shopPicPath;	
			
			ImgLoader imgLoader = new ImgLoader(image_url, imgShop, context, spinner);
			imgLoader.setSize(60, 60);
			imgLoader.showImageOnFail(R.drawable.shop_icon);
			imgLoader.load();
		}
		else
		{
			if (convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.menu_listview_custom, parent, false);
			}
			else
			{
				row = convertView;
			}
			ImageView imgIcon = (ImageView) row.findViewById(R.id.imgViewIcon);
			TextView txtMenu = (TextView) row.findViewById(R.id.txtViewMenu);
			
			imgIcon.setImageResource(images[position]);
			txtMenu.setText(menu_txt[position]);
		}
		
		return row;
	}

}
