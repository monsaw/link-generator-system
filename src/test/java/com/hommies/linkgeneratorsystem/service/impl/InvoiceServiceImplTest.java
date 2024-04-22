package com.hommies.linkgeneratorsystem.service.impl;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceRequest;
import com.hommies.linkgeneratorsystem.dtos.InvoiceResponse;
import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.exceptions.InvoiceNotExistException;
import com.hommies.linkgeneratorsystem.model.Invoice;
import com.hommies.linkgeneratorsystem.model.Merchant;
import com.hommies.linkgeneratorsystem.repository.InvoiceItemsRepository;
import com.hommies.linkgeneratorsystem.repository.InvoiceRepository;
import com.hommies.linkgeneratorsystem.repository.MerchantRepository;
import com.hommies.linkgeneratorsystem.repository.TransactionLinkRepository;
import com.hommies.linkgeneratorsystem.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {InvoiceServiceImpl.class})
@ExtendWith(SpringExtension.class)
class InvoiceServiceImplTest {
    @MockBean
    private InvoiceItemsRepository invoiceItemsRepository;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @MockBean
    private MerchantRepository merchantRepository;

    @MockBean
    private TransactionLinkRepository transactionLinkRepository;

    @MockBean
    private Utils utils;


    @Test
    void testGenerateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        invoice.setCustomerName("Hommies Firm");
        invoice.setId(1L);
        invoice.setInvoiceReference("HOMM_Agshdgejgd");
        invoice.setItems(new ArrayList<>());
        invoice.setMerchantCode("HOMM");
        invoice.setPaymentReference("1");
        invoice.setStatus(Status.CREATED);
        invoice.setTotalPrice(new BigDecimal("10000.0"));
        invoice.setTransactionLink(new ArrayList<>());
        when(invoiceRepository.save(Mockito.<Invoice>any())).thenReturn(invoice);

        Merchant merchant = new Merchant();
        merchant.setCompanyName("Hommies Firm");
        merchant.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        merchant.setFirstName("Monsuru");
        merchant.setId(1L);
        merchant.setLastName("Lawal");
        merchant.setMerchantCode("HOMM");
        Optional<Merchant> ofResult = Optional.of(merchant);
        when(merchantRepository.findByMerchantCode(Mockito.<String>any())).thenReturn(ofResult);
        when(utils.generateInvoiceReference(Mockito.<String>any())).thenReturn("HOMM_Agshdgejgd");
        when(utils.generateTransactionLink(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("HOMM_ahdjuhfkfjd_1");
        InvoiceRequest request = mock(InvoiceRequest.class);
        when(request.getCustomerName()).thenReturn("Enoch");
        when(request.getItems()).thenReturn(new ArrayList<>());
        when(request.getMerchantCode()).thenReturn("HOMM");
        ApiResponse<InvoiceResponse> actualGenerateInvoiceResult = invoiceServiceImpl.generateInvoice(request);
        verify(request).getCustomerName();
        verify(request).getItems();
        verify(request, atLeast(1)).getMerchantCode();
        verify(merchantRepository).findByMerchantCode(Mockito.<String>any());
        verify(utils).generateInvoiceReference(Mockito.<String>any());
        verify(utils).generateTransactionLink(Mockito.<String>any(), Mockito.<String>any());
        verify(invoiceRepository, atLeast(1)).save(Mockito.<Invoice>any());
        InvoiceResponse data = actualGenerateInvoiceResult.getData();
        assertEquals("HOMM_ahdjuhfkfjd_1", data.getTransactionLink());
        assertEquals("HOMM_Agshdgejgd", data.getInvoiceReference());
        assertEquals("Invoice generated successfully", actualGenerateInvoiceResult.getMessage());
        assertEquals(Status.CREATED, data.getInvoiceStatus());
        assertEquals(HttpStatus.CREATED, actualGenerateInvoiceResult.getStatus());
        BigDecimal expectedTotalAmount = new BigDecimal("10000.0");
        assertEquals(expectedTotalAmount, data.getTotalAmount());
    }


    @Test
    void testFetchInvoiceByReference() {
        Optional<Invoice> emptyResult = Optional.empty();
        when(invoiceRepository.findByInvoiceReference(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(InvoiceNotExistException.class, () -> invoiceServiceImpl.fetchInvoiceByReference("HOMM_123343"));
        verify(invoiceRepository).findByInvoiceReference(Mockito.<String>any());
    }




    @Test
    void testFetchInvoiceByInvoiceReference() {
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        invoice.setCustomerName("Enouch");
        invoice.setId(1L);
        invoice.setInvoiceReference("HOMM_shjdgeje");
        invoice.setItems(new ArrayList<>());
        invoice.setMerchantCode("HOMM");
        invoice.setPaymentReference("2");
        invoice.setStatus(Status.CREATED);
        invoice.setTotalPrice(new BigDecimal("100.0"));
        invoice.setTransactionLink(new ArrayList<>());
        Optional<Invoice> ofResult = Optional.of(invoice);
        when(invoiceRepository.findByInvoiceReference(Mockito.<String>any())).thenReturn(ofResult);
        Invoice actualFetchInvoiceByInvoiceReferenceResult = invoiceServiceImpl
                .fetchInvoiceByInvoiceReference("HOMM_shjdgeje");
        verify(invoiceRepository).findByInvoiceReference(Mockito.<String>any());
        assertSame(invoice, actualFetchInvoiceByInvoiceReferenceResult);
    }

}
