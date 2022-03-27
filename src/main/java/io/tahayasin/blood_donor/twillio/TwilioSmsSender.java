package io.tahayasin.blood_donor.twillio;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(SmsRequestDto smsRequestDto) {
        if (isPhoneNumberValid(smsRequestDto.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber("whatsapp:"+ smsRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber("whatsapp:+14155238886"/*+twilioConfiguration.getTrialNumber()*/);
            String message = smsRequestDto.getMessage();
            MessageCreator creator = Message.creator(to, from, message); //.setStatusCallback(URI.create("https://enumzit0aiq6b.x.pipedream.net"));
            creator.create();
            LOGGER.info("Send sms {}", smsRequestDto);
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequestDto.getPhoneNumber() + "] is not a valid number"
            );
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }
}

