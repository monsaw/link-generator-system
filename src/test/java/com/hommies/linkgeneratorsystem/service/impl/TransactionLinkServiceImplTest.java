package com.hommies.linkgeneratorsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.model.Invoice;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import com.hommies.linkgeneratorsystem.repository.TransactionLinkRepository;
import com.hommies.linkgeneratorsystem.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransactionLinkServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TransactionLinkServiceImplTest {
    @MockBean
    private InvoiceServiceImpl invoiceServiceImpl;

    @MockBean
    private TransactionLinkRepository transactionLinkRepository;

    @Autowired
    private TransactionLinkServiceImpl transactionLinkServiceImpl;

    @MockBean
    private Utils utils;




    @Test
    void testCreateTransactionLink() {
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        invoice.setCustomerName("Enouch");
        invoice.setId(1L);
        invoice.setInvoiceReference("HOMM_Dhsjahd");
        invoice.setItems(new ArrayList<>());
        invoice.setMerchantCode("HOMM");
        invoice.setPaymentReference("3");
        invoice.setStatus(Status.CREATED);
        invoice.setTotalPrice(new BigDecimal("2000"));
        invoice.setTransactionLink(new ArrayList<>());

        TransactionLink transactionLink = new TransactionLink();
        transactionLink.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        transactionLink.setExpiredAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        transactionLink.setId(1L);
        transactionLink.setInvoice(invoice);
        transactionLink.setInvoiceReference("HOMM_Dhsjahd");
        transactionLink.setLink("HOMM_Dhsjahd_03");
        transactionLink.setUsed(false);
        when(transactionLinkRepository.save(Mockito.<TransactionLink>any())).thenReturn(transactionLink);

        Invoice invoice2 = new Invoice();
        invoice2.setCreatedAt(LocalDate.of(2024, 4, 22).atStartOfDay());
        invoice2.setCustomerName("Enouch");
        invoice2.setId(1L);
        invoice2.setInvoiceReference("HOMM_Dhsjahdz");
        invoice2.setItems(new ArrayList<>());
        invoice2.setMerchantCode("HOMM");
        invoice2.setPaymentReference("4");
        invoice2.setStatus(Status.CREATED);
        invoice2.setTotalPrice(new BigDecimal("3000"));
        invoice2.setTransactionLink(new ArrayList<>());
        when(invoiceServiceImpl.fetchInvoiceByInvoiceReference(Mockito.<String>any())).thenReturn(invoice2);
        when(utils.generateTransactionLink(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("HOMM_eudhjfuuf_3");
        ApiResponse<TransactionLink> actualCreateTransactionLinkResult = transactionLinkServiceImpl
                .createTransactionLink("HOMM_eudhjfubv");
        verify(invoiceServiceImpl).fetchInvoiceByInvoiceReference(Mockito.<String>any());
        verify(utils).generateTransactionLink(Mockito.<String>any(), Mockito.<String>any());
        verify(transactionLinkRepository).save(Mockito.<TransactionLink>any());
        assertEquals("Invoice generated successfully", actualCreateTransactionLinkResult.getMessage());
        assertEquals(HttpStatus.CREATED, actualCreateTransactionLinkResult.getStatus());
        assertSame(transactionLink, actualCreateTransactionLinkResult.getData());
    }




    @Test
    void testFetchAll() {
        PageImpl<TransactionLink> pageImpl = new PageImpl<>(new ArrayList<>());
        when(transactionLinkRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ApiResponse<Page<TransactionLink>> actualFetchAllResult = transactionLinkServiceImpl.fetchAll(1, 3);
        verify(transactionLinkRepository).findAll(Mockito.<Pageable>any());
        assertEquals("data fetched successfully", actualFetchAllResult.getMessage());
        assertEquals(HttpStatus.OK, actualFetchAllResult.getStatus());
        Page<TransactionLink> data = actualFetchAllResult.getData();
        assertTrue(data.toList().isEmpty());
        assertSame(pageImpl, data);
    }



}
