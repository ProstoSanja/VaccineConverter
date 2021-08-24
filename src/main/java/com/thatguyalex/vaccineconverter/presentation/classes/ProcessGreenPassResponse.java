package com.thatguyalex.vaccineconverter.presentation.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProcessGreenPassResponse {

    private String firstName, lastName;
    private String dosesAdministered;
    private String dateOfPass, dateOfBirth;
    private String vaccineType;
    private String googlePayLink, applePayLink;

}
