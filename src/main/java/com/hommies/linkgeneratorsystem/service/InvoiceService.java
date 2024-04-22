package com.hommies.linkgeneratorsystem.service;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.dtos.InvoiceRequest;
import com.hommies.linkgeneratorsystem.dtos.InvoiceResponse;
import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.model.Invoice;
import org.springframework.data.domain.Page;

public interface InvoiceService {

    ApiResponse<InvoiceResponse> generateInvoice(InvoiceRequest request);

    ApiResponse<InvoiceDTO> fetchInvoiceByReference(String invoiceReference);

    ApiResponse<Page<Invoice>> fetchAllInvoicesPerMerchant(String merchantCode, int page, int size);

    ApiResponse<Page<Invoice>> fetchAllInvoices(int page, int size);

    Invoice fetchInvoiceByInvoiceReference(String invoiceReference);

    ApiResponse<InvoiceDTO> fetchInvoiceByTransactionLink(String transactionLink);

    Invoice fetchInvoiceByInvoiceTransactionLink(String transactionLink);

    Invoice updateInvoice(String invoiceReference, Status status, String paymentReference);
}
