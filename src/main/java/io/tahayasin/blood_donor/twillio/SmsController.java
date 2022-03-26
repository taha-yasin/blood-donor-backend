package io.tahayasin.blood_donor.twillio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/twillio")
public class SmsController {

    private final Service service;

    @Autowired
    public SmsController(Service service) {
        this.service = service;
    }

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequestDto smsRequestDto) {
        service.sendSms(smsRequestDto);
    }
}

