package com.wk.controller;

import com.wk.mapper.CasaMapper;
import com.wk.model.CasaAccountDto;
import com.wk.service.CasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("accounts")
public class CasaController {

    private final CasaService casaService;
    private final CasaMapper casaMapper;

    // todo change to pagination if required.
    @GetMapping("/casa")
    public List<CasaAccountDto> getAllCasaByUserId() {
        // todo get from security context
        return casaService.getAllByUserId(1)
                .stream()
                .map(casaMapper::toDto)
                .toList();


    }

}
