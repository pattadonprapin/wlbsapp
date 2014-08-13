
package common;

public class OPCODE 
{ 
    public static final int SERVICE_REG_OWNER_REQUEST                = 100001;
    public static final int SERVICE_SAVE_SHOP_REQUEST                = 100002;
    public static final int SERVICE_SAVE_ACCESSPOINT_REQUEST         = 100003;
    public static final int SERVICE_SAVE_PROMOTION_REQUEST           = 100004;
    public static final int SERVICE_SAVE_COPROMOTION_REQUEST         = 100005;
    public static final int SERVICE_SAVE_ACCEPT_COPROMOTION_REQUEST  = 100006;
    public static final int SERVICE_SAVE_COMMENT_REQUEST             = 100007;
    public static final int SERVICE_SAVE_PICTURE_REQUEST             = 100008;

    public static final int SERVICE_REMOVE_ACCESSPOINT_REQUEST   = 110001;
    public static final int SERVICE_REMOVE_COMMENT_REQUEST       = 110002;
    public static final int SERVICE_REMOVE_COPROMOTION_REQUEST   = 110003;
    public static final int SERVICE_REMOVE_PROMOTION_REQUEST     = 110004;
    public static final int SERVICE_REMOVE_PICTURE_REQUEST       = 110005;
    public static final int SERVICE_REMOVE_SHOP_REQUEST          = 110006;

    public static final int SERVICE_GET_REQUESTED_COPROMOTION_REQUEST  = 120001;
    public static final int SERVICE_GET_COPROMOTION_INFO_REQUEST       = 120002;
    public static final int SERVICE_GET_VALIDATION_LOGIN_REQUEST       = 120003;
    public static final int SERVICE_GET_ACCESSPOINT_REQUEST            = 120004;
    public static final int SERVICE_GET_COMMENT_REQUEST                = 120005;
    public static final int SERVICE_GET_SHOP_INFO_REQUEST              = 120006;
    public static final int SERVICE_GET_SHOP_INFO_REQUEST_BY_BSSID     = 120007;
    public static final int SERVICE_GET_PICTURE_REQUEST                = 120008;
    public static final int SERVICE_GET_PROMOTION_REQUEST              = 120009;

    public static final int SERVER_SAVE_SUCCESS_RESPONSE      = 200001;
    public static final int SERVER_SAVE_UNSUCCESS_RESPONSE    = 200002;

    public static final int SERVER_REMOVE_SUCCESS_RESPONSE   = 200003;
    public static final int SERVER_REMOVE_UNSUCCESS_RESPONSE = 200004;

    public static final int SERVER_ERROR_DUPLICATE_EMAIL_RESPONSE   = 210001;	
    public static final int SERVER_ERROR_DUPLICATE_BSSID_RESPONSE   = 210002;
    public static final int SERVER_ERROR_INVALID_PARAMETER_RESPONSE = 210003;

    public static final int SERVER_AUTHENTICATION_INVALID_RESPONSE    = 220001;
    public static final int SERVER_AUTHENTICATION_VALID_RESPONSE      = 220002;
    public static final int SERVER_AUTHENTICATION_INACTIVED_RESPONSE  = 220003;

}
