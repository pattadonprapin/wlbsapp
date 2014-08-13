<?php
switch ($requestCode)
{
    case OPCODE::SERVICE_REG_OWNER_REQUEST:
       if (empty($json->{'Email'}) || empty($json->{'Password'}) || empty($json->{'Firstname'}) || 
           empty($json->{'Lastname'}) || empty($json->{'PhoneNumber'}) || empty($json->{'CitizenId'}))
       echo_invalid_param($json);
         
       register_owner($json);
   
    break;
    
    case OPCODE::SERVICE_SAVE_SHOP_REQUEST:
        if (!empty($json->{'ShopId'}))
        {
            if (empty($json->{'ShopName'}) || empty($json->{'PhoneNumber'}) || empty($json->{'Latitude'}) || 
                empty($json->{'Longitude'}) || empty($json->{'Address'}))
            echo_invalid_param($json);
        }
        else
        {
            if (empty($json->{'ShopName'}) || empty($json->{'PhoneNumber'}) || empty($json->{'Latitude'}) || 
                empty($json->{'Longitude'}) || empty($json->{'Address'}) || empty($json->{'ShopType'}) || 
                empty($json->{'OwnerId'}))
            echo_invalid_param($json);
        }
        save_shop($json);
        
    break;
    
    case OPCODE::SERVICE_SAVE_ACCESSPOINT_REQUEST:
        
        if (empty($json->{'Name'}) || empty($json->{'BSSID'}) || empty($json->{'ShopId'}))
            echo_invalid_param($json);
        
        save_accesspoint($json);
    
    break;
    
    case OPCODE::SERVICE_SAVE_PROMOTION_REQUEST:
        if (!empty($json->{'PromotionId'}))
        {
            if (empty($json->{'Name'}) || empty($json->{'Detail'}))
                echo_invalid_param($json);
        }
        else
        {
            if (empty($json->{'Name'}) || empty($json->{'Detail'}) || !isset($json->{'P'}))
                echo_invalid_param($json);
        }
        save_promotion($json);
        
    break;
    
    case OPCODE::SERVICE_SAVE_COPROMOTION_REQUEST:
        
        if (empty($json->{'ShopRequesterId'}) || empty($json->{'ShopPartnerId'}) || 
            empty($json->{'Name'}) || empty($json->{'Detail'}))
        echo_invalid_param($json);
            
        save_co_promotion($json);
        
    break;
    
    case OPCODE::SERVICE_SAVE_ACCEPT_COPROMOTION_REQUEST:
        
        if (empty($json->{'CoPromotionId'}))
            echo_invalid_param($json);
        
        save_accept_co_promotion($json);  
        
    break;
    
    case OPCODE::SERVICE_SAVE_COMMENT_REQUEST:
        
        if (empty($json->{'ShopId'}) || empty ($json->{'CommentText'}))
            echo_invalid_param($json);
            
        save_comment($json);
        
    break;
    
    case OPCODE::SERVICE_SAVE_PICTURE_REQUEST:
        
        if (empty($json->{'Name'}) || empty($json->{'ShopId'}) || empty($_FILES['file']['name']))
            echo_invalid_param($json);
        
        save_picture($json);
    break;
}

function register_owner($json)
{
    $database = new Database();
    
    $userTbl = $database->getUserTbl();
    $ownerTbl = $database->getOwnerTbl();

    $exist = $userTbl->isExisted($json->{'Email'});

    if ($exist)
       echo_dup_email_msg();

    $user = new User();
    $user->Firstname = $json->{'Firstname'};
    $user->Lastname = $json->{'Lastname'};
    $user->PhoneNumber = $json->{'PhoneNumber'};
    $user->Email = $json->{'Email'};
    $user->Password = $json->{'Password'};
    $user->CitizenId = $json->{'CitizenId'};

    $success = $userTbl->save($user);

    if (!$success)
       echo_save_unsuc_msg();

    $owner = new Owner();
    $owner->UserId = $user->UserId;
    $owner->Activated = false;

    $success = $ownerTbl->save($owner);
    if (!$success)
       echo_save_unsuc_msg();
    else
       echo_save_suc_msg();

}

function save_shop($json)
{
    $newFilename = "";
    if (!empty($_FILES['file']['name']))
    {
        $filename = $_FILES['file']['name'];

        $spt = explode(".", $filename);

        $extension = $spt[1];
        $newFilename = "resource/shop/".uniqid(date('mdy', time())).".".$extension;

        $isMove = move_uploaded_file($_FILES['file']['tmp_name'], "../".$newFilename);

        if (!$isMove)
            echo_save_unsuc_msg ();
    }

    $database = new Database();
    
    $shopTbl = $database->getShopTbl();
    $shop = new Shop();
    if (!empty($json->{'ShopId'}))
        $shop->ShopId = $json->{'ShopId'};
    $shop->Name = $json->{'ShopName'};
    $shop->PhoneNumber = $json->{'PhoneNumber'};
    $shop->Latitude = $json->{'Latitude'};
    $shop->Longitude = $json->{'Longitude'};
    $shop->Address = $json->{'Address'};
    if (!empty($json->{'ShopType'}))
        $shop->ShopType = $json->{'ShopType'};
    if (!empty($json->{'OwnerId'}))
        $shop->OwnerId = $json->{'OwnerId'};
    $shop->PicturePath = $newFilename;

    $success = $shopTbl->save($shop);
    if ($success)
    {
        echo_save_suc_msg();
    }
    else
    {
        if (!empty($_FILES['file']['name']))
            unlink("../".$newFilename);
        echo_save_unsuc_msg();
    }
}

