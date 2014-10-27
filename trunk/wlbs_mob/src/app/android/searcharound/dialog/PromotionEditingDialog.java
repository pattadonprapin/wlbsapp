package app.android.searcharound.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import app.android.searcharound.R;
import app.android.searcharound.model.ShopPromotion;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;

public class PromotionEditingDialog implements DialogInterface.OnClickListener
{
	private Context context;
	private ShopPromotion data;
	
	private View view;
	private EditText txtboxName;
	private EditText txtboxDetail;
	
	public PromotionEditingDialog(Context context)
	{
		this.context = context;
	}
	
	public void setData(ShopPromotion data)
	{
		this.data = data;
	}
	
	public void show()
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_promotion_editing, null);
		txtboxName = (EditText) view.findViewById(R.id.txtbox_name);
		txtboxDetail = (EditText) view.findViewById(R.id.txtbox_detail);
		
		txtboxName.setText(data.name);
		txtboxDetail.setText(data.detail);
		
		AlertBox.showInputMessageBox(context, "Edit Promotion", this, null, view);
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		try
		{
			ShopOwnerService service = new ShopOwnerService();
			int pId = data.id;
			String name = txtboxName.getText().toString();
			String detail = txtboxDetail.getText().toString();
			service.editPromotion(pId, name, detail);
		}
		catch (Exception e)
		{
			AlertBox.showErrorMessage(context, "(Network unavailable)");
		}
		
	}
	
	
}
