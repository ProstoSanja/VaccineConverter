package com.thatguyalex.vaccineconverter.presentation;

import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalController {

    @Autowired
    GoogleLoyaltyRepository googleLoyaltyRepository;

    @GetMapping("/test")
    public void test() {
        googleLoyaltyRepository.updateLoyaltyClass();
    }

}
