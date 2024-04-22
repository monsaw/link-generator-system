package com.hommies.linkgeneratorsystem.service;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.MerchantDTO;
import com.hommies.linkgeneratorsystem.dtos.OnboardMerchantRequest;
import com.hommies.linkgeneratorsystem.dtos.OnboardingResponse;
import com.hommies.linkgeneratorsystem.model.Merchant;
import org.springframework.data.domain.Page;


public interface MerchantService{
    ApiResponse<OnboardingResponse> OnboardMerchant(OnboardMerchantRequest request);

    ApiResponse<Page<Merchant>> fetchAllMerchants(int page, int size);

    ApiResponse<MerchantDTO> fetchMerchantByCode(String code);

}
