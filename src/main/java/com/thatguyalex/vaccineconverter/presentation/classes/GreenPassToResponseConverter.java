package com.thatguyalex.vaccineconverter.presentation.classes;

import com.thatguyalex.vaccineconverter.domain.model.GreenPass;
import com.thatguyalex.vaccineconverter.presentation.utils.ProcessGreenPassResponse;

public class GreenPassToResponseConverter {

    public static ProcessGreenPassResponse fromGreenPass(GreenPass greenPass) {
        return ProcessGreenPassResponse.builder()
                .dateOfPass(greenPass.getDateOfPass())
                .dosesAdministered(greenPass.getDosesGiven() + "/" + greenPass.getDosesNeeded())
                .firstName(greenPass.getFirstName())
                .lastName(greenPass.getLastName())
                .vaccineType(greenPass.getVaccineType())
                .googlePayLink(greenPass.getGooglePayLink())
                .build();
    }

}
