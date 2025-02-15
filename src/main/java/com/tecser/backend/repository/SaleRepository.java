package com.tecser.backend.repository;

import com.tecser.backend.model.Sale;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
