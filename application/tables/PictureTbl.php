<?php

require_once(APPPATH."models/Picture.php");
class PictureTbl extends CI_Controller
{
    public function save($picture)
    {
        if (!isset($picture)) return false;
        
        $sql = "";
        if ($picture->PictureId == 0)
        {
            $sql.= "INSERT INTO picture (Name, SavePath, ShopId) ";
            $sql.= "VALUES ";
            $sql.= "('".$picture->Name."', '".$picture->SavePath."', ".$picture->ShopId.")";
        }
        else
        {
            $sql.= "UPDATE picture SET ";
            $sql.= "Name = '".$picture->Name."' ";
            $sql.= "WHERE PictureId = ". $picture->PictureId;
        }
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function remove($pictureId)
    {
        $sql = "DELETE FROM picture WHERE PictureId = $pictureId";
        
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function getAllByShopId($shopId)
    {
        $sql = "SELECT * FROM picture WHERE ShopId = $shopId";
        
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $pictures = array();
        
        foreach ($query->result_array() as $result)
        {
           $picture = new Picture();
           
           $picture->PictureId = $result['PictureId'];
           $picture->Name = $result['Name'];
           $picture->SavePath = $result['SavePath'];
           $picture->ShopId = $result['ShopId'];
           
           $pictures[$cnt++] =  $picture;
        }
        return $pictures;
    }
    
    public function get($pictureId)
    {
        $sql = "SELECT * FROM picture WHERE PictureId = $pictureId";
        
        $query = $this->db->query($sql);
        $result = $query->result_array();
        $picture = new Picture();
        if ($result)
        {         
            $picture->PictureId = $result[0]['PictureId'];
            $picture->Name = $result[0]['Name'];
            $picture->ShopId = $result[0]['ShopId'];
            $picture->SavePath = $result[0]['SavePath'];
            return $picture;
        }
        else 
        {
            return null;
        }
        
    }
}

?>
