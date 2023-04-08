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
}
