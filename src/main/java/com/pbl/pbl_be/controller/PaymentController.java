package com.pbl.pbl_be.controller;


import com.pbl.pbl_be.dto.PaymentRequestDTO;
import com.pbl.pbl_be.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody PaymentRequestDTO dto, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String paymentUrl = paymentService.createPayment(dto, ip);

        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }


    @GetMapping("/return")
    public String handleReturn(@RequestParam Map<String, String> params) {
        return paymentService.handleReturnUrl(params);
    }
}

