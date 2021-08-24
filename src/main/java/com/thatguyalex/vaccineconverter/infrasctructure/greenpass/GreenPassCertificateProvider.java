package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import se.digg.dgc.signatures.CertificateProvider;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GreenPassCertificateProvider implements CertificateProvider {

    private List<GreenPassCert> certs;
    private CertificateFactory cf;

    private Pattern countryPattern = Pattern.compile("C=(.*?),");

    @SneakyThrows
    public GreenPassCertificateProvider() {
        var certificateDump = new String(this.getClass().getResourceAsStream("/credentials/greenpass_certs.json").readAllBytes());
        var certArray = new Gson().fromJson(certificateDump, GreenPassCert.RawGreenPassCert[].class);

        cf = CertificateFactory.getInstance("X.509");

        certs = Arrays.stream(certArray).map(this::certFromRaw).collect(Collectors.toList());
    }

    @SneakyThrows
    private GreenPassCert certFromRaw(GreenPassCert.RawGreenPassCert rawCert) {

        var cert = (X509Certificate) cf.generateCertificate(
                new ByteArrayInputStream(Base64.decodeBase64(rawCert.getKey()))
        );
        String country = null;

        var countryMatcher = countryPattern.matcher(cert.getSubjectX500Principal().getName());
        if (countryMatcher.find()) {
            country = countryMatcher.group(1);
        }

        return new GreenPassCert(
                rawCert.getKeyId(),
                rawCert.getSign(),
                country,
                cert
                );
    }

    @Override
    public List<X509Certificate> getCertificates(String country, byte[] kid) {
        return certs.stream().filter(it -> country.equals(it.getCountry())).map(GreenPassCert::getKey).collect(Collectors.toList());
    }
}
