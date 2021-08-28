package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor
public class GreenPassIssuerProvider {

    private Map<String, String> issuers = new HashMap<>() {{
        put("TIS", "Estonian Health Information System");
    }};

    public String getCorrectedIssuerName(String defaultIssuer) {
        var result = issuers.get(defaultIssuer);
        if (result == null) {
            return defaultIssuer;
        }
        return result;
    }

}
