<?php

require_once(APPPATH."models/Promotion.php");
class PromotionTbl extends CI_Controller
{
    public function save($promotion)
    {
        if (!isset($promotion))return false;
        
        $sql = "";
        if ($promotion->PromotionId == 0)
        {
            $sql.= "INSERT INTO promotion (Name, Detail, PicturePath) ";
            $sql.= "VALUES ";
            $sql.= "('".$promotion->Name."', '".$promotion->Detail."', '".$promotion->PicturePath."')";
        }
        else
        {
            $sql.= "UPDATE promotion SET ";
            $sql.= "Name = '".$promotion->Name."', ";
            $sql.= "Detail = '".$promotion->Detail."' ";
            $sql.= "WHERE PromotionId = ".$promotion->PromotionId;
        }
        $query = $this->db->query($sql);
        $promotion->PromotionId = mysql_insert_id();
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function remove($promotionId)
    {
        $sql = "DELETE FROM promotion WHERE PromotionId = $promotionId";
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function get($promotionId)
    {
        $sql = "SELECT * FROM promotion WHERE PromotionId = $promotionId";
        
        $query = $this->db->query($sql);
        $result = $query->result_array();
       
        if ($result)
        {
            $promotion = new Promotion();
            $promotion->PromotionId = $result[0]['PromotionId'];
            $promotion->Name = $result[0]['Name'];
            $promotion->Detail = $result[0]['Detail'];
            $promotion->RecordDate = $result[0]['RecordDate'];
            $promotion->PicturePath = $result[0]['PicturePath'];
        }
        else
        {
            return null;
        }
        
        return $promotion;
    }
}

?>
