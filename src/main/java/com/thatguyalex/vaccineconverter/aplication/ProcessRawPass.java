package com.thatguyalex.vaccineconverter.aplication;

import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
public class ProcessRawPass {

    private GreenPassRepository greenpassRepository;
    private GoogleLoyaltyRepository googleLoyaltyRepository;

    public String convertPdf(MultipartFile file) {
        var greenPass = greenpassRepository.parseGreenPass(file);

        return googleLoyaltyRepository.generatePassLink(greenPass);
    }
}