function save_accesspoint($json)
{
    $database = new Database();
    $apTbl = $database->getAccessPointTbl();
        
    $isExist = $apTbl->isExisted($json->{'BSSID'});
    if ($isExist) echo_dup_bssid_msg();

    $ap = new AccessPoint();
    $ap->Name = $json->{'Name'};
    $ap->BSSID = $json->{'BSSID'};
    $ap->ShopId = $json->{'ShopId'};

    $success = $apTbl->save($ap);

    if ($success)
        echo_save_suc_msg();
    else
        echo_save_unsuc_msg();
}

function save_promotion($json)
{
    $newFilename = "";
    if (!empty($_FILES['file']['name']))
    {
        $filename = $_FILES['file']['name'];

        $spt = explode(".", $filename);

        $extension = $spt[1];
        $newFilename = "resource/promotion/".uniqid(date('mdy', time())).".".$extension;

        $isMove = move_uploaded_file($_FILES['file']['tmp_name'], "../".$newFilename);

        if (!$isMove)
            echo_save_unsuc_msg ();
    }

    $database = new Database();
    
    $promotionTbl = $database->getPromotionTbl();
    $shopProTbl = $database->getShopPromotionTbl();

    $promotion = new Promotion();
    if (!empty ($json->{'PromotionId'}))
    $promotion->PromotionId = $json->{'PromotionId'};
    $promotion->Name = $json->{'Name'};
    $promotion->Detail = $json->{'Detail'};
    $promotion->PicturePath = $newFilename;

    $success = $promotionTbl->save($promotion);

    if (!$success)
        echo_save_unsuc_msg();


    if (!empty($json->{'PromotionId'})) echo_save_suc_msg(); // edit promotion only !

    // Add promotion as shop promotion 
    $shopPromotion = new ShopPromotion();
    $shopPromotion->PromotionId = $promotion->PromotionId;
    $shopPromotion->ShopId = $json->{'ShopId'};
    $success = $shopProTbl->save($shopPromotion);

    if ($success) {echo_save_suc_msg();}
    else {echo_save_unsuc_msg (); if (!empty($_FILES['file']['name'])) unlink("../".$newFilename);}
}

function save_co_promotion($json)
{
    $database = new Database();
    
    $newFilename = "";
    if (!empty($_FILES['file']['name']))
    {
        $filename = $_FILES['file']['name'];

        $spt = explode(".", $filename);

        $extension = $spt[1];
        $newFilename = "resource/promotion/".uniqid(date('mdy', time())).".".$extension;

        $isMove = move_uploaded_file($_FILES['file']['tmp_name'], "../".$newFilename);

        if (!$isMove)
            echo_save_unsuc_msg ();
    }

    $promotionTbl = $database->getPromotionTbl();
    $coProTbl = $database->getCoPromotionTbl();

    $promotion = new Promotion();

    $promotion->Name = $json->{'Name'};
    $promotion->Detail = $json->{'Detail'};
    $promotion->PicturePath = $newFilename;

    $success = $promotionTbl->save($promotion);

    if(!$success) echo_save_unsuc_msg(); 

    $co_promotion = new CoPromotion();
    $co_promotion->PromotionId = $promotion->PromotionId;
    $co_promotion->ShopRequesterId = $json->{'ShopRequesterId'};
    $co_promotion->ShopPartnerId = $json->{'ShopPartnerId'};

    $success = $coProTbl->save($co_promotion);

    if ($success) {echo_save_suc_msg();}
    else {echo_save_unsuc_msg(); if (!empty($_FILES['file']['name'])) unlink("../".$newFilename);}    
}


function save_accept_co_promotion($json)
{
    $database = new Database();
    
    $coProTbl = $database->getCoPromotionTbl();
    $success = $coProTbl->acceptCoPromotion($json->{'CoPromotionId'});

    if ($success) echo_save_suc_msg();
    else echo_save_unsuc_msg ();   
}

function save_comment($json)
{
    $database = new Database();
    
    $commentTbl = $database->getCommentTbl();
        
    $comment = new Comment();
    $comment->ShopId = $json->{'ShopId'};
    $comment->Text = $json->{'CommentText'};

    $success = $commentTbl->save($comment);

    if ($success) echo_save_suc_msg();
    else echo_save_unsuc_msg (); 
}

function save_picture($json)
{
    $newFilename = "";
    if (!empty($_FILES['file']['name']))
    {
        $filename = $_FILES['file']['name'];

        $spt = explode(".", $filename);

        $extension = $spt[1];
        $newFilename = "resource/shop/".uniqid(date('mdy', time())).".".$extension;

        $isMove = move_uploaded_file($_FILES['file']['tmp_name'], "../".$newFilename);

        if (!$isMove)
            echo_save_unsuc_msg ();
    }

    $database = new Database();
    $pictureTbl = $database->getPictureTbl();

    $picture = new Picture();
    $picture->Name = $json->{'Name'};
    $picture->ShopId = $json->{'ShopId'};
    $picture->SavePath = $newFilename;

    $success = $pictureTbl->save($picture);

    if ($success) {echo_save_suc_msg();}
    else {echo_save_unsuc_msg (); unlink("../".$newFilename);}
}
?>