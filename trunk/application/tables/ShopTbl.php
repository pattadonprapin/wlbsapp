<?php

require_once(APPPATH."models/Shop.php");

class ShopTbl extends CI_Controller
{
    public function save(&$shop)
    {
        if (!isset($shop))
            return false;
        
        $sql = "";
        $location = $shop->Latitude.",".$shop->Longitude;
        if ($shop->ShopId == 0)
        { 
            $sql.= "INSERT INTO shop ";
            $sql.= "(Name, Location, Address, PhoneNumber, ShopType, OwnerId, PicturePath) ";
            $sql.= "VALUES ";
            $sql.= "('".$shop->Name."', '$location', '".$shop->Address."', '".$shop->PhoneNumber."', ";
            $sql.= "".$shop->ShopType.", ".$shop->OwnerId.", '".$shop->PicturePath."')";
        }
        else
        {
            $sql.= "UPDATE shop SET ";
            $sql.= "Name = '".$shop->Name."', ";
            $sql.= "Location = '$location', ";
            $sql.= "Address = '".$shop->Address."', ";
            $sql.= "PhoneNumber = '".$shop->PhoneNumber."', ";
//            $sql.= "ShopType = ".$shop->ShopType.", ";
            if (!emply($shop->PicturePath))
                $sql.= "PicturePath = '".$shop->PicturePath."' ";
            $sql.= "WHERE ShopId = ".$shop->ShopId;
            //echo $sql;;
        }
        $query = $this->db->query($sql);
        $shop->ShopId = mysql_insert_id();
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function get($shopId)
    {
        $sql = "SELECT * FROM shop WHERE ShopId = $shopId";
        
        $query = $this->db->query($sql);
        $result = $query->result_array();
       
        $shop = new Shop();
        if ($result)
        {
            $shop->ShopId = $result[0]['ShopId'];
            $shop->Name = $result[0]['Name'];
            $location = $result[0]['Location'];

            $part = explode(",",$location);
            //print_r($part);
            if ($part)
            {
                    $shop->Latitude = $part[0];
                    $shop->Longitude = $part[1];
            }

            $shop->Address = $result[0]['Address'];
            $shop->PhoneNumber = $result[0]['PhoneNumber'];
            $shop->ShopType = $result[0]['ShopType'];
            $shop->OwnerId = $result[0]['OwnerId'];
            $shop->PicturePath = $result[0]['PicturePath'];
        }
        else
        {
            return null;
        }
        return $shop;
    }
    
    public function getByBSSID($bssid)
    {
        $sql = "SELECT s.* FROM shop AS s, accesspoint AS a ";
        $sql.= "WHERE a.ShopId = s.ShopId AND a.BSSID = '$bssid'";
        $query = $this->db->query($sql);
        $result = $query->result_array();
       
        $shop = new Shop();
        if ($result)
        {
            $shop->ShopId = $result[0]['ShopId'];
            $shop->Name = $result[0]['Name'];
            $location = $result[0]['Location'];

            $part = explode(",",$location);
            //print_r($part);
            if ($part)
            {
                    $shop->Latitude = $part[0];
                    $shop->Longitude = $part[1];
            }

            $shop->Address = $result[0]['Address'];
            $shop->PhoneNumber = $result[0]['PhoneNumber'];
            $shop->ShopType = $result[0]['ShopType'];
            $shop->OwnerId = $result[0]['OwnerId'];
            $shop->PicturePath = $result[0]['PicturePath'];
        }
        else
        {
            return null;
        }
        return $shop;
    }

    public function remove($shopId)
    {
        $sql = "DELETE FROM shop WHERE ShopId = $shopId";
        
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
}

?>
