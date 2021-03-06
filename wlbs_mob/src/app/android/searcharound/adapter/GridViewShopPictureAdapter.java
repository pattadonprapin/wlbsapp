package app.android.searcharound.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import app.android.searcharound.R;
import app.android.searcharound.activity.PictureFullScreenActivity;
import app.android.searcharound.model.ShopPicture;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.NavigationService;

public class GridViewShopPictureAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<ShopPicture> items;
	
	public GridViewShopPictureAdapter(Context context)
	{
		this.context = context;
	}
	
	public void setData(ArrayList<ShopPicture> items)
	{
		this.items = items;
	}
	
	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return items.get(position).pictureId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = null;
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_gridview_shop_picture, parent, false);
		}
		else
		{
			row = convertView;
		}
		
		ProgressBar spinner = (ProgressBar) row.findViewById(R.id.progressbar_img);
		ImageView imgViewShop = (ImageView) row.findViewById(R.id.imgview_shop);
		//"http://"+SERVER_ADDRESS.IP+"/"+
		String img_url = items.get(position).picturePath;
		ImgLoader img = new ImgLoader(img_url, imgViewShop, context, spinner);
		img.showImageOnFail(R.drawable.error);
		int size = (int)((parent.getWidth() - 13 ) / 3);
		img.setSize(size, size);
		img.load();
		
		
		final int p = position;
		imgViewShop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Bundle param = new Bundle();
				param.putInt("position", p);
				NavigationService.getInstance().navigate(context,
						PictureFullScreenActivity.class, param);
				
			}
		});
		
		return row;
	}
	


}
