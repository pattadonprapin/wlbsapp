<?php

require_once(APPPATH."models/ShopType.php");

class ShopTypeTbl extends CI_Controller
{
    public function getAll()
    {
        $sql = "SELECT * FROM shop_type";
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $shopTypes = array();
        
        foreach ($query->result_array() as $result)
        {
           $shopType = new ShopType();
           
           $shopType->TypeId = $result['TypeId'];
           $shopType->Text = $result['Text'];
           
           $shopTypes[$cnt++] =  $shopType;
        }
        return $shopTypes;
    }
}

?>
