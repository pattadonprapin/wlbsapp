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
import android.widget.TextView;
import app.android.searcharound.R;
import app.android.searcharound.dialog.PromotionEditingDialog;
import app.android.searcharound.model.ShopPromotion;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.ImgLoader;
import app.android.searcharound.utility.ScaleImageView;

public class FullScreenPromotionAdapter extends PagerAdapter
{
	private ArrayList<ShopPromotion> items;
	private Context context;
	private ViewPager pager;
	
	public FullScreenPromotionAdapter(Context context, ViewPager pager)
	{
		this.context = context;
		this.pager = pager;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
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
	
	public void setItems(ArrayList<ShopPromotion> items)
	{
		this.items = items;
	}
	
	public ShopPromotion getItem(int position)
	{
		return items.get(position);
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (View)object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.custom_fullscreen_promotion, container, false);
		
		ScaleImageView imgView = (ScaleImageView) view.findViewById(R.id.imgview_full_screen);
	
		ImgLoader imgLoader = new ImgLoader(items.get(position).picPath, imgView, context, null);
		imgLoader.showImageOnFail(R.drawable.no_img_ic);
		imgLoader.load();
		
		TextView txtViewDetail = (TextView) view.findViewById(R.id.txtview_detail);
		txtViewDetail.setText(items.get(position).detail);
		
		final int pt = position;
		
		ImageView imgRemove = (ImageView) view.findViewById(R.id.imgview_remove);
		
		imgRemove.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				onRemovePromotion(pt);
			}
		});

		final String name = items.get(position).name;
		final String detail = items.get(position).detail;
		
		ImageView imgEdit = (ImageView) view.findViewById(R.id.imgview_edit);
		imgEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onEditPromotion(pt, name, detail);
				
			}
		});
		
		((ViewPager) container).addView(view);
		return view;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
 
    }
	
	public void onEditPromotion(int position, String name, String detail)
	{
		PromotionEditingDialog dialog = new PromotionEditingDialog(context);
		dialog.setData(items.get(position));
		dialog.show();
//		LayoutInflater inflater = LayoutInflater.from(context);
//		
//		
//		final View view = inflater.inflate(R.layout.custom_dialog_promotion_editing, null);
//		final EditText txtboxName = (EditText) view.findViewById(R.id.txtbox_name);
//		final EditText txtboxDetail = (EditText) view.findViewById(R.id.txtbox_detail);
//		final int pt = position;
//		txtboxName.setText(name);
//		txtboxDetail.setText(detail);
		
//		AlertBox.showInputMessageBox(context, "Edit Promotion",
//				new OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) 
//					{
//						try
//						{
//							ShopOwnerService service = new ShopOwnerService();
//							int pId = items.get(pt).id;
//							String name = txtboxName.getText().toString();
//							String detail = txtboxDetail.getText().toString();
//							service.editPromotion(pId, name, detail);
//						}
//						catch (Exception e)
//						{
//							AlertBox.showErrorMessage(context, "(Network unavailable)");
//						}
//						
//					}
//				}, null
//				, view);
	}
	
	public void onRemovePromotion(int position)
	{
		try
		{			
			 final int pt = position;
			 AlertBox.showConfirmMessage(context, "Confirmation", 
					 "Are you sure you want to delete this shop?", 
					 new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try
							{
								ShopOwnerService service = new ShopOwnerService();
								service.removePromotion(items.get(pt).id);	
								removeItem(pt);
							}
							catch (Exception e)
							{
								AlertBox.showErrorMessage(context, "(Network unavailable)");
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
