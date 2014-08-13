<?php
require_once (APPPATH."common/OPCODE.php");
require_once (APPPATH."common/Database.php");
require_once (APPPATH."transaction/echo.php");
require_once (APPPATH."test/UTestExecuter.php");

class Services extends CI_Controller
{
    public function api()
    {
        if (!isset($_POST['json']))
            exit;
        $input = str_replace("\\", "", $_POST['json']);
        $json = json_decode($input);
        //echo json_encode($json);
        
        if (empty($json->{'RequestCode'}))
            exit;
       
        $requestCode = $json->{'RequestCode'};    
        $json = $json->{'Data'};
        if ($requestCode >= OPCODE::MIN_SAVING && $requestCode <= OPCODE::MAX_SAVING)
            require_once APPPATH.'transaction/saving_transaction.php';
        else if ($requestCode >= OPCODE::MIN_REMOVING && $requestCode <= OPCODE::MAX_REMOVING)
            require_once APPPATH.'transaction/removing_transaction.php';
        else if ($requestCode >= OPCODE::MIN_GETTING && $requestCode <= OPCODE::MAX_GETTING)
            require_once APPPATH.'transaction/getting_transaction.php';
        
    }
    
    public function help()
    {
        $this->load->view('help');
    }
    
    public function unitTest()
    {
        $test = new UTestExecuter();
        $test->execute();
    }
}

?>
