<?php
require_once(APPPATH."models/AccessPoint.php");
class AccessPointTbl extends CI_Controller
{
    public function save($ap)
    {
        if (!isset($ap))
            return false;
        
        $sql = "";
        if ($ap->AccessPointId == 0)
        {
            $sql.= "INSERT INTO accesspoint (BSSID, ShopId, Name) ";
            $sql.= "VALUES ";
            $sql.= "('".$ap->BSSID."', ".$ap->ShopId.", '".$ap->Name."')"; 
            //echo $sql;
        }
        else
        {
            $sql.= "UPDATE accesspoint SET ";
            $sql.= "Name = '".$ap->Name."' ";
            $sql.= "WHERE AccessPointId = ".$ap->AccessPointId;
        }
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function isExisted($bssid)
    {
        $sql = "SELECT BSSID FROM accesspoint WHERE BSSID='$bssid'";
        $query = $this->db->query($sql);
        
        //$result = $query->result();
        //echo $query->num_rows();
//        $x = $query->result_array();
       
        return $query->num_rows() > 0 ? true : false;
    }
    
    public function remove($apId)
    {
        $sql = "DELETE FROM accesspoint WHERE AccessPointId=$apId";
        $query = $this->db->query($sql);
        //echo $sql;
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function getAllByShopId($shopId)
    {
        $sql = "SELECT * FROM accesspoint WHERE ShopId=$shopId";
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $aps = array();
        
        foreach ($query->result_array() as $result)
        {
           $ap = new AccessPoint();
           
           $ap->AccessPointId = $result['AccessPointId'];
           $ap->BSSID = $result['BSSID'];
           $ap->Name = $result['Name'];
           $ap->ShopId = $result['ShopId'];
           
           $aps[$cnt++] =  $ap;
        }
        return $aps;
    }
}

?>
