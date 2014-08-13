<?php

require_once APPPATH.'tables/ShopTypeTbl.php';

class ShopTypeTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->getAllTest();
    }
    
    public function getAllTest()
    {
        $sTypeTbl = new ShopTypeTbl();
        
        $name = "getAll() -> ShopTypeTbl";
        $expResult = array();
        
        $m1 = new ShopType();
        $m1->TypeId = 1;
        $m1->Text = "test1";
        
        $m2 = new ShopType();
        $m2->TypeId = 2;
        $m2->Text = "test2";
        
        array_push($expResult, $m1);
        array_push($expResult, $m2);
        
        $actResult = $sTypeTbl->getAll();
        echo $this->unit->run($expResult, $actResult, $name);
    }
}

?>
