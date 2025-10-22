package com.example.PaymentService.config;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RazorpayWrapper {

    private final RazorpayConfig config;
    private RazorpayClient client;

    @PostConstruct
    private void init() throws RazorpayException {
        client = new RazorpayClient(config.getKeyId(), config.getKeySecret());
    }

    public Order createOrder(int amountInPaise, String currency, String receiptId) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receiptId);
        orderRequest.put("payment_capture", 1);
        return client.orders.create(orderRequest);
    }
}
