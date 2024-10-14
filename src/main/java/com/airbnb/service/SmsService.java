package com.airbnb.service;

import com.airbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    public void sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),   // Recipient's phone number
                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),  // Your Twilio number
                messageBody
        ).create();

        System.out.println("SMS sent successfully with SID: " + message.getSid());
    }
}
