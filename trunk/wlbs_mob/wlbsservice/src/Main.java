
import org.eclipse.persistence.sessions.server.Server;
import org.json.JSONObject;
import service.ClientService;
import service.CommonService;
import service.ShopOwnerService;
import transport.HttpPostTransport;


public class Main 
{
    public static void main(String args[]) throws Exception
    {
        HttpPostTransport t = new HttpPostTransport();
        JSONObject json = new JSONObject();
        t.setRequestCode(1000);
        json.put("toy", "XYZ");
        t.setJSON(json);
        t.post();
        
        System.err.println(t.getJSONResponse().getInt("RequestCode"));
    }
}
