package com.wk.controller;

import com.wk.mapper.CasaMapper;
import com.wk.model.CasaAccountDto;
import com.wk.service.CasaService;
import com.wk.starter.model.CurrentUserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("accounts")
@Slf4j
public class CasaController {

    private final CasaService casaService;
    private final CasaMapper casaMapper;
    private final CurrentUserDto currentUserDto;

    // todo change to pagination if required.
    @GetMapping("/casa")
    public List<CasaAccountDto> getAllCasaByUserId() {
        log.info("CurrentUserDto:{}", currentUserDto);
        return casaService.getAllByUserId(currentUserDto.getUserId())
            .stream()
            .map(casaMapper::toDto)
            .toList();

    }

}
