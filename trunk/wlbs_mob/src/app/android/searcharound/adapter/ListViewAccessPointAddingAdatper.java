package app.android.searcharound.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.model.ShopAccessPoint;

public class ListViewAccessPointAddingAdatper extends BaseAdapter
{
	private Context context;
	private ArrayList<ShopAccessPoint> items;
	private int selected;
	
	public ListViewAccessPointAddingAdatper(Context context)
	{
		this.context = context;
	}
	
	public void setItems(ArrayList<ShopAccessPoint> items)
	{
		this.items = items;
	}
	
	@Override
	public int getCount()
	{
		return items.size();
	}
	
	public int getSelectedPosition()
	{
		return selected;
	}
	
	public ShopAccessPoint getSelectedAccessPoint()
	{
		return items.get(selected);
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
		
		if (convertView != null)
		{
			row = convertView;
		}
		else
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_listview_select_access_point,
					parent, false);
		}
		
		TextView txtName = (TextView) row.findViewById(R.id.txtview_name);
		TextView txtBssid = (TextView) row.findViewById(R.id.txtviwe_bssid);
		CheckBox chkBox = (CheckBox) row.findViewById(R.id.chkbox_access_point);
		chkBox.setClickable(false);
		chkBox.setChecked(false);
		
		if (position == selected)
		{
			chkBox.setChecked(true);
		}
		
		
		final int p = position;
		
		row.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selected = p;
				notifyDataSetChanged();
				//Log.e("position", p+"");			
			}
		});
		
		txtName.setText(items.get(position).name);
		txtBssid.setText(items.get(position).bssid);
		
		return row;
	}

}
