<?php

require_once APPPATH.'tables/CoPromotionTbl.php';

class CoPromotionTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->acceptCoPromotionTest();
        $this->removeTest();
        $this->getAllRequestedTest();
        $this->getAllCoPromotionTest();
    }
    
    public function saveTest()
    {
        $coproTbl = new CoPromotionTbl();
        
        $null_name = "Adds null data to save() -> CoPromotionTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $coproTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> CoPromotionTbl";
        $cpt_in = new CoPromotion();
        $cpt_in->PromotionId = 1;
        $cpt_in->ShopRequesterId = 1;
        $cpt_in->ShopPartnerId = 2;
        $cpt_in->Acceptance = false;
        $cpt_expResult = true;
        $cpt_actResult = $coproTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function removeTest()
    {
        $coproTbl = new CoPromotionTbl();
        
        $null_name = "Adds not exist id to remove() -> CoPromotionTbl";
        $null_in = 0;
        $null_expResult = false;
        $null_actResult = $coproTbl->remove($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds exist id to remove() -> CoPromotionTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $coproTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getAllRequestedTest()
    {
        $coproTbl = new CoPromotionTbl();
        
        $null_name = "Adds not exist id to getAllRequested() -> CoPromotionTbl";
        $null_in = 0;
        $null_expResult = array();
        $null_actResult = $coproTbl->getAllRequested($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds exist id to getAllRequested() -> CoPromotionTbl";
        $cpt_in = 2;
        $cpt_expResult = array();
        
        $m1 = new CoPromotion;
        $m1->CoPromotionId = 1;
        $m1->PromotionId = 1;
        $m1->ShopRequesterId = 1;
        $m1->ShopPartnerId = 2;
        $m1->Acceptance = false;
        
        array_push($cpt_expResult, $m1);
        
        $cpt_actResult = $coproTbl->getAllRequested($cpt_in);
//        print_r($cpt_expResult); echo "<br/>";
//        print_r($cpt_actResult);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getAllCoPromotionTest()
    {
        $coproTbl = new CoPromotionTbl();
        
        $null_name = "Adds not exist id to getAllCoPromotion() -> CoPromotionTbl";
        $null_in = 0;
        $null_expResult = array();
        $null_actResult = $coproTbl->getAllCoPromotion($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds exist id case 1 to getAllCoPromotion() -> CoPromotionTbl";
        $cpt_in = 1;
        $cpt_expResult = array();
        
        $m1 = new CoPromotion;
        $m1->CoPromotionId = 2;
        $m1->PromotionId = 2;
        $m1->ShopRequesterId = 1;
        $m1->ShopPartnerId = 2;
        $m1->Acceptance = true;
        
        array_push($cpt_expResult, $m1);
        
        $cpt_actResult = $coproTbl->getAllCoPromotion($cpt_in);
//        print_r($cpt_expResult); echo "<br/>";
//        print_r($cpt_actResult);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
        
        $cpt_name = "Adds exist id case 2 to getAllCoPromotion() -> CoPromotionTbl";
        $cpt_in = 2;
        $cpt_expResult = array();
        
        $m1 = new CoPromotion;
        $m1->CoPromotionId = 2;
        $m1->PromotionId = 2;
        $m1->ShopRequesterId = 1;
        $m1->ShopPartnerId = 2;
        $m1->Acceptance = true;
        
        array_push($cpt_expResult, $m1);
        
        $cpt_actResult = $coproTbl->getAllCoPromotion($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function acceptCoPromotionTest()
    {
        $coproTbl = new CoPromotionTbl();
        
        $null_name = "Adds not exist id to acceptCoPromotion() -> CoPromotionTbl";
        $null_in = 0;
        $null_expResult = false;
        $null_actResult = $coproTbl->acceptCoPromotion($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds exist id to acceptCoPromotion() -> CoPromotionTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $coproTbl->acceptCoPromotion($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
}

?>
