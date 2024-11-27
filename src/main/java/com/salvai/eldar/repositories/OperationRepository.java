package com.salvai.eldar.repositories;

import com.salvai.eldar.models.jpa.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<OperationEntity, Integer> {
}
