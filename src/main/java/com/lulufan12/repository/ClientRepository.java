package com.lulufan12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lulufan12.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
