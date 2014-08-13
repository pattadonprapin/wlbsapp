/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import common.OPCODE;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Supanut
 */
public class ClientServiceTest {
    private ClientService service;
    public ClientServiceTest() {
        service = new ClientService();
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
     * Test of postComment method, of class ClientService.
     */
    @Test
    public void testPostComment() throws Exception {
        System.out.println("postComment");
        
        // Case : Test with missing some parameters have input data
        int shopId = 1;
        String text = "";
        int expResult, actResult;
        JSONObject result;
        
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE; 
        result = service.postComment(shopId, text);
        actResult = result.getInt("ResponseCode");
        assertEquals(expResult, actResult);
        // Case : Test with all parameters have input data
        
        
        shopId = 1;
        text = "test1";
        expResult = OPCODE.SERVER_SAVE_SUCCESS_RESPONSE;
        result = service.postComment(shopId, text);
        actResult = result.getInt("ResponseCode"); 
        assertEquals(expResult, actResult);
        
        
        // Case : Test with not exist shop id in database
        shopId = -30;
        text = "test1";
        expResult = OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE;
        result = service.postComment(shopId, text);
        actResult = result.getInt("ResponseCode"); 
        assertEquals(expResult, actResult);
        
    }

    /**
     * Test of getShopInfoByBSSID method, of class ClientService.
     */
    @Test
    public void testGetShopInfoByBSSID() throws Exception {
        System.out.println("getShopInfoByBSSID");
        JSONObject actResult, expResult;
        String bssid;
        
        // Case : Test with not exist BSSID
        bssid = "";
        expResult = new JSONObject("{Shop : null}");
        actResult = service.getShopInfoByBSSID(bssid);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Case : Test with exist BSSID
        bssid = "01:30:0B:C2:C5:DA";
        expResult = new JSONObject("{Shop : {\"ShopId\":\"1\",\"OwnerId\":\"1\",\"Name\":\"Test1\","
                                 + "\"Latitude\":\"1.11\",\"Longitude\":\"2.22\",\"Address\":\"Test Address\","
                                 + "\"PhoneNumber\":\"053123021\",\"ShopType\":\"1\",\"PicturePath\":\"XXX\"}}");
        actResult = service.getShopInfoByBSSID(bssid);
        assertEquals(expResult.toString(), actResult.toString());
        
    }
}
