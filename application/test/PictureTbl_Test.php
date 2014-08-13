<?php

require_once APPPATH.'tables/PictureTbl.php';

class PictureTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->removeTest();
        $this->getAllByShopIdTest();
        $this->getTest();
    }
    
    public function saveTest()
    {
        $picTbl = new PictureTbl();
        
        $null_name = "Adds null data to save() -> PictureTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $picTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> PictureTbl";
        $cpt_in = new Picture();
        $cpt_in->Name = "Test1";
        $cpt_in->SavePath = "XXX";
        $cpt_in->ShopId = 1;
        $cpt_expResult = true;
        $cpt_actResult = $picTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
        
    }
    
    public function removeTest()
    {
        $picTbl = new PictureTbl();
        
        $noId_name = "Adds no id data remove() -> PictureTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $picTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist id data remove() -> PictureTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $picTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getAllByShopIdTest()
    {
        $picTbl = new PictureTbl();
        
        $noId_name = "Adds no Id getAllByShopId() -> PictureTbl";
        $noId_in = 0;
        $noId_expResult = array();
        $noId_actResult = $picTbl->getAllByShopId($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id getAllByShopId() -> PictureTbl";
        $ext_in = 1;
        $ext_expResult = array();
        
        $pic2 = new Picture();
        $pic2->PictureId = 1;
        $pic2->Name = "Chocolate1";
        $pic2->SavePath = "XXX";
        $pic2->ShopId = 1;
        
        
        $pic1 = new Picture();
        $pic1->PictureId = 2;
        $pic1->Name = "Chocolate2";
        $pic1->SavePath = "XXX";
        $pic1->ShopId = 1;
        array_push($ext_expResult, $pic2);
        array_push($ext_expResult, $pic1);
        
        $ext_actResult = $picTbl->getAllByShopId($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
    
    public function getTest()
    {
        $picTbl = new PictureTbl();
        
        $noId_name = "Adds no Id get() -> PictureTbl";
        $noId_in = 0;
        $noId_expResult = null;
        $noId_actResult = $picTbl->get($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id get() -> PictureTbl";
        $ext_in = 1;
        
        $ext_expResult = new Picture();
        $ext_expResult->PictureId = 1;
        $ext_expResult->Name = "Chocolate1";
        $ext_expResult->SavePath = "XXX";
        $ext_expResult->ShopId = 1;
        
        $ext_actResult = $picTbl->get($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
}

?>
