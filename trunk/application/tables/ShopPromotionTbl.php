<?php

require_once(APPPATH."models/ShopPromotion.php");
class ShopPromotionTbl extends CI_Controller
{
    public function save($shopPromotion)
    {
        if (!isset($shopPromotion)) return false;
        
        $sql = "INSERT INTO shop_promotion (PromotionId, ShopId) ";
        $sql.= "VALUES ";
        $sql.= "(".$shopPromotion->PromotionId.", ".$shopPromotion->ShopId.")";
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function remove($shopPromotionId)
    {
//        $sql = "DELETE s, p FROM shop_promotion AS s, promotion AS p ";
//        $sql.= "WHERE s.PromotionId = p.PromotionId AND ShopPromotionId = $shopPromotionId";
        $sql = "DELETE FROM shop_promotion WHERE ShopPromotionId = $shopPromotionId";
        $query = $this->db->query($sql);

        //echo $sql;
        
        return ($this->db->affected_rows() == 0) ? false : true;
    }
    
    public function getAllByShopId($shopId)
    {
        $sql = "SELECT * FROM shop_promotion WHERE ShopId = $shopId";
        $query = $this->db->query($sql);

        $cnt = 0;
        $promotions = array();
        
        

        foreach ($query->result_array() as $result)
        {
            $shopPromotion = new ShopPromotion();
            $shopPromotion->ShopPromotionId = $result['ShopPromotionId'];
            $shopPromotion->PromotionId = $result['PromotionId'];
            $shopPromotion->ShopId = $result['ShopId'];
            $promotions[$cnt++] = $shopPromotion;
        
        }
        return $promotions;
    }
    
    public function getPicturePath($shopPromotionId)
    {
        $sql = "SELECT p.PicturePath FROM shop_promotion AS s, promotion AS p ";
        $sql.= "WHERE s.PromotionId = p.PromotionId AND s.ShopPromotionId = $shopPromotionId";
        
        $query = $this->db->query($sql);
        $result = $query->result_array();
        $picPath = "";
        
        if ($result)              
            $picPath = $result[0]['PicturePath'];
        
        return $picPath;
    }
}

?>
