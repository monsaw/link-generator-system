package com.hommies.linkgeneratorsystem.service;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.PaymentDTO;
import com.hommies.linkgeneratorsystem.dtos.PaymentRequest;
import com.hommies.linkgeneratorsystem.model.Payment;
import org.springframework.data.domain.Page;

public interface PaymentService {

    ApiResponse<PaymentDTO> makePayment(PaymentRequest request);

    ApiResponse<Page<Payment>> fetchAllPayments(int page, int size);

    ApiResponse<Page<Payment>> fetchAllPaymentsPerMerchant(String merchantCode, int page, int size);

    ApiResponse<Payment> fetchPaymentByPaymentReference(String paymentReference);
}
