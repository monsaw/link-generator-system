package com.hommies.linkgeneratorsystem.service;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import org.springframework.data.domain.Page;

public interface TransactionLinkService {

    ApiResponse<InvoiceDTO> fetchInvoiceByTransactionLink(String transactionLink);

    ApiResponse<TransactionLink> createTransactionLink(String invoiceReference);

    ApiResponse<TransactionLink> fetchTransactionLinkByLink(String transactionLink);

    ApiResponse<Page<TransactionLink>> fetchAll(int page, int size);
}
