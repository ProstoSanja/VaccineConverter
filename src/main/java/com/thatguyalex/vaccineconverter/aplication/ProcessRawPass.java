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
        var greenPass = greenpassRepository.parseGreenPassFromPdf(file);
        generatePasses(greenPass);
        return greenPass;
    }

    public GreenPass convertImage(MultipartFile file) {
        var greenPass = greenpassRepository.parseGreenPassFromImage(file);
        generatePasses(greenPass);
        return greenPass;
    }

    private void generatePasses(GreenPass greenPass) {
        applePassProvider.generateApplePass(greenPass);
        googleLoyaltyRepository.generatePassLink(greenPass);
    }

    public byte[] getApplePass(String id) {
        return applePassProvider.getApplePass(id);
    }
}
