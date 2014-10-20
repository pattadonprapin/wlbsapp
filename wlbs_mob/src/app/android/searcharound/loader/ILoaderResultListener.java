package app.android.searcharound.loader;

import org.json.JSONObject;


public interface ILoaderResultListener 
{
	public void onProcessEvent(int responseCode, JSONObject response);
}
