package com.kltn.medical_consultation.models.auth;

import com.kltn.medical_consultation.models.IMessageCode;

public enum AuthMessageCode implements IMessageCode {
    UNKNOWN("1111.0.Unknown", "Chưa có thông điệp cho trường hợp này", "Chưa có thông điệp cho trường hợp này"),
    AUTH_1_1("auth.1.1","Đăng nhập thành công","Đăng nhập thành công"),
    AUTH_2_1("auth.2.1","Đăng xuất thành công","Đăng xuất thành công"),
    AUTH_3_1("auth.3.1","Đăng Ký thành công","Đăng Ký thành công"),
    AUTH_1_0("auth.1.0","Email hoặc mật khẩu không chính xác","Đăng nhập thất bại"),
    AUTH_3_0_OTP("auth.3.0.OTP","YOTP không chính xác. Vui lòng thử lại!","OTP không chính xác"),
    AUTH_3_0_OTP_EXPIRED("auth.3.0.OTP","OTP đã hết hạn. Vui lòng thử lại!","OTP hết hạn"),
    AUTH_3_1_OTP("auth.3.1.OTP","OTP chính xác","OTP chính xác"),
    AUTH_3_1_SEND_OTP("auth.3.1.SEND.OTP","Gửi OTP thành công","Gửi OTP thành công"),
    AUTH_4_1("auth.4.1","Đổi mật khẩu thành công. Vui lòng đăng nhập lại!","Đổi mật khẩu thành công"),
    AUTH_5_0_NOT_FOUND("auth.5.0.NOT.FOUND","Không tìm thấy tài khoản","Tài khoản không tìm thấy"),
    AUTH_5_0_NOT_EXIST("auth.5.0.NOT.EXIST","Tài khoản không tồn tại","Tài khoản đã bị xóa"),
    AUTH_5_0_EXIST("auth.5.0.EXIST","Tài khoản đã tồn tại","Tài khoản đã tồn tại"),
    AUTH_5_0_INACTIVE("auth.5.0.INACTIVE","Tài khoản của bạn chưa được xác thực. Vui lòng xác thực tài khoản!","Tài khoản đã bị vô hiệu hóa"),
    ;
    private String code;
    private String message;
    private String instance;

    AuthMessageCode(String code, String message, String instance) {
        this.code = code;
        this.message = message;
        this.instance = instance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
