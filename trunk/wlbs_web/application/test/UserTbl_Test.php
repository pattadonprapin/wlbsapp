<?php
require_once APPPATH.'tables/UserTbl.php';

class UserTbl_Test extends CI_Controller
{
    public $insertId;
    
    public function test()
    {
        $this->load->library('unit_test');
        $this->saveTest();
        $this->isExistedTest();
        $this->isValidTest();
        $this->getByEmailTest();
    }
    
    public function saveTest()
    {
        $userTbl = new UserTbl();
        
        $null_name = "Adds null data to save() -> UserTbl";
        $null_in = null;
        $null_expResult = false;
        $null_actResult = $userTbl->save($null_in);
        echo $this->unit->run($null_expResult, $null_actResult, $null_name);
        
        $cpt_name = "Adds complete data to save() -> UserTbl";
        $cpt_in = new User();
        $cpt_in->CitizenId = "1519910231213";
        $cpt_in->Email = "zzz@gmail.com";
        $cpt_in->Firstname = "Test1";
        $cpt_in->Lastname = "Test1";
        $cpt_in->Password = "123456";
        $cpt_in->PhoneNumber = "053123021";
        $cpt_expResult = true;
        $cpt_actResult = $userTbl->save($cpt_in);
        $this->insertId = mysql_insert_id();
        echo $this->unit->run($cpt_expResult, $cpt_actResult, $cpt_name);
        
        $dup_name = "Adds duplicate Email to save() -> UserTbl";
        $dup_in = new User();
        $dup_in->CitizenId = "1519910231213";
        $dup_in->Email = "xxx@gmail.com";
        $dup_in->Firstname = "Test1";
        $dup_in->Lastname = "Test1";
        $dup_in->Password = "123456";
        $dup_in->PhoneNumber = "053123021";
        $dup_expResult = false;
        $dup_actResult = $userTbl->save($dup_in);
        echo $this->unit->run($dup_expResult, $dup_actResult, $dup_name);
    }
    
    public function isValidTest()
    {
        $userTbl = new UserTbl();
        
        $val_name = "Adds valid email and password isValid() -> UserTbl";
        $val_email_in = "xxx@gmail.com";
        $val_pass_in = "123456";
        $val_expResult = true;
        $val_actResult = $userTbl->isValid($val_email_in, $val_pass_in);
        echo $this->unit->run($val_expResult, $val_actResult, $val_name);
        
        $inval_name = "Adds invalid email and password isValid() -> UserTbl";
        $inval_email_in = "xxx2@gmail.com";
        $inval_pass_in = "1234567";
        $inval_expResult = false;
        $inval_actResult = $userTbl->isValid($inval_email_in, $inval_pass_in);
        echo $this->unit->run($inval_expResult, $inval_actResult, $inval_name);
        
        $invale_name = "Adds invalid email isValid() -> UserTbl";
        $invale_email_in = "xxx2@gmail.com";
        $invale_pass_in = "123456";
        $invale_expResult = false;
        $invale_actResult = $userTbl->isValid($invale_email_in, $invale_pass_in);
        echo $this->unit->run($invale_expResult, $invale_actResult, $invale_name);
        
        $invalp_name = "Adds invalid password isValid() -> UserTbl";
        $invalp_email_in = "xxx@gmail.com";
        $invalp_pass_in = "1234567";
        $invalp_expResult = false;
        $invalp_actResult = $userTbl->isValid($invalp_email_in, $invalp_pass_in);
        echo $this->unit->run($invalp_expResult, $invalp_actResult, $invalp_name);
    }
    
    public function getByEmailTest()
    {
        $userTbl = new UserTbl();
        
        $next_name = "Adds not exist email to getByEmail() -> UserTbl";
        $next_in = "y@email.com";
        $next_expResult = null;
        $next_actResult = $userTbl->getByEmail($next_in);
        echo $this->unit->run($next_expResult, $next_actResult, $next_name);
        
        $ext_name = "Adds exist email to getByEmail() -> UserTbl";
        $ext_in = "xxx@gmail.com";
        $ext_expResult = new User();
        $ext_expResult->UserId = 1;
        $ext_expResult->CitizenId = "1519910231213";
        $ext_expResult->Email = "xxx@gmail.com";
        $ext_expResult->Firstname = "Test1";
        $ext_expResult->Lastname = "Test1";
        $ext_expResult->PhoneNumber = "053123021";
        $ext_actResult = $userTbl->getByEmail($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);     
    }
    
    public function isExistedTest()
    {
        $userTbl = new UserTbl();
        
        $next_name = "Adds not exist email isExisted() -> UserTbl";
        $next_in = "y@email.com";
        $next_expResult = false;
        $next_actResult = $userTbl->isExisted($next_in);
        echo $this->unit->run($next_expResult, $next_actResult, $next_name);
        
        $ext_name = "Adds exist email isExisted() -> UserTbl";
        $ext_in = "xxx@gmail.com";
        $ext_expResult = true;
        $ext_actResult = $userTbl->isExisted($ext_in);
        echo $this->unit->run($ext_expResult, $ext_actResult, $ext_name);
        
        
    }
}

?>
