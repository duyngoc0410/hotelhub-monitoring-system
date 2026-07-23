package com.hotelhub.backend.address.repository;

import com.hotelhub.backend.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    /**
     * Lấy toàn bộ địa chỉ của một User.
     */
    List<Address> findByUserId(UUID userId);

    /**
     * Lấy địa chỉ mặc định của User.
     */
    Optional<Address> findByUserIdAndIsDefaultTrue(UUID userId);

    /**
     * Kiểm tra Address có thuộc User hay không.
     */
    boolean existsByIdAndUserId(UUID addressId, UUID userId);

}
