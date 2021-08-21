package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.security.cert.X509Certificate;

@Data
@AllArgsConstructor
public class GreenPassCert {

    private String keyId, sign;
    private X509Certificate key;

    @Data
    @AllArgsConstructor
    public class RawGreenPassCert {

        private String key, keyId, sign;

    }

}
