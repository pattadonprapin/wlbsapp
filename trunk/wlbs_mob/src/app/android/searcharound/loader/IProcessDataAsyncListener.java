package app.android.searcharound.loader;

import org.json.JSONObject;


public interface IProcessDataAsyncListener 
{
	public void onProcessEvent(int responseCode, JSONObject response);
}
