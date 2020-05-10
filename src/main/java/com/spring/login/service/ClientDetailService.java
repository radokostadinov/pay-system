package com.spring.login.service;

import com.spring.login.repo.ClientRepository;
import com.spring.login.repo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailService {

    @Autowired
    ClientRepository clientRepository;

    public Client loadClientByName(String name) throws UsernameNotFoundException {

        Client client = clientRepository.findClientByName(name).get();

        if (client != null) {
            return client;
        } else {
            throw new UsernameNotFoundException("Client not found! ");
        }
    }
}
