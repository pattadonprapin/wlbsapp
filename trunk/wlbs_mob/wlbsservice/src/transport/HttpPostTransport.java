
package transport;

import java.io.File;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpPostTransport 
{
    private ProgressFileTransferListener listener;
    private String responseString;
    private File file;
    private int requestCode;
    private JSONObject json;
    
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
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost/web_wlbs/index.php/services/api");
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
            JSONObject jsx = new JSONObject();
            jsx.put("RequestCode", this.requestCode);
            jsx.put("Data", json);
            builder.addPart("json", new StringBody(jsx.toString(), ContentType.APPLICATION_FORM_URLENCODED));
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
            JOptionPane.showConfirmDialog(null, e);
        }
        return json;
    }
}
