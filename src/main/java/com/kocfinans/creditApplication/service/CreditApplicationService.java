package com.kocfinans.creditApplication.service;

import com.kocfinans.creditApplication.constant.LimitConstant;
import com.kocfinans.creditApplication.constant.MonthlyIncomeConstant;
import com.kocfinans.creditApplication.constant.ScoreConstant;
import com.kocfinans.creditApplication.constant.SmsConstant;
import com.kocfinans.creditApplication.dto.CreditApplicationDto;
import com.kocfinans.creditApplication.dto.CreditStatusDto;
import com.kocfinans.creditApplication.entity.CreditApplication;
import com.kocfinans.creditApplication.enums.CreditStatus;
import com.kocfinans.creditApplication.repository.CreditApplicationRepository;
import com.kocfinans.creditApplication.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditApplicationService {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CreditApplicationRepository repository;


    @Autowired
    private DummyCreditScoreService creditScoreService;

    @Autowired
    private SmsService smsService;

    public CreditStatusDto apply(CreditApplicationDto dto) {
        long score = creditScoreService.getCustomerScoreByTcNo(dto.getTcNo());
        long limitAmount = 0;
        CreditStatus status;
        CreditApplication application = modelMapper.map(dto, CreditApplication.class);

        if (score < ScoreConstant.minScore) {
            status = CreditStatus.REFUSE;
        } else if (score >= ScoreConstant.minScore && score < ScoreConstant.maxScore && application.getMonthlyIncome() < MonthlyIncomeConstant.minMonthlyIncome) {
            status = CreditStatus.APPROVE;
            limitAmount = LimitConstant.minLimit;
        } else if(score>=ScoreConstant.maxScore){
            status = CreditStatus.APPROVE;
            limitAmount = application.getMonthlyIncome() * LimitConstant.defaultCreditMultiple;
        }else{
            status = CreditStatus.REFUSE;
        }

        application.setCreditStatus(status);
        application.setLimitAmount(limitAmount);
        repository.save(application);

        StringBuilder body = new StringBuilder("Kredi başvurunuz ");
        body.append(status.getName() + " edilmiştir. ");
        if (status != CreditStatus.REFUSE) {
            body.append("Başvurunuz için " + limitAmount + " tl tutarında limit tahsis edilmiştir.");
        }

        smsService.sendSms(body.toString(), dto.getTelNo(), SmsConstant.FROM_NUMBER);

        CreditStatusDto statusDto = new CreditStatusDto();
        statusDto.setStatus(status.getName());
        if (status != CreditStatus.REFUSE) {
            statusDto.setLimitAmount(limitAmount);
        }

        return statusDto;
    }

    public void validateApplication(CreditApplicationDto dto) {
        validateTcNo(dto.getTcNo());
        StringUtil.checkEmptyString(dto.getName(),"Ad");
        StringUtil.checkEmptyString(dto.getLastName(),"Soyad");
        validateTelNo(dto.getTelNo());
        if (dto.getMonthlyIncome() == 0){
            throw  new IllegalArgumentException("Aylık Gelir bilgisi girilmelidir.");
        }
    }

    private void validateTcNo(String tcNo) {
        StringUtil.checkEmptyString(tcNo,"TC Kimlik numarası");
        if (tcNo.trim().length() != 11){
            throw  new IllegalArgumentException("TC Kimlik numarası bilgisi 11 karakter olmalıdır.");
        }
        // Diğer TC kimlik validasyonları
    }

    private void validateTelNo(String telNo) {
        StringUtil.checkEmptyString(telNo,"Telefon numarası bilgisi");
        // Diğer telefon numarasıvalidasyonları
    }
}
