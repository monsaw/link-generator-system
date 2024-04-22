package com.hommies.linkgeneratorsystem.controller;


import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.dtos.InvoiceRequest;
import com.hommies.linkgeneratorsystem.dtos.InvoiceResponse;
import com.hommies.linkgeneratorsystem.model.Invoice;
import com.hommies.linkgeneratorsystem.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("invoice")
public class InvoiceController {

    private final InvoiceService service;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<InvoiceResponse>> generateInvoice(@RequestBody @Valid InvoiceRequest request){
       return new ResponseEntity<>(service.generateInvoice(request),HttpStatus.CREATED);

    }

    @GetMapping("/{invoiceReference}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<InvoiceDTO>> fetchInvoiceByReference(@PathVariable String invoiceReference){
        return new ResponseEntity<>(service.fetchInvoiceByReference(invoiceReference),HttpStatus.OK);

    }

    @GetMapping("/merchant/{merchantCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<Invoice>>> fetchAllInvoicesPerMerchant(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize,
            @PathVariable String merchantCode){
        return new ResponseEntity<>(service.fetchAllInvoicesPerMerchant(merchantCode,pageNumber,pageSize),HttpStatus.OK);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<Invoice>>> fetchAllInvoices(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize){
        return new ResponseEntity<>(service.fetchAllInvoices(pageNumber,pageSize),HttpStatus.OK);

    }

    @GetMapping("transaction/{transactionLink}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<InvoiceDTO>> fetchInvoiceByTransactionLink(
            @PathVariable String transactionLink){
        return new ResponseEntity<>(service.fetchInvoiceByTransactionLink(transactionLink),HttpStatus.OK);

    }




}
