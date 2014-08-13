<?php
require_once APPPATH.'tables/ShopPromotionTbl.php';

class ShopPromotionTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->removeTest();
        $this->getAllByShopIdTest();
    }
    
    public function saveTest()
    {
        $shopProTbl = new ShopPromotionTbl();
        
        $null_name = "Adds null data to save() -> ShopPromotionTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $shopProTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> ShopPromotionTbl";
        $cpt_in = new ShopPromotion();
        $cpt_in->PromotionId = 1;
        $cpt_in->ShopId = 1;
        $cpt_expResult = true;
        $cpt_actResult = $shopProTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getAllByShopIdTest()
    {
        $shopProTbl = new ShopPromotionTbl();
        
        $noId_name = "Adds no Id getAllByShopId() -> ShopPromotionTbl";
        $noId_in = 0;
        $noId_expResult = array();
        $noId_actResult = $shopProTbl->getAllByShopId($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id getAllByShopId() -> ShopPromotionTbl";
        $ext_in = 1;
        $ext_expResult = array();
        
        $m1 = new ShopPromotion();
        $m1->ShopPromotionId = 1;
        $m1->PromotionId = 1;
        $m1->ShopId = 1;
       
        $m2 = new ShopPromotion();
        $m2->ShopPromotionId = 2;
        $m2->PromotionId = 2;
        $m2->ShopId = 1;
          
        array_push($ext_expResult, $m1);
        array_push($ext_expResult, $m2);
        
        
        $ext_actResult = $shopProTbl->getAllByShopId($ext_in);
//        print_r($ext_expResult); echo "<br/>";
//        print_r($ext_actResult);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
    
    public function removeTest()
    {
        $shopProTbl = new ShopPromotionTbl();
        
        $noId_name = "Adds no id data remove() -> ShopPromotionTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $shopProTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist id data remove() -> ShopPromotionTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $shopProTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
}

?>
