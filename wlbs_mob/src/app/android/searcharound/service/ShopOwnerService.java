package app.android.searcharound.service;

import java.io.File;

import org.json.JSONObject;

import app.android.searcharound.common.OPCODE;
import app.android.searcharound.transport.ProgressFileTransferListener;

public class ShopOwnerService extends CommonService
{
    public JSONObject registerShopOwner(String email,
                                        String password,
                                        String phoneNumber,
                                        String firstname,
                                        String lastname,
                                        String citizenId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Email", email);
        json.put("Password", password);
        json.put("PhoneNumber", phoneNumber);
        json.put("Firstname", firstname);
        json.put("Lastname", lastname);
        json.put("CitizenId", citizenId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REG_OWNER_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject addNewShop(String shopName,
                                 String phoneNumber,
                                 String latitude,
                                 String longitude,
                                 String address,
                                 int shopType,
                                 int ownerId,
                                 File picture) throws Exception
    {
        JSONObject json = new JSONObject();
        //json.put("ShopId", shopId);
        json.put("ShopName", shopName);
        json.put("PhoneNumber", phoneNumber);
        json.put("Latitude", latitude);
        json.put("Longitude", longitude);
        json.put("Address", address);
        json.put("ShopType", shopType);
        json.put("OwnerId", ownerId);
        
        transport.setJSON(json);
        transport.setFile(picture);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_SHOP_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject editShopInformation(int shopId,
                                          String shopName,
                                          String phoneNumber,
                                          String latitude,
                                          String longitude,
                                          String address,
                                          File picture) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        json.put("ShopName", shopName);
        json.put("PhoneNumber", phoneNumber);
        json.put("Latitude", latitude);
        json.put("Longitude", longitude);
        json.put("Address", address);

        transport.setJSON(json);
        transport.setFile(picture);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_SHOP_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject removeShop(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_SHOP_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject addAccessPoint(String name, String bssid, int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("BSSID", bssid);
        json.put("ShopId", shopId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_ACCESSPOINT_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject removeAccessPoint(int apId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("AccessPointId", apId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_ACCESSPOINT_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getAccessPoint(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_ACCESSPOINT_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject addPromotion(String name, String detail, int shopId, File picture) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Detail", detail);
        json.put("ShopId", shopId);
        
        transport.setJSON(json);
        transport.setFile(picture);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_PROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject editPromotion(int pId, String name, String detail) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("PromotionId", pId);
        json.put("Name", name);
        json.put("Detail", detail);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_PROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject removePromotion(int pId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("PromotionId", pId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_PROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject removeComment(int commentId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("CommentId", commentId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_COMMENT_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject login(String email, String password) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Email", email);
        json.put("Password", password);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_VALIDATION_LOGIN_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getShopInformation(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_SHOP_INFO_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject sendCoPromotion(int requesterId, int partnerId, String name, String detail, File picture) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopRequesterId", requesterId);
        json.put("ShopPartnerId", partnerId);
        json.put("Name", name);
        json.put("Detail", detail);
        
        transport.setJSON(json);
        transport.setFile(picture);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_COPROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
            
    public JSONObject acceptCoPromotion(int coPromotionId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("CoPromotionId", coPromotionId);
       
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_ACCEPT_COPROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject ignoreCoPromotion(int coPromotionId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("PromotionId", coPromotionId);
       
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_COPROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject cancelCoPromotion(int coPromotionId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("PromotionId", coPromotionId);
       
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_COPROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getRequestedCoPromotion(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
       
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_REQUESTED_COPROMOTION_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getCoPromotion(int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("ShopId", shopId);
       
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_COPROMOTION_INFO_REQUEST);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject uploadPicture(File file, String name, int shopId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("ShopId", shopId);
        
        transport.setFile(file);
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_PICTURE_REQUEST);
        
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject uploadPicture(File file, String name, int shopId, 
    		ProgressFileTransferListener listener) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("ShopId", shopId);
        
        transport.setFile(file);
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_SAVE_PICTURE_REQUEST);
        transport.setProgressListener(listener);
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject removePicture(int pictureId) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("PictureId", pictureId);
        
        transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_REMOVE_PICTURE_REQUEST);
        
        transport.post();
        
        return transport.getJSONResponse();
    }
    
    public JSONObject getAllShopInfo(int ownerId) throws Exception
    {
    	JSONObject json = new JSONObject();
    	json.put("OwnerId", ownerId);
    	
    	transport.setJSON(json);
        transport.setRequestCode(OPCODE.SERVICE_GET_ALL_SHOP_INFO_BY_OWNER_REQUESET);
        
        transport.post();
         
        return transport.getJSONResponse();
    }

}
