package com.example.jwtcrud.vendors.domain.persistence;

import com.example.jwtcrud.vendors.domain.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("select v from Vendor v where v.email = ?1")
    Optional<Vendor> findByEmail(String email);

    Boolean existsByEmail(String email);
}
