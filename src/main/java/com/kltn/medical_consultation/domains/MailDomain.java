package com.kltn.medical_consultation.domains;

import com.kltn.medical_consultation.entities.redis.EmailOtpDTO;
import com.kltn.medical_consultation.models.ShareConstant;
import com.kltn.medical_consultation.services.MailService;
import com.kltn.medical_consultation.services.VelocityService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class MailDomain {
    private static final Logger LOGGER = LogManager.getLogger(MailDomain.class);

    @Value("${mode}")
    private String mode;

    @Autowired
    MailService mailService;

    @Autowired
    VelocityService velocityService;

//    public synchronized void sendOtpEmail(ResetPasswordDTO resetPasswordDTO, String subject) {
//        String functionName = "sendOtpEmail";
//        Map<String, Object> model = new HashMap<>();
////        model.put("email", resetPasswordDTO.getEmail());
////        model.put("otp", resetPasswordDTO.getOtp());
//
//        String body = velocityService.mergeTemplate(ShareConstant.MAIL_TEMPLATE.PHA_MAIL_VM, model);
////        String body = "Dear VietJetAir-Team,\n\nyou have asked us, we have spoken with our bank and clarified everything (see our email history and an excerpt attached).\nThe money did not arrive, because the credit card we used to book the flights was no longer active (see attachments, pls check the ARN references).\n\nIt concerns the following flights, which we were cancelled due to COVID-19:\nref no. EHAUMV; SP3M9R; XZAHRT.\n\nWe did everything you wanted:\ntherefore, we would like to ask you to transfer the money (3,221,000 VND + 4,520,000 VND + 8,423.68 TWD)\nasap again to the following account:\n\nIBAN DE70 2004 1144 0302 6556 00\n(Owner: Christian Meurisch)\n\nThanks for your support & best regards,\nChristian Meurisch";
//
//        LOGGER.info("[{}] Send OTP email (email={}, otp={})", functionName, resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp());
//
//        if(!body.equals(ShareConstant.MAIL_TEMPLATE.OTP_VM)){
//            try {
//                mailService.sendSimpleMessage(resetPasswordDTO.getEmail(), subject, body, true);
//            } catch (MessagingException e){
//                LOGGER.error("[{}] Cannot send OTP (email={}): {}", functionName, resetPasswordDTO.getEmail(), e);
//            }
//        }
//    }

    public void sendOtpEmail(EmailOtpDTO emailOtpDTO) {
        String functionName = "sendOtpEmail";
        Map<String, Object> model = new HashMap<>();
        model.put("email", emailOtpDTO.getEmail());
        model.put("otp", emailOtpDTO.getOtp());

        String body = velocityService.mergeTemplate(ShareConstant.MAIL_TEMPLATE.OTP_VM, model);

        LOGGER.info("[{}] Send OTP email (email={}, otp={})", functionName, emailOtpDTO.getEmail(), emailOtpDTO.getOtp());

        if (!body.equals(ShareConstant.MAIL_TEMPLATE.OTP_VM)) {
            try {
                mailService.sendSimpleMessage(emailOtpDTO.getEmail(), String.format("Your OTP"), body, true);
            } catch (MessagingException e) {
                LOGGER.error("[{}] Cannot send OTP (email={}): {}", functionName, emailOtpDTO.getEmail(), e);
            }
        }
    }

    public void sendVerifyEmail(String email, String verifyLink) {
        String functionName = "sendVerifyEmail";
        Map<String, Object> model = new HashMap<>();
        model.put("email", email);
        model.put("verifyLink", verifyLink);

        String body = velocityService.mergeTemplate(ShareConstant.MAIL_TEMPLATE.VERIFY_EMAIL_VM, model);

        LOGGER.info("[{}] Send verify email (email={})", functionName, email);

        if (!body.equals(ShareConstant.MAIL_TEMPLATE.OTP_VM)) {
            try {
                mailService.sendSimpleMessage(email, String.format("Verify Email"), body, true);
            } catch (MessagingException e) {
                LOGGER.error("[{}] Cannot send OTP (email={}): {}", functionName, email, e);
            }
        }
    }
}
