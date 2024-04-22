package com.hommies.linkgeneratorsystem.controller;


import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import com.hommies.linkgeneratorsystem.service.TransactionLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("transaction-link")
public class TransactionLinkController {
    private final TransactionLinkService transactionLinkService;

    @GetMapping("/fetch/{transactionLink}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<InvoiceDTO>> fetchInvoiceByTransactionLink(@PathVariable String transactionLink){
        return new ResponseEntity<>(transactionLinkService.fetchInvoiceByTransactionLink(transactionLink),HttpStatus.CREATED);

    }


    @PostMapping("{invoiceReference}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<TransactionLink>> createTransactionLink(@PathVariable String invoiceReference){
        return new ResponseEntity<>(transactionLinkService.createTransactionLink(invoiceReference),HttpStatus.CREATED);

    }

    @GetMapping("{transactionLink}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<TransactionLink>> fetchTransactionLinkByLink(@PathVariable String transactionLink){
        return new ResponseEntity<>(transactionLinkService.fetchTransactionLinkByLink(transactionLink),HttpStatus.OK);

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<TransactionLink>>> fetchAll(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                                                                       @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize
    ){
        return new ResponseEntity<>(transactionLinkService.fetchAll(pageNumber,pageSize),HttpStatus.OK);

    }


}
