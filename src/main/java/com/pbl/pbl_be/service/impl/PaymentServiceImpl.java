package com.pbl.pbl_be.service.impl;


import com.pbl.pbl_be.config.VNPayConfig;
import com.pbl.pbl_be.dto.PaymentRequestDTO;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final VNPayConfig vnpayConfig;
    private final DonationRepository donationRepository;

    @Override
    public String createPayment(PaymentRequestDTO dto, String ip) {
        String txnRef = UUID.randomUUID().toString();
        String amount = String.valueOf(dto.getAmount() * 100);

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnpayConfig.vnp_TmnCode);
        vnpParams.put("vnp_Amount", amount);
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", "Donate to project");
        vnpParams.put("vnp_OrderType", "donation");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnpayConfig.vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", ip);
        vnpParams.put("vnp_CreateDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String field : fieldNames) {
            String value = vnpParams.get(field);
            hashData.append(field).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            query.append(field).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
        }
        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String secureHash = hmacSHA512(vnpayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        // Lưu đơn hàng trước khi redirect
        Donation donation = Donation.builder()
                .amount(dto.getAmount())
                .status("PENDING")
                .txnRef(txnRef)
                .createdAt(LocalDateTime.now())
                .build();
        donationRepository.save(donation);

        return vnpayConfig.vnp_PayUrl + "?" + query.toString();
    }

    @Override
    public String handleReturnUrl(Map<String, String> params) {
        String receivedHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String field : fieldNames) {
            String value = params.get(field);
            hashData.append(field).append('=').append(value).append('&');
        }
        hashData.setLength(hashData.length() - 1);

        String calculatedHash = hmacSHA512(vnpayConfig.vnp_HashSecret, hashData.toString());
        if (!calculatedHash.equals(receivedHash)) return "Sai chữ ký";

        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        Optional<Donation> optional = donationRepository.findByTxnRef(txnRef);
        if (optional.isPresent()) {
            Donation donation = optional.get();
            if ("00".equals(responseCode)) {
                donation.setStatus("SUCCESS");
            } else {
                donation.setStatus("FAILED");
            }
            donationRepository.save(donation);
            return donation.getStatus();
        }
        return "Không tìm thấy giao dịch";
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo chữ ký", e);
        }
    }
}

