<?php
require_once(APPPATH."models/CoPromotion.php");
class CoPromotionTbl extends CI_Controller
{    
    public function save($co_promotion)
    {
        if (!isset($co_promotion)) return false;
        $sql = "INSERT INTO co_promotion ";
        $sql.= "(PromotionId, ShopRequesterId, ShopPartnerId, Acceptance) ";
        $sql.= "VALUES ";
        $sql.= "(".$co_promotion->PromotionId.", ".$co_promotion->ShopRequesterId.", ";
        $sql.= "".$co_promotion->ShopPartnerId.", false)";
        
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function remove($co_promotionId)
    {
       // $sql = "DELETE c, p FROM co_promotion AS c, promotion AS p ";
        //$sql.= "WHERE c.PromotionId = p.PromotionId AND CoPromotionId = $co_promotionId";
        $sql = "DELETE FROM co_promotion WHERE CoPromotionId = $co_promotionId";
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() == 0) ? false : true;
    }
    
    public function getAllRequested($shopId)
    {
        $sql = "SELECT * FROM co_promotion ";
        $sql.= "WHERE ShopPartnerId = $shopId AND Acceptance = false";
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $requests = array();
        
        foreach ($query->result_array() as $result)
        {
            $request = new CoPromotion();
            $request->CoPromotionId = $result['CoPromotionId'];
            $request->PromotionId = $result['PromotionId'];
            $request->ShopRequesterId = $result['ShopRequesterId'];
            $request->ShopPartnerId = $result['ShopPartnerId'];
            $request->Acceptance = $result['Acceptance'];
            
            $requests[$cnt++] = $request;
        }
        
        return $requests;
    }
    
    public function getAllCoPromotion($shopId)
    {
        $sql = "SELECT * FROM co_promotion ";
        $sql.= "WHERE Acceptance = true AND (ShopPartnerId = $shopId OR ShopRequesterId = $shopId)";
        //echo $sql;
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $requests = array();
        
        foreach ($query->result_array() as $result)
        {
            $request = new CoPromotion();
            $request->CoPromotionId = $result['CoPromotionId'];
            $request->PromotionId = $result['PromotionId'];
            $request->ShopRequesterId = $result['ShopRequesterId'];
            $request->ShopPartnerId = $result['ShopPartnerId'];
            $request->Acceptance = $result['Acceptance'];
            
            $requests[$cnt++] = $request;
        }
        
        return $requests;
    }
    
    public function acceptCoPromotion($co_promotionId)
    {
        $sql = "UPDATE co_promotion SET Acceptance = true WHERE CoPromotionId = $co_promotionId";
        $query = $this->db->query($sql);
        
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function getPicturePath($co_promotionId)
    {
        $sql = "SELECT p.PicturePath FROM co_promotion AS c, promotion AS p ";
        $sql.= "WHERE c.PromotionId = p.PromotionId AND c.CoPromotionId = $co_promotionId";
        
        $query = $this->db->query($sql);
        $result = $query->result_array();
        $picPath = "";
        
        if ($result)              
            $picPath = $result[0]['PicturePath'];
       
        return $picPath;
    }
}

?>
