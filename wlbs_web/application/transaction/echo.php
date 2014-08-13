<?php

function echo_invalid_param($json)
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_SAVE_UNSUCCESS_RESPONSE,
                 "Message"=>"Invalid Parameter",
                 "Successful"=>false,
                 "RequestMessage"=>json_encode($json));
    echo json_encode($ret);
    exit;
}

function echo_save_suc_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_SAVE_SUCCESS_RESPONSE,
                 "Message"=>"Save successful",
                 "Successful"=>true);
    echo json_encode($ret);
    exit;
}

function echo_save_unsuc_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_SAVE_UNSUCCESS_RESPONSE,
                 "Message"=>"Save unsuccessful",
                 "Successful"=>false);
    echo json_encode($ret);
    exit;
}



function echo_dup_email_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_ERROR_DUPLICATE_EMAIL_RESPONSE,
                 "Message"=>"Duplicate email",
                 "Successful"=>false);
    echo json_encode($ret);
    exit;
}

function echo_dup_bssid_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_ERROR_DUPLICATE_BSSID_RESPONSE,
                 "Message"=>"Duplicate BSSID",
                 "Successful"=>false);
    echo json_encode($ret);
    exit;
}

function echo_rm_suc_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_REMOVE_SUCCESS_RESPONSE,
                 "Message"=>"Remove successful",
                 "Successful"=>true);
    echo json_encode($ret);
    exit;
}

function echo_rm_unsuc_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_REMOVE_UNSUCCESS_RESPONSE,
                 "Message"=>"Remove unsuccessful",
                 "Successful"=>false);
    echo json_encode($ret);
    exit;
}

function echo_auth_invalid_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_AUTHENTICATION_INVALID_RESPONSE,
                 "Message"=>"Invalid email or password",
                 "Valid"=>false);
    echo json_encode($ret);
    exit;
}

//function echo_auth_valid_msg()
//{
//    $ret = array("ResponseCode"=>OPCODE::SERVER_AUTHENTICATION_VALID_RESPONSE,
//                 "Message"=>"Valid email or password",
//                 "Valid"=>true);
//    echo json_encode($ret);
//    exit;
//}

function echo_auth_inact_msg()
{
    $ret = array("ResponseCode"=>OPCODE::SERVER_AUTHENTICATION_INACTIVED_RESPONSE,
                 "Message"=>"User is not activated",
                 "Valid"=>false);
    echo json_encode($ret);
    exit;
}
?>