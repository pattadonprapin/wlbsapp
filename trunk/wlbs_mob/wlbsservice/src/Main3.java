
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Consts;
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


public class Main3 
{
    public static void main(String args[]) 
    {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://localhost/Test/test.php");
            //post.addHeader("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            json.put("xs", "toy");
            json.put("name", "boy");
    //        List<NameValuePair> arguments = new ArrayList<NameValuePair>();
    ////        System.out.println(">> "+json.toString());
    //        arguments.add(new BasicNameValuePair("toy", "Hiiiii"));
    //        post.setEntity(new UrlEncodedFormEntity(arguments));
    //        //HttpResponse response = client.execute(post);
    //         StringEntity params = new StringEntity(json.toString());
    //         post.addHeader("content-type", "application/x-www-form-urlencoded");
    //         post.setEntity(params);
             MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
             builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
         
             //post.addHeader("content-type", "application/x-www-form-urlencoded");
             //post.addHeader("Content-Type", "application/json");
             File file = new File("E:\\1471371_764941696854508_88725480_n.jpg");
             System.out.println(file.isFile());
             System.out.println(file.getTotalSpace());
             FileBody fileBody = new FileBody(file); //image should be a String
             builder.addPart("file", fileBody);
            
             builder.addPart("toy", new StringBody("Supanut", ContentType.TEXT_PLAIN)); 
             //builder.addPart("toy", new StringBody(json.toString(), create("application/json", Consts.UTF_8))); 
             //builder.addPart()
             //builder.addPart("json", new JSO);
             System.out.println(">> " + json.toString());
             //builder.addTextBody("json", json.toString(),ContentType.APPLICATION_JSON);
            // builder.addTextBody("json_text",json_text,ContentType.APPLICATION_JSON);
             //builder.addPart("xx", "xcdf");
             post.setEntity(builder.build());
             
             HttpResponse response = client.execute(post);
             HttpEntity entity = response.getEntity();
    String responseString = EntityUtils.toString(entity, "UTF-8");
    System.out.println(responseString);
        } catch (Exception ex) {
            System.out.println(ex);
        } 
    }
}
    