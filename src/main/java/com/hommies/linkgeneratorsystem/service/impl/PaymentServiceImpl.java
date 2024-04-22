package com.hommies.linkgeneratorsystem.service.impl;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.PaymentDTO;
import com.hommies.linkgeneratorsystem.dtos.PaymentRequest;
import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.exceptions.*;
import com.hommies.linkgeneratorsystem.model.Payment;
import com.hommies.linkgeneratorsystem.repository.PaymentRepository;
import com.hommies.linkgeneratorsystem.repository.TransactionLinkRepository;
import com.hommies.linkgeneratorsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceServiceImpl invoiceService;
    private final TransactionLinkRepository transactionLinkRepository;


    @Override
    public ApiResponse<PaymentDTO> makePayment(PaymentRequest request) {
        log.info("Payment request {}", request);
       var invoice = invoiceService.fetchInvoiceByInvoiceTransactionLink(request.getTransactionLink());
       var transactionLink = transactionLinkRepository.findByLink(request.getTransactionLink());
       if(transactionLink.isEmpty()){
           throw new TransactionInvoiceNotExistException("Transaction Invoice does not exist!");
       }
       var payment = paymentRepository.findByTransactionLink(request.getTransactionLink());
       if(payment.isPresent() && payment.get().getStatus().equals(Status.PAID)){
           throw new PaymentException("Transaction Link has been used to make the payment");
       }
       
       if(invoice.getPaymentReference() != null && invoice.getStatus().equals(Status.PAID)){
           throw new PaymentException("Payment Already made for this invoice!");
       }
       
       if(LocalDateTime.now().isAfter(transactionLink.get().getExpiredAt())){
           throw new TransactionException("Link already expired, Kindly Reach out to get new Transaction Link");
       }

        if(transactionLink.get().isUsed()){
            throw new TransactionException("Link already used ,");
        }

       if(new BigDecimal(request.getAmount()).compareTo(invoice.getTotalPrice()) != 0){
           throw new PaymentAmountException("Amount to pay is not equivalent to the invoice Amount");
       }

       var newPayment = Payment.builder()
                .createdAt(LocalDateTime.now())
                .totalAmount(new BigDecimal(request.getAmount()))
                .status(Status.PAID)
                .merchantCode(invoice.getMerchantCode())
                .payerName(invoice.getCustomerName())
                .invoiceReference(invoice.getInvoiceReference())
                .transactionLink(request.getTransactionLink())
                .build();
       
        newPayment = paymentRepository.save(newPayment);
        log.info("Payment saved successfully");

        invoiceService.updateInvoice(newPayment.getInvoiceReference(),Status.PAID, String.valueOf(newPayment.getId()));
        log.info("Invoice Updated successfully");
        var transLink = transactionLinkRepository.findByLink(request.getTransactionLink());
        transLink.get().setUsed(true);
        transactionLinkRepository.save(transLink.get());
        var paymentResponse = PaymentDTO.builder()
                .paymentReference(String.valueOf(newPayment.getId()))
                .status(newPayment.getStatus())
                .totalAmount(newPayment.getTotalAmount())
                .merchantCode(newPayment.getMerchantCode())
                .payerName(newPayment.getPayerName())
                .invoiceReference(newPayment.getInvoiceReference())
                .transactionLink(newPayment.getTransactionLink())
                .createdAt(newPayment.getCreatedAt())
                .build();

        return new ApiResponse<>(HttpStatus.CREATED, "Payment made successfully",paymentResponse );

    }

    @Override
    public ApiResponse<Page<Payment>> fetchAllPayments(int page, int size) {
        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

       var response =  paymentRepository.findAll(pageRequest);
        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully",response);

    }

    @Override
    public ApiResponse<Page<Payment>> fetchAllPaymentsPerMerchant(String merchantCode,int page, int size) {

        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

        var response =  paymentRepository.findAllByMerchantCode(merchantCode,pageRequest);
        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully",response);

    }

    @Override
    public ApiResponse<Payment> fetchPaymentByPaymentReference(String paymentReference) {
        var response = paymentRepository.findById(Long.valueOf(paymentReference));
        if(response.isEmpty()){
            throw new PaymentNotExistException("Payment Not Found");
        }
        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully",response.get());

    }


}
