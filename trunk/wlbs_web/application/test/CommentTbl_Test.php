<?php
require_once APPPATH.'tables/CommentTbl.php';
class CommentTbl_Test extends CI_Controller
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
        $cmtTbl = new CommentTbl();
        
        $null_name = "Adds null data to save() -> CommentTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $cmtTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> CommentTbl";
        $cpt_in = new Comment();
        $cpt_in->Text = "test1";
        $cpt_in->ShopId = 1;
        $cpt_expResult = true;
        $cpt_actResult = $cmtTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
    public function removeTest()
    {
        $cmtTbl = new CommentTbl();
        
        $noId_name = "Adds no id data remove() -> CommentTbl";
        $noId_in = 0;
        $noId_expResult = false;
        $noId_actResult = $cmtTbl->remove($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $cpt_name = "Adds exist id data remove() -> CommentTbl";
        $cpt_in = $this->insertId;
        $cpt_expResult = true;
        $cpt_actResult = $cmtTbl->remove($cpt_in);
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
    }
    
   public function getAllByShopIdTest()
    {
        $cmtTbl = new CommentTbl();
        
        $noId_name = "Adds no Id getAllByShopId() -> CommentTbl";
        $noId_in = 0;
        $noId_expResult = array();
        $noId_actResult = $cmtTbl->getAllByShopId($noId_in);
        echo $this->unit->run($noId_expResult, $noId_actResult, $noId_name);
        
        
        $ext_name = "Adds exist Id getAllByShopId() -> CommentTbl";
        $ext_in = 1;
        $ext_expResult = array();
        
        $cmt2 = new Comment();
        $cmt2->CommentId = 1;
        $cmt2->ShopId = 1;
        $cmt2->Text = "test1";
        
        
        $cmt1 = new Comment();
        $cmt1->CommentId = 2;
        $cmt1->ShopId = 1;
        $cmt1->Text = "test2";
        
        array_push($ext_expResult, $cmt2);
        array_push($ext_expResult, $cmt1);
        $ext_actResult = $cmtTbl->getAllByShopId($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
    }
}
?>
