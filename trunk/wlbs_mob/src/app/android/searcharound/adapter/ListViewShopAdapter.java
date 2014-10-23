package app.android.searcharound.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import app.android.searcharound.R;
import app.android.searcharound.common.SERVER_ADDRESS;
import app.android.searcharound.model.ListViewShop;
import app.android.searcharound.utility.ImgLoader;

public class ListViewShopAdapter extends BaseAdapter
{
	public final static int ADD_POSITION = -1;
	
	private Context context;
	private ArrayList<ListViewShop> items;
		
	public ListViewShopAdapter(Context context) 
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public ListViewShop getItem(int position) {
		
		return items.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return items.get(position).id;
	}
	
	public void setData(ArrayList<ListViewShop> items)
	{
		this.items = items;
	}
	
	public void removeItem(int position)
	{
		items.remove(position);
		notifyDataSetChanged();
	}
	
	private OnClickRemoveCallBack callback;
	public void setOnClickRemoveCallBack(OnClickRemoveCallBack callback)
	{
		this.callback = callback;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;
		
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_listview_shop_selecting, parent, false);
		}
		else
		{
			row = convertView;
		}
		
		TextView txtView = (TextView) row.findViewById(R.id.txtview_shop_name);
		ImageView imgView = (ImageView) row.findViewById(R.id.imgview_shop);
		ProgressBar spinner = (ProgressBar) row.findViewById(R.id.progressbar_img);
		ImageView imgBin = (ImageView) row.findViewById(R.id.imgview_remove);
		
		final int p = position;
		imgBin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callback.processOnRemoveCallback(p);
				
			}
		});

		txtView.setText(items.get(position).name);	
		
		if (items.get(position).id == ADD_POSITION)
		{
			imgView.setImageResource(R.drawable.add);
			spinner.setVisibility(View.GONE);
			imgBin.setVisibility(View.GONE);
		}
		else
		{				
			imgBin.setVisibility(View.VISIBLE);
			txtView.setText(items.get(position).name);	
			
			try
			{
				String image_url = "http://"+SERVER_ADDRESS.IP+"/"+items.get(position).picturePath;	
				
				ImgLoader imgLoader = new ImgLoader(image_url, imgView, context, spinner);
				imgLoader.setSize(50, 50);
				imgLoader.showImageOnFail(R.drawable.shop_icon);
				imgLoader.load();
			}
			catch (Exception e)
			{
				Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
			}
			
			
		}
		
		
		return row;
	}
	
	public interface OnClickRemoveCallBack
	{
		public void processOnRemoveCallback(int position);
	}
	
}	