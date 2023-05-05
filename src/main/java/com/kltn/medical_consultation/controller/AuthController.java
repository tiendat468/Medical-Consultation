package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.auth.request.*;
import com.kltn.medical_consultation.models.auth.response.LoginResponse;
import com.kltn.medical_consultation.models.auth.response.UserProfileResponse;
import com.kltn.medical_consultation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request, false);
    }

    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest httpServletRequest) throws ApiException {
        return authService.logout(httpServletRequest);
    }

    @GetMapping("/profile")
    public BaseResponse<UserProfileResponse> getProfileUser(HttpServletRequest httpServletRequest) throws ApiException {
        return authService.getProfileUser(httpServletRequest);
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/forgot-password")
    public BaseResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return authService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping("/reset-password")
    public BaseResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return authService.resetPassword(resetPasswordRequest);
    }

    @PostMapping("/register/resend-otp")
    public BaseResponse register(@RequestBody RegisterResendOTPRequest request) {
        return authService.resendOTP(request);
    }

    @PostMapping("/verify-otp")
    public BaseResponse verifyOTP(@RequestBody VerifyOtpRequest request) {
        return authService.verifyOTP(request);
    }

    @GetMapping("/verify-email")
    public BaseResponse verify(@RequestParam("code") String code) {
        Boolean isVerify = authService.verifyEmail(code);
        if (isVerify) {
            return new BaseResponse(ERROR.SUCCESS);
        }
        return new BaseResponse("Xác thực tài khoản không thành công");
    }
}
