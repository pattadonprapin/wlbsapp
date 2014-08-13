
package service;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;
import org.json.JSONArray;
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
public class CommonServiceTest {
    private CommonService service;
    public CommonServiceTest() {
        service = new CommonService();
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
    public void tearDown() 
    {
    }
    @Test
    public void testGetComment() throws Exception {
        System.out.println("getComment");
        int shopId;
        JSONObject expResult, actResult;
       
        // Case : not exist shop id       
        shopId = 0;
        expResult = new JSONObject();
        expResult.put("Comment",  new JSONArray());
        actResult = service.getComment(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Case : exit shop id
        shopId = 1;
        expResult = new JSONObject("{Comment : [{\"CommentId\":1,\"Text\":\"test1\",\"ShopId\":1},"
                                             + "{\"CommentId\":2,\"Text\":\"test2\",\"ShopId\":1}]}");
       
        actResult = service.getComment(shopId);
        
        assertEquals(expResult.toString(), actResult.toString());       
    }
 
    @Test
    public void testGetPromotion() throws Exception {
        System.out.println("getPromotion");
        int shopId;
        JSONObject expResult, actResult;
        
        // Case : not exist shop id
        shopId = 0;
        expResult = new JSONObject("{Shop Promotion : []}");
        actResult = service.getPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        // Case : exist shop if
        shopId = 1;
        expResult = new JSONObject("{Shop Promotion : ["
                + "{\"ShopPromotionId\":1,\"PromotionName\":\"Test1\",\"PromotionDetail\":\"Test Detail\",\"RecordDate\":\"2014-07-01 00:00:00\",\"PicturePath\":\"XXX\"},"
                + "{\"ShopPromotionId\":2,\"PromotionName\":\"Test2\",\"PromotionDetail\":\"Test Detail\",\"RecordDate\":\"2014-07-04 21:39:43\",\"PicturePath\":\"XXX\"}]}");
        actResult = service.getPromotion(shopId);
        assertEquals(expResult.toString(), actResult.toString());
    }

    /**
     * Test of getPicture method, of class CommonService.
     */
    @Test
    public void testGetPicture() throws Exception {
        System.out.println("getPicture");
        int shopId;
        JSONObject expResult, actResult;
        
        shopId = 0;
        expResult = new JSONObject("{Picture : []}");
        actResult = service.getPicture(shopId);
        assertEquals(expResult.toString(), actResult.toString());
        
        shopId = 1;
        expResult = new JSONObject("{Picture : "
                + "[{\"PictureId\":1,\"Name\":\"Chocolate1\",\"ShopId\":1,\"SavePath\":\"XXX\"},"
                + "{\"PictureId\":2,\"Name\":\"Chocolate2\",\"ShopId\":1,\"SavePath\":\"XXX\"}]}");
        actResult = service.getPicture(shopId);
        assertEquals(expResult.toString(), actResult.toString());
    }
    
    
}
