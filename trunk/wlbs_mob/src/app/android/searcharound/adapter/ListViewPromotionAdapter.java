package app.android.searcharound.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.activity.PromotionFullScreenActivity;
import app.android.searcharound.model.ShopPromotion;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.NavigationService;

public class ListViewPromotionAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<ShopPromotion> items;
	
	
	public ListViewPromotionAdapter(Context context)
	{
		this.context = context;
	}

	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public ShopPromotion getItem(int position)
	{
		return items.get(position);
	}
	
	public void setItem(ArrayList<ShopPromotion> items)
	{
		this.items = items;
	}

	public void removeItem(int position)
	{
		this.items.remove(position);
		notifyDataSetChanged();
	}
	
	@Override
	public long getItemId(int position) 
	{
		return items.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = null;
		if (convertView != null)
		{
			row = convertView;
		}
		else
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_listview_promotion, parent, false);
		}
		
		ProgressBar spinner = (ProgressBar) row.findViewById(R.id.progressbar_status);
		ImageView imagePromotion = (ImageView) row.findViewById(R.id.imgview_promotion);	
		TextView name = (TextView) row.findViewById(R.id.txtview_name);
		TextView detail = (TextView) row.findViewById(R.id.txtview_detail);
		TextView date = (TextView) row.findViewById(R.id.txtview_date);
		TextView time = (TextView) row.findViewById(R.id.txtview_time);
		ImageView imageRemove = (ImageView) row.findViewById(R.id.imgview_remove);
		
		final int p = position;
		imageRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 AlertBox.showConfirmMessage(context, "Confirmation", 
						 "Are you sure you want to delete this shop?", 
						 new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								callback.processOnRemoveCallback(p);
							}
						}, null);	
				
				
			}
		});
		
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle param = new Bundle();
				param.putInt("position", p);
				
				NavigationService.getInstance().navigate(context, 
						PromotionFullScreenActivity.class, param);
			}
		});
		
		ShopPromotion item = items.get(position);
		
		name.setText(item.name);
		detail.setText(item.detail);
		date.setText(item.date);
		time.setText(item.time);
		
		ImgLoader loader = new ImgLoader(item.picPath, imagePromotion, context, spinner);
		loader.setSize(70, 70);
		loader.showImageOnFail(R.drawable.no_img_ic);
		loader.load();
		
		
		
		return row;
	}
	
	private OnClickRemoveCallBack callback;
	public void setOnClickRemoveCallBack(OnClickRemoveCallBack callback)
	{
		this.callback = callback;
	}
	
	public interface OnClickRemoveCallBack
	{
		public void processOnRemoveCallback(int position);
	}
}
