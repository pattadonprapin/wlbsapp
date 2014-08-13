<?php
switch ($requestCode)
{
    case OPCODE::SERVICE_GET_REQUESTED_COPROMOTION_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_requested_co_promotion($json);
    break;

    case OPCODE::SERVICE_GET_COPROMOTION_INFO_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_co_promotion($json);
    break;

    case OPCODE::SERVICE_GET_VALIDATION_LOGIN_REQUEST:
        if (empty($json->{'Email'}) || empty ($json->{'Password'}))
            echo_auth_invalid_msg();
        get_validation_login($json);
    break;

    case OPCODE::SERVICE_GET_ACCESSPOINT_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_access_point($json);
    break;

    case OPCODE::SERVICE_GET_COMMENT_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_comment($json);
    break;

    case OPCODE::SERVICE_GET_SHOP_INFO_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_shop_info($json);
    break;

    case OPCODE::SERVICE_GET_SHOP_INFO_REQUEST_BY_BSSID:
        if (!isset($json->{'BSSID'}))
            echo_invalid_param($json);
        get_shop_info_by_bssid($json);
    break;

    case OPCODE::SERVICE_GET_PICTURE_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_picture($json);
    break;

    case OPCODE::SERVICE_GET_PROMOTION_REQUEST:
        if (!isset($json->{'ShopId'}))
            echo_invalid_param($json);
        get_promotion($json);
    break;
}

function get_requested_co_promotion($json)
{
    $database = new Database();
    $coProTbl = $database->getCoPromotionTbl();
    $promotionTbl = $database->getPromotionTbl();

    $copros = $coProTbl->getAllRequested($json->{'ShopId'});
    
    $ret_array = array();
    $cnt = 0;
    foreach ($copros as $co)
    {
        $promotionId = $co->PromotionId;
        $promotion = $promotionTbl->get($promotionId);
        $ret_array[$cnt++] = array("CoPromotionId"=>$co->CoPromotionId,
                                   "ShopRequesterId"=>$co->ShopRequesterId,
                                   "ShopPartnerId"=>$co->ShopPartnerId,
                                   "PromotionName"=>$promotion->Name,
                                   "PromotionDetail"=>$promotion->Detail,
                                   "RecordDate"=>$promotion->RecordDate,
                                   "PicturePath"=>$promotion->PicturePath);
       
    }
    echo "{ Requested Co-Promotion : " . json_encode($ret_array) . "}";
    exit;
}

function get_co_promotion($json)
{
    $database = new Database();
    $coProTbl = $database->getCoPromotionTbl();
    $promotionTbl = $database->getPromotionTbl();

    $copros = $coProTbl->getAllCoPromotion($json->{'ShopId'});
    
    $ret_array = array();
    $cnt = 0;
    foreach ($copros as $co)
    {
        $promotionId = $co->PromotionId;
        $promotion = $promotionTbl->get($promotionId);
        $ret_array[$cnt++] = array("CoPromotionId"=>$co->CoPromotionId,
                                   "ShopRequesterId"=>$co->ShopRequesterId,
                                   "ShopPartnerId"=>$co->ShopPartnerId,
                                   "PromotionName"=>$promotion->Name,
                                   "PromotionDetail"=>$promotion->Detail,
                                   "RecordDate"=>$promotion->RecordDate,
                                   "PicturePath"=>$promotion->PicturePath);
       
    }
    echo "{ Co-Promotion : " . json_encode($ret_array) . "}";
    exit;
}

function get_validation_login($json)
{
    $database = new Database();
    $userTbl = $database->getUserTbl();
    $ownerTbl = $database->getOwnerTbl();
    
    $valid = $userTbl->isValid($json->{'Email'}, $json->{'Password'});
    
    if ($valid)
    {
        $user = $userTbl->getByEmail($json->{'Email'});
    
        $owner = $ownerTbl->getByUserId($user->UserId);
        $activate = $ownerTbl->isActivated($owner->OwnerId);
        if ($activate)
        {
            $ret = array("ResponseCode"=>OPCODE::SERVER_AUTHENTICATION_VALID_RESPONSE,
                         "UserId"=>$user->UserId,
                         "Firstname"=>$user->Firstname,
                         "Lastname"=>$user->Lastname,
                         "Email"=>$user->Email,
                         "PhoneNumber"=>$user->PhoneNumber,
                         "CitizenId"=>$user->CitizenId,
                         "OwnerId"=>$owner->OwnerId,
                         "Message"=>"Valid Username and Password",
                         "Valid"=>true);
            echo json_encode($ret);
            exit;
        }
        else
        {
            echo_auth_inact_msg();
        }
    }
    else
    {
        echo_auth_invalid_msg();
    }
    
    
}

