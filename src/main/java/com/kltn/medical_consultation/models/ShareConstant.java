package com.kltn.medical_consultation.models;

public interface ShareConstant {

    class HEADER {
        public static final String TOKEN_PREFIX = "bearer ";
        public static final String HEADER_TOKEN = "Authorization";
    }

    class REDIS_HASH {
        public static final String TOKEN = "mc_token";
        public static final String BLOCK_IP = "mc_block_ip";
        public static final String RESET_PASSWORD = "mc_reset_password";
        public static final String SEND_OTP = "mc_send_otp";
        public static final String USER_LOGIN = "mc_user_login";
    }

    class MAIL_TEMPLATE{
        public static final String ERROR = "template/mail/error.vm";
        public static final String OTP_VM = "templates/mail/otp.vm";
        public static final String VERIFY_EMAIL_VM = "templates/mail/xac-thuc-email.vm";
        public static final String FORGOT_PASSWORD_VM = "templates/mail/quen-mat-khau.vm";
    }

    class OTP{
        public static final int LENGTH = 4;
        public static final String SEND_OTP_VERIFY_EMAIL = "vm";
        public static final String SEND_OTP_RESET_PASSWORD = "rs";
    }

    class REST_HEADER {
        public static final String traceId = "X-B3-TraceId";
        public static final String DEVICE_Id = "device-id";
        public static final String TOKEN_HEADER = "x-authen-token";
        public static final String AGENT_HEADER = "platform";
        public static final String TOKEN_PREFIX = "bearer ";
        public static final String AUTHORIZATION = "Authorization";
    }

    class SEX {
        public static final String MALE = "MALE";
        public static final String FEMALE = "FEMALE";
    }

    class VN_PAY {
        public static final String TMN_CODE = "Y8NJ5ZR3";
        public static final String SECRET_KEY = "VGCCMWLLTLWAGVJTVPZFSEIUGEALJWWF";
        public static final String VNP_AMOUNT = "vnp_Amount";
        public static final String VNP_COMMAND = "vnp_Command";
        public static final String VNP_CREATE_DATE = "vnp_CreateDate";
        public static final String VNP_CURRCODE = "vnp_CurrCode";
        public static final String VNP_IP_ADDRR = "vnp_IpAddr";
        public static final String VNP_LOCALE = "vnp_Locale";
        public static final String VNP_ORDER_INFO = "vnp_OrderInfo";
        public static final String VNP_ORDER_TYPE = "vnp_OrderType";
        public static final String VNP_RETURN_URL = "vnp_ReturnUrl";
        public static final String VNP_TMN_CODE = "vnp_TmnCode";
        public static final String VNP_TXN_REF = "vnp_TxnRef";
        public static final String VNP_VERSION = "vnp_Version";
        public static final String VNP_SECURE_HASH = "vnp_SecureHash";
        public static final String VNP_BANK_CODE = "vnp_BankCode";
        public static final String VNP_EXPIRE_DATE = "vnp_ExpireDate";
        public static final String VNP_TRANSACTION_NO = "vnp_TransactionNo";
        public static final String VNP_TRANS_DATE = "vnp_TransDate";
        public static final String RETURN_URL = "http://localhost:8088/api/vnpay/update-payment";
        public static final String COMMAND = "pay";
        public static final String REFUND = "refund";
        public static final String REFUND_TYPE = "02";
        public static final String VERSION = "2.1.0";
        public static final String CURR_CODE = "VND";
        public static final String LOCALE = "vn";
        public static final String ORDER_TYPE ="1000";
        public static final String QUERYDR = "querydr";
        public static final String VNP_IPN = "vnp_Ipn";
        public static final String VNP_IPN_URL = "http://localhost:8088/api/vnpay/vnpay-webhook/vnpay_ipn";
        public static final String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        public static final String VNP_API_URL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    }
}
