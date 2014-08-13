package service;

import common.OPCODE;
import java.io.File;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShopOwnerServiceTest {
    private ShopOwnerService service;
    public ShopOwnerServiceTest() {
        service = new ShopOwnerService();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of login method, of class ShopOwnerService.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String email = "";
        String password = "";
        int expResult, actResult;
        JSONObject result;
        
        // test with missing some parameter
        email = "";
        password = "12345678";
        expResult = OPCODE.SERVER_AUTHENTICATION_INVALID_RESPONSE;
        result = service.login(email, password);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with correct email and password
        email = "true1@gmail.com";
        password = "123456";
        expResult = OPCODE.SERVER_AUTHENTICATION_VALID_RESPONSE;
        result = service.login(email, password);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with correct email and password but not activated user
        email = "true2@gmail.com";
        password = "123456";
        expResult = OPCODE.SERVER_AUTHENTICATION_INACTIVED_RESPONSE;
        result = service.login(email, password);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with incorrect email and password
        email = "df@gmail.com";
        password = "123456";
        expResult = OPCODE.SERVER_AUTHENTICATION_INVALID_RESPONSE;
        result = service.login(email, password);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
    }

     
    /**
     * Test of getAccessPoint method, of class ShopOwnerService.
     */
    @Test
    public void testGetAccessPoint() throws Exception {
        
        System.out.println("getAccessPoint");
        int shopId = 0;
        JSONObject actResult, expResult;
        
        // Test with doesn't exist shop id
        shopId = 0;
        expResult = new JSONObject("{Access point : []}");
        actResult = service.getAccessPoint(shopId);
        assertEquals(expResult.toString(), actResult.toString());
       
        
        // Test with exist shop id
        shopId = 1;
        expResult = new JSONObject("{Access point : "
                + "[{\"AccessPointId\":\"1\",\"BSSID\":\"01:30:0B:C2:C5:DA\",\"Name\":\"test1\",\"ShopId\":\"1\"},"
                + "{\"AccessPointId\":\"2\",\"BSSID\":\"03:12:F3:C2:12:V3\",\"Name\":\"test2\",\"ShopId\":\"1\"}]}");
        actResult = service.getAccessPoint(shopId);
        assertEquals(expResult.toString(), actResult.toString());
    }
    
    /**
     * Test of getRequestedCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testGetRequestedCoPromotion() throws Exception {
        System.out.println("getRequestedCoPromotion");
        int shopId = 0;
        JSONObject actResult, expResult;
        
        // Test with doesn't exist shop id
        shopId = 0;
        expResult = new JSONObject("{ Requested Co-Promotion : []}");
        actResult = service.getRequestedCoPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Test with exist shop id
        shopId = 2;
        expResult = new JSONObject("{ Requested Co-Promotion : ["
                + "{\"CoPromotionId\":\"1\",\"ShopRequesterId\":\"1\","
                + "\"ShopPartnerId\":\"2\",\"PromotionName\":\"Test1\","
                + "\"PromotionDetail\":\"Test Detail\",\"RecordDate\":\"2014-07-01 00:00:00\","
                + "\"PicturePath\":\"XXX\"}]}");
        actResult = service.getRequestedCoPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
      
    }

    /**
     * Test of getCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testGetCoPromotion() throws Exception {
        System.out.println("getCoPromotion");
        int shopId = 0;
        JSONObject actResult, expResult;
        
        // Test with doesn't exist shop id
        shopId = 0;
        expResult = new JSONObject("{ Co-Promotion : []}");
        actResult = service.getCoPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Test with exist shop id = 1
        shopId = 1;
        expResult = new JSONObject("{ Co-Promotion : ["
                + "{\"CoPromotionId\":\"2\",\"ShopRequesterId\":\"1\","
                + "\"ShopPartnerId\":\"2\",\"PromotionName\":\"Test2\","
                + "\"PromotionDetail\":\"Test Detail\",\"RecordDate\":\"2014-07-04 21:39:43\","
                + "\"PicturePath\":\"XXX\"}]}");
        actResult = service.getCoPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Test with exist shop id = 2
        shopId = 2;
        expResult = new JSONObject("{ Co-Promotion : ["
                + "{\"CoPromotionId\":\"2\",\"ShopRequesterId\":\"1\","
                + "\"ShopPartnerId\":\"2\",\"PromotionName\":\"Test2\","
                + "\"PromotionDetail\":\"Test Detail\",\"RecordDate\":\"2014-07-04 21:39:43\","
                + "\"PicturePath\":\"XXX\"}]}");
        actResult = service.getCoPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
    }

    
     /**
     * Test of getShopInformation method, of class ShopOwnerService.
     */
    @Test
    public void testGetShopInformation() throws Exception {
        System.out.println("getShopInformation");
        int shopId = 0;
        JSONObject actResult, expResult;
        
        // Test with doesn't not exist shop id
        shopId = 0;
        expResult = new JSONObject("{ Shop : null}");
        actResult = service.getShopInformation(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Test with exist shop id
        shopId = 1;
        expResult = new JSONObject("{ Shop : "
                + "{\"ShopId\":\"1\",\"OwnerId\":\"1\",\"Name\":\"Test1\","
                + "\"Latitude\":\"1.11\",\"Longitude\":\"2.22\",\"Address\":"
                + "\"Test Address\",\"PhoneNumber\":\"053123021\",\"ShopType\":\"1\","
                + "\"PicturePath\":\"XXX\"}}");
        actResult = service.getShopInformation(shopId);
        assertEquals(expResult.toString(), actResult.toString());     
    }
    
    /**
     * Test of registerShopOwner method, of class ShopOwnerService.
     */
    
    
    @Test
    public void testRegisterShopOwner() throws Exception {
        System.out.println("registerShopOwner");
        
        // Case : Test with missing some parameter
        String email = "";
        String password = "123456789";
        String phoneNumber = "053-123123";
        String firstname = "Supanut";
        String lastname = "Panyagosa";
        String citizenId = "1123123231";
        JSONObject result;
        int expResult, actResult;
        
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result  = service.registerShopOwner(email, password, phoneNumber, firstname, lastname, citizenId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with all parameter have input data
        email = "jjj@gmail.com";
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result  = service.registerShopOwner(email, password, phoneNumber, firstname, lastname, citizenId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
         // Test with  duplicate email
        email = "jjj@gmail.com";
        expResult = OPCODE.SERVER_ERROR_DUPLICATE_EMAIL_RESPONSE;
        result  = service.registerShopOwner(email, password, phoneNumber, firstname, lastname, citizenId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);  
    }

    /**
     * Test of addNewShop method, of class ShopOwnerService.
     */
    @Test
    public void testAddNewShop() throws Exception {
        System.out.println("addNewShop");
        String shopName = "";
        String phoneNumber = "";
        String latitude = "";
        String longitude = "";
        String address = "";
        int shopType = 0;
        int ownerId = 0;
        File picture = null;
        JSONObject result;
        int expResult, actResult;
        
        // Test with missing parameters
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.addNewShop(shopName, 
                                    phoneNumber, 
                                    latitude, 
                                    longitude, 
                                    address, 
                                    shopType, 
                                    ownerId, 
                                    picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with input all parameters
        shopName = "Test1";
        latitude = "1.11";
        longitude = "2.22";
        address = "Test Address";
        phoneNumber = "053123021";
        shopType = 1;
        ownerId = 1;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.addNewShop(shopName, 
                                    phoneNumber, 
                                    latitude, 
                                    longitude, 
                                    address, 
                                    shopType, 
                                    ownerId, 
                                    picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        
    }

    /**
     * Test of editShopInformation method, of class ShopOwnerService.
     */
    @Test
    public void testEditShopInformation() throws Exception {
        System.out.println("editShopInformation");
        int shopId = 0;
        String shopName = "";
        String phoneNumber = "";
        String latitude = "";
        String longitude = "";
        String address = "";
        File picture = null;
        JSONObject result;
        int expResult, actResult;
        
        // Test with missing parameters
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.editShopInformation(shopId, shopName, phoneNumber, latitude, longitude, address, picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with input all parameter
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        shopId = 4;
        shopName = "Test Change";
        phoneNumber = "053123021";
        latitude = "1.11";
        longitude = "2.22";
        address = "Test Address";
        result = service.editShopInformation(shopId, shopName, phoneNumber, latitude, longitude, address, picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
       
    }

    /**
     * Test of removeShop method, of class ShopOwnerService.
     */
    @Test
    public void testRemoveShop() throws Exception {
        System.out.println("removeShop");
        int shopId = 0;
        int actResult, expResult;
        JSONObject result;
        
        // Test with does not exist shop id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.removeShop(shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with exist shop id
        shopId = 4;
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.removeShop(shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
    }

    /**
     * Test of addAccessPoint method, of class ShopOwnerService.
     */
    @Test
    public void testAddAccessPoint() throws Exception {
        System.out.println("addAccessPoint");
        String name = "";
        String bssid = "";
        int shopId = 0;
        JSONObject result;
        int actResult, expResult;
        
        // Test with missing parameters
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.addAccessPoint(name, bssid, shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with input all parameter
        name = "test1";
        bssid = "00:30:0A:C2:C5:DA";
        shopId = 5;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.addAccessPoint(name, bssid, shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with duplicate BSSID
        expResult = OPCODE.SERVER_ERROR_DUPLICATE_BSSID_RESPONSE;
        result = service.addAccessPoint(name, bssid, shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
    }

    /**
     * Test of removeAccessPoint method, of class ShopOwnerService.
     */
    @Test
    public void testRemoveAccessPoint() throws Exception {
        System.out.println("removeAccessPoint");
        int apId = 0;
        JSONObject result;
        int actResult, expResult;
        
        // Test with doesn't exsit acceess point
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.removeAccessPoint(apId);
        actResult = result.getInt("ResponseCode");
        assertEquals(actResult, expResult);
        
        // Test with doesn't exsit acceess point
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        apId = 3;
        result = service.removeAccessPoint(apId);
        actResult = result.getInt("ResponseCode");
        assertEquals(actResult, expResult);
        
    }

    

    /**
     * Test of addPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testAddPromotion() throws Exception {
        System.out.println("addPromotion");
        String name = "";
        String detail = "";
        int shopId = 0;
        JSONObject result;
        int expResult, actResult;
        
        // Test with missing parameter 
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.addPromotion(name, detail, shopId, null);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        name = "Test1";
        detail = "Test Detail";
        shopId = 1;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.addPromotion(name, detail, shopId, null);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
    }

    /**
     * Test of editPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testEditPromotion() throws Exception {
        System.out.println("editPromotion");
        int pId = 0;
        String name = "";
        String detail = "";
        JSONObject result;
        int actResult, expResult;
        
        // Test with missing parameter
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.editPromotion(pId, name, detail);
        actResult = result.getInt("ResponseCode");
        assertEquals(actResult, expResult);
        
        // Test with input all parameter
        pId = 3;
        name = "Test1 Change";
        detail = "Test Change";
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.editPromotion(pId, name, detail);
        actResult = result.getInt("ResponseCode");
        assertEquals(actResult, expResult);
        
    }

    /**
     * Test of removePromotion method, of class ShopOwnerService.
     */
    @Test
    public void testRemovePromotion() throws Exception {
        System.out.println("removePromotion");
        int pId = 0;
        JSONObject result;
        int actResult, expResult;
        
        // Test with doesn't exist shop promotion id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.removePromotion(pId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with exist shop promotion id
        pId = 3;
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.removePromotion(pId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
    }

    /**
     * Test of removeComment method, of class ShopOwnerService.
     */
    @Test
    public void testRemoveComment() throws Exception {
        System.out.println("removeComment");
        int commentId = 0;
        JSONObject result;
        int expResult, actResult;
        
        // Test with doesn't exist comment id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.removeComment(commentId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        commentId = 3;
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.removeComment(commentId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
      
    }


    /**
     * Test of sendCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testSendCoPromotion() throws Exception {
        System.out.println("sendCoPromotion");
        int requesterId = 0;
        int partnerId = 0;
        String name = "";
        String detail = "";
        File picture = null;
        JSONObject result;
        int expResult, actResult;
        
        // Test with missing parameter
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.sendCoPromotion(requesterId, partnerId, name, detail, picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with input all paremeter
        name = "Test1";
        detail = "Test Detail";
        requesterId = 1;
        partnerId = 2;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.sendCoPromotion(requesterId, partnerId, name, detail, picture);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        
       
    }

    /**
     * Test of acceptCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testAcceptCoPromotion() throws Exception {
        System.out.println("acceptCoPromotion");
        int coPromotionId = 0;
        JSONObject result;
        int expResult, actResult;
        
        // Test with not exist co-promotion id
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.acceptCoPromotion(coPromotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with exist co-promotion id
        coPromotionId = 3;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.acceptCoPromotion(coPromotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
       
    }

    /**
     * Test of ignoreCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testIgnoreCoPromotion() throws Exception {
        System.out.println("ignoreCoPromotion");
        int promotionId = 0;
        JSONObject result;
        int expResult, actResult;
        
        // Test with not exist co-promotion id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.ignoreCoPromotion(promotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with exist co-promotion id
        promotionId = 5;
        
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.ignoreCoPromotion(promotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        
       
    }

    /**
     * Test of cancelCoPromotion method, of class ShopOwnerService.
     */
    @Test
    public void testCancelCoPromotion() throws Exception {
        System.out.println("cancelCoPromotion");
        int promotionId = 0;
        JSONObject result;
        int expResult, actResult;
        
        // Test with not exist co-promotion id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.cancelCoPromotion(promotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with exist co-promotion id
        promotionId = 5;
        
        expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.cancelCoPromotion(promotionId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
    }

    
    /**
     * Test of uploadPicture method, of class ShopOwnerService.
     */
    @Test
    public void testUploadPicture() throws Exception {
        System.out.println("uploadPicture");
        File file = null;
        String name = "";
        int shopId = 0;
        JSONObject result;
        int actResult, expResult;
        
        // Test with missing parameters
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.uploadPicture(file, name, shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        // Test with input all parameter
        file = new File("C:\\Users\\Supanut\\Desktop\\Untitled.png");
        name = "Test1";
        shopId = 1;
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.uploadPicture(file, name, shopId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
       
    }

    /**
     * Test of removePicture method, of class ShopOwnerService.
     */
    @Test
    public void testRemovePicture() throws Exception {
        System.out.println("removePicture");
        int pictureId = 0;
        JSONObject result;
        int actResult, expResult;
        
        // Test with not exist picture Id
        expResult = OPCODE.SERVER_REMOVE_UNSUCCESS_RESPONSE;
        result = service.removePicture(pictureId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        
        
        // Test with exist picture Id
        pictureId = 3;
         expResult = OPCODE.SERVER_REMOVE_SUCCESS_RESPONSE;
        result = service.removePicture(pictureId);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
       
    }
}
