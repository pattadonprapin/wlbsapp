<?php require_once APPPATH.'common/OPCODE.php'?>
<html>
    <h2>Test API</h2>
    <form method="POST" action="api" enctype="multipart/form-data">
        JSON Request <input type="text" name="json" style="width: 100%"/><br/>
        <input type="file" name="file" /><br/>
        <input type="submit" value="Submit" />
    </form>
    
    <table border="1">
        <tr><th colspan="2">Saving Mode</th></tr>
        <tr>
            <th>Command Request</th>
            <th>Example</th>
        </tr>
        <tr>
            <td>Register shop owner</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REG_OWNER_REQUEST;?>, 
                 "Data":{"Email":"xxx@gmail.com", "Password":"123456", "PhoneNumber":"053511234", 
                 "Firstname":"test1", "Lastname":"test2" ,"CitizenId":"150332051651"}}</td>
        </tr>
        <tr>
            <td>Save shop information</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_SHOP_REQUEST;?>, 
                 "Data":{"ShopId":0, "ShopName":"toy", "PhoneNumber":"053511234", "Latitude":"10.12312", 
                 "Longitude":"1.21232", "Address":"15sdfadsf", "ShopType":1, "OwnerId":123}}</td>
        </tr>
        
        <tr>
            <td>Add access point</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_ACCESSPOINT_REQUEST;?>, 
                 "Data":{"Name":"WE85", "BSSID":"1239skaq2", "ShopId":1}}</td>
        </tr>
        
        <tr>
            <td>Add Promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_PROMOTION_REQUEST;?>, 
                 "Data":{"PromotionId":0, "Name":"X322DS", "Detail":"Buy 1 Get 1", 
                 "ShopId":5}}</td>
        </tr>
        
        <tr>
            <td>Save co-promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_COPROMOTION_REQUEST;?>, 
                 "Data":{"ShopRequesterId":12, "ShopPartnerId":43, "Name":"Hello, Promotion", 
                 "Detail":"EIEIEI"}}</td>
        </tr>
        
        <tr>
            <td>Accept Co-Promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_ACCEPT_COPROMOTION_REQUEST;?>, 
                 "Data":{"CoPromotionId":12}}</td>
        </tr>
        
        <tr>
            <td>Post Comment</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_COMMENT_REQUEST;?>, 
                 "Data":{"ShopId":12, "CommentText":"Hi Tejerl"}}</td>
        </tr>
        
        <tr>
            <td>Save Image</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_SAVE_PICTURE_REQUEST;?>, 
                 "Data":{"Name":"Chocolate", "ShopId":12}}</td>
        </tr>
        
        <tr><th colspan="2">Removing Mode</th></tr>
        <tr>
            <td>Remove Accesspoint</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_ACCESSPOINT_REQUEST;?>, 
                 "Data":{"AccessPointId":1}}</td>
        </tr>
        
        <tr>
            <td>Remove Comment</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_COMMENT_REQUEST;?>, 
                 "Data":{"CommentId":1}}</td>
        </tr>
        
        <tr>
            <td>Remove Co-Promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_COPROMOTION_REQUEST;?>, 
                 "Data":{"CoPromotionId":1}}</td>
        </tr>
        
        <tr>
            <td>Remove Shop Promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_PROMOTION_REQUEST;?>, 
                 "Data":{"ShopPromotionId":1}}</td>
        </tr>
        
        <tr>
            <td>Remove Picture</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_PICTURE_REQUEST;?>, 
                 "Data":{"PictureId":1}}</td>
        </tr>
        
        <tr>
            <td>Remove Shop</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_REMOVE_SHOP_REQUEST;?>, 
                 "Data":{"ShopId":1}}</td>
        </tr>
        
        <tr><th colspan="2">Getting Mode</th></tr>
        <tr>
            <td>Get requested co-promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_REQUESTED_COPROMOTION_REQUEST;?>, 
                 "Data":{"ShopId":1}}</td>
        </tr>
        
        <tr>
            <td>Get co-promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_COPROMOTION_INFO_REQUEST;?>, 
                 "Data":{"ShopId":1}}</td>
        </tr>
        
        <tr>
            <td>Get Validation</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_VALIDATION_LOGIN_REQUEST;?>, 
                 "Data":{"Email":"test1", "Password":"123456789"}}</td>
        </tr>
        
        <tr>
            <td>Get access point</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_ACCESSPOINT_REQUEST;?>, 
                 "Data":{"ShopId":0}}</td>
        </tr>
        
        <tr>
            <td>Get Commment</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_COMMENT_REQUEST;?>, 
                 "Data":{"ShopId":0}}</td>
        </tr>
        
        <tr>
            <td>Get shop information</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_SHOP_INFO_REQUEST;?>, 
                 "Data":{"ShopId":0}}</td>
        </tr>
        
        <tr>
            <td>Get shop information by bssid</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_SHOP_INFO_REQUEST_BY_BSSID;?>, 
                 "Data":{"BSSID":"1239dskaqs21"}}</td>
        </tr>
        
        <tr>
            <td>Get picture</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_PICTURE_REQUEST;?>, 
                 "Data":{"ShopId":0}}</td>
        </tr>
        
        <tr>
            <td>Get promotion</td>
            <td>{"RequestCode":<?php echo OPCODE::SERVICE_GET_PROMOTION_REQUEST;?>, 
                 "Data":{"ShopId":0}}</td>
        </tr>
    </table>
</html>
