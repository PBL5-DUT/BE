package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentRedirectController {

    private final PaymentService paymentService;

    public PaymentRedirectController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment-success")
    public String handlePaymentSuccess(@RequestParam Map<String, String> params) {
        return paymentService.handleReturnUrl(params);
    }
}

