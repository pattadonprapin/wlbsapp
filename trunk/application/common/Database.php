<?php
require_once(APPPATH."tables/AccessPointTbl.php");
require_once(APPPATH."tables/CommentTbl.php");
require_once(APPPATH."tables/OwnerTbl.php");
require_once(APPPATH."tables/CoPromotionTbl.php");
require_once(APPPATH."tables/UserTbl.php");
require_once(APPPATH."tables/PictureTbl.php");
require_once(APPPATH."tables/PromotionTbl.php");
require_once(APPPATH."tables/ShopPromotionTbl.php");
require_once(APPPATH."tables/ShopTypeTbl.php");
require_once(APPPATH."tables/ShopTbl.php");

class Database 
{
    public function getAccessPointTbl()
    {
        return new AccessPointTbl();
    }
    
    public function getCommentTbl()
    {
        return new CommentTbl();
    }
    
    public function getOwnerTbl()
    {
        return new OwnerTbl();
    }
    
    public function getCoPromotionTbl()
    {
        return new CoPromotionTbl();
    }
    
    public function getUserTbl()
    {
        return new UserTbl();
    }
    
    public function getPictureTbl()
    {
        return new PictureTbl();
    }
    
    public function getPromotionTbl()
    {
        return new PromotionTbl();
    }
    
    public function getShopPromotionTbl()
    {
        return new ShopPromotionTbl();
    }
    
    public function getShopTypeTbl()
    {
        return new ShopTypeTbl();
    }
    
    public function getShopTbl()
    {
        return new ShopTbl();
    }
}

?>
