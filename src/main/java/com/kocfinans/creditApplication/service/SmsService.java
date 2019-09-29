package com.kocfinans.creditApplication.service;

import com.kocfinans.creditApplication.constant.SmsConstant;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String body, String to, String from) {
        Twilio.init(SmsConstant.ACCOUNT_SID, SmsConstant.AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(to), new PhoneNumber(from), body).create();
    }
}
