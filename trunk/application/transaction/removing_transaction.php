<?php
switch ($requestCode)
{
    case OPCODE::SERVICE_REMOVE_ACCESSPOINT_REQUEST:
//        if (!isset($json->{'AccessPointId'}))
//            echo_invalid_param($json);
        //echo ">>";
        remove_accesspoint($json);
    break;

    case OPCODE::SERVICE_REMOVE_COMMENT_REQUEST:
//        if (!isset($json->{'CommentId'}))
//            echo_invalid_param($json);
        remove_comment($json);
    break;

    case OPCODE::SERVICE_REMOVE_COPROMOTION_REQUEST:
//        if (!isset($json->{'CoPromotionId'}))
//            echo_invalid_param($json);
        remove_co_promotion($json);
    break;

    case OPCODE::SERVICE_REMOVE_PROMOTION_REQUEST:
//        if (!isset($json->{'ShopPromotionId'}))
//            echo_invalid_param($json);
        remove_promotion($json);
    break;

    case OPCODE::SERVICE_REMOVE_PICTURE_REQUEST:
//        if (!isset($json->{'PictureId'}))
//            echo_invalid_param($json);
        remove_picture($json);
    break;
    
    case OPCODE::SERVICE_REMOVE_SHOP_REQUEST:
//         if (!isset($json->{'ShopId'}))
//            echo_invalid_param($json);
        remove_shop($json);
    break;
}

function remove_accesspoint($json)
{
    $database = new Database();
    $apTbl = $database->getAccessPointTbl();
    
    $success = $apTbl->remove($json->{'AccessPointId'});
    
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
}

function remove_comment($json)
{
    $database = new Database();
    $commentTbl = $database->getCommentTbl();
    
    $success = $commentTbl->remove($json->{'CommentId'});
    
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
}

function remove_co_promotion($json)
{
    $database = new Database();
    $proTbl = $database->getPromotionTbl();
    
    $promotion = $proTbl->get($json->{'PromotionId'});
    if ($promotion)
        $picPath = $promotion->PicturePath;
    
    if (!empty($picPath))
        unlink("../".$picPath);
    
    $success = $proTbl->remove($json->{'PromotionId'});
    
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
    
//    $database = new Database();
//    $coProTbl = $database->getCoPromotionTbl();
//    
//    $picPath = $coProTbl->getPicturePath($json->{'CoPromotionId'});
//    if (!empty($picPath))
//        unlink("../".$picPath);
//    
//    $success = $coProTbl->remove($json->{'CoPromotionId'});
//    
//    if ($success) echo_rm_suc_msg();
//    else echo_rm_unsuc_msg();
}

function remove_promotion($json)
{
    $database = new Database();
    $promotionTbl = $database->getPromotionTbl();
    
    $picPath = $promotionTbl->getPicturePath($json->{'PromotionId'});
    if (!empty($picPath))
        unlink("../".$picPath);
    
    $success = $promotionTbl->remove($json->{'PromotionId'});
    
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
}

function remove_picture($json)
{
    $database = new Database();
    $pictureTbl = $database->getPictureTbl();
    
    $picture = $pictureTbl->get($json->{'PictureId'});
    if (!empty($picture->SavePath))
       unlink("../".$picture->SavePath);
    
    $success = $pictureTbl->remove($json->{'PictureId'});
    
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
}

function remove_shop($json)
{
    $database = new Database();
    $shopTbl = $database->getShopTbl();
    
    $shop_info = $shopTbl->get($json->{'ShopId'});
    if (!empty($shop_info->PicturePath))
       unlink("../".$shop_info->PicturePath);
    
    $success = $shopTbl->remove($json->{'ShopId'});
    if ($success) echo_rm_suc_msg();
    else echo_rm_unsuc_msg();
}
?>
