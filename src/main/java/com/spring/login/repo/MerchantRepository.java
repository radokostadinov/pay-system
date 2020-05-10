package com.spring.login.repo;

import com.spring.login.repo.model.Client;
import com.spring.login.util.MerchantStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MerchantRepository extends CrudRepository<Client, Integer> {

    @Modifying
    @Query("UPDATE Merchant m SET m.status = :status where m.name = :name")
    void setMerchantStatus(@Param("status") String status, @Param("name") String name);

    @Query("SELECT m.status FROM Merchant m WHERE m.name = ?1")
    Optional<MerchantStatus> getMerchantStatus(@Param("name") String name);

    @Query(value = "SELECT Merchant.status FROM Merchant LEFT JOIN Purchases on Merchant.id = :uuid", nativeQuery = true)
    Optional<MerchantStatus> getMerchantStatusByProductUuid(@Param("uuid") String uuid);

}
