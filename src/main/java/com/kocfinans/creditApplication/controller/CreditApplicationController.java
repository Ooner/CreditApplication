package com.kocfinans.creditApplication.controller;

import com.kocfinans.creditApplication.dto.CreditApplicationDto;
import com.kocfinans.creditApplication.dto.CreditStatusDto;
import com.kocfinans.creditApplication.service.CreditApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("credit")
public class CreditApplicationController {

    @Autowired
    public CreditApplicationService service;

    @PostMapping("/application")
    public CreditStatusDto createApplicattion(@RequestBody CreditApplicationDto dto){
        service.validateApplication(dto);
        CreditStatusDto statusDto = service.apply(dto);
        return statusDto;
    }
}
