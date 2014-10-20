package app.android.searcharound.service;

import org.json.JSONObject;

import app.android.searcharound.utility.OPCODE;

public class ClientService extends CommonService
{    
    public JSONObject postComment(int shopId, String commentText) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        json.put("CommentText", commentText);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_COMMENT_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getShopInfoByBSSID(String bssid) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("BSSID", bssid);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_SHOP_INFO_REQUEST_BY_BSSID);
        transport.post();
        
        return transport.getJSONResponse();
    }

}
