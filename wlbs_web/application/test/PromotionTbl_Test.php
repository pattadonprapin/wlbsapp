<?php

require_once APPPATH.'tables/PromotionTbl.php';

class PromotionTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->removeTest();
        $this->getTest();
        
    }
    
    public function saveTest()
    {
        $proTbl = new PromotionTbl();
        
        $null_name = "Adds null data to save() -> PromotionTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $proTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> PromotionTbl";
        $cpt_in = new Promotion();
        $cpt_in->Name = "Test1";
        $cpt_in->Detail = "Test Detail";
        $cpt_expResult = true;
        $cpt_actResult = $proTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function removeTest()
    {
        $proTbl = new PromotionTbl();
        
        $noId_name = "Adds no id data remove() -> PromotionTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $proTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist id data remove() -> PromotionTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $proTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getTest()
    {
        $proTbl = new PromotionTbl();
        
        $noId_name = "Adds no Id get() -> PromotionTbl";
        $noId_in = 0;
        $noId_expResult = null;
        $noId_actResult = $proTbl->get($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id get() -> PromotionTbl";
        $ext_in = 1;
        $ext_expResult = new Promotion();
        $ext_expResult->PromotionId = 1;
        $ext_expResult->Name = "Test1";
        $ext_expResult->Detail = "Test Detail";
        $ext_expResult->RecordDate = "2014-07-01 00:00:00";
        $ext_expResult->PicturePath = "XXX";
        
        $ext_actResult = $proTbl->get($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
}

?>
