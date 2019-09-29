package com.kocfinans.creditApplication.enums.attributeconverter;

import com.kocfinans.creditApplication.enums.CreditStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class CreditStatusConverter implements AttributeConverter<CreditStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CreditStatus creditStatus) {
        return creditStatus.getId();
    }

    @Override
    public CreditStatus convertToEntityAttribute(Integer id) {
        return Arrays.stream(CreditStatus.values()).filter(value -> value.getId() == id).findFirst().orElse(null);
    }
}
