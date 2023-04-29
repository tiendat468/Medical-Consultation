package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.configs.VNPayConfig;
import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.models.auth.AuthMessageCode;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.ScheduleMessageCode;
import com.kltn.medical_consultation.models.vnpay.*;
import com.kltn.medical_consultation.repository.database.*;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.ShareConstant;
import com.kltn.medical_consultation.utils.JsonHelper;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class VNPayService extends BaseService {
    @Autowired
    private MedicalScheduleRepository scheduleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    VnpayPaymentRepository vnpayPaymentRepository;

    @Autowired
    PaymentRepository paymentRepository;

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    public BaseResponse createPayment(PaymentDTO paymentDTO, Long userId, HttpServletRequest request) throws UnsupportedEncodingException {
        paymentDTO.setUser_id(userId);
        validatePayment(paymentDTO);
        String vnpCreateDate = formatter.format(cld.getTime());
        Payment payment = new Payment(paymentDTO);
        String txnRef = UUID.randomUUID().toString().replaceAll("-", "");
        payment.setVnpTxnRef(txnRef);
        payment.setVnpPayDate(vnpCreateDate);
        payment = paymentRepository.save(payment);
        updateSchedule(payment, paymentDTO.getScheduleId());
        PaymentDTO paymentDto = new PaymentDTO(payment);
        if(paymentDto == null){
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

        Optional<User> optionalUser = userRepository.findById(paymentDTO.getUser_id());
        if (optionalUser.isEmpty()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_NOT_FOUND);
        }

        Optional<Patient> optionalPatient = patientRepository.findByUserId(paymentDTO.getUser_id());
        if (optionalPatient.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_NOT_FOUND);
        }

        Optional<MedicalSchedule> optionalSchedule = scheduleRepository.findById(paymentDTO.getScheduleId());
        if (optionalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        if (optionalSchedule.get().getPatientProfile().getPatientId() != optionalPatient.get().getId()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_INVALID);
        }
    }

    public void updateSchedule(Payment payment, Long scheduleId) {
        Optional<MedicalSchedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        MedicalSchedule medicalSchedule = optionalSchedule.get();
        medicalSchedule.setPayCode(payment.getVnpTxnRef());
        scheduleRepository.save(medicalSchedule);
    }

    public BaseResponse getPayment(String vnpTxnRef, HttpServletRequest request) {
        if(StringUtils.isBlank(vnpTxnRef)){
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("vnpTxnRef"));
        }

        Payment payment = paymentRepository.findByVnpTxnRef(vnpTxnRef);
        if(payment == null){
            throw new ApiException(VNPayMessageCode.ERROR_PAYMENT_NOT_FOUND);
        }
        return getTransaction(payment, request);
    }

    public BaseResponse getTransaction(Payment payment, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        List<Values> key = initRequestTransaction(payment, request);
        List<VnpayPayment> list = vnpayPaymentRepository.findByPaymentId(payment.getId());
        VnpayPayment vnpayPayment = list.size() == 0 ? null : list.get(list.size()-1);
        if(vnpayPayment != null && vnpayPayment.getIs_done()){
            baseResponse.setData(vnpayPayment);
            return baseResponse;
        }

        if (!payment.getHookStatus()) {
            return processQuerydr(key, payment);
        } else {
            QueryDrDTO queryDrDTO = createQueryDrDTO(key);
            queryDrDTO.setVnp_TransactionNo(payment.getVnpTransactionNo());
            VnpayPaymentDTO vnpayPaymentDTO = redirectApiVnpay(queryDrDTO);
            vnpayPayment = createVnpayPayment(vnpayPaymentDTO, payment);
        }

        baseResponse.setData(vnpayPayment);
        return baseResponse;
    }

    public VnpayPayment createVnpayPayment(VnpayPaymentDTO vnpayPaymentDTO, Payment payment) {
        try {
            VnpayPayment vnpayPayment = JsonHelper.getObject(JsonHelper.toString(vnpayPaymentDTO), VnpayPayment.class);
            Long vnpayAmount = Long.valueOf(vnpayPaymentDTO.getVnp_Amount() + "")/100;
            BigDecimal amount = new BigDecimal(vnpayAmount);
            vnpayPayment.setVnp_Amount(amount);
            if(vnpayPaymentDTO.getVnp_TransactionStatus() != null && vnpayPaymentDTO.getVnp_TransactionStatus().equals("00")){
                vnpayPayment.setIs_done(true);
            }

            payment = updateTransactionStatus(payment, vnpayPaymentDTO);
            vnpayPayment.setPayment(payment);
            vnpayPayment.setPaymentId(payment.getId());
            return vnpayPaymentRepository.save(vnpayPayment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public VnpayPaymentDTO redirectApiVnpay(QueryDrDTO queryDrDTO) {
        String url = ShareConstant.VN_PAY.VNP_API_URL;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(queryDrDTO, header);
        ResponseEntity<VnpayPaymentDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
                VnpayPaymentDTO.class);
        return responseEntity.getBody();
    }

    public QueryDrDTO createQueryDrDTO(List<Values> values) {
        StringBuilder hashData = new StringBuilder();
        Iterator<Values> iterator = values.iterator();
        Values value;

        while (iterator.hasNext()) {
            value = iterator.next();
            if ((value.getValue() != null) && (value.getValue().length() > 0)) {
                hashData.append(value.getValue());
                if (iterator.hasNext()) {
                    hashData.append('|');
                }
            }
        }

        String vnpSecureHash = VNPayConfig.hmacSHA512(ShareConstant.VN_PAY.SECRET_KEY, hashData.toString());
        QueryDrDTO queryDrDTO = new QueryDrDTO();
        queryDrDTO.queryDTO(values);
        queryDrDTO.setVnp_SecureHash(vnpSecureHash);
        return queryDrDTO;
    }

    public BaseResponse processQuerydr(List<Values> values, Payment payment) {
        QueryDrDTO queryDrDTO = createQueryDrDTO(values);
        queryDrDTO.setVnp_TransactionNo(payment.getVnpTransactionNo());
        VnpayPaymentDTO vnpayPaymentDTO = redirectApiVnpay(queryDrDTO);
        VnpayPayment vnpayPayment = createVnpayPayment(vnpayPaymentDTO, payment);

        BaseResponse baseResponse = new BaseResponse();
        if(vnpayPaymentDTO.getVnp_TransactionStatus() != null && vnpayPaymentDTO.getVnp_TransactionStatus().equals("00")){
            baseResponse.setData(vnpayPayment);
            return baseResponse;
        }

        baseResponse.setData(vnpayPaymentDTO);
        return baseResponse;
    }

    public List<Values> initRequestTransaction(Payment payment, HttpServletRequest request) {
        String vnpCreateDate = formatter.format(cld.getTime());
        String requestId = UUID.randomUUID().toString();
        List<Values> values = new LinkedList<>();

        values.add(new Values(requestId));
        values.add(new Values(ShareConstant.VN_PAY.VERSION));
        values.add(new Values(ShareConstant.VN_PAY.QUERYDR));
        values.add(new Values(ShareConstant.VN_PAY.TMN_CODE));
        values.add(new Values(payment.getVnpTxnRef()));
        values.add(new Values(payment.getVnpPayDate()));
        values.add(new Values(vnpCreateDate));
        values.add(new Values(VNPayConfig.getIpAddress(request)));
        values.add(new Values(payment.getVnpOrderInfo()));

        return values;
    }

    public Payment updateTransactionStatus(Payment payment, VnpayPaymentDTO vnpayPaymentDTO) {
        payment.setVnpResponseCode(vnpayPaymentDTO.getVnp_ResponseCode());
        payment.setVnpTmnCode(vnpayPaymentDTO.getVnp_TmnCode());
        payment.setVnpTransactionNo(vnpayPaymentDTO.getVnp_TransactionNo());
        payment.setVnpTransactionStatus(vnpayPaymentDTO.getVnp_TransactionStatus());
        payment.setHookStatus(true);
        payment = paymentRepository.save(payment);
        return payment;
    }

    public BaseResponse updatePayment(PaymentDTO paymentDTO) {
        Payment paymentUpdate = paymentRepository.findByVnpTxnRef(paymentDTO.getVnp_TxnRef());
        if (!paymentUpdate.getHookStatus()) {
            Long userId = paymentUpdate.getUserId();
            paymentUpdate.payment(paymentDTO);
            paymentUpdate.setUserId(userId);
            paymentUpdate.setHookStatus(true);
            paymentUpdate = paymentRepository.save(paymentUpdate);
        }
        return new BaseResponse(paymentUpdate);
    }
}
