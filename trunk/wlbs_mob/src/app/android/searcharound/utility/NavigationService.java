package app.android.searcharound.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NavigationService 
{
	private static NavigationService _instance;
	
	private NavigationService(){}
	
	public static NavigationService getInstance()
	{
		if (_instance == null)
			_instance = new NavigationService();
		return _instance;
	}
	
	public void navigate(Context context, Class<?> toClass)
	{
		context.startActivity(new Intent(context, toClass));
	}
	
	public void navigate(Context context, Class<?> toClass, Bundle param)
	{
		Intent intent = new Intent();
		if (param != null)
			intent.putExtras(param);
		
		intent.setClass(context, toClass);
		
		context.startActivity(intent);
	}
	
	
}
