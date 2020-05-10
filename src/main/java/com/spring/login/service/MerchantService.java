package com.spring.login.service;

import com.spring.login.repo.MerchantRepository;
import com.spring.login.repo.model.Client;
import com.spring.login.util.MerchantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    public MerchantStatus getMerchantStatus(String name) {

        MerchantStatus merchantStatus = merchantRepository.getMerchantStatus(name).get();

        if (merchantStatus != null) {
            return merchantStatus;
        } else {
            throw new UsernameNotFoundException("Merchant not found! ");
        }
    }

    public MerchantStatus getMerchantStatusByProductUuid(String uuid) {

        MerchantStatus merchantStatus = merchantRepository.getMerchantStatusByProductUuid(uuid).get();

        if (merchantStatus != null) {
            return merchantStatus;
        } else {
            throw new UsernameNotFoundException("Merchant not found! ");
        }
    }
}
