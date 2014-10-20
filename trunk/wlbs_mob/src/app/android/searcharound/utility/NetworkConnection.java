package app.android.searcharound.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

public class NetworkConnection 
{
	private static NetworkConnection _instance;
	
	private NetworkConnection()
	{
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = 
		    		new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	}
	
	public static NetworkConnection getInstance()
	{
		if (_instance == null)
			_instance = new NetworkConnection();
		return _instance;
	}
	
	public boolean isConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni == null ? false : true;
	}
}
