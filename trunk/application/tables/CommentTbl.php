<?php
require_once(APPPATH."models/Comment.php");


class CommentTbl extends CI_Controller
{
    public function save($comment)
    {
        if (!isset($comment)) return false;
        
        $sql = "";
        if ($comment->CommentId == 0)
        {
            $sql.= "INSERT INTO comment (Text, ShopId) ";
            $sql.= "VALUES ";
            $sql.= "('".$comment->Text."', ".$comment->ShopId.")";
        }
        else
        {
            $sql.= "UPDATE comment SET ";
            $sql.= "Text = '".$comment->Text."' ";
            $sql.= "WHERE CommentId = ".$comment->CommentId; 
        }
        $query = $this->db->query($sql);

        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function remove($commentId)
    {
        $sql = "DELETE FROM comment WHERE CommentId=$commentId";
        $query = $this->db->query($sql);
        
        return ($this->db->affected_rows() != 1) ? false : true;
    }
    
    public function getAllByShopId($shopId)
    {
        $sql = "SELECT * FROM comment WHERE ShopId=$shopId";
        $query = $this->db->query($sql);
        
        $cnt = 0;
        $comments = array();
        
        foreach ($query->result_array() as $result)
        {
           $comment = new Comment();
           
           $comment->CommentId = $result['CommentId'];
           $comment->Text = $result['Text'];
           $comment->ShopId = $result['ShopId'];

           $comments[$cnt++] =  $comment;
        }
        return $comments;
    }
}

?>
