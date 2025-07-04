package com.wk.repo;

import com.wk.domain.CasaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasaAccountRepository extends JpaRepository<String, CasaAccount> {

    List<CasaAccount> getAllCasaByUserId(Integer userId);
}
