package app.android.seracharound.transport;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.StrictMode;
import app.android.searcharound.utility.SERVER_ADDRESS;

public class HttpPostTransport 
{
	private ProgressFileTransferListener listener;
    private String responseString;
    private File file;
    private int requestCode;
    private JSONObject json;
    
    public HttpPostTransport()
    {
    	if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = 
		    		new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
    }
    
    public void setProgressListener(ProgressFileTransferListener listener)
    {
        this.listener = listener;
        
    }
    
    public void setFile(File file)
    {
        this.file = file;
    }
    
    public void setRequestCode(int requestCode)
    {
        this.requestCode =  requestCode;
    }
    
    public void setJSON(JSONObject json)
    {
        this.json = json;
    }
    
    public void post() throws Exception
    {
    	final HttpParams http_param  = new BasicHttpParams();
    	HttpConnectionParams.setConnectionTimeout(http_param, 7000);
    	HttpClient client = new DefaultHttpClient(http_param);
    	
    	String url = "http://"+SERVER_ADDRESS.IP+"/wlbs_web/index.php/services/api";
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        
        ProgressHttpEntityWrapper.ProgressCallback progressCallback =
                new ProgressHttpEntityWrapper.ProgressCallback() 
                {
                    @Override
                    public void progress(float progress) {
                        notifyProgress(progress);
                    }

                };
                
        if (this.file != null)
        {
            FileBody fileBody = new FileBody(file); //image should be a String
            builder.addPart("file", fileBody);
        }
        
        
        if (this.json != null)
        {
            JSONObject js_request = new JSONObject();
            js_request.put("RequestCode", this.requestCode);
            js_request.put("Data", json);
            builder.addPart("json", 
            				new StringBody(js_request.toString(), 
            				ContentType.APPLICATION_FORM_URLENCODED));
        }
        
        post.setEntity(new ProgressHttpEntityWrapper(builder.build(), progressCallback));

        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        this.responseString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseString);
    }
    
    private void notifyProgress(float percent)
    {
        if (this.listener != null)
            this.listener.progress(percent);
    }
    
    public JSONObject getJSONResponse()
    {
        JSONObject json = null;
        try
        {
            json = new JSONObject(this.responseString);
        }
        catch (Exception e)
        {
            
        }
        return json;
    }
}
