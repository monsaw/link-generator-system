package com.hommies.linkgeneratorsystem.service.impl;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.exceptions.InvoiceNotExistException;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import com.hommies.linkgeneratorsystem.repository.InvoiceRepository;
import com.hommies.linkgeneratorsystem.repository.TransactionLinkRepository;
import com.hommies.linkgeneratorsystem.service.TransactionLinkService;
import com.hommies.linkgeneratorsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionLinkServiceImpl implements TransactionLinkService {

    private final TransactionLinkRepository transactionLinkRepository;

    private final InvoiceServiceImpl invoiceService;

    private final Utils utils;

    @Override
    public ApiResponse<InvoiceDTO> fetchInvoiceByTransactionLink(String transactionLink) {

       return invoiceService.fetchInvoiceByTransactionLink(transactionLink);

    }

    @Override
    public ApiResponse<TransactionLink> createTransactionLink(String invoiceReference) {

        log.info("create transaction-link request {}", invoiceReference);
       var invoice =  invoiceService.fetchInvoiceByInvoiceReference(invoiceReference);
       if(invoice == null){
           throw new InvoiceNotExistException("Invoice does not exist");
       }

       var transactionLink = TransactionLink.builder()
               .link(utils.generateTransactionLink(invoice.getMerchantCode(), String.valueOf(invoice.getId())))
               .invoice(invoice)
               .invoiceReference(invoiceReference)
               .createdAt(LocalDateTime.now())
               .expiredAt(LocalDateTime.now().plusMinutes(5))
               .used(false)
               .build();
       var link = transactionLinkRepository.save(transactionLink);
        log.info("transaction-link saved..");
        return new ApiResponse<>(HttpStatus.CREATED, "Invoice generated successfully",link );

    }

    @Override
    public ApiResponse<TransactionLink> fetchTransactionLinkByLink(String transactionLink) {

        var response =  transactionLinkRepository.findByLink(transactionLink);
        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully",response.get() );

    }

    @Override
    public ApiResponse<Page<TransactionLink>> fetchAll(int page, int size) {

        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

       var response =  transactionLinkRepository.findAll(pageRequest);

        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully",response);

    }


}
