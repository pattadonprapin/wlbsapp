<?php
require_once(APPPATH."models/User.php");
class UserTbl extends CI_Controller
{
   public function save(&$user)
   {
       if (!isset($user))
           return false;
       
       $sql = "";
       if ($user->UserId == 0)
       {
           $sql.= "INSERT INTO user (Firstname, Lastname, PhoneNumber, Email, Password, CitizenId) ";
           $sql.= "VALUES ";
           $sql.= "('".$user->Firstname."', '".$user->Lastname."', '".$user->PhoneNumber."', ";
           $sql.= "'".$user->Email."', '".$user->Password."', '".$user->CitizenId."')";                   
       }
       else
       {
           $sql.= "UPDATE user SET ";
           $sql.= "Firtname = '".$user->Firstname."', ";
           $sql.= "Lastname = '".$user->Lastname."' ";
           $sql.= "PhoneNumber = '".$user->PhoneNumber."' ";
           $sql.= "WHERE UserId = ".$user->UserId;
       }
       $query = $this->db->query($sql);
       $user->UserId = mysql_insert_id();
       return ($this->db->affected_rows() != 1) ? false : true;
   }
   
   public function isValid($email, $password)
   {
       $sql = "SELECT UserId FROM user WHERE ";
       $sql.= "Email = '$email' ";
       $sql.= "AND ";
       $sql.= "Password = '$password'";
       
       $query = $this->db->query($sql);
       
       return $query->num_rows() > 0 ? true : false;
   }
   
   public function getByEmail($email)
   {
       $sql = "SELECT * FROM user WHERE Email = '$email'";
       
       $query = $this->db->query($sql);
       $result = $query->result_array();
       
       $user = new User();
       if ($result)
       {
           $user->UserId = $result[0]['UserId'];
           $user->Firstname = $result[0]['Firstname'];
           $user->Lastname = $result[0]['Lastname'];
           $user->Email = $result[0]['Email'];
           $user->PhoneNumber = $result[0]['PhoneNumber'];
           $user->CitizenId = $result[0]['CitizenId'];
       }
       else
       {
           return null;
       }
       
       return $user;
   }
   
   public function isExisted($email)
   {
       $sql = "SELECT * FROM user WHERE Email = '$email'";
       $query = $this->db->query($sql);
       return $query->num_rows() > 0 ? true : false;
   }
   
   //public function getByEmail($email)
}

?>
