package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Data
@AllArgsConstructor
public class GreenPassCert {

    private String keyId, country;
    private PublicKey key;

    @Data
    @AllArgsConstructor
    public class RawGreenPassCert {

        private String key, keyId, sign;

    }

    @Data
    @AllArgsConstructor
    public class RawNhsPassCert {

        private String kid, publicKey;

    }

}
