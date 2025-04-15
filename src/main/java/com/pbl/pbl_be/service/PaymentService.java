package com.pbl.pbl_be.service;


import com.pbl.pbl_be.dto.PaymentRequestDTO;

import java.util.Map;

public interface PaymentService {
    String createPayment(PaymentRequestDTO requestDTO, String clientIp);
    String handleReturnUrl(Map<String, String> params);
}

