<?php
require_once APPPATH.'tables/AccessPointTbl.php';

class AccessPointTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->isExistedTest();
        $this->removeTest();
        $this->getAllByShopIdTest();
    }
    
    public function saveTest()
    {
        $apTbl = new AccessPointTbl();
        
        $null_name = "Adds null data to save() -> AccessPointTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $apTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> AccessPointTbl";
        $cpt_in = new AccessPoint();
        $cpt_in->Name = "test1";
        $cpt_in->BSSID = "00:30:0A:C2:C5:DA";
        $cpt_in->ShopId = 1;
        $cpt_expResult = true;
        $cpt_actResult = $apTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
        
        $dup_name = "Adds duplicate BSSID to save() -> AccessPointTbl";
        $dup_in = new AccessPoint();
        $dup_in->Name = "test1";
        $dup_in->BSSID = "00:30:0A:C2:C5:DA ";
        $dup_in->ShopId = 1;
        $dup_expResult = false;
        $dup_actResult = $apTbl->save($dup_in);
        echo $this->unit->run($dup_expResult, $dup_actResult, $dup_name);
    }
    
    public function removeTest()
    {
        $apTbl = new AccessPointTbl();
        
        $noId_name = "Adds no id data remove() -> AccessPointTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $apTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist id data remove() -> AccessPointTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $apTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function isExistedTest()
    {
        $apTbl = new AccessPointTbl();
        
        $ext_name = "Adds exist BSSID isExisted() -> AccessPointTbl";
        $ext_in = "00:30:0A:C2:C5:DA";
        $ext_expResult = true;
        $ext_actResult = $apTbl->isExisted($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
        
        $next_name = "Adds not exist BSSID isExisted() -> AccessPointTbl";
        $next_in = "";
        $next_expResult = false;
        $next_actResult = $apTbl->isExisted($next_in);
        echo $this->unit->run($next_expResult, $next_actResult, $next_name);
    }
    
    public function getAllByShopIdTest()
    {
        $apTbl = new AccessPointTbl();
        
        $noId_name = "Adds no Id getAllByShopId() -> AccessPointTbl";
        $noId_in = 0;
        $noId_expResult = array();
        $noId_actResult = $apTbl->getAllByShopId($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id getAllByShopId() -> AccessPointTbl";
        $ext_in = 1;
        $ext_expResult = array();
        
        $ap1 = new AccessPoint();
        $ap1->AccessPointId = 1;
        $ap1->BSSID = "01:30:0B:C2:C5:DA";
        $ap1->Name = "test1";
        $ap1->ShopId = 1;
        
        
        $ap2 = new AccessPoint();
        $ap2->AccessPointId = 2;
        $ap2->BSSID = "03:12:F3:C2:12:V3";
        $ap2->Name = "test2";
        $ap2->ShopId = 1;
        array_push($ext_expResult, $ap1);
        array_push($ext_expResult, $ap2);
              
        $ext_actResult = $apTbl->getAllByShopId($ext_in);
        
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }

}
?>
