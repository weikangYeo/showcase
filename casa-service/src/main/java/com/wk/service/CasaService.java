package com.wk.service;

import com.wk.domain.CasaAccount;
import com.wk.repo.CasaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CasaService {

    private final CasaAccountRepository repository;

    public List<CasaAccount> getAllByUserId(Integer userId) {
        return repository.getAllCasaByUserId(userId);
    }
}
