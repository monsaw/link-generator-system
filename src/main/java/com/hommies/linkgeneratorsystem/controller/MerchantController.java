package com.hommies.linkgeneratorsystem.controller;


import com.hommies.linkgeneratorsystem.dtos.ApiResponse;
import com.hommies.linkgeneratorsystem.dtos.MerchantDTO;
import com.hommies.linkgeneratorsystem.dtos.OnboardMerchantRequest;
import com.hommies.linkgeneratorsystem.model.Merchant;
import com.hommies.linkgeneratorsystem.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("merchant")
public class MerchantController {

    private final MerchantService merchantService;


    @PostMapping("/onboard")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<String>> onboardMerchant(@RequestBody @Valid OnboardMerchantRequest request){
        return new ResponseEntity<>(merchantService.OnboardMerchant(request),HttpStatus.CREATED);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Page<Merchant>>> fetchAllMerchants(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                                                                         @RequestParam(value = "size", required = false,defaultValue = "50") int pageSize
                                                                         ){
        return new ResponseEntity<>(merchantService.fetchAllMerchants(pageNumber,pageSize),HttpStatus.OK);

    }

    @GetMapping("/{merchantCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<MerchantDTO>> fetchMerchantByCode(
            @PathVariable String merchantCode){
        return new ResponseEntity<>(merchantService.fetchMerchantByCode(merchantCode),HttpStatus.OK);

    }

}
