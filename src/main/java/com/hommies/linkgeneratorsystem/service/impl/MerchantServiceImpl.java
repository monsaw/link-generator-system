package com.hommies.linkgeneratorsystem.service.impl;

import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.MerchantDTO;
import com.hommies.linkgeneratorsystem.dtos.OnboardMerchantRequest;
import com.hommies.linkgeneratorsystem.dtos.OnboardingResponse;
import com.hommies.linkgeneratorsystem.exceptions.CompanyAlreadyExistException;
import com.hommies.linkgeneratorsystem.exceptions.CompanyNotExistException;
import com.hommies.linkgeneratorsystem.model.Merchant;
import com.hommies.linkgeneratorsystem.repository.MerchantRepository;
import com.hommies.linkgeneratorsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    @Override
    public ApiResponse<OnboardingResponse> OnboardMerchant(OnboardMerchantRequest request) {

    log.info("Onboarding merchant request {}", request);
    var merchant = merchantRepository.findByMerchantCode(request.getMerchantCode().toUpperCase());
    if(merchant.isPresent()){
        throw new CompanyAlreadyExistException("Company Code Already Exist");

    }

    var newMerchant = Merchant.builder()
            .companyName(request.getCompanyName().toUpperCase())
            .firstName(request.getFirstName().toUpperCase())
            .lastName(request.getLastName().toUpperCase())
            .merchantCode(request.getMerchantCode().toUpperCase())
            .createdAt(LocalDateTime.now())
            .build();

   var response =  merchantRepository.save(newMerchant);
   var result = OnboardingResponse.builder()
                        .merchantCode(request.getMerchantCode())
                        .companyName(response.getCompanyName())
                   .build();
        log.info("merchant saved successfully");
    return new  ApiResponse<>(HttpStatus.CREATED, "Merchant Successfully Onboarded!", result);


    }

    @Override
    public ApiResponse<Page<Merchant>> fetchAllMerchants(int page, int size) {

        page = page == 0 ? 0 : page - 1;
        page = Math.max(page, 0);

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

        var allMerchants = merchantRepository.findAll(pageRequest);

        return new  ApiResponse<>(HttpStatus.OK, "Data fetched successfully!",allMerchants );

    }




    @Override
    public ApiResponse<MerchantDTO> fetchMerchantByCode(String code) {


        var merchant = merchantRepository.findByMerchantCode(code.toUpperCase());
        if(merchant.isEmpty()){
            throw new CompanyNotExistException("Company Code Does Not Exist");

        }

        return new  ApiResponse<>(HttpStatus.OK, "Merchant Successfully Fetched!",toDTO(merchant.get()) );

    }


    private MerchantDTO toDTO(Merchant merchant) {
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant,merchantDTO);
        return merchantDTO;
    }


}
