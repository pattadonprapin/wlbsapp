package app.android.searcharound.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertBox 
{
	public static void showMessageBox(Context context, String title, String msg)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setCancelable(false);
		
		dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		dialog.create().show();
	}
	
	public static void showErrorMessage(Context context, String msg)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Error");
		dialog.setMessage("Error occured "+ msg + ". Please, try again.");
		dialog.setCancelable(false);
		
		dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		dialog.create().show();
	}
	
	public static void showConfirmMessage(Context context, String title, String msg, 
			OnClickListener pos_listener, OnClickListener nag_listerner)
	{
		AlertDialog dialog = new AlertDialog.Builder(context).create();
	    dialog.setTitle(title);
	    dialog.setMessage(msg);
	    dialog.setCancelable(false);
	    
	    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm", pos_listener);
	    if (nag_listerner == null)
	    {
	    	dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
	    }
	    else
	    {
	    	dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", nag_listerner);
	    }
	    dialog.show();
	}
}
