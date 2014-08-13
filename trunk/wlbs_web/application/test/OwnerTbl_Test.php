<?php
require_once APPPATH.'tables/OwnerTbl.php';

class OwnerTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->isActivatedTest();        
        $this->getByUserIdTest();
        $this->activateOwnerTest();
    }
    
    public function saveTest()
    {
        $ownerTbl = new OwnerTbl();
        
        $null_name = "Adds null data to save() -> OwnerTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $ownerTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> OwnerTbl";
        $cpt_in = new Owner();
        $cpt_in->UserId = 1;
        $cpt_in->Activated = false;
        $cpt_expResult = true;
        $cpt_actResult = $ownerTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function getByUserIdTest()
    {
        $ownerTbl = new OwnerTbl();
        
        $ext_name = "Adds exsit id to getByUserId() -> OwnerTbl";
        $ext_in = 1;
        $ext_expResult = new Owner();
        $ext_expResult->Activated = true;
        $ext_expResult->UserId = 1;
        $ext_expResult->OwnerId = 1;
        
        $ext_actResult = $ownerTbl->getByUserId($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
        
        $noId_name = "Adds not exsit id to getByUserId() -> OwnerTbl";
        $noId_in = 0;
        $noId_expResult = null;
        $noId_actResult = $ownerTbl->getByUserId($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);   
    }
    
    public function activateOwnerTest()
    {
        $ownerTbl = new OwnerTbl();
        
        $noId_name = "Adds no id to activateOwner() -> OwnerTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $ownerTbl->activateOwner($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        $aext_name = "Adds exist id to activateOwner() -> OwnerTbl";
        $aext_in = $this->insertId;
        $aext_expResult = true;
        $aext_actResult = $ownerTbl->activateOwner($aext_in);
        echo $this->unit->run($aext_expResult, $aext_actResult, $aext_name);
    }
    
    public function isActivatedTest()
    {
        $ownerTbl = new OwnerTbl();
        
        $noId_name = "Adds no id to isActivated() -> OwnerTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $ownerTbl->isActivated($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        $ext_name = "Adds exist id to isActivated() -> OwnerTbl";
        $ext_in = 1;
        $ext_expResult = true;
        $ext_actResult = $ownerTbl->isActivated($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
        
        $ext_name = "Adds exist id to isActivated() -> OwnerTbl";
        $ext_in = 2;
        $ext_expResult = false;
        $ext_actResult = $ownerTbl->isActivated($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
}

?>
