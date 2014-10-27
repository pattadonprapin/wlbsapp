package app.android.searcharound.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.model.ShopAccessPoint;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;

public class ListViewAccessPointAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<ShopAccessPoint> items;
	
	public ListViewAccessPointAdapter(Context context)
	{
		this.context = context;
	}
	
	public void setItems(ArrayList<ShopAccessPoint> items)
	{
		this.items = items;
	}
	
	public void removeItem(int position)
	{
		items.remove(position);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public ShopAccessPoint getItem(int position) 
	{
		return items.get(position);
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
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_listview_access_point, parent, false);
		}
		else
		{
			row = convertView;
		}
		
		TextView txtViewSsid = (TextView) row.findViewById(R.id.txtview_ssid);
		TextView txtViewBssid = (TextView) row.findViewById(R.id.txtview_bssid);
		ImageView imgViewRemove = (ImageView) row.findViewById(R.id.imgview_remove);
		
		txtViewSsid.setText(items.get(position).name);
		txtViewBssid.setText(items.get(position).bssid);
		
		final int p = position;
		imgViewRemove.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				onClickRemove(p);
			}
		});
		
		return row;
	}
	
	public void onClickRemove(int position)
	{
		final int p = position;
		try
		{		
			 AlertBox.showConfirmMessage(context, "Confirmation", 
					 "Are you sure you want to delete this access point?", 
					 new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try
							{
								ShopOwnerService service = new ShopOwnerService();
								service.removeAccessPoint(items.get(p).id);
								removeItem(p);
							}
							catch (Exception e)
							{
								AlertBox.showErrorMessage(context, "(Network Unavailable)");
							}
						}
					}, null);	
			 
			
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(context, "(Network unavailable)");
		}
	}

}
