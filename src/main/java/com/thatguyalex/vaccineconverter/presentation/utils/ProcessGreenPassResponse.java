package com.thatguyalex.vaccineconverter.presentation.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ProcessGreenPassResponse {

    private String firstName, lastName;
    private String dosesAdministered;
    private String dateOfPass;
    private String vaccineType;
    private String googlePayLink, applePayLink;

}
