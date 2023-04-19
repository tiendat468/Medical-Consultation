package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.configs.VNPayConfig;
import com.kltn.medical_consultation.entities.database.Payment;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.ShareConstant;
import com.kltn.medical_consultation.models.vnpay.KeyValue;
import com.kltn.medical_consultation.models.vnpay.VNPayMessageCode;
import com.kltn.medical_consultation.models.vnpay.PaymentDTO;
import com.kltn.medical_consultation.repository.database.PaymentRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class VNPayService extends BaseService {
    private final PaymentRepository paymentRepository;

    public VNPayService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    public BaseResponse createPayment(PaymentDTO paymentDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        validatePayment(paymentDTO);
        String vnpCreateDate = formatter.format(cld.getTime());
        Payment payment = new Payment();
        payment.payment(paymentDTO);
        String txnRef = UUID.randomUUID().toString().replaceAll("-", "");
        payment.setVnpTxnRef(txnRef);
        payment.setVnpPayDate(vnpCreateDate);
        PaymentDTO paymentDto = new PaymentDTO();
        paymentDto.paymentDTO(paymentRepository.save(payment));
        if(paymentDto == null){
//            return new ResponseHandler(Errors.ERROR_CREATE_PAYMENT);
            throw new ApiException(VNPayMessageCode.ERROR_CREATE_PAYMENT);
        }
        return createTransaction(paymentDto, request);
    }

    public BaseResponse createTransaction(PaymentDTO paymentDTO, HttpServletRequest request) {
        try {
            List<KeyValue> keyValues = initRequestData(paymentDTO, request);
            return process(keyValues);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<KeyValue> initRequestData(PaymentDTO paymentDTO, HttpServletRequest request) {
        String expiredDate = formatter.format(new Date(cld.getTimeInMillis() + 24 * 60 * 60 * 1000));
        List<KeyValue> keyValues = new LinkedList<>();

        BigDecimal amount = paymentDTO.getVnp_Amount();
        amount = amount.multiply(BigDecimal.valueOf(100));

        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_AMOUNT, amount.toString()));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_COMMAND, ShareConstant.VN_PAY.COMMAND));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_CREATE_DATE, paymentDTO.getVnp_PayDate()));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_CURRCODE, ShareConstant.VN_PAY.CURR_CODE));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_IP_ADDRR, VNPayConfig.getIpAddress(request)));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_LOCALE, ShareConstant.VN_PAY.LOCALE));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_ORDER_INFO, paymentDTO.getVnp_OrderInfo()));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_ORDER_TYPE, ShareConstant.VN_PAY.ORDER_TYPE));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_RETURN_URL, ShareConstant.VN_PAY.RETURN_URL));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_TMN_CODE, ShareConstant.VN_PAY.TMN_CODE));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_TXN_REF, paymentDTO.getVnp_TxnRef()));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_VERSION, ShareConstant.VN_PAY.VERSION));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_BANK_CODE, paymentDTO.getVnp_BankCode()));
        keyValues.add(new KeyValue(ShareConstant.VN_PAY.VNP_EXPIRE_DATE, expiredDate));

        Collections.sort(keyValues, Comparator.comparing(KeyValue::getKey));
        return keyValues;
    }

    public BaseResponse process(List<KeyValue> keyValues) throws UnsupportedEncodingException {

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<KeyValue> iterator = keyValues.iterator();
        KeyValue keyValue;
        while (iterator.hasNext()) {
            keyValue = iterator.next();
            if ((keyValue.getValue() != null) && (keyValue.getValue().length() > 0)) {
                hashData.append(keyValue.getKey());
                hashData.append('=');
                hashData.append(URLEncoder.encode(keyValue.getValue(), StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(keyValue.getKey(), StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(keyValue.getValue(), StandardCharsets.US_ASCII.toString()));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnpSecurehash = VNPayConfig.hmacSHA512(ShareConstant.VN_PAY.SECRET_KEY, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnpSecurehash;
        String paymentUrl = ShareConstant.VN_PAY.VNP_PAY_URL + "?" + queryUrl;
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(paymentUrl);
        return baseResponse;
    }

    public void validatePayment(PaymentDTO paymentDTO){
        if(StringUtils.isBlank(paymentDTO.getVnp_BankCode())){
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("vnp_BankCode"));
        }
        if(StringUtils.isBlank(paymentDTO.getVnp_OrderInfo())){
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("vnp_OrderInfo"));
        }
        if(paymentDTO.getVnp_Amount() == null){
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("vnp_Amount"));
        }
        if(paymentDTO.getUser_id() == null){
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("user_id"));
        }
    }
}
