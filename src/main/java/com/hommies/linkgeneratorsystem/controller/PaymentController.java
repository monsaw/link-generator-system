package com.hommies.linkgeneratorsystem.controller;


import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.dtos.PaymentDTO;
import com.hommies.linkgeneratorsystem.dtos.PaymentRequest;
import com.hommies.linkgeneratorsystem.model.Payment;
import com.hommies.linkgeneratorsystem.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("make-payment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PaymentDTO>> makePayment(@RequestBody @Valid PaymentRequest paymentRequest){
        return new ResponseEntity<>(paymentService.makePayment(paymentRequest),HttpStatus.CREATED);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<Payment>>> fetchAllPayments(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                                                                       @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize
                                                                       ){
        return new ResponseEntity<>(paymentService.fetchAllPayments(pageNumber,pageSize),HttpStatus.OK);

    }

    @GetMapping("/{merchantCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<Payment>>> fetchAllPaymentsPerMerchant(
                            @PathVariable String merchantCode,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                            @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize
    ){
        return new ResponseEntity<>(paymentService.fetchAllPaymentsPerMerchant(merchantCode,pageNumber,pageSize),HttpStatus.OK);

    }

    @GetMapping("/reference/{paymentReference}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Payment>> fetchPaymentByPaymentReference(
            @PathVariable String paymentReference
    ){
        return new ResponseEntity<>(paymentService.fetchPaymentByPaymentReference(paymentReference),HttpStatus.OK);

    }
}
