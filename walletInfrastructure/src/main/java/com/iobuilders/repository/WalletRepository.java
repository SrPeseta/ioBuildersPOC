package com.iobuilders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iobuilders.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long>{

}
