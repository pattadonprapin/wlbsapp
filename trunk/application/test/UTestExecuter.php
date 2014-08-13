<?php
require_once 'AccessPointTbl_Test.php';
require_once 'CommentTbl_Test.php';
require_once 'OwnerTbl_Test.php';
require_once 'CoPromotionTbl_Test.php';
require_once 'UserTbl_Test.php';
require_once 'PictureTbl_Test.php';
require_once 'ShopTypeTbl_Test.php';
require_once 'PromotionTbl_Test.php';
require_once 'ShopPromotionTbl_Test.php';
require_once 'ShopTbl_Test.php';

class UTestExecuter extends CI_Controller
{
    public function execute()
    {
        echo "<h2>Test ShopTypeTbl</h2>";
        $sTypeTest = new ShopTypeTbl_Test();
        $sTypeTest->test();
        
        echo "<h2>Test UserTbl</h2>";
        $userTest = new UserTbl_Test();
        $userTest->test();
        
        echo "<h2>Test OwnerTbl</h2>";
        $ownerTest = new OwnerTbl_Test();
        $ownerTest->test();
        
        echo "<h2>Test ShopTbl</h2>";
        $shopTest = new ShopTbl_Test();
        $shopTest->test();
           
        echo "<h2>Test AccessPointTbl</h2>";
        $apTest = new AccessPointTbl_Test();
        $apTest->test();
        
        echo "<h2>Test CommentTbl</h2>";
        $cmtTest = new CommentTbl_Test();
        $cmtTest->test();
        
        echo "<h2>Test PictureTbl</h2>";
        $picTest = new PictureTbl_Test();
        $picTest->test();
        
             
        echo "<h2>Test PromotionTbl</h2>";
        $proTest = new PromotionTbl_Test();
        $proTest->test();
        
        echo "<h2>Test ShopPromotionTbl</h2>";
        $sproTest = new ShopPromotionTbl_Test();
        $sproTest->test();
        
        echo "<h2>Test CoPromotionTbl</h2>";
        $coproTest = new CoPromotionTbl_Test();
        $coproTest->test();
      
    }
}
?>
