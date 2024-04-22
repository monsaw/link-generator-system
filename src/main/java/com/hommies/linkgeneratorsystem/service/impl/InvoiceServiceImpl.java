package com.hommies.linkgeneratorsystem.service.impl;


import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.InvoiceDTO;
import com.hommies.linkgeneratorsystem.dtos.InvoiceRequest;
import com.hommies.linkgeneratorsystem.dtos.InvoiceResponse;
import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.exceptions.CompanyNotExistException;
import com.hommies.linkgeneratorsystem.exceptions.InvoiceNotExistException;
import com.hommies.linkgeneratorsystem.model.Invoice;
import com.hommies.linkgeneratorsystem.model.InvoiceItems;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import com.hommies.linkgeneratorsystem.repository.InvoiceItemsRepository;
import com.hommies.linkgeneratorsystem.repository.InvoiceRepository;
import com.hommies.linkgeneratorsystem.repository.MerchantRepository;
import com.hommies.linkgeneratorsystem.repository.TransactionLinkRepository;
import com.hommies.linkgeneratorsystem.service.InvoiceService;
import com.hommies.linkgeneratorsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl  implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemsRepository invoiceItemsRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionLinkRepository transactionLinkRepository;
    private final Utils utils;

    @Override
    public ApiResponse<InvoiceResponse> generateInvoice(InvoiceRequest request) {
       log.info("generate invoice request ...{}",request );
        var merchant = merchantRepository.findByMerchantCode(request.getMerchantCode().toUpperCase());
        if(merchant.isEmpty()){
            throw new CompanyNotExistException("Invoice cannot be generated because company code does not exist");
        }

        var items = request.getItems();
        double totalPrice = 0.0;
            for(var item : items){
                totalPrice += item.getQuantity() * Double.parseDouble(item.getUnitPrice());

            }


        var invoice = Invoice.builder()
                .merchantCode(request.getMerchantCode().toUpperCase())
                .customerName(request.getCustomerName().toUpperCase())
//                .items(request.getItems())
                .invoiceReference(utils.generateInvoiceReference(request.getMerchantCode()))
                .createdAt(LocalDateTime.now())
                 .totalPrice(new BigDecimal(totalPrice))
                .status(Status.CREATED)
                .build();

            var response =  invoiceRepository.save(invoice);
            saveInvoiceItems(invoice, items);
            response.setTransactionLink(new LinkedList<>(Arrays.asList(
                    TransactionLink.builder()
                            .invoice(response)
                            .link(utils.generateTransactionLink(request.getMerchantCode().toUpperCase(), String.valueOf(response.getId())))
                            .invoiceReference(response.getInvoiceReference())
                            .expiredAt(LocalDateTime.now().plusMinutes(5))
                            .createdAt(LocalDateTime.now())
                            .used(false)
                            .build())));

            response = invoiceRepository.save(response);

            var invoiceResponse = InvoiceResponse.builder()
                            .totalAmount(response.getTotalPrice())
                            .invoiceStatus(response.getStatus())
                            .transactionLink(response.getTransactionLink().get(0).getLink())
                            .invoiceReference(response.getInvoiceReference())
                            .build();


            log.info(" invoice generated successfully" );
          return new ApiResponse<>(HttpStatus.CREATED, "Invoice generated successfully",invoiceResponse );



    }

    @Override
    public ApiResponse<InvoiceDTO> fetchInvoiceByReference(String invoiceReference) {
        InvoiceDTO invoiceDTO  = new InvoiceDTO();
        var invoice = invoiceRepository.findByInvoiceReference(invoiceReference);
        if(invoice.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist");
        }
        BeanUtils.copyProperties(invoice.get(), invoiceDTO);

        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully", invoiceDTO);
    }

    @Override
    public Invoice fetchInvoiceByInvoiceReference(String invoiceReference) {

        var invoice = invoiceRepository.findByInvoiceReference(invoiceReference);
        if(invoice.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist");
        }

        return invoice.get();
    }

    @Override
    public ApiResponse<Page<Invoice>> fetchAllInvoicesPerMerchant(String merchantCode, int page, int size) {

        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());


        var invoice = invoiceRepository.findAllByMerchantCode(merchantCode, pageRequest);
        if(invoice.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist for this merchant");
        }


        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully", invoice);


    }

    @Override
    public ApiResponse<Page<Invoice>> fetchAllInvoices(int page, int size) {

        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());


        var invoice = invoiceRepository.findAll(pageRequest);
        if(invoice.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist for this merchant");
        }


        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully", invoice);


    }

    @Override
    public ApiResponse<InvoiceDTO> fetchInvoiceByTransactionLink(String transactionLink) {
        InvoiceDTO invoiceDTO  = new InvoiceDTO();
        var link = transactionLinkRepository.findByLink(transactionLink);
        if(link.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist");
        }
        BeanUtils.copyProperties(link.get().getInvoice(),invoiceDTO);
        return new ApiResponse<>(HttpStatus.OK, "data fetched successfully", invoiceDTO);

    }

    @Override
    public Invoice fetchInvoiceByInvoiceTransactionLink(String transactionLink) {

        var link = transactionLinkRepository.findByLink(transactionLink);
        if(link.isEmpty()){
            throw new InvoiceNotExistException("Transaction Link does not exist");
        }

        return link.get().getInvoice();

    }

    @Override
    public Invoice updateInvoice(String invoiceReference, Status status, String paymentReference) {

        var invoice = invoiceRepository.findByInvoiceReference(invoiceReference);
        if(invoice.isEmpty()){
            throw new InvoiceNotExistException("Invoice does not exist!!");
        }
        invoice.get().setStatus(status);
        invoice.get().setPaymentReference(paymentReference);
        Invoice updatedInvoice = invoiceRepository.save(invoice.get());

        log.trace("Invoice updated successfully");
        return updatedInvoice;
    }

    private void saveInvoiceItems(Invoice invoice, List< InvoiceItems> items){

        items.stream()
                .forEach(invoiceItem -> {
                    InvoiceItems invoiceItems = new InvoiceItems();
                    invoiceItems.setItemName(invoiceItem.getItemName());
                    invoiceItems.setQuantity(invoiceItem.getQuantity());
                    invoiceItems.setUnitPrice(invoiceItem.getUnitPrice());
                    invoiceItems.setInvoice(invoice);
                    invoiceItemsRepository.save(invoiceItems);
                } );

    }


}