function get_access_point($json)
{
    $database = new Database();
    $apTbl = $database->getAccessPointTbl();
    
    $aps = $apTbl->getAllByShopId($json->{'ShopId'});
    //print_r($aps);
    $ret_array = array();
    $cnt = 0;
    
    foreach ($aps as $ap)
    {
        $ret_array[$cnt++] = array("AccessPointId"=>$ap->AccessPointId,
                                   "BSSID"=>$ap->BSSID,
                                   "Name"=>$ap->Name,
                                   "ShopId"=>$ap->ShopId);
    }
    
    echo "{Access point : " . json_encode($ret_array) . "}";
    exit;   
}

function get_comment($json)
{
    $database = new Database();
    $commentTbl = $database->getCommentTbl();
    
    $comments = $commentTbl->getAllByShopId($json->{'ShopId'});
    
    $ret_array = array();
    $cnt = 0;
   
    foreach ($comments as $c)
    {
        
        $ret_array[$cnt++] = array("CommentId"=>(int)$c->CommentId,
                                   "Text"=>$c->Text,
                                   "ShopId"=>(int)$c->ShopId);
    }
    
    echo "{Comment : " . json_encode($ret_array) . "}";
    exit;   
}

function get_shop_info($json)
{
    $database = new Database();
    $shopTbl = $database->getShopTbl();
    
    $shop = $shopTbl->get($json->{'ShopId'});
    $ret_array = null;
    
    if ($shop)
    {
        $ret_array = array("ShopId"=>$shop->ShopId,
                       "OwnerId"=>$shop->OwnerId,
                       "Name"=>$shop->Name,
                       "Latitude"=>$shop->Latitude,
                       "Longitude"=>$shop->Longitude,
                       "Address"=>$shop->Address,
                       "PhoneNumber"=>$shop->PhoneNumber,
                       "ShopType"=>$shop->ShopType,
                       "PicturePath"=>$shop->PicturePath);
    }
    

    echo "{ Shop : " . json_encode($ret_array) . "}";
    exit;
}

function get_shop_info_by_bssid($json)
{
    $database = new Database();
    $shopTbl = $database->getShopTbl();
    
    $shop = $shopTbl->getByBSSID($json->{'BSSID'});
    $ret_array = null;
    if ($shop)
    {
        $ret_array = array("ShopId"=>$shop->ShopId,
                           "OwnerId"=>$shop->OwnerId,
                           "Name"=>$shop->Name,
                           "Latitude"=>$shop->Latitude,
                           "Longitude"=>$shop->Longitude,
                           "Address"=>$shop->Address,
                           "PhoneNumber"=>$shop->PhoneNumber,
                           "ShopType"=>$shop->ShopType,
                           "PicturePath"=>$shop->PicturePath);
    }
    echo "{Shop : " . json_encode($ret_array) . "}";
    exit;
}

function get_picture($json)
{
    $database = new Database();
    $pictureTbl = $database->getPictureTbl();
    
    $pictures = $pictureTbl->getAllByShopId($json->{'ShopId'});
    
    $ret_array = array();
    $cnt = 0;
   
    foreach ($pictures as $pic)
    {
        
        $ret_array[$cnt++] = array("PictureId"=>(int)$pic->PictureId,
                                   "Name"=>$pic->Name,
                                   "ShopId"=>(int)$pic->ShopId,
                                   "SavePath"=>$pic->SavePath);
    }
    
    echo "{Picture : " . json_encode($ret_array)."}";
    exit; 
}

function get_promotion($json)
{
    $database = new Database();
    $shopProTbl = $database->getShopPromotionTbl();
    $promotionTbl = $database->getPromotionTbl();

    $spros = $shopProTbl->getAllByShopId($json->{'ShopId'});
    
    $ret_array = array();
    $cnt = 0;
    foreach ($spros as $spro)
    {
        $promotionId = $spro->PromotionId;
        $promotion = $promotionTbl->get($promotionId);
        $ret_array[$cnt++] = array("ShopPromotionId"=>(int)$spro->ShopPromotionId,
                                   "PromotionName"=>$promotion->Name,
                                   "PromotionDetail"=>$promotion->Detail,
                                   "RecordDate"=>$promotion->RecordDate,
                                   "PicturePath"=>$promotion->PicturePath);
       
    }
    echo "{Shop Promotion : " . json_encode($ret_array)."}";
    exit;
}
?>
