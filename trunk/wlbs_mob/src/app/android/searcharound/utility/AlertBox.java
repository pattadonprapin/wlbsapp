package app.android.searcharound.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertBox 
{
	public static void showMessageBox(Context context, String title, String msg)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		//dialog.setCancelable(true);
		
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
		//dialog.setCancelable(true);
		
		dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		dialog.create().show();
	}
}
