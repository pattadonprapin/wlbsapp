package app.android.searcharound.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.model.ShopComment;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;

public class ListViewCommentAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<ShopComment> items;
	
	public ListViewCommentAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ShopComment getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return items.get(position).commentId;
	}

	public void setItems(ArrayList<ShopComment> items)
	{
		this.items = items;
	}
	
	public void removeItem(int position)
	{
		items.remove(position);
		notifyDataSetChanged();
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
			row = inflater.inflate(R.layout.custom_listview_comment, parent, false);
		}
		
		TextView txtComment = (TextView) row.findViewById(R.id.txtview_comment);
		TextView txtDateTime = (TextView) row.findViewById(R.id.txtview_datetime);
		
		txtComment.setText(items.get(position).text);
		txtDateTime.setText(items.get(position).dateTime);
		
		ImageView btnRemove = (ImageView) row.findViewById(R.id.imgview_remove);
		
		final int p = position;
		btnRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickRemove(p);
				
			}
		});
		
		return row;
	}
	
	private void onClickRemove(int position)
	{
		final int p = position;
		try
		{		
			 AlertBox.showConfirmMessage(context, "Confirmation", 
					 "Are you sure you want to delete this shop?", 
					 new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try
							{
								ShopOwnerService service = new ShopOwnerService();
								service.removeComment(items.get(p).commentId);	
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
