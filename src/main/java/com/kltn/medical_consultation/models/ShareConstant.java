package com.kltn.medical_consultation.models;

public interface ShareConstant {

    class HEADER {
        public static final String TOKEN_PREFIX = "bearer ";
        public static final String HEADER_TOKEN = "Authorization";
    }

    class REDIS_HASH {
        public static final String TOKEN = "quotation_token";
        public static final String BLOCK_IP = "quotation_block_ip";
        public static final String QUOTATION_GENERATE = "quotation_quotation_generate";
        public static final String RESET_PASSWORD = "quotation_reset_password";
        public static final String QUOTATION_DATA = "quotation_quotation_data";
        public static final String USER_LOGIN = "quotation_user_login";
    }

    class REST_HEADER {
        public static final String traceId = "X-B3-TraceId";
        public static final String DEVICE_Id = "device-id";
        public static final String TOKEN_HEADER = "x-authen-token";
        public static final String AGENT_HEADER = "platform";
        public static final String TOKEN_PREFIX = "bearer ";
        public static final String AUTHORIZATION = "Authorization";
    }
}
