package com.salvai.eldar.repositories;

import com.salvai.eldar.models.jpa.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByDni(Integer dni);
}
