<?php
require_once(APPPATH."models/Owner.php");

class OwnerTbl extends CI_Controller 
{
    public function save($owner)
    {
        if (!isset($owner))
            return false;
        
        $activated = $owner->Activated ? "true" : "false";
        
        $sql = "INSERT INTO owner (UserId, Activated) ";
        $sql.= "VALUES ";
        $sql.= "(".$owner->UserId.", ".$activated.")";
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function getByUserId($userId)
    {
        $sql = "SELECT * FROM owner WHERE UserId=$userId";
        
        $query = $this->db->query($sql);
        
        $result = $query->result_array();
        
        if ($result)
        {
            $owner = new Owner();
            $owner->OwnerId = $result[0]['OwnerId'];
            $owner->UserId = $result[0]['UserId'];
            $owner->Activated = $result[0]['Activated'];
        }
        else
        {
            return null;
        }
        return $owner;
    }
    
    public function activateOwner($ownerId)
    {
        $sql = "UPDATE owner SET Activated = true WHERE OwnerId = $ownerId";
       
        $query = $this->db->query($sql);
        
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function isActivated($ownerId)
    {
        $sql = "SELECT Activated FROM owner WHERE OwnerId=$ownerId";
        $query = $this->db->query($sql);
        
        $result = $query->result_array();
        if (!$result) return false;
        return $result[0]['Activated'];
    }

}

?>
