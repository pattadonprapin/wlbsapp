<?php

require_once APPPATH.'tables/ShopTbl.php';

class ShopTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->getByBSSIDTest();
        $this->getByOwnerIdTest();
        $this->removeTest();
    }
    
    public function saveTest()
    {
        $shopTbl = new ShopTbl();
        
        $null_name = "Adds null data to save() -> ShopTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $shopTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> ShopTbl";
        $cpt_in = new Shop();
        $cpt_in->Address = "Test Address";
        $cpt_in->Latitude = "1.11";
        $cpt_in->Longitude = "2.22";
        $cpt_in->Name = "Test1";
        $cpt_in->OwnerId = 1;
        $cpt_in->PhoneNumber = "053123021";
        $cpt_in->PicturePath = "XXX";
        $cpt_in->ShopType = 1;
        $cpt_expResult = true;
        $cpt_actResult = $shopTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getByOwnerIdTest()
    {
        $shopTbl = new ShopTbl();
        
        $next_name = "Adds not exist id to getByOwnerId() -> ShopTbl";
        $next_in = 0;
        $next_expResult = null;
        $next_actResult = $shopTbl->get($next_in);
        echo $this->unit->run($next_expResult, $next_actResult, $next_name);
        
        $ext_name = "Adds exist id to get() -> ShopTbl";
        $ext_in = 1;
        $ext_expResult = new Shop();
        $ext_expResult->ShopId = 1;
        $ext_expResult->Name = "Test1";
        $ext_expResult->Latitude = 1.11;
        $ext_expResult->Longitude = 2.22;
        $ext_expResult->Address = "Test Address";
        $ext_expResult->PhoneNumber = "053123021";
        $ext_expResult->ShopType = 1;
        $ext_expResult->OwnerId = 1;
        $ext_expResult->PicturePath = "XXX";
        $ext_actResult = $shopTbl->get($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
    
    public function getByBSSIDTest()
    {
        $shopTbl = new ShopTbl();
        
        $next_name = "Adds not exist id to getByOwnerId() -> ShopTbl";
        $next_in = "";
        $next_expResult = null;
        $next_actResult = $shopTbl->getByBSSID($next_in);
        echo $this->unit->run($next_expResult, $next_actResult, $next_name);
        
        $ext_name = "Adds exist id to getByOwnerId() -> ShopTbl";
        $ext_in = "01:30:0B:C2:C5:DA ";
        $ext_expResult = new Shop();
        $ext_expResult->ShopId = 1;
        $ext_expResult->Name = "Test1";
        $ext_expResult->Latitude = 1.11;
        $ext_expResult->Longitude = 2.22;
        $ext_expResult->Address = "Test Address";
        $ext_expResult->PhoneNumber = "053123021";
        $ext_expResult->ShopType = 1;
        $ext_expResult->OwnerId = 1;
        $ext_expResult->PicturePath = "XXX";
        $ext_actResult = $shopTbl->getByBSSID($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
    
    public function removeTest()
    {
        $shopTbl = new ShopTbl();
        
        $noId_name = "Adds not exist shop id to remove() -> ShopTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $shopTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist shop id to remove() -> ShopTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $shopTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
}

?>
