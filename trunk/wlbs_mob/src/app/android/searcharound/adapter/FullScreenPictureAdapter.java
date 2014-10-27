package app.android.searcharound.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.android.searcharound.R;
import app.android.searcharound.model.ShopPicture;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.ScaleImageView;

public class FullScreenPictureAdapter extends PagerAdapter 
{
	private ArrayList<ShopPicture> items;
	private ViewPager pager;
	private Context context;
	
	public FullScreenPictureAdapter(Context context, ViewPager pager)
	{
		this.context = context;
		this.pager = pager;
	}
	
	@Override
	public int getCount() 
	{
		return items.size();
	}

	public ShopPicture getItem(int position)
	{
		return items.get(position);
	}
	
	public void setItems(ArrayList<ShopPicture> items)
	{
		this.items = items;
	}
	
	public void removeItem(int position)
	{
		items.remove(position);
		notifyDataSetChanged();
		pager.invalidate();
		
		if (position - 1 >= 0)
			pager.setCurrentItem(position - 1, true);
		else if (position + 1 <= items.size())
			pager.setCurrentItem(position + 1, true);
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) 
	{
		return view == (View)object;
	}

	
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    View view = inflater.inflate(R.layout.custom_fullscreen_picture, container, false);
		
	    ScaleImageView imgView = (ScaleImageView) view.findViewById(R.id.imgview_full_screen);
		
		ImgLoader imgLoader = new ImgLoader(items.get(position).picturePath, imgView, context, null);
		imgLoader.showImageOnFail(R.drawable.no_img_ic);
		imgLoader.load();
		
		final int pt = position;
		
		ImageView imgRemove = (ImageView) view.findViewById(R.id.imgview_remove);
		imgRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
										service.removePicture(items.get(pt).pictureId);	
										removeItem(pt);
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
		});
		
		((ViewPager) container).addView(view);
		
		
		return view;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}
