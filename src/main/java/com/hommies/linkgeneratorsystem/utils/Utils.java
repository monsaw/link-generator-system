package com.hommies.linkgeneratorsystem.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String generateInvoiceReference(String merchantCode){

        return merchantCode.concat("_").concat(RandomStringUtils.randomAlphabetic(5));
    }

    public String generateTransactionLink(String merchantCode, String invoiceId){

        return merchantCode.concat("_").concat(RandomStringUtils.randomAlphabetic(7).concat("_").concat(invoiceId));
    }

}
