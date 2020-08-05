package com.idosinchuk.codechallenge.backend.bank.infrastructure.adapter.repository;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

}
