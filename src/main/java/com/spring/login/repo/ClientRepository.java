package com.spring.login.repo;

import com.spring.login.repo.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    @Query("SELECT c FROM Client c WHERE c.username = ?1")
    Optional<Client> findClientByName(@Param("username") String username);
}
