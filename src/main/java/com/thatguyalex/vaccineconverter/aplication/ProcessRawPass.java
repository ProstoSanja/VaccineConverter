package com.thatguyalex.vaccineconverter.aplication;

import com.thatguyalex.vaccineconverter.domain.model.GreenPass;
import com.thatguyalex.vaccineconverter.infrasctructure.apple.ApplePassProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
public class ProcessRawPass {

    private GreenPassRepository greenpassRepository;
    private GoogleLoyaltyRepository googleLoyaltyRepository;
    private ApplePassProvider applePassProvider;

    public GreenPass convertPdf(MultipartFile file) {
        var greenPass = greenpassRepository.parseGreenPass(file);
        applePassProvider.generateApplePass(greenPass);
        googleLoyaltyRepository.generatePassLink(greenPass);
        return greenPass;
    }

    public byte[] getApplePass(String id) {
        return applePassProvider.getApplePass(id);
    }
}
