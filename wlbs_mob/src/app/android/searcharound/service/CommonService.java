package app.android.searcharound.service;

import org.json.JSONObject;

import app.android.searcharound.common.OPCODE;
import app.android.searcharound.transport.HttpPostTransport;

public class CommonService
{
    protected  HttpPostTransport transport;
    
    protected CommonService()
    {
        transport = new HttpPostTransport();
    }
    
    public JSONObject getComment(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setRequestCode(OPCODE.SERVICE_GET_COMMENT_REQUEST);
        transport.setJSON(json);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getPromotion(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setRequestCode(OPCODE.SERVICE_GET_PROMOTION_REQUEST);
        transport.setJSON(json);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getPicture(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setRequestCode(OPCODE.SERVICE_GET_PICTURE_REQUEST);
        transport.setJSON(json);
        transport.post();
        
        return transport.getJSONResponse();
    }

}
